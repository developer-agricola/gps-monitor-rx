package com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt;

import com.sonda.transporte.consola.agente.firmware.application.command.GuardarFirmwareCommand;
import com.sonda.transporte.consola.agente.firmware.application.converter.CommandConverter;
import com.sonda.transporte.consola.agente.firmware.application.converter.MqttIdentificadorConverter;
import com.sonda.transporte.consola.agente.firmware.infrastructure.handler.CommandHandler;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.Gateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;


@Getter
@Setter
public class MqttGateway implements Gateway, MqttCallbackExtended {

    private MqttAsyncClient mqttClient;

    private CommandHandler commandHandler;

    protected static final Properties appProperties = UtilProperties.getProperties();

    protected static final UtilLogger log = UtilLogger.getLogger(MqttGateway.class);

    public void initialize() throws MqttException {
        while (!isConnected()) {
            try {
                // client
                String identifier =
                        MqttIdentificadorConverter.getConverterInstance().converter(MqttGateway.class.getSimpleName());
                String mqttServer = new URIBuilder()
                        .setScheme("tcp")
                        .setHost(appProperties.getProperty(UtilProperties.MQTT_SERVER_PROPERTY_KEY))
                        .setPort(Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_SERVER_PORT_PROPERTY_KEY)))
                        .build()
                        .toString();
                mqttClient = new MqttAsyncClient(mqttServer, identifier);
                // buffer default options
                DisconnectedBufferOptions bufferOptions = new DisconnectedBufferOptions();
                boolean bufferEnabled =
                        Boolean.valueOf(appProperties.getProperty(UtilProperties.MQTT_BUFFER_OPTIONS_BUFFER_ENABLED_PROPERTY_KEY));
                bufferOptions.setBufferEnabled(bufferEnabled); // def false
                boolean persistBuffer =
                        Boolean.valueOf(appProperties.getProperty(UtilProperties.MQTT_BUFFER_OPTIONS_PERSIST_BUFFER_PROPERTY_KEY));
                bufferOptions.setPersistBuffer(persistBuffer); // def false
                int bufferSize =
                        Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_BUFFER_OPTIONS_BUFFER_SIZE_PROPERTY_KEY));
                bufferOptions.setBufferSize(bufferSize); //def 5000
                mqttClient.setBufferOpts(bufferOptions);

                // set callback
                mqttClient.setCallback(this);

                attach();

            } catch (Exception e) {
                log.warning("[%s] Se ha producido un error al conectar con el server [%s]. Reintentando conexion!!!", MqttGateway.class.getSimpleName(), mqttClient.getServerURI());
                log.error(e.getMessage(), e);

                mqttClient.close(true);
                try {
                    Thread.sleep(10000);
                }catch (Exception e2){
                    log.error(e2.getMessage(), e2);
                }
            }
        }
    }

    public void attach() throws MqttException {
        //defaults options
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        boolean cleanSession =
                Boolean.valueOf(appProperties.getProperty(UtilProperties.MQTT_OPTIONS_CLEAN_SESSION_PROPERTY_KEY));
        connectOptions.setCleanSession(cleanSession); // def true
        int keepAliveInterval =
                Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_OPTIONS_KEEP_ALIVE_INTERVAL_PROPERTY_KEY));
        connectOptions.setKeepAliveInterval(keepAliveInterval); // def 60 seg
        boolean automaticReconnect =
                Boolean.valueOf(appProperties.getProperty(UtilProperties.MQTT_OPTIONS_AUTOMATIC_RECONNECT_PROPERTY_KEY));
        connectOptions.setAutomaticReconnect(automaticReconnect); // def false
        int connectionTimeout =
                Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_OPTIONS_CONNECTION_TIMEOUT_PROPERTY_KEY));
        connectOptions.setConnectionTimeout(connectionTimeout); // def 30 seg
        int maxInflight =
                Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_OPTIONS_MAX_INFLIGHT_PROPERTY_KEY));
        connectOptions.setMaxInflight(maxInflight);
        //
        mqttClient.connect(connectOptions).waitForCompletion();
    }

    public boolean isConnected() {
        boolean isConnected = false;
        try{
            if(Objects.nonNull(mqttClient)) {
                isConnected = mqttClient.isConnected();
            }
        }catch (Exception e){
            log.error(" [%s] no esta conectado", MqttSubcriberGateway.class.getSimpleName());
            log.error(e.getMessage(), e);
        }
        return isConnected;
    }

    public void detach() throws MqttException {
        mqttClient.disconnect();
        mqttClient.close(true);
    }

    public void reconnect() throws MqttException {
        mqttClient.reconnect();
    }

    public void subscribe(String mqttTopic, int qos){
        try {
            mqttClient.subscribe(mqttTopic, qos);
        } catch (Exception e) {
            log.error("[%s] Se ha producido un error al subscribir el cliente", MqttGateway.class.getSimpleName(), e);
            log.error(e.getMessage(), e);
        }
    }

    public void publish(String mqttTopic, byte[] message, int qos, boolean retained){
        try {
            mqttClient.publish(mqttTopic, message, qos, retained);
        }catch (Exception e){
            log.error("[%s] Se ha producido un error al publicar el mensaje [%s]", MqttGateway.class.getSimpleName(), Arrays.toString(message), e);
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        if (!mqttMessage.isDuplicate()) {
            try {
                GuardarFirmwareCommand guardarFirmwareCommand =
                        CommandConverter.getConverterInstance().converter(ArrayUtils.toObject(mqttMessage.getPayload()));
                //
                log.info("Recibiendo el firmware Id[%s] ...", guardarFirmwareCommand.getIdDifusion());
                commandHandler.handle(guardarFirmwareCommand);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void connectComplete(boolean b, String host) {
        log.info("[%s] Se ha conectado completamente el agente al host [%s]", MqttGateway.class.getSimpleName(), host);
    }

    @Override
    public void connectionLost(Throwable mqtte) {
        try {
            log.error(mqtte.getMessage(), mqtte);
            log.info("[%s] Mqtt perdio conexion con el host [%s]", MqttSubcriberGateway.class.getSimpleName(), mqttClient.getServerURI());

        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken mqttDeliveryToken) {
        log.info("MQTT: Entrega completa Token id[%s]. ", mqttDeliveryToken.getMessageId());
    }
}
