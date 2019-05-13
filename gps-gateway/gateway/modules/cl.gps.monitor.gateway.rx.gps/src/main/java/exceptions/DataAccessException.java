package com.sonda.transporte.consola.agente.firmware.infrastructure.exceptions;

/**
 * Created by daniel.carvajal on 05-07-2018.
 */
public class DataAccessException extends Exception {

    public DataAccessException(String message){
        super(message);
    }

    public DataAccessException(String message, Throwable e){
        super(message, e);
    }
}
