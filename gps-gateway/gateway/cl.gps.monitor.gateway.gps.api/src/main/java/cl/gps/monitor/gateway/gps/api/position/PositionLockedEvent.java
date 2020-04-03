package cl.gps.monitor.gateway.gps.api.position;

import java.util.Map;

/**
 * PositionLockedEvent is raised when a valid GPS position has been acquired.
 *
 * @noextend This class is not intended to be subclassed by clients.
 */

public class PositionLockedEvent {
//public class PositionLockedEvent extends Event {

    /** Topic of the PositionLockedEvent */
    public static final String POSITION_LOCKED_EVENT_TOPIC = "org/eclipse/kura/position/locked";

    public PositionLockedEvent(Map<String, ?> properties) {
        //super(POSITION_LOCKED_EVENT_TOPIC, properties);
    }
}
