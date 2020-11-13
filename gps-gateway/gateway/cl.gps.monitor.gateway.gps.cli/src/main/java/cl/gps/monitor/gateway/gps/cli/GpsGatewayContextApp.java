package cl.gps.monitor.gateway.gps.cli;


import cl.gps.monitor.gateway.gps.api.configuration.ConfigurationService;
import cl.gps.monitor.gateway.gps.api.position.PositionService;

import java.util.HashMap;
import java.util.Map;

public class GpsGatewayContextApp {

    // singleton instance
    private static GpsGatewayContextApp gpsGatewayContextApp;

    public static GpsGatewayContextApp getContextInstance() {
        if (gpsGatewayContextApp == null) {
            gpsGatewayContextApp = new GpsGatewayContextApp();
        }
        return gpsGatewayContextApp;
    }

    public void initialize(String[] args) {
        // configurations
        Iterable<ConfigurationService>  configurationServices = ConfigurationService.getConfigurations();
        for(ConfigurationService service : configurationServices){
            configure(service);
            break; // only one
        }
        // positions
        Iterable<PositionService>  positionServices = PositionService.getPositions();
        for(PositionService service : positionServices){
            configure(service);
            break; // only one
        }

    }


    private void configure(ConfigurationService service) {
        // set external system properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("gps.gateway.properties.file", System.getProperty("gps.gateway.properties.file"));
        properties.put("gps.gateway.logging.file", System.getProperty("gps.gateway.logging.file"));
        //
        service.activate(properties);
    }

    private void configure(PositionService service) {
        // set external system properties
        Map<String, Object> properties = new HashMap<>();
        //
        service.activate(properties);
    }
}
