package cl.gps.monitor.gateway.gps.configuration;

import cl.gps.monitor.gateway.gps.configuration.util.UtilLogger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LoggingConfigurationTest {

    private LoggingConfiguration configuration;

    @Before
    public void setup(){
        System.setProperty("gps.gateway.logging.file", "src/test/resources/logging.properties");
    }

    @After
    public void destroy(){
        configuration.destroy();
    }

    @Test
    public void testConfigureProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("gps.gateway.logging.file", System.getProperty("gps.gateway.logging.file"));

        ConfigurationServiceOptions options = new ConfigurationServiceOptions(properties);

        configuration = new LoggingConfiguration();
        configuration.configure(options);

        Assert.assertEquals(UtilLogger.IsDefaultConfig(), false);
    }

}