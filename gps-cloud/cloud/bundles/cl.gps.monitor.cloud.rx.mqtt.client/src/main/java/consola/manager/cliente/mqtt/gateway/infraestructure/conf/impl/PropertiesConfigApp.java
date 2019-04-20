package consola.manager.cliente.mqtt.gateway.infraestructure.conf.impl;

import consola.manager.cliente.mqtt.gateway.infraestructure.conf.Configuration;
import consola.manager.cliente.mqtt.gateway.infraestructure.util.UtilProperties;

/**
 * Created by daniel.carvajal on 19-06-2018.
 */

public class PropertiesConfigApp implements Configuration {

    @Override
    public void configure() {
        configureLogger(); // configure logger
    }

    private void configureLogger() {
        UtilProperties.configure("/jsr47min.properties");
    }

    private void configureProperties() {
        // TODO ver si esta clase se debe utilizar con algo
    }

    @Override
    public void destroy() {
        //TODO ver si esta clase se debe utilizar con algo
    }
}
