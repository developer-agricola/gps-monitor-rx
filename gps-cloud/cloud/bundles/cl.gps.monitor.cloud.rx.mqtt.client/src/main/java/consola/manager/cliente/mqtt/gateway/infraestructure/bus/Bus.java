package consola.manager.cliente.mqtt.gateway.infraestructure.bus;


import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by daniel.carvajal on 29-03-2019.
 */
public interface Bus {

    void subscribe(Consumer<?> subscriber);

    void subscribe(Function<?, ?> function);

    <T>void handle(T message);

    void handle(MqttMessage message);

    void unSubscribe();

}
