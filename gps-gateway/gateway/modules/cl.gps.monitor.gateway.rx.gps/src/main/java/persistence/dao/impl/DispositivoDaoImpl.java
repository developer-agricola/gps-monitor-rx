package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.dao.impl;

import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.DispositivoDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.FirmwareDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.PosicionDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.FirmwareStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.exceptions.DataAccessException;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.archiva.ArchivaGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.dao.DispositivoDao;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.derby.DerbyDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.filesystem.FileSystemDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.sqlite.SqLiteDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import lombok.Getter;
import lombok.Setter;
import org.knowm.yank.Yank;
import org.knowm.yank.exceptions.YankSQLException;

import java.io.File;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Created by daniel.carvajal on 20-06-2018.
 */
@Getter
@Setter
public class DispositivoDaoImpl implements DispositivoDao {

    private SqLiteDataSource sqLiteDataSource;

    private DerbyDataSource derbyDataSource;

    private FileSystemDataSource fileSystemDataSource;

    private ArchivaGateway archivaGateway;

    public static final UtilLogger log = UtilLogger.getLogger(DispositivoDaoImpl.class);

    @Override
    public DispositivoDTO findDispositivoPorDefecto() throws DataAccessException {
        DispositivoDTO dispositivoDTO = null;
        try {
            String query = new StringBuilder()
                    .append("SELECT   ")
                    .append("   DISPOSITIVO_ID,  ")
                    .append("   NOMBRE_DISPOSITIVO, ")
                    .append("   TIPO_ID,  ")
                    .append("   ESTADO_ID ")
                    .append("FROM DISPOSITIVO ")
                    .append("ORDER BY DISPOSITIVO_ID DESC LIMIT 1")
                    .toString();

            dispositivoDTO = Yank.queryBean(sqLiteDataSource.getPoolName(), query, DispositivoDTO.class, new Object[]{});
            if (Objects.nonNull(dispositivoDTO) && Objects.nonNull(dispositivoDTO.getDispositivoId())) {
                log.debug("Se ha encontrado el dispositivo Id[%s][%s]", dispositivoDTO.getDispositivoId(), dispositivoDTO.getNombreDispositivo());

            } else {
                log.debug("No se ha encontrado el dipositivo por defecto...");
            }

        } catch (YankSQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return dispositivoDTO;
    }

    @Override
    public DispositivoDTO saveDispositivo(DispositivoDTO dispositivoDto) throws DataAccessException {
        long idDispositivo = 0;
        try {
            Object[] params = {
                    null, //DISPOSITIVO_ID
                    dispositivoDto.getNombreDispositivo(), //NOMBRE_DISPOSITIVO
                    dispositivoDto.getTipoId(), //TIPO_ID
                    dispositivoDto.getEstadoId(), //ESTADO_ID
            };

            idDispositivo = Yank.insert(sqLiteDataSource.getPoolName(), "INSERT INTO DISPOSITIVO VALUES(?, ?, ?, ?)", params);
            if (idDispositivo > 0) {
                dispositivoDto.setDispositivoId(idDispositivo);

                log.debug("Se a guardado el dispositivo [%s] correctamente", dispositivoDto.getNombreDispositivo());
            } else {
                throw new DataAccessException(String.format("No se ha guardado correctamente el dispositivo [%s]", dispositivoDto.getNombreDispositivo()));
            }

        } catch (YankSQLException e) {
            throw new DataAccessException(e.getMessage(), e);

        }
        return dispositivoDto;
    }

    @Override
    public FirmwareDTO saveFirmware(FirmwareDTO firmwareDto) throws DataAccessException {
        //
        try {
            Object[] params = {
                    firmwareDto.getDifusionId(), //FIRMWARE_ID
                    firmwareDto.getNombreFirmware(), //NOMBRE_FIRMWARE
                    firmwareDto.getDifusionId(), //DIFUSION_ID
                    firmwareDto.getEquipoId(), //EQUIPO_ID
                    firmwareDto.getGroupId(), //GROUP_ID
                    firmwareDto.getArtifactId(), //ARTIFACT_ID
                    firmwareDto.getVersion(), //VERSION
                    firmwareDto.getPackageId(), //PACKAGE_ID
                    firmwareDto.getRutaArchivo(), //RUTA_ARCHIVO
                    firmwareDto.getNombreArchivo(), //NOMBRE_ARCHIVO
                    firmwareDto.getDispositivoId(), //DISPOSITIVO_ID
                    firmwareDto.getTipoId(), //TIPO_ID
                    firmwareDto.getEstadoId(), //ESTADO_ID
                    firmwareDto.getFechaCreacion(), //FECHA_CREACION
                    firmwareDto.getFechaActualizacion(), //FECHA_ACTUALIZACION
                    firmwareDto.getNotificado() //NOTIFICADO
            };
            //
            long idFirmware = Yank.insert(sqLiteDataSource.getPoolName(),
                    "INSERT INTO FIRMWARE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", params);
            if (idFirmware > 0) {
                firmwareDto.setFirmwareId(idFirmware);

                log.debug("Se ha guardado el firmware Id[%s] correctamente", firmwareDto.getFirmwareId());

            } else {
                throw new DataAccessException(String.format("No se pudo guardar el firmware Id[%s]", firmwareDto.getDifusionId()));
            }

        } catch (YankSQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return firmwareDto;
    }

    @Override
    public FirmwareDTO findFirmwareById(long id) throws DataAccessException {
        FirmwareDTO firmwareDto = null;
        try {
            String query = new StringBuilder()
                    .append("SELECT  ")
                    .append("   FIRMWARE_ID,  ")
                    .append("   NOMBRE_FIRMWARE,  ")
                    .append("   DIFUSION_ID,  ")
                    .append("   EQUIPO_ID,  ")
                    .append("   GROUP_ID,  ")
                    .append("   ARTIFACT_ID,  ")
                    .append("   VERSION,  ")
                    .append("   PACKAGE_ID,  ")
                    .append("   RUTA_ARCHIVO,  ")
                    .append("   NOMBRE_ARCHIVO,  ")
                    .append("   DISPOSITIVO_ID,  ")
                    .append("   TIPO_ID,  ")
                    .append("   ESTADO_ID,  ")
                    .append("   FECHA_CREACION,  ")
                    .append("   FECHA_ACTUALIZACION,  ")
                    .append("   NOTIFICADO  ")
                    .append("FROM FIRMWARE  ")
                    .append("WHERE FIRMWARE_ID = ? ")
                    .toString();

            firmwareDto = Yank.queryBean(sqLiteDataSource.getPoolName(), query, FirmwareDTO.class, new Object[]{id});
            if (Objects.nonNull(firmwareDto) && Objects.nonNull(firmwareDto.getFirmwareId())) {
                log.debug("Se ha encontrado el firmware Id[%s]", id);

            } else {
                log.debug("No se ha encontrado el firmware Id[%s]", id);
            }

        } catch (YankSQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return firmwareDto;
    }

    @Override
    public boolean updateFirware(FirmwareDTO firmwareDto) throws DataAccessException {
        boolean isSave = false;
        try {
            Object[] params = {
                    firmwareDto.getNombreFirmware(), //NOMBRE_FIRMWARE
                    firmwareDto.getDifusionId(), //DIFUSION_ID
                    firmwareDto.getEquipoId(), //EQUIPO_ID
                    firmwareDto.getGroupId(), //GROUP_ID
                    firmwareDto.getArtifactId(), //ARTIFACT_ID
                    firmwareDto.getVersion(), //VERSION
                    firmwareDto.getPackageId(), //PACKAGE_ID
                    firmwareDto.getRutaArchivo(), //RUTA_ARCHIVO
                    firmwareDto.getNombreArchivo(), //NOMBRE_ARCHIVO
                    firmwareDto.getDispositivoId(), //DISPOSITIVO_ID
                    firmwareDto.getTipoId(), //TIPO_ID
                    firmwareDto.getEstadoId(), //ESTADO_ID
                    firmwareDto.getFechaCreacion(), //FECHA_CREACION
                    firmwareDto.getFechaActualizacion(), //FECHA_ACTUALIZACION
                    firmwareDto.getNotificado(), //NOTIFICADO
                    firmwareDto.getFirmwareId() //FIRMWARE_ID

            };

            //
            String query = new StringBuilder()
                    .append("UPDATE FIRMWARE ")
                    .append("SET NOMBRE_FIRMWARE = ?, ")
                    .append("    DIFUSION_ID = ?, ")
                    .append("    EQUIPO_ID = ?, ")
                    .append("    GROUP_ID = ?, ")
                    .append("    ARTIFACT_ID = ?, ")
                    .append("    VERSION = ?,")
                    .append("    PACKAGE_ID = ?, ")
                    .append("    RUTA_ARCHIVO = ?, ")
                    .append("    NOMBRE_ARCHIVO = ?, ")
                    .append("    DISPOSITIVO_ID = ?, ")
                    .append("    TIPO_ID = ?, ")
                    .append("    ESTADO_ID = ?, ")
                    .append("    FECHA_CREACION = ?, ")
                    .append("    FECHA_ACTUALIZACION = ?, ")
                    .append("    NOTIFICADO = ? ")
                    .append("WHERE FIRMWARE_ID = ?")
                    .toString();

            int numAfectados = Yank.execute(sqLiteDataSource.getPoolName(), query, params);
            if (numAfectados == 1) {
                isSave = true;

                log.debug("Se ha actualizado el firmware Id[%s] correctamente", firmwareDto.getFirmwareId());
            } else {
                throw new DataAccessException(String.format("No se pudo actualizar el firmware Id[%s]", firmwareDto.getFirmwareId()));
            }

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return isSave;
    }

    @Override
    public boolean updateFirware(long firmwareId, String estadoId, boolean notificado) throws DataAccessException {
        boolean isSave = false;
        try {
            Object[] params = {
                    estadoId, //ESTADO_ID
                    notificado, //NOTIFICADO
                    firmwareId//FIRMWARE_ID
            };

            //
            String query = new StringBuilder()
                    .append("UPDATE FIRMWARE ")
                    .append("SET ESTADO_ID = ?, ")
                    .append("    NOTIFICADO = ? ")
                    .append("WHERE FIRMWARE_ID = ?")
                    .toString();

            int numAfectados = Yank.execute(sqLiteDataSource.getPoolName(), query, params);
            if (numAfectados == 1) {
                isSave = true;

                log.debug("Se ha actualizado el firmware Id[%s] correctamente", firmwareId);
            } else {
                throw new DataAccessException(String.format("No se pudo actualizar el firmware Id[%s]", firmwareId));
            }

        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return isSave;
    }

    @Override
    public FirmwareDTO savePaqueteFirmware(FirmwareDTO firmwareDto) throws DataAccessException {
        Path toPath = null;
        try {
            // get file path
            URI fromPath = archivaGateway.getFirmwareURI(firmwareDto);

            // get file name
            //artifactId-version.packageId
            String fileName = MessageFormat.format(ArchivaGateway.getFileFormat(), firmwareDto.getArtifactId(), firmwareDto.getVersion(), firmwareDto.getPackageId());
            firmwareDto.setNombreArchivo(fileName); // file name
            //
            Path toDirPath = Paths.get(
                    new StringBuilder()
                            .append(fileSystemDataSource.getDefaultWorkDirectory())
                            .append(File.separator)
                            .append(firmwareDto.getNombreFirmware())
                    .toString());

            Path toFilePath = Paths.get(
                    new StringBuilder()
                            .append(File.separator)
                            .append(firmwareDto.getNombreArchivo())
                    .toString());

            toPath = Paths.get(toDirPath.toString(), toFilePath.toString()); // file local path

            boolean isStore = false;
            // create, download and copy file
            boolean isCreated = fileSystemDataSource.create(toPath);
            if (isCreated) {
                isStore = fileSystemDataSource.copy(fromPath, toPath);
            } else {
                throw new DataAccessException(String.format("No se pudo crear el archivo [%s] en el filesystem local", firmwareDto.getNombreArchivo()));
            }
            //
            if (isStore) {
                firmwareDto.setRutaArchivo(toDirPath.toAbsolutePath().toString()); // file final path

                log.debug("Se ha guardado el paquete de firmware [%s] correctamente", firmwareDto.getNombreArchivo());
            } else {
                throw new DataAccessException(String.format("No se pudo guardar el paquete de firmware Id[%s]", firmwareDto.getFirmwareId()));
            }
        } catch (Exception e) {
            // remove temp files
            if(Objects.nonNull(toPath) && toPath.toFile().exists()){
                try {
                    fileSystemDataSource.remove(toPath);
                }catch (Exception e2){
                    log.error(e.getMessage(), e2);
                }
            }
            throw new DataAccessException(e.getMessage(), e);
        }
        return firmwareDto;
    }

    @Override
    public List<FirmwareDTO> findAllFirmwarePorAplicar() throws DataAccessException {
        List<FirmwareDTO> firmwareDTOs = null;
        try {
            String query = new StringBuilder()
                    .append("SELECT  ")
                    .append("   FIRMWARE_ID, ")
                    .append("   NOMBRE_FIRMWARE, ")
                    .append("   DIFUSION_ID, ")
                    .append("   EQUIPO_ID, ")
                    .append("   GROUP_ID, ")
                    .append("   ARTIFACT_ID, ")
                    .append("   VERSION, ")
                    .append("   PACKAGE_ID, ")
                    .append("   RUTA_ARCHIVO, ")
                    .append("   NOMBRE_ARCHIVO, ")
                    .append("   DISPOSITIVO_ID, ")
                    .append("   TIPO_ID, ")
                    .append("   ESTADO_ID, ")
                    .append("   FECHA_CREACION, ")
                    .append("   FECHA_ACTUALIZACION, ")
                    .append("   NOTIFICADO ")
                    .append("FROM FIRMWARE ")
                    .append("WHERE ESTADO_ID = ? AND DATE(FECHA_ACTUALIZACION) = DATE('now') ")
                    .append("ORDER BY FIRMWARE_ID, FECHA_ACTUALIZACION ASC ")
                    .toString();

            firmwareDTOs = Yank.queryBeanList(sqLiteDataSource.getPoolName(),
                    query, FirmwareDTO.class, new Object[]{FirmwareStatusEnum.FIRMWARE_DESCARGADO_END_DISCO.getId()});
            if (!firmwareDTOs.isEmpty()) {
                log.debug(String.format("Se han encontrado paquetes de firware por aplicar [%s]", firmwareDTOs));
            } else {
                log.debug("No han encontrado paquetes de firware por aplicar");
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return firmwareDTOs;
    }


    @Override
    public File findFirmwareScript(FirmwareDTO firmwareDto) throws DataAccessException {
        File file = null;
        List<Path> filePaths = new ArrayList<>();
        try {
            filePaths = fileSystemDataSource.findStartsWith(Paths.get(firmwareDto.getRutaArchivo()), "install");
            if (!filePaths.isEmpty()) {
                Optional<Path> path = filePaths.stream().findFirst();
                if (path.isPresent()) {
                    file = path.get().toFile();
                    log.debug("Se ha encontrado el script de instalacion [%s]", file.getAbsolutePath());

                } else {
                    throw new DataAccessException("No se ha encontrado el script de instalacion");
                }
            } else {
                throw new DataAccessException("No se ha encontrado el script de instalacion");
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return file;
    }

    @Override
    public File findPaqueteFirmware(FirmwareDTO firmwareDto) throws DataAccessException {
        File file = null;
        try {
            file = fileSystemDataSource.get(
                    Paths.get(firmwareDto.getRutaArchivo().concat(File.separator).concat(firmwareDto.getNombreArchivo())));
            if (file.exists()) {
                log.debug("Se ha encontrado el paquete de instalacion [%s]", file.getAbsolutePath());

            } else {
                throw new DataAccessException("No se ha encontrado el archivo de instalacion en el paquete de firmware");
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return file;
    }

    @Override
    public boolean openPaqueteFirmware(FirmwareDTO firmwareDto) throws DataAccessException {
        boolean isOpen = true;
        try {
            Path filePath = Paths.get(
                    new StringBuilder()
                            .append(firmwareDto.getRutaArchivo())
                            .append(File.separator)
                            .append(firmwareDto.getNombreArchivo())
                            .toString());

            String extension = filePath.toString();
            if (extension.endsWith("tar.gz")) {
                File in = Paths.get(filePath.toString()).toFile();
                File out = Paths.get(firmwareDto.getRutaArchivo()).toFile();
                fileSystemDataSource.uncompress(in, out);

                if (out.exists()) {
                    log.debug("Se han descomprimido los archivos correctamente en el directorio [%s]", out.getAbsolutePath());
                } else {
                    throw new DataAccessException(String.format("Error al descomprimir los archivos en [%s]", firmwareDto.getRutaArchivo()));
                }
            } else {
                throw new DataAccessException(String.format("No se puede abrir el archivo[%s] porque no es un archivo *.tar.gz", filePath.toString()));
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return isOpen;
    }

    @Override
    public Double getVelocidadPromedio() throws DataAccessException {
        double velocidadPromedio = 0L;
        try {
            String query = new StringBuilder()
                    .append("SELECT ")
                    .append("COALESCE(AVG(VELOCIDAD),0) AS VELOCIDAD ")
                    .append("FROM CONSOLA_POSICION ")
                    .toString();

            if (derbyDataSource.test()) {
                velocidadPromedio =
                        Yank.queryScalar(
                                derbyDataSource.getPoolName(), query, Double.class, new Object[]{});
            } else {
                boolean isReconnect = derbyDataSource.reconnect();
                if (isReconnect) {
                    velocidadPromedio =
                            Yank.queryScalar(
                                    derbyDataSource.getPoolName(), query, Double.class, new Object[]{});
                } else {
                    throw new DataAccessException("No se pudo reconectar con la base datos derby para obtener la velocidad promedio");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return velocidadPromedio;
    }

    @Override
    public PosicionDTO getLastPosicion() throws DataAccessException {
        PosicionDTO lastPosision = null;
        try {
            String query = new StringBuilder()
                    .append("SELECT ")
                    .append("MOMENTO, LAT, LON, VELOCIDAD ")
                    .append("FROM CONSOLA_POSICION ")
                    .append("ORDER BY MOMENTO DESC FETCH FIRST 1 ROWS ONLY ")
                    .toString();

            if (derbyDataSource.test()) {
                lastPosision = Yank.queryBean(derbyDataSource.getPoolName(), query, PosicionDTO.class, new Object[]{});

            } else {
                if (derbyDataSource.reconnect()) {
                    lastPosision = Yank.queryBean(derbyDataSource.getPoolName(), query, PosicionDTO.class, new Object[]{});
                } else {
                    throw new DataAccessException("No se pudo reconectar con la base datos derby para obtener la ultima posicion");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return lastPosision;
    }

    @Override
    public boolean checkPaquetFirmware(FirmwareDTO firmwareDto) throws DataAccessException {
        boolean isValid = false;
        Path toPath = null;
        try {
            // get file path
            URI fromPath = archivaGateway.getFirmwareMd5URI(firmwareDto);

            // get file name
            //artifactId-version.packageId.md5
            String fileName = MessageFormat.format(ArchivaGateway.getFileMd5Format(), firmwareDto.getArtifactId(), firmwareDto.getVersion(), firmwareDto.getPackageId());
            //
            Path toDirPath = Paths.get(
                    new StringBuilder()
                            .append(fileSystemDataSource.getDefaultWorkDirectory())
                            .append(File.separator)
                            .append(firmwareDto.getNombreFirmware())
                            .toString());

            Path toFilePath = Paths.get(
                    new StringBuilder()
                            .append(File.separator)
                            .append(fileName)
                            .toString());

            toPath = Paths.get(toDirPath.toString(), toFilePath.toString()); // file local path

            boolean isStore = false;
            // create, download and copy file
            boolean isCreated = fileSystemDataSource.create(toPath);
            if (isCreated) {
                // get md5 remote
                isStore = fileSystemDataSource.copy(fromPath, toPath);
            } else {
                throw new DataAccessException(String.format("No se pudo crear el archivo [%s] en el filesystem local", fileName));
            }
            //
            if (isStore) {
                log.debug("Se ha guardado el archivo checksum [%s] del paquete de firmware correctamente", fileName);

                // get md5 from  remote file
                String md5FromRemoteFile = fileSystemDataSource.getMd5(toPath);
                // generate md5 from local file
                Path localPath = Paths.get(firmwareDto.getRutaArchivo().concat(File.separator).concat(firmwareDto.getNombreArchivo()));
                String md5FromLocalFile = fileSystemDataSource.createMd5(localPath);
                // compare
                isValid = fileSystemDataSource.checkSumMd5(md5FromRemoteFile, md5FromLocalFile);

                if (isValid) {
                    log.info(
                            String.format("El checksum md5 del archivo [%s] esta correcto!!! Se procede con la operacion !!!",  localPath));
                } else {
                    throw new DataAccessException(String.format("El ckecksum md5 del archivo [%s] ha fallado!!! Se aborta la operacion!!!", localPath));
                }

            } else {
                throw new DataAccessException(String.format("No se pudo guardar el paquete de firmware Id[%s]", firmwareDto.getFirmwareId()));
            }
        } catch (Exception e) {
            // remove temp files
            if(Objects.nonNull(toPath) && toPath.toFile().exists()){
                try {
                    fileSystemDataSource.remove(toPath);
                }catch (Exception e2){
                    log.error(e.getMessage(), e2);
                }
            }
            throw new DataAccessException(e.getMessage(), e);
        }
        return isValid;
    }
}
