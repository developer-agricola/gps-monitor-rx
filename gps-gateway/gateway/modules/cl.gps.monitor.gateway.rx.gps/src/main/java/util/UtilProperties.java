package com.sonda.transporte.consola.agente.firmware.infrastructure.util;

import org.apache.log4j.helpers.LogLog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Properties;

/**
 * Created by daniel.carvajal on 22-06-2018.
 */
public class UtilProperties {

    private UtilProperties() {
    }

    private static final Properties properties = new Properties();

    public static final String SQLITE_JDBC_URL_PROPERTY_KEY = "sqlite.jdbc.url";
    public static final String SQLITE_MAXIMUM_POOL_SIZE_KEY = "sqlite.maximum.pool.size";

    public static final String DERBY_JDBC_URL_PROPERTY_KEY = "derby.jdbc.url";
    public static final String DERBY_MAXIMUM_POOL_SIZE_PROPERTY_KEY = "derby.maximum.pool.size";
    public static final String DERBY_DRIVER_CLASSNAME_PROPERTY_KEY = "derby.driver.className";

    public static final String MQTT_SERVER_PROPERTY_KEY = "mqtt.server";
    public static final String MQTT_SERVER_PORT_PROPERTY_KEY = "mqtt.server.port";

    public static final String MQTT_OPTIONS_CLEAN_SESSION_PROPERTY_KEY = "mqtt.options.clean.session";
    public static final String MQTT_OPTIONS_KEEP_ALIVE_INTERVAL_PROPERTY_KEY = "mqtt.options.keep.alive.interval";
    public static final String MQTT_OPTIONS_AUTOMATIC_RECONNECT_PROPERTY_KEY = "mqtt.options.automatic.reconnect";
    public static final String MQTT_OPTIONS_CONNECTION_TIMEOUT_PROPERTY_KEY = "mqtt.options.connection.timeout";
    public static final String MQTT_OPTIONS_MAX_INFLIGHT_PROPERTY_KEY = "mqtt.options.max.inflight";

    public static final String MQTT_BUFFER_OPTIONS_BUFFER_ENABLED_PROPERTY_KEY = "mqtt.buffer.options.buffer.enabled";
    public static final String MQTT_BUFFER_OPTIONS_PERSIST_BUFFER_PROPERTY_KEY = "mqtt.buffer.options.persist.buffer";
    public static final String MQTT_BUFFER_OPTIONS_BUFFER_SIZE_PROPERTY_KEY = "mqtt.buffer.options.buffer.size";


    public static final String MQTT_TOPIC_SUSCRIBER_PROPERTY_KEY = "mqtt.topic.subscriber";
    public static final String MQTT_TOPIC_SUBCRIBER_QOS_PROPERTY_KEY = "mqtt.topic.subcriber.qos";

    public static final String MQTT_TOPIC_PUBLISHER_PROPERTY_KEY = "mqtt.topic.publisher";
    public static final String MQTT_TOPIC_PUBLISHER_QOS_PROPERTY_KEY = "mqtt.topic.publisher.qos";
    public static final String MQTT_TOPIC_PUBLISHER_RETENTION_PROPERTY_KEY = "mqtt.topic.publisher.retention";

    public static final String JOB_UPDATE_FIRMWARE_CRON_EXPRESSION_PROPERTY_KEY = "job.update.firmware.cron.expression";
    public static final String JOB_MQTT_CONNECTION_LISTENER_CRON_EXPRESSION_PROPERTY_KEY = "job.mqtt.conection.listener.cron.expression";

    public static final String ARCHIVA_SERVER_PROPERTY_KEY = "archiva.server";
    public static final String ARCHIVA_SERVER_PORT_PROPERTY_KEY = "archiva.server.port";
    public static final String ARCHIVA_SERVER_REPOSITORY_PROPERTY_KEY = "archiva.server.repository";
    public static final String ARCHIVA_SERVER_USERNAME_PROPERTY_KEY = "archiva.server.user";
    public static final String ARCHIVA_SERVER_PASSWRD_PROPERTY_KEY = "archiva.server.password";
    public static final String ARCHIVA_SERVER_REQUEST_CONNECT_TIMEOUT = "archiva.server.request.connect.timeout";
    public static final String ARCHIVA_SERVER_CONNECT_TIMEOUT = "archiva.server.connect.timeout";
    public static final String ARCHIVA_SERVER_SOCKET_TIMEOUT = "archiva.server.socket.timeout";

    public static final String ARCHIVA_SERVER_API_PINGSERVICE_PING_ENDPOINT = "archiva.server.api.PingService.ping.endpoint";

    public static final String FIRMWARE_DEFAULT_WORK_DIRECTORY_PROPERTY_KEY = "firmware.default.work.directory";

    public static final String DISPOSITIVO_VELOCIDAD_RULE_ENABLED = "dispositivo.velocidad.rule.enabled";
    public static final String DISPOSITIVO_VELOCIDAD_RULE_LIMIT_DEFAULT_PROPERTY_KEY = "dispositivo.velocidad.rule.limit.default";

    public static final String DISPOSITIVO_POSICION_RULE_ENABLED = "dispositivo.posicion.rule.enabled";
    public static final String DISPOSITIVO_POSICION_RULE_RADIO_DEFAULT_PROPERTY_KEY = "dispositivo.posicion.rule.radio.default";
    public static final String DISPOSITIVO_POSICION_RULE_LATITUD_DEFAULT_PROPERTY_KEY = "dispositivo.posicion.rule.latitud.default";
    public static final String DISPOSITIVO_POSICION_RULE_LONGITUD_DEFAULT_PROPERTY_KEY = "dispositivo.posicion.rule.longitud.default";

    public static final String DISPOSITIVO_HORARIO_RULE_ENABLED = "dispositivo.horario.rule.enabled";
    public static final String DISPOSITIVO_HORARIO_RULE_HORA_APLICAR_DEFAULT_PROPERTY_KEY = "dispositivo.horario.rule.hora.aplicar.default";
    public static final String DISPOSITIVO_HORARIO_RULE_LIMITE_HORA_DEFAULT_PROPERTY_KEY = "dispositivo.horario.rule.limite.hora.aplicar.default";

    public static void configure(InputStream inputStream){
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            if (e instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            LogLog.error("Could not read configuration file from InputStream [" + inputStream + "].", e);
            LogLog.error("Ignoring configuration InputStream [" + inputStream +"].");
        }
    }

    public static void configure(String configFileName) {
        FileInputStream istream = null;
        try {
            istream = new FileInputStream(configFileName);
            properties.load(istream);
            istream.close();

        } catch (Exception e) {
            if (e instanceof InterruptedIOException || e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            LogLog.error("Could not read configuration file [" + configFileName + "].", e);
            LogLog.error("Ignoring configuration file [" + configFileName + "].");
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (InterruptedIOException e) {
                    Thread.currentThread().interrupt();
                } catch (Throwable e) {
                    ;
                }
            }
        }
    }

    public static Properties getProperties() {
        return properties;
    }

}
