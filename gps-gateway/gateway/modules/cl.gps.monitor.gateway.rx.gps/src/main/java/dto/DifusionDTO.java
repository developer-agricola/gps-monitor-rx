package com.sonda.transporte.consola.agente.firmware.infrastructure.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * Class DifusionDTO.
 * COMPLETAR DESCRIPCION.
 *
 * @author patricio.vergara
 * 12-01-2018
 */
@Data
public class DifusionDTO implements Serializable {
 
    /** difusionId COMPLETAR DESCRIPCION. */
    private long idDifusion;

    /** Identificador del equipo en el sistema central.*/
    private long idEquipo;

    /** fecha COMPLETAR DESCRIPCION. */
    private String fecha;
    
    /** estado COMPLETAR DESCRIPCION. */
    private int estado;
    
    /** detalle COMPLETAR DESCRIPCION. */
    private String detalle;
    
    /** difusion COMPLETAR DESCRIPCION. */
    private PaqueteDTO difusion;

    private boolean notificado;
}
