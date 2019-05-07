package com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl;

import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.ServiceStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.derby.DerbyDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.filesystem.FileSystemDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.sqlite.SqLiteDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Created by daniel.carvajal on 19-06-2018.
 */

@Setter
@Getter
public class DataSourceConfigApp implements Configuration {

    private SqLiteDataSource sqLiteDataSource;

    private DerbyDataSource derbyDataSource;

    private FileSystemDataSource fileSystemDataSource;

    public static final UtilLogger log = UtilLogger.getLogger(DataSourceConfigApp.class);

    @Override
    public void configure() {
        sqLiteDataSource();
        derbyDataSource();
        fileSystemDataSource();
    }

    private void sqLiteDataSource() {
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE, SqLiteDataSource.class.getSimpleName());

            sqLiteDataSource.configure(); // configure datasource
            sqLiteDataSource.open(); // open datasource
            //
            boolean isValid = sqLiteDataSource.test(); //test connection
            if (isValid) {
                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        SqLiteDataSource.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        SqLiteDataSource.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

            }
        } catch (Exception e){
                log.error(e.getMessage(), e);
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    SqLiteDataSource.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }

    private void derbyDataSource() {
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE, DerbyDataSource.class.getSimpleName());

            derbyDataSource.configure(); // configure datasource
            derbyDataSource.open(); // open datasource


            boolean isValid = derbyDataSource.test(); //test connection
            if (isValid) {
                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        DerbyDataSource.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        DerbyDataSource.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

            }
        }catch (Exception e){
                log.error(e.getMessage(), e);
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        DerbyDataSource.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }


    private void fileSystemDataSource() {
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE, FileSystemDataSource.class.getSimpleName());

            fileSystemDataSource.configure(); // configure datasource
            //dataSource.open(); // open datasource

            boolean isValid = fileSystemDataSource.test(); //test connection
            if (isValid) {
                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        FileSystemDataSource.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        FileSystemDataSource.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    FileSystemDataSource.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }


    @Override
    public void destroy() {
        try {
            if (Objects.nonNull(sqLiteDataSource) && sqLiteDataSource.test()) {
                sqLiteDataSource.close();
            }
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    SqLiteDataSource.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    SqLiteDataSource.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }

        try {
            if(Objects.nonNull(sqLiteDataSource) && derbyDataSource.test()){
                derbyDataSource.close();
            }
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    DerbyDataSource.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    DerbyDataSource.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }

        try {
            if(Objects.nonNull(fileSystemDataSource) && fileSystemDataSource.test()){
                fileSystemDataSource.close();
            }
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    FileSystemDataSource.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    FileSystemDataSource.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }
}
