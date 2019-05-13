package com.sonda.transporte.consola.agente.firmware.infrastructure.handler;

import com.sonda.transporte.consola.agente.firmware.application.converter.ExceptionConverter;
import com.sonda.transporte.consola.agente.firmware.application.event.firmware.FirmwareNoDescargadoExceptionEvent;
import com.sonda.transporte.consola.agente.firmware.application.event.firmware.FirmwareNoEncontradoNoAplicadoExceptionEvent;
import com.sonda.transporte.consola.agente.firmware.application.event.firmware.FirmwareNoRegistradoExceptionEvent;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.FirmwareEventBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class FirmwareErrorHandler implements Handler {

    private FirmwareEventBus firmwareEventBus;

    public static final UtilLogger log = UtilLogger.getLogger(FirmwareErrorHandler.class);

    public void handle(FirmwareNoRegistradoExceptionEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getException())) {
                message = String.format(event.getEstadoFirmwareEnum().getDescripcion(),
                                event.getFirmwareId().getDifusionValue(), event.getException().getClass().getSimpleName(), event.getException());
                log.error(message, event.getException());
            } else {
                throw new IllegalArgumentException(String.format("El firmware del evento [%s] no puede ser nulo ", FirmwareNoRegistradoExceptionEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, ExceptionConverter.getConverterInstance().converter(event.getException()));


        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareNoDescargadoExceptionEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getException())) {
                message = String.format(event.getEstadoFirmwareEnum().getDescripcion(),
                                event.getFirmwareId().getDifusionValue(), event.getException().getClass().getSimpleName(), event.getException().getMessage());
                log.error(message, event.getException());
            } else {
                throw new IllegalArgumentException(String.format("El firmware del evento [%s] no puede ser nulo  ", FirmwareNoDescargadoExceptionEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, ExceptionConverter.getConverterInstance().converter(event.getException()));

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareNoEncontradoNoAplicadoExceptionEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getException())) {
                message = String.format(event.getEstadoFirmwareEnum().getDescripcion(),
                                event.getFirmwareId(), event.getException().getClass().getSimpleName(), event.getException().getMessage());
                log.error(message, event.getException());
            } else {
                throw new IllegalArgumentException(String.format("El firmware del evento [%s] no puede ser nulo  ", FirmwareNoEncontradoNoAplicadoExceptionEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, ExceptionConverter.getConverterInstance().converter(event.getException()));

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}