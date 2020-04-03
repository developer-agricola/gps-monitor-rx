package cl.gps.monitor.gateway.gps;

import cl.gps.monitor.gateway.gps.config.Configuration;
import cl.gps.monitor.gateway.gps.config.PropertiesConfigApp;
import cl.gps.monitor.gateway.gps.config.ShutDownHookConfigApp;

public class GpsGatewayApp {

    public static void main(String[] args) {
        run(args, PropertiesConfigApp.class, ShutDownHookConfigApp.class);
    }

    public static void run(String[] args, Class<? extends Configuration>... configurations){
        // get context
        GpsGatewayContextApp gpsGatewayContextApp = GpsGatewayContextApp.getContextInstance();
        // init context
        gpsGatewayContextApp.initialize(args, configurations);
    }
}
