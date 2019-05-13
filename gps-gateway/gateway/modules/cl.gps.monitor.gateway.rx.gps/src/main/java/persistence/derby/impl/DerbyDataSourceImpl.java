package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.derby.impl;

import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.derby.DerbyDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import org.knowm.yank.Yank;

import java.util.Properties;

public class DerbyDataSourceImpl implements DerbyDataSource {

    private Properties derbyProperties;

    private static final int RETRY_COUNT = 3;

    private static final String DERBY_POOL_NAME = "derby-pool";

    protected static final Properties appProperties = UtilProperties.getProperties();

    public static final UtilLogger log = UtilLogger.getLogger(DerbyDataSourceImpl.class);

    public DerbyDataSourceImpl() {
        this.derbyProperties = new Properties();
    }

    @Override
    public void configure() {
        derbyProperties.setProperty("jdbcUrl",
                appProperties.getProperty(UtilProperties.DERBY_JDBC_URL_PROPERTY_KEY));
        derbyProperties.setProperty("maximumPoolSize",
                appProperties.getProperty(UtilProperties.DERBY_MAXIMUM_POOL_SIZE_PROPERTY_KEY));
        derbyProperties.setProperty("driverClassName",
                appProperties.getProperty(UtilProperties.DERBY_DRIVER_CLASSNAME_PROPERTY_KEY));
    }

    @Override
    public void open() {
        Yank.setupConnectionPool(DERBY_POOL_NAME, derbyProperties);
        Yank.setThrowWrappedExceptions(true);
    }

    @Override
    public void close() {
        Yank.releaseConnectionPool(DERBY_POOL_NAME);
    }

    @Override
    public boolean test() {
        boolean isActive = false;
        try {
            int result =
                    Yank.queryScalar(DERBY_POOL_NAME, "SELECT 1 AS RESULT from sysibm.sysdummy1", Integer.class, null);
            if (result == 1) {
                isActive = true;
            } else {
                log.error(" [%s] is not active", DERBY_POOL_NAME);
            }
        }catch (Exception e){
            log.error(" [%s] is not active", DERBY_POOL_NAME);
        }
        return isActive;
    }

    @Override
    public void init() {
        String query = new StringBuilder()
                .append("CREATE TABLE ")
                .append("    CONSOLA_POSICION ( ")
                .append("                  MOMENTO                TIMESTAMP DEFAULT CURRENT_TIMESTAMP, ")
                .append("                  LAT                    DOUBLE, ")
                .append("                  LON                    DOUBLE, ")
                .append("                  VELOCIDAD              DOUBLE)")
                .toString();
        Yank.execute(DERBY_POOL_NAME, query, new Object[]{});

        Object[] row1 = {"2018-06-05 12:13:27", -33.4547767639, -70.7623443604, 1 };
        Yank.insert(DERBY_POOL_NAME,"INSERT INTO CONSOLA_POSICION VALUES(?, ?, ?, ?)", row1);

        Object[] row2 = {"2018-06-05 12:13:31", -33.4549484253, -70.7623062134, 2  };
        Yank.insert(DERBY_POOL_NAME,"INSERT INTO CONSOLA_POSICION VALUES(?, ?, ?, ?) ", row2);

        Object[] row3 = {"2018-06-05 12:13:35", -33.4552230835, -70.7622375488, 3 };
        Yank.insert(DERBY_POOL_NAME,"INSERT INTO CONSOLA_POSICION VALUES(?, ?, ?, ?)  ", row3);

        Object[] row4 = {"2018-06-05 12:13:39", -33.4553794861, -70.7621917725, 4 };
        Yank.insert(DERBY_POOL_NAME,"INSERT INTO CONSOLA_POSICION VALUES(?, ?, ?, ?)   ", row4);

        Object[] row5 = {"2018-06-05 12:13:43", -33.4554748535, -70.7621536255, 5 };
        Yank.insert(DERBY_POOL_NAME,"INSERT INTO CONSOLA_POSICION VALUES(?, ?, ?, ?)    ", row5); //last row
    }

    @Override
    public boolean reconnect() {
        boolean isReconnect = false;

        int retryCount = 0;
        while(retryCount < RETRY_COUNT) {
            open();
            if (!test()) {
                retryCount++;
            }else{
                isReconnect = true;
                break;
            }
        }
        return isReconnect;
    }


    @Override
    public String getPoolName() {
        return DERBY_POOL_NAME;
    }
}
