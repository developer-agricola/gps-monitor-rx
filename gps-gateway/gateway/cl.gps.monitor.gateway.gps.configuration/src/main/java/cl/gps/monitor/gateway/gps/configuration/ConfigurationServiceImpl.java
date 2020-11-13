package cl.gps.monitor.gateway.gps.configuration;

import cl.gps.monitor.gateway.gps.api.configuration.ConfigurationService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ConfigurationServiceImpl implements ConfigurationService {

    @Getter
    @Setter
    private LoggingConfiguration loggingConfiguration;

    @Getter
    @Setter
    private PropertiesConfiguration propertiesConfiguration;


    public ConfigurationServiceImpl() {
        this.loggingConfiguration = new LoggingConfiguration(); // TODO change to DI
        this.propertiesConfiguration = new PropertiesConfiguration(); // TODO change to DI
    }

    @Override
    public void activate(Map<String, Object> properties) {
        configure(properties);
    }

    @Override
    public void deactivate() {
        destroy();
    }

    @Override
    public void configure(Map<String, Object> properties) {
        final ConfigurationServiceOptions options = new ConfigurationServiceOptions(properties);

        // log
        log.info("Init service   ............... [{}]", LoggingConfiguration.class.getSimpleName());
        loggingConfiguration.configure(options);
        log.info("Status service ............... [{}] [{}]", LoggingConfiguration.class.getSimpleName(), "Up!!!");

        // properties
        log.info("Init service   ............... [{}]", PropertiesConfiguration.class.getSimpleName());
        propertiesConfiguration.configure(options);
        log.info("Status service ............... [{}] [{}]", PropertiesConfiguration.class.getSimpleName(), "Up!!!");
    }

    @Override
    public void destroy() {
        // properties
        log.info("Status service ............... [{}] [{}]", PropertiesConfiguration.class.getSimpleName(), "Down!!!");
        propertiesConfiguration.destroy();
        log.error("Status service ............... [{}] [{}]", PropertiesConfiguration.class.getSimpleName(), "Down!!!");

        //logging
        log.info("Status service ............... [{}] [{}]", LoggingConfiguration.class.getSimpleName(), "Down!!!");
        loggingConfiguration.destroy();
        log.error("Status service ............... [{}] [{}]", LoggingConfiguration.class.getSimpleName(), "Down!!!");
    }

    public static ConfigurationServiceImpl provider () {
        return new ConfigurationServiceImpl();
    }
}