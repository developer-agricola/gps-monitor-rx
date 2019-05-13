package com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl;

import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.ServiceStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttPublisherGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttSubcriberGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by daniel.carvajal on 25-06-2018.
 */
@Getter
@Setter
public class MqttConfigApp implements Configuration {

    private MqttGateway mqttGateway;

    private MqttSubcriberGateway mqttSubcriberGateway;

    private MqttPublisherGateway mqttPublisherGateway;

    public static final UtilLogger log = UtilLogger.getLogger(MqttConfigApp.class);

    @Override
    public void configure() {
        mqttGateway();
        mqttPublisherGateway();
        mqttSubscriberGateway();
    }


    public void mqttGateway(){
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE,
                    MqttGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());
            mqttGateway.initialize();

            boolean isValid = mqttGateway.isConnected();
            if (isValid) {
                log.info("[%s] Id[%s] Host[%s] ", MqttGateway.class.getSimpleName(),
                        mqttGateway.getMqttClient().getClientId(), mqttGateway.getMqttClient().getCurrentServerURI());


                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        MqttGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        MqttGateway.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    MqttGateway.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }
    }

    public void mqttSubscriberGateway(){
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE,
                    MqttSubcriberGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());
            //
            mqttSubcriberGateway.inicialize();
            mqttSubcriberGateway.subscribe();

            boolean isValid = mqttSubcriberGateway.isConnected();
            if (isValid) {
                log.info("[%s] subscrito al topico [%s] ",
                        MqttSubcriberGateway.class.getSimpleName(), mqttSubcriberGateway.getMqttTopic());


                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        MqttSubcriberGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        MqttSubcriberGateway.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    MqttSubcriberGateway.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }
    }

    public void mqttPublisherGateway(){
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE,
                    MqttPublisherGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());
            //
            mqttPublisherGateway.initialize();

            boolean isValid = mqttPublisherGateway.isConnected();
            if (isValid) {
                log.info("[%s] publicando en el topico [%s] ",
                        MqttPublisherGateway.class.getSimpleName(), mqttPublisherGateway.getMqttTopic());

                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        MqttPublisherGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        MqttPublisherGateway.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    MqttPublisherGateway.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }

    @Override
    public void destroy() {
        try {
            mqttGateway.detach();

            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    MqttGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    MqttGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }
}
