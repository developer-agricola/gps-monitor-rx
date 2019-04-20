package consola.manager.cliente.mqtt.gateway.infraestructure.integration;

import consola.manager.cliente.mqtt.gateway.infraestructure.conf.impl.MqttServerConfigApp;
import consola.manager.cliente.mqtt.gateway.infraestructure.conf.impl.PropertiesConfigApp;
import junit.framework.TestCase;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Test;

/**
 * Created by daniel.carvajal on 20-06-2018.
 */
public class MqttGatewayBuilderITest extends TestCase {

    private MqttServerConfigApp mqttServerConfigApp;

    @Override
    protected void setUp() throws Exception {
        PropertiesConfigApp propertiesConfigApp = new PropertiesConfigApp();
        propertiesConfigApp.configure();
        //
        mqttServerConfigApp = new MqttServerConfigApp();
        mqttServerConfigApp.configure();
    }

    @Test
    public void testBuildBasicMqttAysncClientOkWithConnection() throws MqttException {
        MqttGateway mqttGateway = new MqttGatewayBuilder()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                }).create();
        //
        assertEquals(mqttGateway.getMqttAsyncClient().getClientId(), "MockIdentifier");
        assertEquals(mqttGateway.getMqttAsyncClient().getServerURI(), "tcp://127.0.0.1:1883");
        assertEquals(mqttGateway.isConnected(), true);
        //
        mqttGateway.detach();
    }

    @Test
    public void testBuildBasicMqttAysncClientOkWithOutConnection() throws MqttException {
        mqttServerConfigApp.destroy();
        //
        MqttGateway mqttGateway = new MqttGatewayBuilder()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                }).create();
        //
        assertEquals(mqttGateway.getMqttAsyncClient().getClientId(), "MockIdentifier");
        assertEquals(mqttGateway.getMqttAsyncClient().getServerURI(), "tcp://127.0.0.1:1883");
        assertEquals(mqttGateway.isConnected(), false);
        //
    }

    @Test
    public void testBuildBasicMqttAysncClientOkWithConnectionDebug() throws MqttException {
        MqttGateway mqttGateway = new MqttGatewayBuilder()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                })
                .with($ -> {
                    $.debug = true;
                }).create();
        //
        assertEquals(mqttGateway.getMqttAsyncClient().getClientId(), "MockIdentifier");
        assertEquals(mqttGateway.getMqttAsyncClient().getServerURI(), "tcp://127.0.0.1:1883");
        assertEquals(mqttGateway.getMqttAsyncClient().isConnected(), true);
        //

        mqttGateway.detach();
    }

    @Test
    public void testBuildBasicMqttAysncClientOkWithOutConnectionDebug() throws MqttException {
        mqttServerConfigApp.destroy();
        //
        MqttGateway mqttGateway = new MqttGatewayBuilder()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                    $.debug = true;
                }).create();
        //
        assertEquals(mqttGateway.getMqttAsyncClient().getClientId(), "MockIdentifier");
        assertEquals(mqttGateway.getMqttAsyncClient().getServerURI(), "tcp://127.0.0.1:1883");
        assertEquals(mqttGateway.isConnected(), false);
    }

    @Test
    public void testBuildConnectionOptionsMqttAysncClientOkWithConnectionDebug() throws MqttException {
        MqttGateway mqttGateway = new MqttGatewayBuilder()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                    $.debug = true;
                })
                .with($ -> {
                    $.connectOptions = new MqttGatewayBuilder.ConnectOptionsBuilder()
                            .with($cOptions -> {
                                $cOptions.cleanSession = false;
                            }).createConnectOptions();
                })
                .create();
        //
        assertEquals(mqttGateway.getMqttAsyncClient().getClientId(), "MockIdentifier");
        assertEquals(mqttGateway.getMqttAsyncClient().getServerURI(), "tcp://127.0.0.1:1883");
        assertEquals(mqttGateway.isConnected(), true);
        // option
        assertEquals(mqttGateway.getConnectOptions().isCleanSession(), false);
        // default options
        assertEquals(mqttGateway.getConnectOptions().getKeepAliveInterval(), 60);
        assertEquals(mqttGateway.getConnectOptions().isAutomaticReconnect(), false);
        assertEquals(mqttGateway.getConnectOptions().getConnectionTimeout(), 30);
        assertEquals(mqttGateway.getConnectOptions().getMaxInflight(), 10);
        //
        mqttGateway.detach();
    }

    @Test
    public void testBuildBufferOptionsMqttAysncClientOkWithConnectionDebug() throws MqttException {
        MqttGateway mqttGateway = new MqttGatewayBuilder()
                .with($ -> {
                    $.host = "127.0.0.1";
                    $.port = 1883;
                    $.identifier = "MockIdentifier";
                })
                .with($ -> {
                    $.debug = true;
                })
                .with($ -> {
                    $.bufferOptions = new MqttGatewayBuilder.BufferOptionsBuilder()
                            .with($cOptions -> {
                                $cOptions.bufferEnabled = true;
                            }).createBufferOptions();
                })
                .create();
        //
        assertEquals(mqttGateway.getMqttAsyncClient().getClientId(), "MockIdentifier");
        assertEquals(mqttGateway.getMqttAsyncClient().getServerURI(), "tcp://127.0.0.1:1883");
        assertEquals(mqttGateway.isConnected(), true);
        // option
        assertEquals(mqttGateway.getBufferOptions().isBufferEnabled(), true);
        // default options
        assertEquals(mqttGateway.getBufferOptions().isPersistBuffer(), false);
        assertEquals(mqttGateway.getBufferOptions().getBufferSize(),  5000);
        assertEquals(mqttGateway.getBufferOptions().isDeleteOldestMessages(), false);
        //
        mqttGateway.detach();
    }

    @Test
    public void testBuildWithOptionsMqttAysncClientOkWithConnectionDebug() throws MqttException {
        MqttGateway mqttGateway = new MqttGatewayBuilder()
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
                                $bOptions.bufferEnabled = true;
                                $bOptions.persistBuffer = true;
                                $bOptions.bufferSize = 10000;
                                $bOptions.deleteOldestMessages = true;
                            }).createBufferOptions();
                })
                .create();
        //
        assertEquals(mqttGateway.getMqttAsyncClient().getClientId(), "MockIdentifier");
        assertEquals(mqttGateway.getMqttAsyncClient().getServerURI(), "tcp://127.0.0.1:1883");
        assertEquals(mqttGateway.isConnected(), true);
        // connect option
        assertEquals(mqttGateway.getConnectOptions().isCleanSession(), true);
        assertEquals(mqttGateway.getConnectOptions().getKeepAliveInterval(), 60);
        assertEquals(mqttGateway.getConnectOptions().isAutomaticReconnect(), true);
        assertEquals(mqttGateway.getConnectOptions().getConnectionTimeout(), 30);
        assertEquals(mqttGateway.getConnectOptions().getMaxInflight(), 1000);
        //
        // buffer option
        assertEquals(mqttGateway.getBufferOptions().isBufferEnabled(), true);
        assertEquals(mqttGateway.getBufferOptions().isPersistBuffer(), true);
        assertEquals(mqttGateway.getBufferOptions().getBufferSize(),  10000);
        assertEquals(mqttGateway.getBufferOptions().isDeleteOldestMessages(), true);
        //

        mqttGateway.detach();
    }

    @Test
    public void testBuildWithPersistenceOptionsMqttAysncClientOkWithConnectionDebug() throws MqttException {
        MqttGateway mqttGateway = new MqttGatewayBuilder()
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
                                $bOptions.bufferEnabled = true;
                                $bOptions.persistBuffer = true;
                                $bOptions.bufferSize = 10000;
                                $bOptions.deleteOldestMessages = true;
                            }).createBufferOptions();
                })
                .create();
        //
        assertEquals(mqttGateway.getMqttAsyncClient().getClientId(), "MockIdentifier");
        assertEquals(mqttGateway.getMqttAsyncClient().getServerURI(), "tcp://127.0.0.1:1883");
        assertEquals(mqttGateway.isConnected(), true);
        // connect option
        assertEquals(mqttGateway.getConnectOptions().isCleanSession(), true);
        assertEquals(mqttGateway.getConnectOptions().getKeepAliveInterval(), 60);
        assertEquals(mqttGateway.getConnectOptions().isAutomaticReconnect(), true);
        assertEquals(mqttGateway.getConnectOptions().getConnectionTimeout(), 30);
        assertEquals(mqttGateway.getConnectOptions().getMaxInflight(), 1000);
        //
        // buffer option
        assertEquals(mqttGateway.getBufferOptions().isBufferEnabled(), true);
        assertEquals(mqttGateway.getBufferOptions().isPersistBuffer(), true);
        assertEquals(mqttGateway.getBufferOptions().getBufferSize(),  10000);
        assertEquals(mqttGateway.getBufferOptions().isDeleteOldestMessages(), true);
        //

        mqttGateway.detach();
    }

    @Override
    protected void tearDown() throws Exception {
        mqttServerConfigApp.destroy();
    }
}
