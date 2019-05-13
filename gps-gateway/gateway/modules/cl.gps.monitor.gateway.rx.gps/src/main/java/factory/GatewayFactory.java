package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.Gateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.archiva.ArchivaGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttPublisherGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttSubcriberGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

public class GatewayFactory {

    private ArchivaGateway archivaGateway;
    private MqttGateway mqttGateway;
    private MqttSubcriberGateway mqttSubcriberGateway;
    private MqttPublisherGateway mqttPublisherGateway;

    public static final UtilLogger log = UtilLogger.getLogger(GatewayFactory.class);

    private static GatewayFactory gatewayFactory;

    public static GatewayFactory getFactoryInstance() {
        if(gatewayFactory == null) {
            gatewayFactory = new GatewayFactory();
        }
        return gatewayFactory;
    }

    public Gateway getGateway(Class<? extends Gateway> clazz){
        Gateway gateway = null;
        if(clazz.equals(ArchivaGateway.class)) {
            if(archivaGateway == null){
                archivaGateway = new ArchivaGateway();
            }
            return archivaGateway;
        }else if(clazz.equals(MqttGateway.class)){
            if(mqttGateway == null){
                mqttGateway = new MqttGateway();
            }
            return mqttGateway;
        }else if(clazz.equals(MqttSubcriberGateway.class)){
            if(mqttSubcriberGateway == null){
                mqttSubcriberGateway = new MqttSubcriberGateway();
            }
            return mqttSubcriberGateway;
        }else if(clazz.equals(MqttPublisherGateway.class)){
            if(mqttPublisherGateway == null){
                mqttPublisherGateway = new MqttPublisherGateway();
            }
            return mqttPublisherGateway;
        }else{
            log.error("La clase [%s] no es un gateway", clazz.getSimpleName());
        }
        return gateway;
    }

}
