package cl.gps.monitor.gateway.gps.api.position;

import cl.gps.monitor.gateway.gps.api.core.Service;

import java.util.ServiceLoader;

public interface PositionService extends Service {

    static Iterable<PositionService> getPositions() {
        return ServiceLoader.load(PositionService.class);
    }

}
