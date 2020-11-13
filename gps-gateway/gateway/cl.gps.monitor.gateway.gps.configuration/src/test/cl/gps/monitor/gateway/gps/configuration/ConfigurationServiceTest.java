package cl.gps.monitor.gateway.gps.configuration;

import cl.gps.monitor.gateway.gps.configuration.util.UtilLogger;
import cl.gps.monitor.gateway.gps.configuration.util.UtilProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationServiceTest {

    private LoggingConfiguration loggingConfiguration;
    private PropertiesConfiguration propertiesConfiguration;
    //
    ConfigurationServiceImpl configurationService;

    @Before
    public void setup(){
        System.setProperty("gps.gateway.logging.file", "src/test/resources/logging.properties");
        System.setProperty("gps.gateway.properties.file", "src/test/resources/application.properties");
        //
        this.loggingConfiguration = new LoggingConfiguration();
        this.propertiesConfiguration = new PropertiesConfiguration();
        this.configurationService = new ConfigurationServiceImpl();
        configurationService.setLoggingConfiguration(loggingConfiguration);
        configurationService.setPropertiesConfiguration(propertiesConfiguration);
    }

    @After
    public void destroy(){
        configurationService.deactivate();
    }

    @Test
    public void testActivateService() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("gps.gateway.logging.file", System.getProperty("gps.gateway.logging.file"));
        properties.put("gps.gateway.properties.file", System.getProperty("gps.gateway.properties.file"));

        ConfigurationServiceOptions options = new ConfigurationServiceOptions(properties);

        configurationService.activate(properties);

        // evaluate properties
        String gpsdHost = (String)  UtilProperties.getProperties().get("gpsd.server.host");
        String gpsdPort = (String)  UtilProperties.getProperties().get("gpsd.server.port");

        Assert.assertNotNull(gpsdHost);
        Assert.assertNotNull(gpsdPort);
        Assert.assertEquals(gpsdHost, "127.0.0.1");
        Assert.assertEquals(gpsdPort, "1883");

        // evaluate logger
        Assert.assertEquals(UtilLogger.IsDefaultConfig(), false);
    }

}