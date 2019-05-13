package com.sonda.transporte.consola.agente.firmware.infrastructure.handler;

import com.sonda.transporte.consola.agente.firmware.application.event.dispositivo.*;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.DispositivoEventBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.DispositivoStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;



/**
 * Created by daniel.carvajal on 22-06-2018.
 */
@Setter
@Getter
public class DispositivoEventHandler implements Handler {

    private DispositivoEventBus eventBus;

    public static final UtilLogger log = UtilLogger.getLogger(DispositivoEventHandler.class);

    public void handle(DispositivoRegistradoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getDispositivo()) && Objects.nonNull(event.getDispositivo().getNombreDispositivo())) {
                message =
                        String.format(event.getEstadoDispositivoEnum().getDescripcion(), event.getDispositivo().getNombreDispositivo());
                log.info(message);

            } else {
                message = DispositivoStatusEnum.DISPOSITIVO_REGISTRADO.getDescripcion();
                log.info(message);
                throw new IllegalArgumentException(UtilMessage.ERROR_DISPOSITIVO_NULO);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public void handle(DispositivoNoRegistradoEvent event) {
        String message = null;
        try {
            if (Objects.nonNull(event.getDispositivo()) && Objects.nonNull(event.getDispositivo().getNombreDispositivo())) {
                message =
                        String.format(event.getEstadoDispositivoEnum().getDescripcion(), event.getDispositivo().getNombreDispositivo());
                log.info(message);

            } else {
                message = DispositivoStatusEnum.DISPOSITIVO_NO_REGISTRADO.getDescripcion();
                log.info(message);
                throw new IllegalArgumentException(UtilMessage.ERROR_DISPOSITIVO_NULO);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }


    public void handle(DispositivoNoVelocidadEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getDispositivo()) && Objects.nonNull(event.getDispositivo().getNombreDispositivo()) &&
                    Objects.nonNull(event.getFirmware()) && Objects.nonNull(event.getFirmware().getFirmwareId()) &&
                        Objects.nonNull(event.getFirmware().getFirmwareId().getDifusionValue()) && Objects.nonNull(event.getFirmware().getFirmwareId().getEquipoValue())) {
                message =
                        String.format(event.getEstadoDispositivoEnum().getDescripcion(),
                                event.getDispositivo().getVelocidadPromedio(), event.getDispositivo().getNombreDispositivo());
                log.info(message);

            } else {
                message = DispositivoStatusEnum.DISPOSITIVO_NO_VELOCIDAD.getDescripcion();
                log.info(message);
                throw new IllegalArgumentException(UtilMessage.ERROR_DISPOSITIVO_NULO);
            }
            eventBus.send(event, message);

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public void handle(DispositivoNoPosicionEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getDispositivo()) && Objects.nonNull(event.getDispositivo().getNombreDispositivo()) &&
                    Objects.nonNull(event.getFirmware()) && Objects.nonNull(event.getFirmware().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmware().getFirmwareId().getDifusionValue()) && Objects.nonNull(event.getFirmware().getFirmwareId().getEquipoValue())) {
                message =
                        String.format(event.getEstadoDispositivoEnum().getDescripcion(), event.getDispositivo().getNombreDispositivo());
                log.info(message);

            } else {
                message = DispositivoStatusEnum.DISPOSITIVO_NO_VELOCIDAD.getDescripcion();
                log.info(message);
                throw new IllegalArgumentException(UtilMessage.ERROR_DISPOSITIVO_NULO);
            }
            eventBus.send(event, message);

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public void handle(DispositivoNoHorarioEvent event) {
        String message = null;
        try {
            if (Objects.nonNull(event.getDispositivo()) && Objects.nonNull(event.getDispositivo().getNombreDispositivo()) &&
                    Objects.nonNull(event.getFirmware()) && Objects.nonNull(event.getFirmware().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmware().getFirmwareId().getDifusionValue()) && Objects.nonNull(event.getFirmware().getFirmwareId().getEquipoValue())) {
                message =
                        String.format(event.getEstadoDispositivoEnum().getDescripcion(), event.getDispositivo().getNombreDispositivo());
                log.info(message);

            } else {
                message = DispositivoStatusEnum.DISPOSITIVO_NO_HORARIO.getDescripcion();
                log.info(message);
                throw new IllegalArgumentException(UtilMessage.ERROR_DISPOSITIVO_NULO);
            }
            eventBus.send(event, message);

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
