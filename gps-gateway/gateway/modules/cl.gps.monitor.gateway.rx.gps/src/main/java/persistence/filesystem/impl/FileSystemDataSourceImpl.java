package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.filesystem.impl;

import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.archiva.ArchivaGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.filesystem.FileSystemDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.net.URI;

import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

@Getter
@Setter
public class FileSystemDataSourceImpl implements FileSystemDataSource {

    private File defWorkDir;

    private ArchivaGateway archivaGateway;

    private static final int CHUNK_SIZE = 2 * 1024;

    private static final int RETRY_COUNT = 5;

    protected static final Properties appProperties = UtilProperties.getProperties();

    public static final UtilLogger log = UtilLogger.getLogger(FileSystemDataSourceImpl.class);

    @Override
    public void configure() {
        try {
            defWorkDir =
                    Paths.get(appProperties.getProperty(
                            UtilProperties.FIRMWARE_DEFAULT_WORK_DIRECTORY_PROPERTY_KEY)).toFile();
            if (!defWorkDir.exists()) {
                Files.createDirectory(Paths.get(defWorkDir.getAbsolutePath()));
            }
        } catch (Exception e) {
            log.error(UtilMessage.FOLDER_NOT_CREATED, defWorkDir.getAbsolutePath());
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void open() {
        try {
            if (!test()) {
                Files.createDirectory(Paths.get(defWorkDir.getAbsolutePath()));
            }
        } catch (Exception e) {
            log.error(UtilMessage.FOLDER_NOT_CREATED, defWorkDir.getAbsolutePath());
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        if (!test()) {
            log.warning(UtilMessage.FOLDER_NOT_CREATED, defWorkDir.getAbsolutePath());
        }
    }

    @Override
    public boolean test() {
        return defWorkDir.exists();
    }

    @Override
    public boolean create(Path toPath) throws IOException {
        boolean isCreated = false;
        //create file
        File file = Paths.get(toPath.toAbsolutePath().toString()).toFile();
        if (file.exists()) {
            FileUtils.forceDelete(file);
        }
        FileUtils.forceMkdirParent(file);
        FileUtils.touch(file);

        isCreated = true;

        log.info(String.format("Se crea el archivo [%s]", file.getAbsolutePath()));

        return isCreated;
    }

    @Override
    public boolean copy(URI fromPath, Path toPath) throws IOException {
        boolean isStore = false;
        try {
            int retryCount = 0;
            File file = Paths.get(toPath.toAbsolutePath().toString()).toFile();
            //download file
            long firmwareLength = archivaGateway.getFirmwareLength(fromPath);
            while (retryCount < RETRY_COUNT) {
                if (firmwareLength != 0) {
                    HttpGet httpGet = new HttpGet(fromPath);
                    //
                    try (CloseableHttpResponse httpResponse = archivaGateway.getHttpClient().execute(httpGet, archivaGateway.getHttpClientContext())) {
                        //
                        if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
                            HttpEntity httpEntity = httpResponse.getEntity();
                            //
                            try (InputStream in = httpEntity.getContent()) {
                                try (OutputStream out = new FileOutputStream(toPath.toFile())) {
                                    log.info("Descargando el archivo [%s] - [%s] bytes de [%s] bytes totales", file.getName(), file.length(), firmwareLength);
                                    write(in, out); // write file
                                    if (file.length() == firmwareLength) {
                                        isStore = true; // it's ok

                                        log.info("Se descargo el archivo [%s] correctamente - [%s] bytes de [%s] bytes totales", file.getName(), file.length(), firmwareLength);
                                        break;
                                    } else {
                                        throw new IOException(String.format("El archivo esta [%s] no se descargo correctamete puede estar corrupto solo se descargaron [%s] bytes", file.getName(), file.length()));
                                    }
                                }
                            }
                        } else {
                            throw new IOException(String.format("No se puede encontrar el recurso [%s] HTTPStatus[%s][%s]", fromPath, httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase()));
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        log.info("Reintentando la descarga del archivo [%s]", ++retryCount);
                    }
                } else {
                    throw new IllegalStateException(String.format("El archivo [%s] no esta integro solo tiene [%s] bytes", fromPath, firmwareLength));
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return isStore;
    }

    @Override
    public boolean write(InputStream in, OutputStream out) throws IOException {
        boolean isWrite = true;
        //
        int inByte;
        byte[] data = new byte[CHUNK_SIZE];
        long download = 0;
        long count = 0;
        while ((inByte = in.read(data)) != -1) {
            if (inByte != CHUNK_SIZE) {
                byte[] smallerData = Arrays.copyOf(data, inByte);
                out.write(smallerData);
            } else {
                out.write(data);
            }
            download = +inByte;
            count++;
            out.flush();
            //
            log.debug(String.format("paquete [%s] [%s] bytes ledidos de [%s] bytes descargados...", count, inByte, download));
        }
        return isWrite;
    }


    @Override
    public File get(Path path) throws IOException {
        return Paths.get(path.toString()).toFile();
    }

    @Override
    public void uncompress(File in, File out) throws IOException {
        if (in.exists()) {
            log.info("Descomprimiendo archivo [%s]", in.getName());
            try {
                try (FileInputStream fileInputStream = new FileInputStream(in)) {
                    try (GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream)) {
                        try (TarArchiveInputStream fin = new TarArchiveInputStream(gzipInputStream)) {
                            TarArchiveEntry entry;
                            while ((entry = fin.getNextTarEntry()) != null) {
                                log.error(entry.getName());

                                if (entry.isDirectory()) {
                                    continue;
                                }
                                File curfile = new File(out, entry.getName());
                                File parent = curfile.getParentFile();
                                if (!parent.exists()) {
                                    parent.mkdirs();
                                }
                                IOUtils.copy(fin, new FileOutputStream(curfile));
                            }
                            log.info("Descompresion Status[OK]");
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw e;
            }
        } else {
            throw new FileNotFoundException(String.format("El archivo [%s] no existe", in.getAbsolutePath()));
        }
    }

    @Override
    public Path find(Path dirPath, String fileName) throws IOException {
        Path filePath = null;
        try (Stream<Path> paths = Files.list(Paths.get(dirPath.toString()))) {
            filePath =
                    paths.filter(Files::isRegularFile)
                            .filter(file -> file.getFileName().toString().equals(fileName)).findFirst().orElse(null);
        }
        return filePath;
    }

    @Override
    public List<Path> findStartsWith(Path dirPath, String startsWith) throws IOException {
        List<Path> filePaths = new ArrayList<>();
        try (Stream<Path> paths = Files.list(Paths.get(dirPath.toString()))) {
            filePaths =
                    paths.filter(Files::isRegularFile)
                            .filter(fileName -> fileName.getFileName().toString().startsWith(startsWith))
                            .collect(Collectors.toList());
        }
        return filePaths;
    }

    @Override
    public List<Path> findEndsWith(Path dirPath, String endsWith) throws IOException {
        List<Path> filePaths = new ArrayList<>();
        try (Stream<Path> paths = Files.list(Paths.get(dirPath.toString()))) {
            filePaths =
                    paths.filter(Files::isRegularFile)
                            .filter(fileName -> fileName.getFileName().toString().startsWith(endsWith))
                            .collect(Collectors.toList());
        }
        return filePaths;
    }

    @Override
    public boolean remove(Path path) throws IOException {
        boolean isRemoved = true;
        FileUtils.forceDelete(path.toFile());
        return isRemoved;
    }

    @Override
    public boolean removeAll() throws IOException {
        boolean isRemoved = true;
        FileUtils.forceDelete(defWorkDir);
        return isRemoved;
    }

    @Override
    public String createMd5(Path filePath) throws IOException {
        String md5 = null;
        if(filePath.toFile().exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(filePath.toAbsolutePath().toString())) {
                md5 = DigestUtils.md5Hex(fileInputStream);

            } catch (Exception e) {
                throw new IOException(
                        String.format("Se produjo un error al extraer el MD5 del archivo [%s]", filePath.toAbsolutePath().toString()), e);
            }
        }else{
            throw new FileNotFoundException(String.format("El archivo [%s] no existe", filePath.toAbsolutePath()));
        }
        return md5;
    }

    @Override
    public boolean checkSumMd5(String md5, String md5toCompare) throws IOException {
        boolean isValid = false;
            if(Objects.nonNull(md5) && !md5.isEmpty()){
               isValid = StringUtils.equals(md5, md5toCompare);
            }
        return isValid;
    }

    @Override
    public String getMd5(Path file) throws IOException {
        String md5 = null;
        try {
            String line =
                Files.readAllLines(file).stream().findFirst().orElse(null);
            if(Objects.nonNull(line)){
                String[] contain = line.split("  "); // regex space
                md5 = contain[0];
            }else{
                throw new IOException(
                        String.format("Se ha producido un error al obtener el string md5 del archivo [%s]", file.toAbsolutePath().toString()));
            }

        }catch (Exception e){
            throw  new IOException(
                    String.format("Se ha producido un problema al obtener el md5 del archivo [%s]", file.toAbsolutePath().toString()));
        }
        return md5;
    }


    @Override
    public String getDefaultWorkDirectory() {
        return defWorkDir.getAbsolutePath();
    }

}
