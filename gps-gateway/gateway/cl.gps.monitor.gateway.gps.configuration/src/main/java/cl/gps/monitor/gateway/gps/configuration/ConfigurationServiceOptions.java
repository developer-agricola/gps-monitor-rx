package cl.gps.monitor.gateway.gps.configuration;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationServiceOptions {

    private static final Property<String> LOGGING_FILE = new Property<>("gps.gateway.logging.file", "");
    private static final Property<String> PROPERTY_FILE = new Property<>("gps.gateway.properties.file", "");

    private final Map<String, Object> properties;

    public ConfigurationServiceOptions(final Map<String, Object> properties) {
        this.properties = new HashMap<>(properties);
    }

    public String getLoggingFile() {
        return LOGGING_FILE.get(properties);
    }

    public String getPropertiesFile() {
        return PROPERTY_FILE.get(properties);
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
