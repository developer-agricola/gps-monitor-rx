package com.sonda.transporte.consola.agente.firmware.infrastructure.handler;

import com.sonda.transporte.consola.agente.firmware.application.event.dispositivo.DispositivoExceptionEvent;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.DispositivoStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;

import java.util.Objects;

/**
 * Created by daniel.carvajal on 22-06-2018.
 */
public class DispositivoErrorHandler implements Handler {

    public static final UtilLogger log = UtilLogger.getLogger(DispositivoErrorHandler.class);

    public void handle(DispositivoExceptionEvent event){
        try {
            String message = null;
            if (Objects.nonNull(event.getException())) {
                message = String.format(event.getEstadoDispositivoEnum().getDescripcion(),
                        event.getException().getClass().getSimpleName(), event.getException());
                log.error(message, event.getException());
            } else {
                message = DispositivoStatusEnum.DISPOSITIVO_EXCEPTION.getDescripcion();
                log.error(message);
                throw new IllegalArgumentException(UtilMessage.ERROR_EVENTO_NULO);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
