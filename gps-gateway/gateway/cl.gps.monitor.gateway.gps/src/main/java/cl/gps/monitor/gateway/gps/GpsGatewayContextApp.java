package cl.gps.monitor.gateway.gps;

import cl.gps.monitor.gateway.gps.config.Configuration;

import cl.gps.monitor.gateway.gps.config.PropertiesConfigApp;
import cl.gps.monitor.gateway.gps.config.ShutDownHookConfigApp;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

@Slf4j
@Getter
@Setter
public class GpsGatewayContextApp {

    private List<Configuration> configurations = new ArrayList<>();

    private static GpsGatewayContextApp gpsGatewayContextApp;

    public static GpsGatewayContextApp getContextInstance() {
        if (gpsGatewayContextApp == null) {
            gpsGatewayContextApp = new GpsGatewayContextApp();
        }
        return gpsGatewayContextApp;
    }

    public void initialize(String[] args, Class<? extends Configuration>... configurations) {
        Configuration configuration = null;



        // create configurations
        for (Class configClazz : configurations) {
            try {
                configuration = (Configuration) configClazz.newInstance();
                //
                if (configuration instanceof PropertiesConfigApp) {
                    configure((PropertiesConfigApp) configuration);
                    //
                } else if (configuration instanceof ShutDownHookConfigApp) {
                    configure((ShutDownHookConfigApp) configuration);
                    //
                } else {
                    log.warn(String.format("[%s] not is configuration class", configuration.getClass().getSimpleName()));
                }
            } catch (Exception e) {
                log.error("Fatal Error to create configurations context", e);
                destroy();
            }
            this.configurations.add(configuration);
        }
    }

    private void configure(PropertiesConfigApp propertiesConfigApp) {
        // configure
        propertiesConfigApp.configure();
    }

    /*public void configure(MqttConfigApp mqttConfigApp) {
        // mqtt
        MqttGateway mqttGateway = (MqttGateway) GatewayFactory.getFactoryInstance().getGateway(MqttGateway.class);
        MqttSubcriberGateway mqttSubcriberGateway = (MqttSubcriberGateway) GatewayFactory.getFactoryInstance().getGateway(MqttSubcriberGateway.class);
        MqttPublisherGateway mqttPublisherGateway = (MqttPublisherGateway) GatewayFactory.getFactoryInstance().getGateway(MqttPublisherGateway.class);
        CommandHandler commandHandler = (CommandHandler) HandlerFactory.getFactoryInstance().getHanlder(CommandHandler.class);
        // business logic
        DispositivoDaoImpl dispositivoDao = (DispositivoDaoImpl) DaoFactory.getFactoryInstance().getDao(DispositivoDaoImpl.class);
        DispositivoRepositoryImpl dispositivoRepository = (DispositivoRepositoryImpl) RepositoryFactory.getFactoryInstance().getDataRepository(DispositivoRepositoryImpl.class);
        DispositivoServiceImpl dispositivoService = (DispositivoServiceImpl) ServiceFactory.getFactoryInstance().getService(DispositivoServiceImpl.class);

        // job retry mqtt conection
        MqttRetryConnectionJob mqttRetryConnectionJob = (MqttRetryConnectionJob) JobFactory.getFactoryInstance().getJob(MqttRetryConnectionJob.class);
        // event cl.gps.monitor.gateway.gps.handler
        HandlerFactory handlerFactory = HandlerFactory.getFactoryInstance();
        DispositivoEventHandler dispositivoEventHandler = (DispositivoEventHandler) handlerFactory.getHanlder(DispositivoEventHandler.class);
        DispositivoErrorHandler dispositivoErrorHandler = (DispositivoErrorHandler) handlerFactory.getHanlder(DispositivoErrorHandler.class);
        FirmwareEventHandler firmwareEventHandler = (FirmwareEventHandler) handlerFactory.getHanlder(FirmwareEventHandler.class);
        FirmwareErrorHandler firmwareErrorHandler = (FirmwareErrorHandler) handlerFactory.getHanlder(FirmwareErrorHandler.class);
        // event cl.gps.monitor.gateway.gps.bus
        CommandBus commandBus = (CommandBus) BusFactory.getFactoryInstance().getBus(CommandBus.class);
        FirmwareEventBus firmwareEventBus = (FirmwareEventBus) BusFactory.getFactoryInstance().getBus(FirmwareEventBus.class);


        // inject dependencies
        // event cl.gps.monitor.gateway.gps.bus
        commandBus.setDispositivoService(dispositivoService);
        firmwareEventBus.setDispositivoDao(dispositivoDao);
        firmwareEventBus.setMqttPublisherGateway(mqttPublisherGateway);

        firmwareEventHandler.setFirmwareEventBus(firmwareEventBus);
        firmwareErrorHandler.setFirmwareEventBus(firmwareEventBus);

        // event handlers
        dispositivoRepository.setDispositivoEventHandler(dispositivoEventHandler);
        dispositivoRepository.setDispositivoErrorHandler(dispositivoErrorHandler);
        dispositivoRepository.setFirmwareEventHandler(firmwareEventHandler);
        dispositivoRepository.setFirmwareErrorHandler(firmwareErrorHandler);

        // business logic
        dispositivoRepository.setDispositivoDao(dispositivoDao);
        dispositivoService.setDispositivoRepository(dispositivoRepository);
        commandHandler.setCommandBus(commandBus);
        mqttSubcriberGateway.setCommandHandler(commandHandler);

        // mqtt gateway
        mqttGateway.setCommandHandler(commandHandler);
        mqttSubcriberGateway.setMqttGateway(mqttGateway);
        mqttPublisherGateway.setMqttGateway(mqttGateway);
        mqttConfigApp.setMqttGateway(mqttGateway); // mqtt gateway
        mqttConfigApp.setMqttSubcriberGateway(mqttSubcriberGateway);  // mqtt subscriber
        mqttConfigApp.setMqttPublisherGateway(mqttPublisherGateway);  // mqtt publisher

        // configure
        mqttConfigApp.configure();
        // set configuration
        setMqttConfigApp(mqttConfigApp);

    }*/

    public void configure(ShutDownHookConfigApp shutdownHookConfigApp) {
        // configure
        shutdownHookConfigApp.configure();
    }

    public void destroy() {
        // destroy configurations
        if (Objects.nonNull(configurations)) {
            ListIterator<Configuration> configurationListIterator = configurations.listIterator(configurations.size());
            while (configurationListIterator.hasPrevious()) {
                configurationListIterator.previous().destroy();
            }
        } else {
            log.warn("Error no exists configurations");
        }
    }

}
