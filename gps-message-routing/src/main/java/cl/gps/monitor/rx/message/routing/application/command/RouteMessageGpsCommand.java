package cl.gps.monitor.rx.message.routing.application.command;

import lombok.Data;


/**
 *
 * Created by daniel.carvajal on 21-06-2018.
 */

@Data
public class RouteMessageGpsCommand implements Command {

    private long idDevice;

    private String deviceName;

    private double latitude;

    private double longitude;

    private double speed;

}
