package com.sonda.transporte.consola.agente.firmware.infrastructure;

import com.sonda.transporte.consola.agente.firmware.application.job.ApplyFirmwareJob;
import com.sonda.transporte.consola.agente.firmware.application.job.MqttRetryConnectionJob;
import com.sonda.transporte.consola.agente.firmware.application.repository.impl.DispositivoRepositoryImpl;
import com.sonda.transporte.consola.agente.firmware.application.service.impl.DispositivoServiceImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.CommandBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.FirmwareEventBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl.*;
import com.sonda.transporte.consola.agente.firmware.infrastructure.factory.*;
import com.sonda.transporte.consola.agente.firmware.infrastructure.handler.*;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.archiva.ArchivaGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttPublisherGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.mqtt.MqttSubcriberGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.dao.impl.DispositivoDaoImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.derby.impl.DerbyDataSourceImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.filesystem.impl.FileSystemDataSourceImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.sqlite.impl.SqLiteDataSourceImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Created by daniel.carvajal on 20-06-2018.
 */
@Getter
@Setter
public class FirmwareContextApp {

    private BusConfigApp busConfigApp;

    private MqttConfigApp mqttConfigApp;

    private HttpConfigApp httpConfigApp;

    private DataSourceConfigApp dataSourceConfigApp;

    private JobSchedulerConfigApp jobSchedulerConfigApp;

    private List<Configuration> configurations = new ArrayList<>();

    public static final UtilLogger log = UtilLogger.getLogger(FirmwareContextApp.class);

    private static FirmwareContextApp firmwareContextApp;

    public static FirmwareContextApp getContextInstance() {
        if (firmwareContextApp == null) {
            firmwareContextApp = new FirmwareContextApp();
        }
        return firmwareContextApp;
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

                } else if (configuration instanceof DataSourceConfigApp) {
                    configure((DataSourceConfigApp) configuration);

                } else if (configuration instanceof HttpConfigApp) {
                    configure((HttpConfigApp) configuration);

                } else if (configuration instanceof JobSchedulerConfigApp) {
                    configure((JobSchedulerConfigApp) configuration);

                } else if (configuration instanceof BusConfigApp) {
                    configure((BusConfigApp) configuration);

                } else if (configuration instanceof MqttConfigApp) {
                    configure((MqttConfigApp) configuration);

                } else if (configuration instanceof ShutdownHookConfigApp) {
                    configure((ShutdownHookConfigApp) configuration);
                } else {
                    log.warning(String.format("[%s] not is config class", configuration.getClass().getSimpleName()));
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

    private void configure(DataSourceConfigApp dataSourceConfigApp) {
        SqLiteDataSourceImpl sqLiteDataSource = (SqLiteDataSourceImpl) DataSourceFactory.getFactoryInstance().getDataSource(SqLiteDataSourceImpl.class);
        DerbyDataSourceImpl derbyDataSource = (DerbyDataSourceImpl) DataSourceFactory.getFactoryInstance().getDataSource(DerbyDataSourceImpl.class);
        //
        FileSystemDataSourceImpl fileSystemDataSource = (FileSystemDataSourceImpl) DataSourceFactory.getFactoryInstance().getDataSource(FileSystemDataSourceImpl.class);

        // inject dependencies
        DispositivoDaoImpl dispositivoDao = (DispositivoDaoImpl) DaoFactory.getFactoryInstance().getDao(DispositivoDaoImpl.class);
        dispositivoDao.setDerbyDataSource(derbyDataSource);
        dispositivoDao.setSqLiteDataSource(sqLiteDataSource);
        //
        dataSourceConfigApp.setSqLiteDataSource(sqLiteDataSource);
        dataSourceConfigApp.setDerbyDataSource(derbyDataSource);
        dataSourceConfigApp.setFileSystemDataSource(fileSystemDataSource);
        // configure
        dataSourceConfigApp.configure();
        // set configuration
        setDataSourceConfigApp(dataSourceConfigApp);
    }

    private void configure(HttpConfigApp httpConfigApp) {
        ArchivaGateway archivaGateway = (ArchivaGateway) GatewayFactory.getFactoryInstance().getGateway(ArchivaGateway.class);
        //
        FileSystemDataSourceImpl fileSystemDataSource = (FileSystemDataSourceImpl) DataSourceFactory.getFactoryInstance().getDataSource(FileSystemDataSourceImpl.class);
        DispositivoDaoImpl dispositivoDao = (DispositivoDaoImpl) DaoFactory.getFactoryInstance().getDao(DispositivoDaoImpl.class);

        //inject dependencies
        dispositivoDao.setArchivaGateway(archivaGateway);
        fileSystemDataSource.setArchivaGateway(archivaGateway);
        dispositivoDao.setFileSystemDataSource(fileSystemDataSource);
        //
        httpConfigApp.setArchivaGateway(archivaGateway);
        // configure
        httpConfigApp.configure();
        // set configuration
        setHttpConfigApp(httpConfigApp);
    }

    public void configure(JobSchedulerConfigApp jobSchedulerConfigApp) {
        ApplyFirmwareJob applyFirmwareJob = (ApplyFirmwareJob) JobFactory.getFactoryInstance().getJob(ApplyFirmwareJob.class);
        MqttRetryConnectionJob mqttRetryConnectionJob = (MqttRetryConnectionJob) JobFactory.getFactoryInstance().getJob(MqttRetryConnectionJob.class);

        // inject dependencies
        jobSchedulerConfigApp.setApplyFirmwareJob(applyFirmwareJob);
        jobSchedulerConfigApp.setMqttRetryConnectionJob(mqttRetryConnectionJob);
        // configure
        jobSchedulerConfigApp.configure();
        // set configuration
        setJobSchedulerConfigApp(jobSchedulerConfigApp);
    }

    public void configure(BusConfigApp busConfigApp) {
        // event bus
        CommandBus commandBus = (CommandBus) BusFactory.getFactoryInstance().getBus(CommandBus.class);
        DispositivoServiceImpl dispositivoService = (DispositivoServiceImpl) ServiceFactory.getFactoryInstance().getService(DispositivoServiceImpl.class);
        commandBus.setDispositivoService(dispositivoService);
        busConfigApp.setCommandBus(commandBus);

        // configure
        busConfigApp.configure();

        setBusConfigApp(busConfigApp);
    }

    public void configure(MqttConfigApp mqttConfigApp) {
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
        // event handler
        HandlerFactory handlerFactory = HandlerFactory.getFactoryInstance();
        DispositivoEventHandler dispositivoEventHandler = (DispositivoEventHandler) handlerFactory.getHanlder(DispositivoEventHandler.class);
        DispositivoErrorHandler dispositivoErrorHandler = (DispositivoErrorHandler) handlerFactory.getHanlder(DispositivoErrorHandler.class);
        FirmwareEventHandler firmwareEventHandler = (FirmwareEventHandler) handlerFactory.getHanlder(FirmwareEventHandler.class);
        FirmwareErrorHandler firmwareErrorHandler = (FirmwareErrorHandler) handlerFactory.getHanlder(FirmwareErrorHandler.class);
        // event bus
        CommandBus commandBus = (CommandBus) BusFactory.getFactoryInstance().getBus(CommandBus.class);
        FirmwareEventBus firmwareEventBus = (FirmwareEventBus) BusFactory.getFactoryInstance().getBus(FirmwareEventBus.class);


        // inject dependencies
        // event bus
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

    }

    public void configure(ShutdownHookConfigApp shutdownHookConfigApp) {
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
            log.error("Error no exists configurations");
        }
    }

}
