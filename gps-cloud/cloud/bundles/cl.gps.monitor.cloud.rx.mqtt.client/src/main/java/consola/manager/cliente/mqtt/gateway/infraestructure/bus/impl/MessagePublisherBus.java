package consola.manager.cliente.mqtt.gateway.infraestructure.bus.impl;

import consola.manager.cliente.mqtt.gateway.infraestructure.integration.MqttPublisherOption;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import java.util.function.Function;

/**
 * Created by daniel.carvajal on 26-03-2019.
 */
public class MessagePublisherBus extends MessageBusAdapter {

    private Function<MqttPublisherOption,IMqttDeliveryToken> function;

    private static MessagePublisherBus mqttPublisherBus;

    public static MessagePublisherBus getInstance() {
        if (mqttPublisherBus == null) {
            mqttPublisherBus = new MessagePublisherBus();
        }
        return mqttPublisherBus;
    }

    @Override
    public void subscribe(Function<?, ?> subscriber){
        this.function = function;
    }

    /*@Override
    public void handle(MqttPublisherOption option) {
        // TODO to define
        function.apply(option);
    }*/

    @Override
    public void unSubscribe(){
        function = null;
    }
}
