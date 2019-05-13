package com.sonda.transporte.consola.agente.firmware.infrastructure.dto;

import lombok.Data;
import org.knowm.yank.annotations.Column;

import java.io.Serializable;

/**
 * Created by daniel.carvajal on 27-06-2018.
 */
@Data
public class DispositivoDTO implements Serializable{

    @Column("DISPOSITIVO_ID")
    private long dispositivoId;

    @Column("NOMBRE_DISPOSITIVO")
    private String nombreDispositivo;

    @Column("TIPO_ID")
    private String tipoId;

    @Column("ESTADO_ID")
    private String estadoId;

    private Double longitud;

    private Double latitud;

    private Double velocidadPromedio;

}
