package consola.manager.cliente.mqtt.gateway.infraestructure.integration;

import consola.manager.cliente.mqtt.gateway.infraestructure.bus.Bus;
import consola.manager.cliente.mqtt.gateway.infraestructure.enums.MessageBusStrategy;
import consola.manager.cliente.mqtt.gateway.infraestructure.subscriber.MessageConsumer;
import consola.manager.cliente.mqtt.gateway.infraestructure.subscriber.MessagePublicator;
import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MqttGateway implements Gateway, MqttCallbackExtended {

    // mqtt client
    private MqttAsyncClient mqttAsyncClient;
    private MqttConnectOptions connectOptions;
    private DisconnectedBufferOptions bufferOptions;

    // subscriber
    private Bus messageSubscriberBus;
    private MessageConsumer messageConsumer;
    private MessageBusStrategy subscriberBusStrategy;
    //
    private MqttSubscriberOptions subscriberOptions;

    // message publisher
    private Bus messagePublisherBus;
    private MessagePublicator messagePublicator;
    private MessageBusStrategy publisherBusStrategy;
    //
    private MqttPublisherOptions publisherOptions;


    private static final Logger logger = Logger.getLogger(MqttGateway.class.getCanonicalName());

    public void attach() throws MqttException {
        try {
            // set default buffer options
            mqttAsyncClient.setBufferOpts(bufferOptions);

            // set callback
            mqttAsyncClient.setCallback(this);

            // connect
            mqttAsyncClient.connect(connectOptions).waitForCompletion();

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al attach del cliente Mqtt!!!", MqttGateway.class.getSimpleName()));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al attach del cliente Mqtt!!!", MqttGateway.class.getSimpleName()));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public boolean isConnected() {
        boolean isConnected = false;
        try {
            if (Objects.nonNull(mqttAsyncClient)) {
                isConnected = mqttAsyncClient.isConnected();
            }
        } catch (Exception e) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, String.format("[%s] no esta conectado el cliente Mqtt!!!  El cliente esta desconectado!!!", MqttGateway.class.getSimpleName()));
                logger.log(Level.WARNING, e.getMessage(), e);
            }
        }
        return isConnected;
    }

    public void detach() throws MqttException {
        try {
            mqttAsyncClient.disconnect();
            mqttAsyncClient.close(true);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al detach del cliente Mqtt!!!", MqttGateway.class.getSimpleName()));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al detach del cliente Mqtt!!!", MqttGateway.class.getSimpleName()));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public void reconnect() throws MqttException {
        try {
            mqttAsyncClient.reconnect();

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al reconnect del cliente Mqtt!!!", MqttGateway.class.getSimpleName()));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al reconnect del cliente Mqtt!!!", MqttGateway.class.getSimpleName()));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public MqttGateway subscribeWithOptions() throws MqttException {
        if (Objects.nonNull(subscriberOptions)) {
            MqttSubscriberOption option = subscriberOptions.getDefaultOption();
            if (Objects.nonNull(option) && option.isValid()) {
                subscribe(option.getTopicFilter(), option.getQos());
            }
            //TODO add more subcription related with parameters

        } else {
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, String.format("[%s] Las optiones de subcripcion al cliente Mqtt no deberian ser nulas!!!", MqttAsyncClient.class.getSimpleName()));
            }
        }
        return this;
    }


    public void publishWithOptions(String payload) throws MqttException {
        if (Objects.nonNull(publisherOptions)) {
            MqttPublisherOption option = publisherOptions.getDefaultOption();
            if (Objects.nonNull(option) && option.isValid()) {
               MqttMessage mqttMessage = new MqttMessage();
                // set options
                //mqttMessage.setId();
                mqttMessage.setQos(option.getQos());
                mqttMessage.setRetained(option.isRetained());
                mqttMessage.setPayload(payload.getBytes());
                //
                messagePublisherBus.handle(mqttMessage);
            }
            if (logger.isLoggable(Level.FINEST)) {
                //TODO add more publish  information related with parameters
            }
        } else {
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, String.format("[%s] Las optiones de publicacion al cliente Mqtt no deberian ser nulas!!!", MqttAsyncClient.class.getSimpleName()));
            }
        }
    }

    public void subscribe(String topicFilter, int qos) throws MqttException {
        try {
            mqttAsyncClient.subscribe(topicFilter, qos);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al subscribir al topico[%s] del cliente Mqtt!!!", MqttGateway.class.getSimpleName(), topicFilter));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al subscribir al topico[%s] del cliente Mqtt!!!", MqttGateway.class.getSimpleName(), topicFilter));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public void subscribe(String topicFilter, int qos, IMqttMessageListener messageListener) throws MqttException {
        try {
            IMqttToken token = mqttAsyncClient.subscribe(topicFilter, qos, messageListener);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al subscribir al topico[%s] del cliente Mqtt!!!", MqttGateway.class.getSimpleName(), topicFilter));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al subscribir al topico[%s] del cliente Mqtt!!!", MqttGateway.class.getSimpleName(), topicFilter));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public void subscribe(String topicFilter, int qos, Object userContext, IMqttActionListener callback) throws MqttException {
        try {
            mqttAsyncClient.subscribe(topicFilter, qos, userContext, callback);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al subscribir al topico[%s] del cliente Mqtt!!!", MqttGateway.class.getSimpleName(), topicFilter));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al subscribir al topico[%s] del cliente Mqtt!!!", MqttGateway.class.getSimpleName(), topicFilter));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public void subscribe(String topicFilter, int qos, Object userContext, IMqttActionListener callback, IMqttMessageListener messageListener) throws MqttException {
        try {
            mqttAsyncClient.subscribe(topicFilter, qos, userContext, callback);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al subscribir al topico[%s] del cliente Mqtt!!!", MqttGateway.class.getSimpleName(), topicFilter));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Se ha producido un error al subscribir al topico[%s] del cliente Mqtt!!!", MqttGateway.class.getSimpleName(), topicFilter));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public void publish(String topic, byte[] payload, int qos, boolean retained) throws MqttException {
        try {
            mqttAsyncClient.publish(topic, payload, qos, retained);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE,
                        String.format("[%s] Se ha producido un error al publicar el mensaje [%s] en el topico [%s]", MqttGateway.class.getSimpleName(), Arrays.toString(payload), topic));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE,
                        String.format("[%s] Se ha producido un error al publicar el mensaje [%s] en el topico [%s]", MqttGateway.class.getSimpleName(), Arrays.toString(payload), topic));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public void publish(String topic, byte[] payload, int qos, boolean retained, Object userContext, IMqttActionListener callback) throws MqttException {
        try {
            mqttAsyncClient.publish(topic, payload, qos, retained, userContext, callback);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE,
                        String.format("[%s] Se ha producido un error al publicar el mensaje [%s] en el topico [%s]", MqttGateway.class.getSimpleName(), Arrays.toString(payload), topic));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE,
                        String.format("[%s] Se ha producido un error al publicar el mensaje [%s] en el topico [%s]", MqttGateway.class.getSimpleName(), Arrays.toString(payload), topic));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public void publish(String topic, MqttMessage message) throws MqttException {
        try {
            mqttAsyncClient.publish(topic, message);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE,
                        String.format("[%s] Se ha producido un error al publicar el mensaje [%s] en el topico [%s]", MqttGateway.class.getSimpleName(), message.toString(), topic));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE,
                        String.format("[%s] Se ha producido un error al publicar el mensaje [%s] en el topico [%s]", MqttGateway.class.getSimpleName(), message.toString(), topic));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    public void publish(String topic, MqttMessage message, Object userContext, IMqttActionListener callback) throws MqttException {
        try {
            mqttAsyncClient.publish(topic, message, userContext, callback);

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE,
                        String.format("[%s] Se ha producido un error al publicar el mensaje [%s] en el topico [%s]", MqttGateway.class.getSimpleName(), message.toString(), topic));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;

        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE,
                        String.format("[%s] Se ha producido un error al publicar el mensaje [%s] en el topico [%s]", MqttGateway.class.getSimpleName(), message.toString(), topic));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            throw e;
        }
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        if (!mqttMessage.isDuplicate()) {
            messageSubscriberBus.handle(new String(mqttMessage.getPayload(), StandardCharsets.UTF_8));
        }else{
            if (logger.isLoggable(Level.FINEST)) {
                logger.log(Level.FINEST,
                        String.format("[%s] Se ha detectado un mensaje[%s] duplicado!!!", MqttGateway.class.getSimpleName()), mqttMessage.toString());
            }
        }
    }

    @Override
    public void connectComplete(boolean b, String host) {
        logger.info(String.format("[%s] Se ha conectado completamente el cliente al host [%s]", MqttGateway.class.getSimpleName(), host));
    }

    @Override
    public void connectionLost(Throwable mqtte) {
        try {
            logger.info(String.format("[%s] Mqtt perdio conexion con el host [%s]", MqttGateway.class.getSimpleName(), mqttAsyncClient.getServerURI()));
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, mqtte.getMessage(), mqtte);
            }
        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken mqttDeliveryToken) {
        logger.info(String.format("MQTT: Entrega completa Token id[%s]. ", mqttDeliveryToken.getMessageId()));
    }

    public MqttAsyncClient getMqttAsyncClient() {
        return mqttAsyncClient;
    }

    public void setMqttAsyncClient(MqttAsyncClient mqttAsyncClient) {
        this.mqttAsyncClient = mqttAsyncClient;
    }

    public MqttConnectOptions getConnectOptions() {
        return connectOptions;
    }

    public void setConnectOptions(MqttConnectOptions connectOptions) {
        this.connectOptions = connectOptions;
    }

    public DisconnectedBufferOptions getBufferOptions() {
        return bufferOptions;
    }

    public void setBufferOptions(DisconnectedBufferOptions bufferOptions) {
        this.bufferOptions = bufferOptions;
    }

    public MqttSubscriberOptions getSubscriberOptions() {
        return subscriberOptions;
    }

    public void setSubscriberOptions(MqttSubscriberOptions subscriberOptions) {
        this.subscriberOptions = subscriberOptions;
    }

    public MqttPublisherOptions getPublisherOptions() {
        return publisherOptions;
    }

    public void setPublisherOptions(MqttPublisherOptions publisherOptions) {
        this.publisherOptions = publisherOptions;
    }

    public Bus getMessageSubscriberBus() {
        return messageSubscriberBus;
    }

    public void setMessageSubscriberBus(Bus messageSubscriberBus) {
        this.messageSubscriberBus = messageSubscriberBus;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public void setMessageConsumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public MessageBusStrategy getSubscriberBusStrategy() {
        return subscriberBusStrategy;
    }

    public void setSubscriberBusStrategy(MessageBusStrategy subscriberBusStrategy) {
        this.subscriberBusStrategy = subscriberBusStrategy;
    }

    public Bus getMessagePublisherBus() {
        return messagePublisherBus;
    }

    public void setMessagePublisherBus(Bus messagePublisherBus) {
        this.messagePublisherBus = messagePublisherBus;
    }

    public MessagePublicator getMessagePublicator() {
        return messagePublicator;
    }

    public void setMessagePublicator(MessagePublicator messagePublicator) {
        this.messagePublicator = messagePublicator;
    }

    public MessageBusStrategy getPublisherBusStrategy() {
        return publisherBusStrategy;
    }

    public void setPublisherBusStrategy(MessageBusStrategy publisherBusStrategy) {
        this.publisherBusStrategy = publisherBusStrategy;
    }
}
