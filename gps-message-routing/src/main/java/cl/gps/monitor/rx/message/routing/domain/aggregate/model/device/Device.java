package cl.gps.monitor.rx.message.routing.domain.aggregate.model.device;

import lombok.Data;

@Data
public class Device {

    private Id deviceId;

    private String deviceName;

    private Geo geo;

}
