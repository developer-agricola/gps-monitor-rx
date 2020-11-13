package cl.gps.monitor.gateway.gps.api.core;

import java.util.Map;

public interface Configuration {

    void configure(Map<String, Object> properties);

    void destroy();
}
