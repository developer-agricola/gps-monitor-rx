package com.sonda.transporte.consola.agente.firmware.infrastructure.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class PaqueteDTO  implements Serializable {


    private long idDifusion;

    private long idEquipo;

    private String repository;

    private String groupdID;

    private String artifactID;

    private String version;

    private String packaging;

    private String fechaMensaje;

    private String fechaActualiza;

    private long estado;

    private String fechaEstado;
}
