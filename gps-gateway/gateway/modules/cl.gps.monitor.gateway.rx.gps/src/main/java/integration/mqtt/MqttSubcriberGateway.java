package com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt;

import com.sonda.transporte.consola.agente.firmware.application.converter.ToHostNameConverter;
import com.sonda.transporte.consola.agente.firmware.infrastructure.handler.CommandHandler;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.Gateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public class MqttSubcriberGateway implements Gateway {

    private int qos;

    private String mqttTopic;

    private MqttGateway mqttGateway;

    private CommandHandler commandHandler;

    protected static final Properties appProperties = UtilProperties.getProperties();

    protected static final UtilLogger log = UtilLogger.getLogger(MqttSubcriberGateway.class);

    public static MqttSubcriberGateway newGateway(){
        return new MqttSubcriberGateway();
    }

    public void inicialize(){
        mqttTopic = ToHostNameConverter.getConverterInstance().converter(appProperties.getProperty(UtilProperties.MQTT_TOPIC_SUSCRIBER_PROPERTY_KEY));
        qos = Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_TOPIC_SUBCRIBER_QOS_PROPERTY_KEY));
    }

    public void subscribe() {
       mqttGateway.subscribe(mqttTopic, qos);
    }

    public boolean isConnected() {
        return mqttGateway.isConnected();
    }
}
