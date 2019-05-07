package com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl;


import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.ServiceStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.archiva.ArchivaGateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


/**
 * Created by daniel.carvajal on 25-06-2018.
 */
 @Getter
 @Setter
public class HttpConfigApp implements Configuration {

    private ArchivaGateway archivaGateway;
    
    public static final UtilLogger log = UtilLogger.getLogger(HttpConfigApp.class);
    
    @Override
    public void configure() {
        archivaGateway();
    }

    public void archivaGateway(){
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE,
                    ArchivaGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            archivaGateway.initialize();

            boolean isAvailable = archivaGateway.isAvailable();
            if (isAvailable) {
                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        ArchivaGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        ArchivaGateway.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    ArchivaGateway.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }
    }

    @Override
    public void destroy() {
        try {
            if (Objects.nonNull(archivaGateway) && Objects.nonNull(archivaGateway.getHttpClient())) {
                archivaGateway.getHttpClient().close();
            }
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    ArchivaGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }catch (Exception e){
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    ArchivaGateway.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }
}
