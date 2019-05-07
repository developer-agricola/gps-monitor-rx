package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.sqlite.impl;

import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.sqlite.SqLiteDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import org.knowm.yank.Yank;

import java.util.Properties;

/**
 * Created by daniel.carvajal on 19-06-2018.
 */
public class SqLiteDataSourceImpl implements SqLiteDataSource {

    private Properties sqLiteProperties;

    private static final String SQLITE_POOL_NAME = "sqLite-pool";

    protected static final Properties appProperties = UtilProperties.getProperties();

    public static final UtilLogger log = UtilLogger.getLogger(SqLiteDataSourceImpl.class);

    public SqLiteDataSourceImpl() {
        this.sqLiteProperties = new Properties();
    }

    @Override
    public void configure() {
        sqLiteProperties.setProperty("jdbcUrl",
                appProperties.getProperty(UtilProperties.SQLITE_JDBC_URL_PROPERTY_KEY));
        sqLiteProperties.setProperty("maximumPoolSize",
                appProperties.getProperty(UtilProperties.SQLITE_MAXIMUM_POOL_SIZE_KEY));
    }

    @Override
    public void open() {
        Yank.setupConnectionPool(SQLITE_POOL_NAME, sqLiteProperties);
        Yank.setThrowWrappedExceptions(true);
    }

    @Override
    public void close() {
        Yank.releaseConnectionPool(SQLITE_POOL_NAME);
    }

    @Override
    public boolean test(){
        boolean isActive = false;
        try {
            int result =
                    Yank.queryScalar(SQLITE_POOL_NAME, "SELECT 1 AS RESULT", Integer.class, null);
            if (result == 1) {
                isActive = true;
            } else {
                log.error(" [%s] is not active", SQLITE_POOL_NAME);
            }
        }catch (Exception e){
            log.error(" [%s] is not active", SQLITE_POOL_NAME);
        }
        return isActive;
    }

    @Override
    public void init() {
        String drop1   = "DROP TABLE IF EXISTS DISPOSITIVO";
        Yank.execute(SQLITE_POOL_NAME, drop1, new Object[]{});
        //
        String create1 = new StringBuilder()
                .append("CREATE TABLE ")
                .append("       DISPOSITIVO( ")
                .append("                  DISPOSITIVO_ID         INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("                  NOMBRE_DISPOSITIVO     TEXT NOT NULL, ")
                .append("                  TIPO_ID                TEXT NOT NULL, ")
                .append("                  ESTADO_ID              TEXT NOT NULL) ")
                .append("                                                        ")
                .toString();
        Yank.execute(SQLITE_POOL_NAME, create1, new Object[]{});

        String drop2   = "DROP TABLE IF EXISTS FIRMWARE";
        Yank.execute(SQLITE_POOL_NAME, drop2, new Object[]{});
        //
        String create2 = new StringBuilder()
                .append("CREATE TABLE")
                .append("       FIRMWARE ( ")
                .append("                  FIRMWARE_ID            INTEGER PRIMARY KEY NOT NULL,")
                .append("                  NOMBRE_FIRMWARE        TEXT,")
                .append("                  DIFUSION_ID            INTEGER,")
                .append("                  EQUIPO_ID              INTEGER,")
                .append("                  GROUP_ID               TEXT,")
                .append("                  ARTIFACT_ID            TEXT,")
                .append("                  VERSION                TEXT,")
                .append("                  PACKAGE_ID             TEXT,")
                .append("                  RUTA_ARCHIVO           TEXT,")
                .append("                  NOMBRE_ARCHIVO         TEXT,")
                .append("                  DISPOSITIVO_ID         INTEGER,")
                .append("                  TIPO_ID                TEXT,")
                .append("                  ESTADO_ID              TEXT,")
                .append("                  FECHA_CREACION         TEXT,")
                .append("                  FECHA_ACTUALIZACION    TEXT,")
                .append("                  NOTIFICADO             INTEGER)")
                .toString();
        Yank.execute(SQLITE_POOL_NAME, create2, new Object[]{});
    }

    @Override
    public String getPoolName() {
        return SQLITE_POOL_NAME;
    }

}
