package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.application.converter.MqttIdentificadorConverter;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Properties;

public class MqttFactory {

    private static MqttFactory mqttFactory;

    private MqttAsyncClient mqttClient;

    public static final UtilLogger log = UtilLogger.getLogger(MqttFactory.class);

    protected static final Properties appProperties = UtilProperties.getProperties();

    public static MqttFactory getFactoryInstance() {
        if (mqttFactory == null) {
            mqttFactory = new MqttFactory();
        }
        return mqttFactory;
    }

    public IMqttAsyncClient getMqtt(Class<? extends IMqttAsyncClient> clazz) throws MqttException {
        IMqttAsyncClient iMqttClient = null;
        try {
            if (clazz.equals(MqttAsyncClient.class)) {
                if (mqttClient == null) {
                    String identifier = MqttIdentificadorConverter
                            .getConverterInstance().converter(MqttGateway.class.getSimpleName());
                    // client
                    String mqttServer = new URIBuilder()
                            .setScheme("tcp")
                            .setHost(appProperties.getProperty(UtilProperties.MQTT_SERVER_PROPERTY_KEY))
                            .setPort(Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_SERVER_PORT_PROPERTY_KEY)))
                            .build()
                    .toString();
                    mqttClient = new MqttAsyncClient(mqttServer, identifier);
                }
                iMqttClient = mqttClient;
            } else {
                log.error("La clase[%s] no es un MqttClient", clazz.getSimpleName());
            }
        }catch (Exception e){
            log.error("[%s] Se provocado una excepcion al crear el cliente mqtt", MqttFactory.class.getSimpleName(), e);
        }
        return iMqttClient;
    }
}