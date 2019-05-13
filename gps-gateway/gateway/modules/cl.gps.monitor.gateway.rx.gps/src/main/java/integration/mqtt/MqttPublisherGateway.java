package com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt;

import com.sonda.transporte.consola.agente.firmware.application.converter.*;
import com.sonda.transporte.consola.agente.firmware.application.event.Event;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.DifusionDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.Gateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Properties;

@Getter
@Setter
public class MqttPublisherGateway  implements Gateway {

    private int qos;

    private boolean retention;

    private String mqttTopic;

    private MqttGateway mqttGateway;

    private Properties appProperties = UtilProperties.getProperties();

    public static final UtilLogger log = UtilLogger.getLogger(MqttPublisherGateway.class);

    public static MqttPublisherGateway newGateway(){
        return new MqttPublisherGateway();
    }

    public void initialize() throws MqttException {
        mqttTopic = ToHostNameConverter.getConverterInstance().converter(
                appProperties.getProperty(UtilProperties.MQTT_TOPIC_PUBLISHER_PROPERTY_KEY));
        qos = Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_TOPIC_PUBLISHER_QOS_PROPERTY_KEY));
        retention = Boolean.getBoolean(appProperties.getProperty(UtilProperties.MQTT_TOPIC_PUBLISHER_RETENTION_PROPERTY_KEY));
    }

    public boolean isConnected() {
        return mqttGateway.isConnected();
    }

    public void notity(byte[] message) {
        mqttGateway.publish(mqttTopic, message, qos, retention);

    }

    public byte[] createByteMessage(Event event) {
        return ArrayUtils.toPrimitive(EventConverter.getConverterInstance().converter(event));
    }

    public byte[] createByteMessage(DifusionDTO difusionDto) {
        return ArrayUtils.toPrimitive(FromDifusionConverter.getConverterInstance().converter(difusionDto));
    }
}
