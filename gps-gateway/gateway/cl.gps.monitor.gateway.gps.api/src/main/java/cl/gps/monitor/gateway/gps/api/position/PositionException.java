package cl.gps.monitor.gateway.gps.api.position;


/**
 * @noextend This class is not intended to be subclassed by clients.
 */
public class PositionException extends Exception {

    private static final long serialVersionUID = 2611760893640245224L;

    public PositionException() {
    }

    public PositionException(String message) {
        super(message);
    }

    public PositionException(Throwable cause) {
        super(cause);
    }

    public PositionException(String message, Throwable cause) {
        super(message, cause);
    }

}
