package cl.gps.monitor.gateway.gps.config;

import cl.gps.monitor.gateway.gps.config.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class PropertiesConfigApp implements Configuration {

    private Properties properties;

    @Override
    public void configure() {
        log.info("Init service   ............... [%s]", Properties.class.getSimpleName());
        //
        configureLogger(); // configure logger properties
        configureProperties(); //properties app
        printProperties(); // print properties stdout
        //
        log.info("Estatus service ............... [%s] [%s]", Properties.class.getSimpleName(), "Up!!!");
    }

    private void configureLogger() {
        //TODO ver como se debe configurar el log nativo de jdk
        //PropertyConfigurator.configure("etc/log4j.properties");
    }

    private void configureProperties() {
        PropertiesUtil.configure("etc/application.properties");
        properties = PropertiesUtil.getProperties();
    }

    private void printProperties() {
        // print properties
        Enumeration keys = properties.keys();
        log.info("Properties Config: ");
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) properties.get(key);
            log.info("[%s] : [%s]", key, value);
        }
    }

    @Override
    public void destroy() {
        try {
            if (Objects.nonNull(properties)) {
                properties = null;
            }
            log.info("Estatus service ............... [%s] [%s]", Properties.class.getSimpleName(), "Down!!!");

        } catch (Exception e) {
            log.error("Estatus service ............... [%s] [%s]", Properties.class.getSimpleName(), "Down!!!");
        }
    }
}
