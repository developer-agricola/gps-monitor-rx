package cl.gps.monitor.gateway.gps.api.position;

import java.util.Map;

/**
 * PositionLostEvent is raised when GPS position has been lost.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */


//TODO ver si se puede utilizar
public class PositionLostEvent {
//public class PositionLostEvent extends Event {

    /** Topic of the PositionLostEvent */
    public static final String POSITION_LOST_EVENT_TOPIC = "org/eclipse/kura/position/lost";

    public PositionLostEvent(Map<String, ?> properties) {
        //super(POSITION_LOST_EVENT_TOPIC, properties);
    }

}
