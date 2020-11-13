package cl.gps.monitor.gateway.gps.api.configuration;

import cl.gps.monitor.gateway.gps.api.core.Configuration;
import cl.gps.monitor.gateway.gps.api.core.Service;

import java.util.ServiceLoader;

public interface ConfigurationService extends Service, Configuration {

    static Iterable<ConfigurationService> getConfigurations() {
        return ServiceLoader.load(ConfigurationService.class);
    }
}
