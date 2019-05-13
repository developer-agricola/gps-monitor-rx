package com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl;


import akka.actor.ActorSystem;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.CommandBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.ServiceStatusEnum;
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
public class BusConfigApp implements Configuration {

    private CommandBus commandBus;

    private ActorSystem actorSystem;
    
    public static final UtilLogger log = UtilLogger.getLogger(BusConfigApp.class);
    
    @Override
    public void configure() {
        commandBus();
    }

    public void commandBus(){
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE,
                    CommandBus.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            actorSystem = ActorSystem.create("consola-agente-firmware");
            commandBus.initialize(actorSystem);

            if (Objects.nonNull(commandBus) && Objects.nonNull(actorSystem)) {
                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        CommandBus.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        CommandBus.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    CommandBus.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }
    }

    @Override
    public void destroy() {
        try {
            if (Objects.nonNull(actorSystem) && Objects.nonNull(commandBus)) {
                actorSystem.terminate();
            }
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    CommandBus.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }catch (Exception e){
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    CommandBus.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }
}
