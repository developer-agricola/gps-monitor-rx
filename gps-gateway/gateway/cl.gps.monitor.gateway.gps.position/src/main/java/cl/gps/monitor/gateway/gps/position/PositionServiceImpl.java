package cl.gps.monitor.gateway.gps.position;

import cl.gps.monitor.gateway.gps.api.position.PositionService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;

@Slf4j
public class PositionServiceImpl implements PositionService {

    @Getter
    @Setter
    private GpsDevice gpsDevice;

    public void activate(final Map<String, Object> properties) {
        log.debug("Activating PositionServiceImpl");

        this.gpsDevice = openGpsDevice();

        log.info("Activating PositionServiceImpl... Done.");
    }

    @Override
    public void deactivate() {
        //TODO to implement
    }

    private GpsDevice openGpsDevice() {
        GpsDevice gpsDevice = null;
        try {
            gpsDevice = new GpsDevice();
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("Failed to open GPS device", e);
            }
        }
        return gpsDevice;
    }
}