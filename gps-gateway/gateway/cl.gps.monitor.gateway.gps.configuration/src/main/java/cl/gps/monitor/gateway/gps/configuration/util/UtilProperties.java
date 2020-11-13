package cl.gps.monitor.gateway.gps.configuration.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class UtilProperties {

    private static final Properties properties = new Properties();

    private UtilProperties() {
    }

    public static void configure(String configFileName, boolean isInternal) {
        InputStream istream = null;
        try {
            if(isInternal){
                // from classpath
                istream = UtilProperties.class.getClassLoader().getResourceAsStream(configFileName);
            }else{
                // from external properties file
                istream = new FileInputStream(configFileName);
            }

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

    public static Map<String, Object> toMap(){return (Map) properties;}

}
