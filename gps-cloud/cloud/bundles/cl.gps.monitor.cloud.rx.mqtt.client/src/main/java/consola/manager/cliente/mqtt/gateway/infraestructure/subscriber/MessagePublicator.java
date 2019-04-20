package consola.manager.cliente.mqtt.gateway.infraestructure.subscriber;

import consola.manager.cliente.mqtt.gateway.infraestructure.integration.MqttGateway;
import consola.manager.cliente.mqtt.gateway.infraestructure.integration.MqttPublisherOption;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

/**
 * Created by daniel.carvajal on 29-03-2019.
 */
public class MessagePublicator implements MessagePublisher<MqttPublisherOption,IMqttDeliveryToken> {

    private MqttGateway mqttGateway;

    @Override
    public IMqttDeliveryToken apply(MqttPublisherOption option) {
        //mqttGateway.publish(mqttMessage);
        return null;
    }

    public MqttGateway getMqttGateway() {
        return mqttGateway;
    }

    public void setMqttGateway(MqttGateway mqttGateway) {
        this.mqttGateway = mqttGateway;
    }
}
