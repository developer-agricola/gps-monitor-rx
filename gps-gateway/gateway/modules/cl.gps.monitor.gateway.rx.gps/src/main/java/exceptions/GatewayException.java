package com.sonda.transporte.consola.agente.firmware.infrastructure.exceptions;

/**
 * Created by daniel.carvajal on 05-07-2018.
 */
public class GatewayException extends Exception {

    public GatewayException(String message){
        super(message);
    }

    public GatewayException(String message, Throwable e){
        super(message, e);
    }
}
