package consola.manager.cliente.mqtt.gateway.infraestructure.conf.impl;

import consola.manager.cliente.mqtt.gateway.infraestructure.conf.Configuration;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.server.Server;
import io.moquette.server.config.ClasspathResourceLoader;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.IResourceLoader;
import io.moquette.server.config.ResourceLoaderConfig;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * Created by daniel.carvajal on 25-06-2018.
 */
public class MqttServerConfigApp implements Configuration {

    private Server mqttBroker;

    private static final Logger logger = Logger.getLogger(MqttServerConfigApp.class.getCanonicalName());

    @Override
    public void configure() {
        mqttServer();
    }


    public void mqttServer() {
        try {
            logger.info(String.format("Init service   ............... [%s]", Server.class.getSimpleName()));
            IResourceLoader classpathLoader = new ClasspathResourceLoader();
            //
            final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
            mqttBroker = new Server();
            List<? extends InterceptHandler> userHandlers = Collections.singletonList(new PublisherListener());
            mqttBroker.startServer(classPathConfig, userHandlers);
            //
            logger.info(String.format("Estatus service ............... [%s] [%s]",Server.class.getSimpleName(), "UP"));

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.log(Level.SEVERE, String.format("Estatus service ............... [%s] [%s]", Server.class.getSimpleName(), "DOWN"));

        }
    }

    @Override
    public void destroy() {
        try {
            if (Objects.nonNull(mqttBroker)) {
                mqttBroker.stopServer();
            }
            logger.info(String.format("Estatus service ............... [%s] [%s]", Server.class.getSimpleName(), "DOWN"));

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            logger.log(Level.SEVERE, String.format("Estatus service ............... [%s] [%s]", Server.class.getSimpleName(), "DOWN"));
        }
    }

    private class PublisherListener extends AbstractInterceptHandler {

        @Override
        public String getID() {
            return "EmbeddedLauncherPublishListener";
        }

        @Override
        public void onPublish(InterceptPublishMessage message) {
            final String decodedPayload = new String(message.toString().getBytes(), UTF_8);
            logger.info(String.format("Received on topic: [{}] | content: [{}]", message.getTopicName(), decodedPayload));
        }
    }
}
