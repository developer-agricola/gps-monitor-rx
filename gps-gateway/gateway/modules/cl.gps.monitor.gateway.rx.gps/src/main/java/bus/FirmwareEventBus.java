package com.sonda.transporte.consola.agente.firmware.infrastructure.bus;

import com.sonda.transporte.consola.agente.firmware.application.converter.ToDifusionConverter;
import com.sonda.transporte.consola.agente.firmware.application.converter.ToDifusionExceptionConverter;
import com.sonda.transporte.consola.agente.firmware.application.event.firmware.*;

import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.DifusionDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.FirmwareDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.exceptions.GatewayException;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttPublisherGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.dao.DispositivoDao;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Triple;


/**
 * Created by daniel.carvajal on 09-07-2018.
 */
@Getter
@Setter
public class FirmwareEventBus implements Bus {

    private MqttPublisherGateway mqttPublisherGateway;

    private DispositivoDao dispositivoDao;

    public static final UtilLogger log = UtilLogger.getLogger(FirmwareEventBus.class);

    public void send(FirmwareRegistradoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareNoRegistradoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareNoRegistradoExceptionEvent event, String... messages) throws GatewayException {
        try {

            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionExceptionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareId(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareId().getDifusionValue(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareDescargadoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareNoDescargadoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareEncontradoEnDiscoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareNoEncontradoEnDiscoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(ScriptEncontradoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(ScriptNoEncontradoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(ScriptEjecutadoOkEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(ScriptEjecutadoNOkEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }


    public void send(FirmwareNoDescargadoExceptionEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionExceptionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareId(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareId().getDifusionValue(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareNoEncontradoNoAplicadoExceptionEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionExceptionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareId(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareId().getDifusionValue(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareEncontradoAplicadoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareNoAplicadoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareFinalStatusNotificadoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareCorruptoAlDescargarEvent event, String... messages) throws GatewayException {
        try {

            log.info(UtilMessage.SEND_EVENT, event);

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(event.getFirmwareDto(), event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(event.getFirmwareDto().getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(FirmwareFinalStatusNoNotificadoEvent event, String... messages) throws GatewayException {
        try {
            log.info(UtilMessage.SEND_EVENT, event);

            FirmwareDTO firmwareDto = new FirmwareDTO();
            firmwareDto.setFirmwareId(event.getFirmwareId().getDifusionValue());

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance().converter(Triple.of(firmwareDto, event.getEstadoFirmwareEnum(), messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

            dispositivoDao.updateFirware(firmwareDto.getFirmwareId(), event.getEstadoFirmwareEnum().getId(), true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }
}
