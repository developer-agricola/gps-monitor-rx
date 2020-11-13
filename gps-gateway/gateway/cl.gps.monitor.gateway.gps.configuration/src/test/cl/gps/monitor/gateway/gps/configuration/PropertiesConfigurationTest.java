package cl.gps.monitor.gateway.gps.configuration;

import cl.gps.monitor.gateway.gps.configuration.util.UtilProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PropertiesConfigurationTest {

    private PropertiesConfiguration configuration;

    @Before
    public void setup(){
        System.setProperty("gps.gateway.properties.file", "src/test/resources/application.properties");
    }

    @After
    public void destroy(){
        configuration.destroy();
    }

    @Test
    public void testConfigureLoggingProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("gps.gateway.properties.file", System.getProperty("gps.gateway.properties.file"));

        ConfigurationServiceOptions options = new ConfigurationServiceOptions(properties);

        configuration = new PropertiesConfiguration();
        configuration.configure(options);

        String gpsdHost = (String)  UtilProperties.getProperties().get("gpsd.server.host");
        String gpsdPort = (String)  UtilProperties.getProperties().get("gpsd.server.port");

        Assert.assertNotNull(gpsdHost);
        Assert.assertNotNull(gpsdPort);
        Assert.assertEquals(gpsdHost, "127.0.0.1");
        Assert.assertEquals(gpsdPort, "1883");
    }

}