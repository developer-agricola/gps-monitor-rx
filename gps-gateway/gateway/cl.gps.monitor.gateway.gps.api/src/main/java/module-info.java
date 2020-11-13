module gateway.gps.api {

    exports cl.gps.monitor.gateway.gps.api.gpsd;
    exports cl.gps.monitor.gateway.gps.api.position;
    exports cl.gps.monitor.gateway.gps.api.configuration;

    uses cl.gps.monitor.gateway.gps.api.configuration.ConfigurationService;

    uses cl.gps.monitor.gateway.gps.api.position.PositionService;
}