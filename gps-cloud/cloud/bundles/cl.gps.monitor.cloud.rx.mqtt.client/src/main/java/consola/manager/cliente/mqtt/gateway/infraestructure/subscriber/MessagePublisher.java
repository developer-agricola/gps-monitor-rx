package consola.manager.cliente.mqtt.gateway.infraestructure.subscriber;

import java.util.function.Function;

/**
 * Created by daniel.carvajal on 29-03-2019.
 */
public interface MessagePublisher<T, R> extends Function<T, R> {
}
