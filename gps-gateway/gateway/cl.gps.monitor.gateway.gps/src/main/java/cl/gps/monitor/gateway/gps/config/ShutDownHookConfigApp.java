package cl.gps.monitor.gateway.gps.config;

import cl.gps.monitor.gateway.gps.GpsGatewayContextApp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShutDownHookConfigApp implements Configuration {

    @Override
    public void configure() {
        log.info("Init service   ............... [%s]", GpsGatewayShutDownHook.class.getSimpleName());
        try {
            Runtime.getRuntime().addShutdownHook(new GpsGatewayShutDownHook());

            log.info( "Estatus service ............... [%s] [%s]", GpsGatewayShutDownHook.class.getSimpleName(), "Up!!!");

        } catch (Exception e) {
            log.error("Estatus service ............... [%s] [%s]", GpsGatewayShutDownHook.class.getSimpleName(), "Down!!!");
        }
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException();
    }

    private class GpsGatewayShutDownHook extends Thread {

        public GpsGatewayShutDownHook() {
            super();
            this.setName(GpsGatewayShutDownHook.class.getSimpleName());
        }

        @Override
        public void run() {
            GpsGatewayContextApp gpsGatewayContextApp = GpsGatewayContextApp.getContextInstance();
            // destroy all context
            gpsGatewayContextApp.destroy();

            Runtime.getRuntime().halt(0);
        }
    }
}
