package consola.manager.cliente.mqtt.gateway.infraestructure.bus.impl;


import java.util.function.Consumer;

/**
 * Created by daniel.carvajal on 26-03-2019.
 */
public class MessageSubscriberBus extends MessageBusAdapter {

    private Consumer consumer;

    private static MessageSubscriberBus mqttSubscriberBus;

    public static MessageSubscriberBus getInstance() {
        if (mqttSubscriberBus == null) {
            mqttSubscriberBus = new MessageSubscriberBus();
        }
        return mqttSubscriberBus;
    }

    @Override
    public void subscribe(Consumer consumer){
        this.consumer = consumer;
    }

    @Override
    public <T>void handle(T message) {
        consumer.accept(message);
    }

    @Override
    public void unSubscribe(){
        consumer = null;
    }
}
