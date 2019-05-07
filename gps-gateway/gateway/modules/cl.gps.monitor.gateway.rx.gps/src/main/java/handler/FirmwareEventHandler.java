package com.sonda.transporte.consola.agente.firmware.infrastructure.handler;

import com.sonda.transporte.consola.agente.firmware.application.event.firmware.*;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.FirmwareEventBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Created by daniel.carvajal on 22-06-2018.
 */

@Getter
@Setter
public class FirmwareEventHandler implements Handler {

    private FirmwareEventBus firmwareEventBus;

    public static final UtilLogger log = UtilLogger.getLogger(FirmwareEventHandler.class);

    public void handle(FirmwareRegistradoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareDto().getFirmwareId());
                log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareRegistradoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareNoRegistradoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareDto().getFirmwareId());
                    log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareNoRegistradoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }


    public void handle(FirmwareDescargadoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(),
                            event.getFirmwareDto().getFirmwareId(), event.getFirmwareDto().getRutaArchivo(), "OK");
                    log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareDescargadoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareNoDescargadoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(),
                            event.getFirmwareDto().getFirmwareId(), event.getFirmwareDto().getRutaArchivo(), event.getFirmwareDto().getNombreArchivo());
                    log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareNoDescargadoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareEncontradoEnDiscoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(),
                            event.getFirmwareDto().getFirmwareId(), event.getFirmwareDto().getNombreArchivo());
                    log.info(message);

            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareEncontradoEnDiscoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    public void handle(FirmwareNoEncontradoEnDiscoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareDto().getFirmwareId());
                log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareNoEncontradoEnDiscoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(ScriptEncontradoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(),
                            event.getFirmwareDto().getFirmwareId(), event.getFirmwareDto().getInstallScript());
                    log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, ScriptEncontradoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(ScriptNoEncontradoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(),
                            event.getFirmwareDto().getFirmwareId(), event.getFirmwareDto().getInstallScript());
                    log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, ScriptNoEncontradoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(ScriptEjecutadoOkEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareDto().getInstallScript(), "OK");
                    log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, ScriptEjecutadoOkEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(ScriptEjecutadoNOkEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareDto().getInstallScript(), "NOK");
                    log.info(message);

            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, ScriptEjecutadoNOkEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareEncontradoAplicadoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareDto().getDifusionId(), event.getFirmwareDto().getNombreArchivo());
                    log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareEncontradoAplicadoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareNoAplicadoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                    message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareDto().getDifusionId());
                    log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareNoAplicadoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareFinalStatusNotificadoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                message = String.format("Se envia el estado  final [%s]", event.getEstadoFirmwareEnum().getId());
                log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareFinalStatusNotificadoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareCorruptoAlDescargarEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareDto()) && Objects.nonNull(event.getFirmwareDto().getFirmwareId()) &&
                    Objects.nonNull(event.getFirmwareDto().getDifusionId()) && Objects.nonNull(event.getFirmwareDto().getEquipoId())) {
                message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareDto().getFirmwareId());
                log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareNoEncontradoEnDiscoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void handle(FirmwareFinalStatusNoNotificadoEvent event){
        String message = null;
        try {
            if (Objects.nonNull(event.getFirmwareId()) && Objects.nonNull(event.getFirmwareId().getDifusionValue())) {
                message = String.format(event.getEstadoFirmwareEnum().getDescripcion(), event.getFirmwareId().getDifusionValue());
                log.info(message);
            }else{
                throw new IllegalArgumentException(String.format(UtilMessage.ERROR_FIRMWARE_NULO, FirmwareFinalStatusNoNotificadoEvent.class.getSimpleName()));
            }
            firmwareEventBus.send(event, message);

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
