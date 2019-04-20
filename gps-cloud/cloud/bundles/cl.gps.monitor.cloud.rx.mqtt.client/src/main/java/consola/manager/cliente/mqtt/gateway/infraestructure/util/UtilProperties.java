package consola.manager.cliente.mqtt.gateway.infraestructure.util;


import java.io.IOException;
import java.io.InputStream;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by daniel.carvajal on 22-06-2018.
 */
public class UtilProperties {

    private UtilProperties() {
    }

    //private static final Properties properties = new Properties();

    //public static final String SQLITE_JDBC_URL_PROPERTY_KEY = "sqlite.jdbc.url";

    public static void configure(String configFileName) {
        InputStream istream = null;
        try {
            istream = UtilProperties.class.getResourceAsStream(configFileName);
            if(Objects.nonNull(istream)){
                LogManager.getLogManager().readConfiguration(istream);
            }else{
                Logger.getGlobal().log(Level.SEVERE, String.format("[%s] No se pudo leer el archivo de configuracion [%s]", UtilProperties.class.getSimpleName(), configFileName));
                Logger.getGlobal().log(Level.SEVERE, String.format("[%s] Ignorando el archivo de configuracion [%s]", UtilProperties.class.getSimpleName(), configFileName));
            }

        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, String.format("[%s] No se pudo leer el archivo de configuracion [%s]", UtilProperties.class.getSimpleName(), configFileName), e);
            Logger.getGlobal().log(Level.SEVERE, String.format("[%s] Ignorando el archivo de configuracion [%s]", UtilProperties.class.getSimpleName(), configFileName), e);
            Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException e) {
                    Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
    }
}
