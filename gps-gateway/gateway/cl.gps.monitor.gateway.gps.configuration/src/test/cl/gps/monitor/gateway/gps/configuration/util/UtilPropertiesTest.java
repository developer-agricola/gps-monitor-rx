package cl.gps.monitor.gateway.gps.configuration.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilPropertiesTest {

    @Before
    public void setup(){
        System.setProperty("gps.gateway.properties.file", "src/test/resources/application.properties");
    }

    @Test
    public void testLoadInternalProperties() {
        // init test internal properties
        UtilProperties.configure("application.properties", true);

        String gpsdHost = (String)  UtilProperties.getProperties().get("gpsd.server.host");
        String gpsdPort = (String)  UtilProperties.getProperties().get("gpsd.server.port");

        Assert.assertNotNull(gpsdHost);
        Assert.assertNotNull(gpsdPort);
        Assert.assertEquals(gpsdHost, "127.0.0.1");
        Assert.assertEquals(gpsdPort, "1883");
    }

    @Test
    public void testLoadExternalProperties() {
        String propertiesFile = System.getProperty("gps.gateway.properties.file");

        // init test external properties
        UtilProperties.configure(propertiesFile, false);

        String gpsdHost = (String)  UtilProperties.getProperties().get("gpsd.server.host");
        String gpsdPort = (String)  UtilProperties.getProperties().get("gpsd.server.port");

        Assert.assertNotNull(gpsdHost);
        Assert.assertNotNull(gpsdPort);
        Assert.assertEquals(gpsdHost, "127.0.0.1");
        Assert.assertEquals(gpsdPort, "1883");
    }
}