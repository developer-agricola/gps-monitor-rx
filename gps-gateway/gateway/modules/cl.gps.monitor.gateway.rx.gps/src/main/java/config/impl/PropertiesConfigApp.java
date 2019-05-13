package com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl;

import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.ServiceStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.PropertyConfigurator;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by daniel.carvajal on 19-06-2018.
 */

@Getter
@Setter
public class PropertiesConfigApp implements Configuration {

    private Properties properties;

    public static final UtilLogger log = UtilLogger.getLogger(PropertiesConfigApp.class);

    @Override
    public void configure() {
        configureLogger(); // configure logger

        configureProperties(); //properties app

        // print properties
        Enumeration keys = properties.keys();
        log.info("Properties Config: ");
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) properties.get(key);
            log.info("%s:  %s", key, value);
        }
    }

    private void configureLogger() {
        PropertyConfigurator.configure("etc/log4j.properties");
    }

    private void configureProperties() {
        log.info(UtilMessage.INIT_SERVICE_MESSAGE, UtilProperties.class.getSimpleName());
        //
        UtilProperties.configure("etc/application.properties");
        properties = UtilProperties.getProperties();
        //
        log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                UtilProperties.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());
    }

    @Override
    public void destroy() {
        try {
            if (Objects.nonNull(properties)) {
                properties = null;
            }
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    UtilProperties.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        } catch (Exception e) {
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    UtilProperties.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }
}
