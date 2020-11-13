package cl.gps.monitor.gateway.gps.api.core;

import java.util.Map;

public interface Service {

    void activate(Map<String, Object> properties);

    void deactivate();

}
