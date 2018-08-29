package cl.gps.monitor.rx.message.routing.infrastructure.integration.mqtt;

import com.sonda.transporte.consola.agente.firmware.application.converter.ToHostNameConverter;
import com.sonda.transporte.consola.agente.firmware.infrastructure.handler.CommandHandler;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.Gateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Properties;

@Getter
@Setter
public class MqttSubcriberGateway implements Gateway {

    private MqttClient mqttClient;

    private CommandHandler commandHandler;

    public static final Properties appProperties = UtilProperties.getProperties();

    protected static final UtilLogger log = UtilLogger.getLogger(MqttSubcriberGateway.class);


    public static MqttSubcriberGateway newGateway(){
        return new MqttSubcriberGateway();
    }

    public void initialize() throws MqttException {
        String identifier = MqttClient.generateClientId()
                .concat(ToHostNameConverter.getConverterInstance().converter(
                                appProperties.getProperty(UtilProperties.MQTT_IDENTIFIER_URL_PROPERTY_KEY)));
        mqttClient =new MqttClient(
                appProperties.getProperty(UtilProperties.MQTT_SERVER_URL_PROPERTY_KEY), identifier, new MemoryPersistence());

        attach();

        String topicFilter = ToHostNameConverter.getConverterInstance().converter(appProperties.getProperty(UtilProperties.MQTT_TOPIC_SUSCRIBER_PROPERTY_KEY));
        int qos = Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_TOPIC_SUBCRIBER_QOS_PROPERTY_KEY));

        mqttClient.subscribe(topicFilter, qos);
        mqttClient.setCallback(commandHandler);
    }

    public boolean isConnected() {
        boolean isConnected = false;
        try{
            isConnected = mqttClient.isConnected();

        }catch (Exception e){
            log.error(" [%s] is not connected", MqttSubcriberGateway.class.getSimpleName());
            log.error(e.getMessage(), e);
        }
        return isConnected;
    }

    public void attach() throws MqttException {
        mqttClient.connect();
    }

    public void detach() throws MqttException {
        mqttClient.disconnect();
    }

    public void reconnect() throws MqttException {
        try {
            mqttClient.reconnect();
        }catch (Exception e){
            initialize();
            throw e;
        }
    }
}
