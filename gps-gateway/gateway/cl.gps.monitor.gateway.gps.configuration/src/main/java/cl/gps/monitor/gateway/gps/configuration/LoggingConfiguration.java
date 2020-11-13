package cl.gps.monitor.gateway.gps.configuration;

import cl.gps.monitor.gateway.gps.configuration.util.UtilLogger;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class LoggingConfiguration {

    public void configure(ConfigurationServiceOptions options) {
        configureLogging(options); //properties app
    }

    private void configureLogging(ConfigurationServiceOptions options) {
        String loggingFile = options.getLoggingFile();
        if (Objects.nonNull(loggingFile) && !loggingFile.isEmpty()) {
            // configure external
            UtilLogger.configure(loggingFile, false);
        } else {
            // configure internal
            UtilLogger.configure("resources/logging.properties", true);
        }
    }

    public void destroy() {
        //TODO to implement
    }
}
