package com.sonda.transporte.consola.agente.firmware.infrastructure.dto;

import lombok.Data;
import org.knowm.yank.annotations.Column;

@Data
public class PosicionDTO {

    @Column (value = "MOMENTO")
    private String fechaHora;

    @Column (value = "LAT")
    private Double latitud;

    @Column (value = "LON")
    private Double longitud;

    @Column (value = "VELOCIDAD")
    private double velocidad;

}
