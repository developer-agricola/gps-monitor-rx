package cl.gps.monitor.rx.message.routing.infrastructure.integration.mqtt;

import com.sonda.transporte.consola.agente.firmware.application.converter.EventConverter;
import com.sonda.transporte.consola.agente.firmware.application.converter.FromDifusionConverter;
import com.sonda.transporte.consola.agente.firmware.application.converter.ToHostNameConverter;
import com.sonda.transporte.consola.agente.firmware.application.event.Event;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.DifusionDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.Gateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Properties;

@Getter
@Setter
public class MqttPublisherGateway implements Gateway {

    private MqttClient mqttClient;

    private MqttTopic mqttTopic;

    private Properties appProperties = UtilProperties.getProperties();

    public static final UtilLogger log = UtilLogger.getLogger(MqttPublisherGateway.class);

    public static MqttPublisherGateway newGateway(){
        return new MqttPublisherGateway();
    }

    public void initialize() throws MqttException {
        String identifier = MqttClient.generateClientId()
                .concat(
                        ToHostNameConverter.getConverterInstance().converter(
                                appProperties.getProperty(UtilProperties.MQTT_IDENTIFIER_URL_PROPERTY_KEY)));
        mqttClient = new MqttClient(
                appProperties.getProperty(UtilProperties.MQTT_SERVER_URL_PROPERTY_KEY), identifier, new MemoryPersistence());

        attach();
        //
        mqttTopic = mqttClient.getTopic(ToHostNameConverter.getConverterInstance().converter(
                                    appProperties.getProperty(UtilProperties.MQTT_TOPIC_PUBLISHER_PROPERTY_KEY)));
    }

    public boolean isConnected(){
        boolean isConnected = false;
        try{
            isConnected = mqttClient.isConnected();

        }catch (Exception e){
            log.error(" [%s] is not connected", MqttPublisherGateway.class.getSimpleName());
            log.error(e.getMessage(), e);
        }
        return isConnected;
    }

    public void notify(byte[] message) throws MqttException {
        int qos = Integer.parseInt(appProperties.getProperty(UtilProperties.MQTT_TOPIC_PUBLISHER_QOS_PROPERTY_KEY));
        boolean retention = Boolean.getBoolean(appProperties.getProperty(UtilProperties.MQTT_TOPIC_PUBLISHER_RETENTION_PROPERTY_KEY));
        //
        mqttTopic.publish(message, qos, retention);
    }

    public void attach() throws MqttException {
        mqttClient.connect();
    }

    public void detach() throws MqttException {
        mqttClient.disconnect();
    }

    public byte[] createByteMessage(Event event) {
        return ArrayUtils.toPrimitive(EventConverter.getConverterInstance().converter(event));
    }

    public byte[] createByteMessage(DifusionDTO difusionDto) {
        return ArrayUtils.toPrimitive(FromDifusionConverter.getConverterInstance().converter(difusionDto));
    }

    public void release() throws MqttException {
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
