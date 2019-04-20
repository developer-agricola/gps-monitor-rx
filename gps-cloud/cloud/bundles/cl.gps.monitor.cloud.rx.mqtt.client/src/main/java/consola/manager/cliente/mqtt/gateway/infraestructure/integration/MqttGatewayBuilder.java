package consola.manager.cliente.mqtt.gateway.infraestructure.integration;

import consola.manager.cliente.mqtt.gateway.infraestructure.bus.Bus;
import consola.manager.cliente.mqtt.gateway.infraestructure.bus.impl.MessageSubscriberAsyncBus;
import consola.manager.cliente.mqtt.gateway.infraestructure.bus.impl.MessageSubscriberBus;
import consola.manager.cliente.mqtt.gateway.infraestructure.enums.MessageBusStrategy;
import consola.manager.cliente.mqtt.gateway.infraestructure.subscriber.MessageConsumer;
import consola.manager.cliente.mqtt.gateway.infraestructure.subscriber.MessagePublicator;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.util.Debug;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by daniel.carvajal on 21-03-2019.
 */
public class MqttGatewayBuilder {

    public String host;
    public int port;
    public String identifier;

    public boolean memoryPersistence;

    public boolean debug;

    public boolean retryAndWait;

    // connect options
    public MqttConnectOptions connectOptions;

    // buffer options
    public DisconnectedBufferOptions bufferOptions;

    // subscriber options
    public MqttSubscriberOptions subscriberOptions;

    // publisher options
    public MqttPublisherOptions publisherOptions;

    // subscriber
    public MessageBusStrategy subscriberBusStrategy;

    public MessageConsumer messageConsumer;

    // subscriber
    public MessageBusStrategy publisherBusStrategy;

    private MqttGateway mqttGateway;

    private static MqttGatewayBuilder mqttGatewayBuilder;

    private static final String PROTOCOL = "tcp";

    private static final Logger logger = Logger.getLogger(MqttGatewayBuilder.class.getCanonicalName());

    public static MqttGatewayBuilder getInstance() {
        if (mqttGatewayBuilder == null) {
            mqttGatewayBuilder = new MqttGatewayBuilder();
        }
        return mqttGatewayBuilder;
    }

    public MqttGatewayBuilder with(Consumer<MqttGatewayBuilder> builder) {
        builder.accept(this);
        return this;
    }

    public MqttGateway create() {
        MqttAsyncClient mqttAsyncClient = null;
        String serverUri = String.format("%s://%s:%s", PROTOCOL, host, port);
        //
        mqttGateway = new MqttGateway();
        try {
            // persistence type
            if(!memoryPersistence) {
                mqttAsyncClient = new MqttAsyncClient(serverUri, identifier, new MqttDefaultFilePersistence());
            }else{
                mqttAsyncClient = new MqttAsyncClient(serverUri, identifier, new MemoryPersistence());
            }

            // create mqtt client and attach
            mqttGateway.setBufferOptions(bufferOptions);
            mqttGateway.setConnectOptions(connectOptions);
            mqttGateway.setSubscriberOptions(subscriberOptions);
            mqttGateway.setPublisherOptions(publisherOptions);
            //
            mqttGateway.setMqttAsyncClient(mqttAsyncClient);
            if(!retryAndWait) {
                mqttGateway.attach();
            }else{
                //TODO colocar un saveAttach
            }
            // message publisher
            createMessagePublisher();

            // message subscriber
            createMessageSubscriber();

            if(debug){
                Debug cDebug = mqttAsyncClient.getDebug();
                cDebug.dumpClientDebug();
            }

        } catch (MqttException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Error en la creacion del cliente Mqtt!!!", MqttAsyncClient.class.getSimpleName()));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format("[%s] Error en la creacion del cliente Mqtt!!!", MqttAsyncClient.class.getSimpleName()));
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return mqttGateway;
    }

    public void createMessageSubscriber(){
        Bus messageSubscriberBus = null;
        if(Objects.nonNull(subscriberBusStrategy)) {
            switch (subscriberBusStrategy) {
                case SECUENCE_STRATEGY:
                    messageSubscriberBus = MessageSubscriberBus.getInstance();
                    messageSubscriberBus.subscribe(messageConsumer);
                case ASYNCHRONOUS_STRATEGY:
                    messageSubscriberBus = MessageSubscriberAsyncBus.getInstance();
                    messageSubscriberBus.subscribe(messageConsumer);
                    break;
                case PARALLEL_ASYNCRONUS_STRATEGY:
                    //TODO reaalizar
                    break;
                default:
                    // default strategy
                    messageSubscriberBus = MessageSubscriberBus.getInstance();
                    messageSubscriberBus.subscribe(messageConsumer);
                    break;
            }
        }else{
            // default strategy
            messageSubscriberBus = MessageSubscriberBus.getInstance();
            messageSubscriberBus.subscribe(messageConsumer);
        }
        mqttGateway.setMessageSubscriberBus(messageSubscriberBus);
        mqttGateway.setMessageConsumer(messageConsumer);
    }

    public void createMessagePublisher(){
        Bus messagePublisherBus = null;
        MessagePublicator messagePublicator = new MessagePublicator();
        messagePublicator.setMqttGateway(mqttGateway);
        //
        if(Objects.nonNull(publisherBusStrategy)) {
            switch (publisherBusStrategy) {
                case SECUENCE_STRATEGY:
                    messagePublisherBus = MessageSubscriberBus.getInstance();
                    messagePublisherBus.subscribe(messagePublicator);
                case ASYNCHRONOUS_STRATEGY:
                    //TODO realizar
                    break;
                case PARALLEL_ASYNCRONUS_STRATEGY:
                    //TODO reaalizar
                    break;
                default:
                    // default strategy
                    messagePublisherBus = MessageSubscriberBus.getInstance();
                    messagePublisherBus.subscribe(messagePublicator);
                    break;
            }
        }else{
            // default strategy
            messagePublisherBus = MessageSubscriberBus.getInstance();
            messagePublisherBus.subscribe(messagePublicator);
        }
        mqttGateway.setMessagePublicator(messagePublicator);

    }

    public static class ConnectOptionsBuilder {
        public boolean cleanSession;
        public int keepAliveInterval;
        public boolean automaticReconnect;
        public int connectionTimeout;
        public int maxInflight;

        public ConnectOptionsBuilder(){
            // default values
            this.cleanSession = true;
            this.keepAliveInterval = 60; //seg
            this.automaticReconnect = false;
            this.connectionTimeout = 30;
            this.maxInflight = 10;
        }

        public ConnectOptionsBuilder with(Consumer<ConnectOptionsBuilder> builder) {
            builder.accept(this);
            return this;
        }

        public MqttConnectOptions createConnectOptions() {
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(cleanSession);
            mqttConnectOptions.setKeepAliveInterval(keepAliveInterval);
            mqttConnectOptions.setAutomaticReconnect(automaticReconnect);
            mqttConnectOptions.setConnectionTimeout(connectionTimeout);
            mqttConnectOptions.setMaxInflight(maxInflight);
            return mqttConnectOptions;
        }
    }

    public static class BufferOptionsBuilder {
        public boolean bufferEnabled;
        public boolean persistBuffer;
        public int bufferSize;
        public boolean deleteOldestMessages;

        public BufferOptionsBuilder(){
            // default values
            this.bufferEnabled = false;
            this.persistBuffer = false;
            this.bufferSize = 5000;
            this.deleteOldestMessages = false;
        }

        public BufferOptionsBuilder with(Consumer<BufferOptionsBuilder> builder) {
            builder.accept(this);
            return this;
        }

        public DisconnectedBufferOptions createBufferOptions() {
            DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
            disconnectedBufferOptions.setBufferEnabled(bufferEnabled);
            disconnectedBufferOptions.setPersistBuffer(persistBuffer);
            disconnectedBufferOptions.setBufferSize(bufferSize);
            disconnectedBufferOptions.setDeleteOldestMessages(deleteOldestMessages);
            return disconnectedBufferOptions;
        }
    }

    public static class SubscriberOptionsBuilder {
        public int qos;
        public String topicFilter;
        public Object userContext;
        public IMqttActionListener callback;
        public IMqttMessageListener messageListener;

        public SubscriberOptionsBuilder with(Consumer<SubscriberOptionsBuilder> builder) {
            builder.accept(this);
            return this;
        }

        public MqttSubscriberOptions createSubcriberOptions() {
            MqttSubscriberOption subscriberOption = new MqttSubscriberOption();
            //TODO add 1..n options
            subscriberOption.setIndex(0); // default option

            subscriberOption.setQos(qos);
            subscriberOption.setTopicFilter(topicFilter);
            subscriberOption.setUserContext(userContext);
            subscriberOption.setCallback(callback);
            subscriberOption.setMessageListener(messageListener);

            //TODO add 1..n options
            MqttSubscriberOptions subscriberOptions = new MqttSubscriberOptions();
            subscriberOptions.addOption(subscriberOption);
            //
            return subscriberOptions;
        }
    }

    public static class PublisherOptionsBuilder {
        public int qos;
        public String topicFilter;
        public boolean retained;
        public Object userContext;
        public IMqttActionListener callback;
        public IMqttMessageListener messageListener;

        public PublisherOptionsBuilder with(Consumer<PublisherOptionsBuilder> builder) {
            builder.accept(this);
            return this;
        }

        public MqttPublisherOptions createPublisherOptions() {
            MqttPublisherOption publisherOption = new MqttPublisherOption();
            //TODO add 1..n options
            publisherOption.setIndex(0); // default option

            publisherOption.setQos(qos);
            publisherOption.setTopicFilter(topicFilter);
            publisherOption.setRetained(retained);
            publisherOption.setUserContext(userContext);
            publisherOption.setCallback(callback);
            publisherOption.setMessageListener(messageListener);

            //TODO add 1..n options
            MqttPublisherOptions publisherOptions = new MqttPublisherOptions();
            publisherOptions.addOption(publisherOption);
            //
            return publisherOptions;
        }
    }


}
