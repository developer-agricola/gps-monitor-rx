package com.sonda.transporte.consola.agente.firmware.infrastructure.dto;

import lombok.Data;
import org.knowm.yank.annotations.Column;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * Created by daniel.carvajal on 27-06-2018.
 */
@Data
public class FirmwareDTO implements Serializable {

    @Column("FIRMWARE_ID")
    private long firmwareId;

    @Column("NOMBRE_FIRMWARE")
    private String nombreFirmware;

    @Column("DIFUSION_ID")
    private long difusionId;

    @Column("EQUIPO_ID")
    private long equipoId;

    @Column("GROUP_ID")
    private String groupId;

    @Column("ARTIFACT_ID")
    private String artifactId;

    @Column("VERSION")
    private String version;

    @Column("PACKAGE_ID")
    private String packageId;

    @Column("RUTA_ARCHIVO")
    private String rutaArchivo;

    @Column("NOMBRE_ARCHIVO")
    private String nombreArchivo;

    @Column("DISPOSITIVO_ID")
    private long dispositivoId;

    @Column("TIPO_ID")
    private String tipoId;

    @Column("ESTADO_ID")
    private String estadoId;

    @Column("FECHA_CREACION")
    private String fechaCreacion;

    @Column("FECHA_ACTUALIZACION")
    private String fechaActualizacion;

    @Column("NOTIFICADO")
    private Boolean notificado;

    @Column("MENSAJE_ID")
    private long mensajId;

    private transient Path installScript;

}
