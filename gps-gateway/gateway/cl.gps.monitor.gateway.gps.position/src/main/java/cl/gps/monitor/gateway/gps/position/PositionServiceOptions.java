package cl.gps.monitor.gateway.gps.position;

import cl.gps.monitor.gateway.gps.api.gpsd.GpsdURI;

import java.util.HashMap;
import java.util.Map;

public class PositionServiceOptions {

    private static final Property<String> SERVER_HOST = new Property<>("gpsd.server.host", "");
    private static final Property<Integer> SERVER_PORT = new Property<>("gpsd.server.port", 0);

    private final Map<String, Object> properties;

    public PositionServiceOptions(final Map<String, Object> properties) {
        this.properties = new HashMap<>(properties);
    }

    public String getServerHost() {
        return SERVER_HOST.get(properties);
    }

    public Integer getServerPort() {
        return SERVER_PORT.get(properties);
    }

    public GpsdURI getGpsDeviceUri() {
        return new GpsdURI(getServerHost(), getServerPort());
    }

    private static final class Property<T> {

        private final String key;
        private final T defaultValue;

        Property(final String key, final T defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        @SuppressWarnings("unchecked")
        T get(final Map<String, Object> properties) {
            final Object value = properties.get(key);

            if (defaultValue.getClass().isInstance(value)) {
                return (T) value;
            }
            return defaultValue;
        }
    }
}
