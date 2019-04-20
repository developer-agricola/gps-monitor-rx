package consola.manager.cliente.mqtt.gateway.infraestructure.integration;

import consola.manager.cliente.mqtt.gateway.infraestructure.conf.impl.MqttServerConfigApp;
import consola.manager.cliente.mqtt.gateway.infraestructure.conf.impl.PropertiesConfigApp;
import consola.manager.cliente.mqtt.gateway.infraestructure.enums.MessageBusStrategy;
import consola.manager.cliente.mqtt.gateway.infraestructure.subscriber.AnyConsumer;
import junit.framework.TestCase;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Test;

/**
 * Created by daniel.carvajal on 20-06-2018.
 */
public class MqttGatewayITest extends TestCase {

    private MqttServerConfigApp mqttServerConfigApp;

    @Override
    protected void setUp() throws Exception {

    }

    @Test
    public void testSubscriberOkWithConnectio() throws MqttException {
        // log properties
        PropertiesConfigApp propertiesConfigApp = new PropertiesConfigApp();
        propertiesConfigApp.configure();
        // start local mqtt
        mqttServerConfigApp = new MqttServerConfigApp();
        mqttServerConfigApp.configure();

        MqttGateway mqttGateway = MqttGatewayBuilder.getInstance()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                })
                .with($ -> {
                    $.debug = false;
                })
                .with($ -> {
                    $.connectOptions = new MqttGatewayBuilder.ConnectOptionsBuilder()
                            .with($cOptions -> {
                                $cOptions.cleanSession = true;
                                $cOptions.keepAliveInterval = 60; //seg
                                $cOptions.automaticReconnect = true;
                                $cOptions.connectionTimeout = 30;
                                $cOptions.maxInflight = 1000;
                            }).createConnectOptions();
                })
                .with($ -> {
                    $.bufferOptions = new MqttGatewayBuilder.BufferOptionsBuilder()
                            .with($bOptions -> {
                                $bOptions.bufferEnabled = false;
                            }).createBufferOptions();
                })
                .with($ -> {
                    $.subscriberOptions = new MqttGatewayBuilder.SubscriberOptionsBuilder()
                            .with($sOptions -> {
                                $sOptions.topicFilter = "/topicoMock";
                                $sOptions.qos = 2;
                            }).createSubcriberOptions();
                })
                .create()
         .subscribeWithOptions();
        //
        mqttGateway.detach();
        mqttServerConfigApp.destroy();
    }

    @Test
    public void testPublisherOkWithConnection() throws MqttException {
        // log properties
        PropertiesConfigApp propertiesConfigApp = new PropertiesConfigApp();
        propertiesConfigApp.configure();
        // start local mqtt
        mqttServerConfigApp = new MqttServerConfigApp();
        mqttServerConfigApp.configure();

        MqttGateway mqttGateway = MqttGatewayBuilder.getInstance()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                })
                .with($ -> {
                    $.debug = false;
                })
                .with($ -> {
                    $.connectOptions = new MqttGatewayBuilder.ConnectOptionsBuilder()
                            .with($cOptions -> {
                                $cOptions.cleanSession = true;
                                $cOptions.keepAliveInterval = 60; //seg
                                $cOptions.automaticReconnect = true;
                                $cOptions.connectionTimeout = 30;
                                $cOptions.maxInflight = 1000;
                            }).createConnectOptions();
                })
                .with($ -> {
                    $.bufferOptions = new MqttGatewayBuilder.BufferOptionsBuilder()
                            .with($bOptions -> {
                                $bOptions.bufferEnabled = false;
                            }).createBufferOptions();
                })
                .with($ -> {
                    $.publisherOptions = new MqttGatewayBuilder.PublisherOptionsBuilder()
                            .with($pOptions -> {
                                $pOptions.topicFilter = "/topicoMock";
                                $pOptions.qos = 2;
                                $pOptions.retained = false;
                            }).createPublisherOptions();
                })
         .create();
        //
        mqttGateway.publishWithOptions("Hello Word!!!");

        //
        mqttGateway.detach();
        mqttServerConfigApp.destroy();
    }


    @Test
    public void testSubcriberPublisherOkWithConnection() throws MqttException, InterruptedException {
        // log properties
        PropertiesConfigApp propertiesConfigApp = new PropertiesConfigApp();
        propertiesConfigApp.configure();
        // start local mqtt
        mqttServerConfigApp = new MqttServerConfigApp();
        mqttServerConfigApp.configure();

        MqttGateway mqttGateway = MqttGatewayBuilder.getInstance()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                })
                .with($ -> {
                    $.debug = false;
                })
                .with($ -> {
                    $.connectOptions = new MqttGatewayBuilder.ConnectOptionsBuilder()
                            .with($cOptions -> {
                                $cOptions.cleanSession = true;
                                $cOptions.keepAliveInterval = 60; //seg
                                $cOptions.automaticReconnect = true;
                                $cOptions.connectionTimeout = 30;
                                $cOptions.maxInflight = 1000;
                            }).createConnectOptions();
                })
                .with($ -> {
                    $.bufferOptions = new MqttGatewayBuilder.BufferOptionsBuilder()
                            .with($bOptions -> {
                                $bOptions.bufferEnabled = false;
                            }).createBufferOptions();
                })
                .with($ -> {
                    $.messageConsumer = new AnyConsumer();
                })
                .with($ -> {
                    $.subscriberOptions = new MqttGatewayBuilder.SubscriberOptionsBuilder()
                            .with($sOptions -> {
                                $sOptions.topicFilter = "/topicoMock";
                                $sOptions.qos = 0;
                            }).createSubcriberOptions();
                })
                .with($ -> {
                    $.publisherOptions = new MqttGatewayBuilder.PublisherOptionsBuilder()
                            .with($pOptions -> {
                                $pOptions.topicFilter = "/topicoMock";
                                $pOptions.qos = 0;
                                $pOptions.retained = false;
                            }).createPublisherOptions();
                })
                .create()
        .subscribeWithOptions();
        //
        mqttGateway.publishWithOptions("Hello Word!!!");

        // wait for message
        Thread.sleep(1000);
        mqttGateway.detach();

        mqttServerConfigApp.destroy();
    }

    @Test
    public void testSubcriberAsyncPublisherOkWithConnection() throws MqttException, InterruptedException {
        // log properties
        PropertiesConfigApp propertiesConfigApp = new PropertiesConfigApp();
        propertiesConfigApp.configure();
        //

        MqttGateway mqttGateway = MqttGatewayBuilder.getInstance()
                .with($ -> {
                    // config desa
                    $.host = "10.162.12.70";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                })
                .with($ -> {
                    $.debug = false;
                    $.memoryPersistence = false;
                })
                .with($ -> {
                    $.connectOptions = new MqttGatewayBuilder.ConnectOptionsBuilder()
                            .with($cOptions -> {
                                $cOptions.cleanSession = true;
                                $cOptions.keepAliveInterval = 60; //seg
                                $cOptions.automaticReconnect = true;
                                $cOptions.connectionTimeout = 30;
                                $cOptions.maxInflight = 1000;
                            }).createConnectOptions();
                })
                .with($ -> {
                    $.bufferOptions = new MqttGatewayBuilder.BufferOptionsBuilder()
                            .with($bOptions -> {
                                $bOptions.bufferEnabled = true;
                                $bOptions.persistBuffer = true;
                                $bOptions.bufferSize = 100;
                            }).createBufferOptions();
                })
                .with($ -> {
                    $.subscriberBusStrategy = MessageBusStrategy.ASYNCHRONOUS_STRATEGY;
                    $.messageConsumer = new AnyConsumer();
                })
                .with($ -> {
                    $.subscriberOptions = new MqttGatewayBuilder.SubscriberOptionsBuilder()
                            .with($sOptions -> {
                                $sOptions.topicFilter = "/topicoMock";
                                $sOptions.qos = 0;
                            }).createSubcriberOptions();
                })
                .with($ -> {
                    $.publisherOptions = new MqttGatewayBuilder.PublisherOptionsBuilder()
                            .with($pOptions -> {
                                $pOptions.topicFilter = "/topicoMock";
                                $pOptions.qos = 0;
                                $pOptions.retained = false;
                            }).createPublisherOptions();
                })
                .create()
        .subscribeWithOptions();
        //
        mqttGateway.publishWithOptions("1.- Hello Word!!! ");
        mqttGateway.publishWithOptions("2.- Hello Word!!! ");
        mqttGateway.publishWithOptions("3.- Hello Word!!! ");
        mqttGateway.publishWithOptions("4.- Hello Word!!! ");

        Thread.sleep(10000);
    }

    @Test
    public void testSubcriberAsyncPublisherOkWithConnection20Messages() throws MqttException, InterruptedException {
        // log properties
        PropertiesConfigApp propertiesConfigApp = new PropertiesConfigApp();
        propertiesConfigApp.configure();
        //

        MqttGateway mqttGateway = MqttGatewayBuilder.getInstance()
                .with($ -> {
                    // config desa
                    $.host = "10.162.12.70";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                })
                .with($ -> {
                    $.debug = false;
                    $.memoryPersistence = false;
                })
                .with($ -> {
                    $.connectOptions = new MqttGatewayBuilder.ConnectOptionsBuilder()
                            .with($cOptions -> {
                                $cOptions.cleanSession = true;
                                $cOptions.keepAliveInterval = 60; //seg
                                $cOptions.automaticReconnect = true;
                                $cOptions.connectionTimeout = 30;
                                $cOptions.maxInflight = 1000;
                            }).createConnectOptions();
                })
                .with($ -> {
                    $.bufferOptions = new MqttGatewayBuilder.BufferOptionsBuilder()
                            .with($bOptions -> {
                                $bOptions.bufferEnabled = true;
                                $bOptions.persistBuffer = true;
                                $bOptions.bufferSize = 100;
                            }).createBufferOptions();
                })
                .with($ -> {
                    $.messageConsumer = new AnyConsumer();
                    $.subscriberBusStrategy = MessageBusStrategy.ASYNCHRONOUS_STRATEGY;
                    //
                    $.subscriberOptions = new MqttGatewayBuilder.SubscriberOptionsBuilder()
                            .with($sOptions -> {
                                $sOptions.topicFilter = "/topicoMock";
                                $sOptions.qos = 0;
                            }).createSubcriberOptions();
                })
                .with($ -> {
                    $.publisherOptions = new MqttGatewayBuilder.PublisherOptionsBuilder()
                            .with($pOptions -> {
                                $pOptions.topicFilter = "/topicoMock";
                                $pOptions.qos = 0;
                                $pOptions.retained = false;
                            }).createPublisherOptions();
                })
                .create()
                .subscribeWithOptions();
        //
        for(int i = 0; i < 10; i++){
            mqttGateway.publishWithOptions(String.format("%s.- Hello Word!!! ", i));
            Thread.sleep(1000);
        }



    }

    @Override
    protected void tearDown() throws Exception {

    }
}
