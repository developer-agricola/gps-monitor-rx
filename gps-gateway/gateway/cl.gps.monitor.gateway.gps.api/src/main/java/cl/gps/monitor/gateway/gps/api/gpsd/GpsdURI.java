package cl.gps.monitor.gateway.gps.api.gpsd;

/**
 * Represents a Uniform Resource Identifier (URI) for a GPS Deamon.
 *
 * @noextend This class is not intended to be subclassed by clients.
 */
public class GpsdURI {

    /**
     * Represents GPSD endpoint host
     */
    private String host;

    /**
     * Represent GPSD endpoint port
     */
    private int port;

    public GpsdURI(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
