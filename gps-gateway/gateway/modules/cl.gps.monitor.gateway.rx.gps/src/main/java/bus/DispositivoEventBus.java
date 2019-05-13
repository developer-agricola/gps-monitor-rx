package com.sonda.transporte.consola.agente.firmware.infrastructure.bus;

import com.sonda.transporte.consola.agente.firmware.application.converter.FromFirmwareConverter;
import com.sonda.transporte.consola.agente.firmware.application.converter.ToDifusionConverter;
import com.sonda.transporte.consola.agente.firmware.application.event.dispositivo.DispositivoNoHorarioEvent;
import com.sonda.transporte.consola.agente.firmware.application.event.dispositivo.DispositivoNoPosicionEvent;
import com.sonda.transporte.consola.agente.firmware.application.event.dispositivo.DispositivoNoVelocidadEvent;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.DifusionDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.FirmwareDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.FirmwareStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.exceptions.GatewayException;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttPublisherGateway;
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
public class DispositivoEventBus implements Bus {

    private MqttPublisherGateway mqttPublisherGateway;

    public static final UtilLogger log = UtilLogger.getLogger(DispositivoEventBus.class);

    public void send(DispositivoNoVelocidadEvent event, String... messages) throws GatewayException {
        log.info(UtilMessage.SEND_EVENT, event);
        try {
            FirmwareDTO firmwareDto = FromFirmwareConverter.getConverterInstance().converter(event.getFirmware());

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance()
                            .converter(Triple.of(firmwareDto, FirmwareStatusEnum.FIRMWARE_NO_REGLAS_RUTA, messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(DispositivoNoPosicionEvent event, String... messages) throws GatewayException {
        log.info(UtilMessage.SEND_EVENT, event);
        try {
            FirmwareDTO firmwareDto = FromFirmwareConverter.getConverterInstance().converter(event.getFirmware());

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance()
                        .converter(Triple.of(firmwareDto, FirmwareStatusEnum.FIRMWARE_NO_REGLAS_RUTA, messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }

    public void send(DispositivoNoHorarioEvent event, String... messages) throws GatewayException {
        log.info(UtilMessage.SEND_EVENT, event);
        try {
            FirmwareDTO firmwareDto = FromFirmwareConverter.getConverterInstance().converter(event.getFirmware());

            DifusionDTO difusionDto =
                    ToDifusionConverter.getConverterInstance()
                        .converter(Triple.of(firmwareDto, FirmwareStatusEnum.FIRMWARE_NO_REGLAS_RUTA, messages));
            //
            byte[] message = mqttPublisherGateway.createByteMessage(difusionDto);
            mqttPublisherGateway.notity(message);

        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GatewayException(String.format(UtilMessage.ERROR_SEND_EVENT, event), e);
        }
    }
}
