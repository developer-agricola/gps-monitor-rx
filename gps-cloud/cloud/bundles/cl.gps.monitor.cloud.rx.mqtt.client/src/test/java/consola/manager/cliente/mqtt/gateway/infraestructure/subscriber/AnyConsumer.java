package consola.manager.cliente.mqtt.gateway.infraestructure.subscriber;

import java.util.logging.Logger;

/**
 * Created by daniel.carvajal on 28-03-2019.
 */
public class AnyConsumer implements MessageConsumer<String> {

    private static final Logger logger = Logger.getLogger(AnyConsumer.class.getCanonicalName());

    @Override
    public void accept(String message) {
        logger.info(String.format(">> Ha llegado el mensaje  [%s]", message));
    }
}
