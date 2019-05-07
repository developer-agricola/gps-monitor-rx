package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.filesystem;

import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.DataSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

public interface FileSystemDataSource extends DataSource {

    boolean create(Path toPath) throws IOException;

    boolean copy(URI fromPath, Path toFilePath) throws IOException;

    boolean write(InputStream in, OutputStream out) throws IOException;

    File get(Path path) throws IOException;

    void uncompress(File in, File out) throws IOException;

    Path find(Path dirPath, String fileName) throws IOException;

    List<Path> findStartsWith(Path dirPath, String startsWith) throws  IOException;

    List<Path> findEndsWith(Path dirPath, String endsWith) throws  IOException;

    boolean remove(Path path) throws  IOException;

    boolean removeAll() throws  IOException;

    String createMd5(Path file) throws IOException;

    boolean checkSumMd5(String md5, String md5toCompare) throws IOException;

    String getMd5(Path file) throws IOException;

    String getDefaultWorkDirectory();

}
