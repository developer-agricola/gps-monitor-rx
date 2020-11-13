package cl.gps.monitor.gateway.gps.cli;


public class GpsGatewayApp {

    public static void main(String[] args) {
        run(args);
    }

    public static void run(String[] args){
        // get context
        GpsGatewayContextApp gpsGatewayContextApp = GpsGatewayContextApp.getContextInstance();
        // init context
        gpsGatewayContextApp.initialize(args);
    }
}
