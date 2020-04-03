package cl.gps.monitor.gateway.gps.position;

import de.taimos.gpsd4java.backend.AbstractResultParser;
import de.taimos.gpsd4java.backend.GPSdEndpoint;

import java.io.IOException;
import java.net.UnknownHostException;

public class GpsdConnectionFactory {

    public GPSdEndpoint createConnection(final String host, final int port) throws UnknownHostException, IOException {
        return new GPSdEndpoint(host, port);
    }

    public GPSdEndpoint createConnection(final String host, final int port, AbstractResultParser parser) throws UnknownHostException, IOException {
        return new GPSdEndpoint(host, port, parser);
    }
}
