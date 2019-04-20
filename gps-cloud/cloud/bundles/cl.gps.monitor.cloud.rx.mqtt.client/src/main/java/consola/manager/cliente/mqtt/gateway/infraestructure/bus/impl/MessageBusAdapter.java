package consola.manager.cliente.mqtt.gateway.infraestructure.bus.impl;

import consola.manager.cliente.mqtt.gateway.infraestructure.bus.Bus;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by daniel.carvajal on 29-03-2019.
 */
public abstract class MessageBusAdapter implements Bus {

    @Override
    public void subscribe(Consumer<?> subscriber){
    }

    @Override
    public void subscribe(Function<?, ?> function){
    }

    @Override
    public <T>void handle(T message){
    }

    @Override
    public void handle(MqttMessage message){
    }

    @Override
    public void unSubscribe(){
    }
}
