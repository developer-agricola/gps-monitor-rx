package cl.gps.monitor.gateway.gps.config.util;


import org.apache.log4j.Logger;

/**
 * Created by daniel.carvajal on 22-06-2018.
 */

public class UtilLogger {

    private Logger log4j;

    private UtilLogger(final Class<? extends Object> clase) {
        log4j = Logger.getLogger(clase);
    }

    /**
     * Factory para construir el logger.
     *
     * @param clase Clase que escribira en el log.
     * @return Instancia del consolaLogger.
     */
    public static UtilLogger getLogger(final Class<? extends Object> clase) {
        return new UtilLogger(clase);
    }

    public void info(String message) {
        if (log4j.isInfoEnabled()) {
            log4j.info(message);
        }
    }

    public void info(String message, Object... parameters) {
        if (log4j.isInfoEnabled()) {
            log4j.info(String.format(message, parameters));
        }
    }

    public void debug(String message) {
        if (log4j.isDebugEnabled()) {
            log4j.debug(message);
        }
    }

    public void debug(String message, Object... parameters) {
        if (log4j.isDebugEnabled()) {
            log4j.debug(String.format(message, parameters));
        }
    }

    public void error(String message) {
        log4j.error(message);
    }

    public void error(String message, Object... parameters) {
        log4j.error(String.format(message, parameters));
    }


    public void error(String message, Throwable e) {
        log4j.error(message, e);
    }

    public void warning(String message) {
        log4j.warn(String.format(message));
    }

    public void warning(String message, Object... parameters) {
        log4j.warn(String.format(message, parameters));
    }
}
