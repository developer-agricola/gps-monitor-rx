module gateway.gps.position {
    requires org.slf4j;
    requires org.slf4j.jul;

    requires gateway.gps.api;
    requires lombok;

    provides cl.gps.monitor.gateway.gps.api.position.PositionService
            with cl.gps.monitor.gateway.gps.position.PositionServiceImpl;
}