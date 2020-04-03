package cl.gps.monitor.gateway.gps.config.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Properties;

@Slf4j
public class PropertiesUtil {

    private static final Properties properties = new Properties();

    private PropertiesUtil() {
    }

    public static void configure(InputStream inputStream){
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            if (e instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            log.error("Could not read configuration file from InputStream [" + inputStream + "].", e);
            log.error("Ignoring configuration InputStream [" + inputStream +"].");
        }
    }

    public static void configure(String configFileName) {
        FileInputStream istream = null;
        try {
            istream = new FileInputStream(configFileName);
            properties.load(istream);
            istream.close();

        } catch (Exception e) {
            if (e instanceof InterruptedIOException || e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            log.error("Could not read configuration file [" + configFileName + "].", e);
            log.error("Ignoring configuration file [" + configFileName + "].");
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (InterruptedIOException e) {
                    Thread.currentThread().interrupt();
                } catch (Throwable e) {
                    ;
                }
            }
        }
    }

    public static Properties getProperties() {
        return properties;
    }

}
