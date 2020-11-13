module gateway.gps.configuration {
    requires org.slf4j;
    requires org.slf4j.jul;

    requires gateway.gps.api;
    requires lombok;

    provides cl.gps.monitor.gateway.gps.api.configuration.ConfigurationService
            with cl.gps.monitor.gateway.gps.configuration.ConfigurationServiceImpl;

}