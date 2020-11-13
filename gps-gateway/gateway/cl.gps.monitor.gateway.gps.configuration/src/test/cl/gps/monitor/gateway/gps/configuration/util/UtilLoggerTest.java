package cl.gps.monitor.gateway.gps.configuration.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilLoggerTest {

    @Before
    public void setup(){
        System.setProperty("gps.gateway.logging.file", "src/test/resources/logging.properties");
    }

    @Test
    public void testLoadInternalLoggerProperties() {
        // init test internal logging properties
        UtilLogger.configure("logging.properties", true);

        Assert.assertEquals(UtilLogger.IsDefaultConfig(), true);
    }

    @Test
    public void testLoadExternalProperties() {
        String loggingFile = System.getProperty("gps.gateway.logging.file");

        // init test external properties
        UtilLogger.configure(loggingFile, false);

        Assert.assertEquals(UtilLogger.IsDefaultConfig(), false);
    }
}