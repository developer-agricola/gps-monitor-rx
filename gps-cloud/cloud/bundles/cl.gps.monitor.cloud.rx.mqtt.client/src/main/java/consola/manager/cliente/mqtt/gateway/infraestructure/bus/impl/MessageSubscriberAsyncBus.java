package consola.manager.cliente.mqtt.gateway.infraestructure.bus.impl;

import reactor.core.publisher.*;

import java.util.function.Consumer;

/**
 * Created by daniel.carvajal on 26-03-2019.
 */
public class MessageSubscriberAsyncBus extends MessageBusAdapter {

    private FluxProcessor processor = UnicastProcessor.create();

    private static MessageSubscriberAsyncBus mqttSubscriberBus;

    public static MessageSubscriberAsyncBus getInstance() {
        if (mqttSubscriberBus == null) {
            mqttSubscriberBus = new MessageSubscriberAsyncBus();
        }
        return mqttSubscriberBus;
    }

    @Override
    public void subscribe(Consumer<?> subscriber){
        processor
                .publish()
                    .autoConnect()
                .subscribe(subscriber);
    }

    @Override
    public <T>void handle(T message) {
        processor.onNext(message);
    }

    @Override
    public void unSubscribe(){
        processor.dispose();
    }
}
