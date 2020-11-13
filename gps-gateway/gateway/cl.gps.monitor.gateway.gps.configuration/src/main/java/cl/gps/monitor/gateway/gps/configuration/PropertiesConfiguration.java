package cl.gps.monitor.gateway.gps.configuration;

import cl.gps.monitor.gateway.gps.configuration.util.UtilProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class PropertiesConfiguration {

    private Properties properties;

    public void configure(ConfigurationServiceOptions options) {
        configureProperties(options); //properties app
        printProperties(); // print properties stdout
    }

    private void configureProperties(ConfigurationServiceOptions options) {
        String propertiesFile = options.getPropertiesFile();
        if (Objects.nonNull(propertiesFile) && !propertiesFile.isEmpty()) {
            // configure external
            UtilProperties.configure(propertiesFile, false);
        } else {
            // configure internal
            UtilProperties.configure("application.properties", true);
        }
        properties = UtilProperties.getProperties();
    }

    private void printProperties() {
        // print properties
        Enumeration keys = properties.keys();
        log.info("Properties Config: ");
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) properties.get(key);
            log.info("[{}] : [{}]", key, value);
        }
    }

    public void destroy() {
        if (Objects.nonNull(properties)) {
            properties = null;
        }
    }
}
