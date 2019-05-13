package com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl;

import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.FirmwareContextApp;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.ServiceStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;

/**
 * Created by daniel.carvajal on 19-06-2018.
 */
public class ShutdownHookConfigApp implements Configuration {

    public static final UtilLogger log = UtilLogger.getLogger(ShutdownHookConfigApp.class);

    @Override
    public void configure() {
        log.info(UtilMessage.INIT_SERVICE_MESSAGE,
                FirmwareAgentShutdownHook.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());
        try {
            Runtime.getRuntime().addShutdownHook(new FirmwareAgentShutdownHook());

            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    FirmwareAgentShutdownHook.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

        } catch (Exception e) {
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    FirmwareAgentShutdownHook.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());
        }
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException();
    }

    private class FirmwareAgentShutdownHook extends Thread {

        public FirmwareAgentShutdownHook() {
            super();
            this.setName("FirmwareAgentShutdownHook");
        }

        @Override
        public void run() {
            FirmwareContextApp firmwareContexApp = FirmwareContextApp.getContextInstance();
            // destroy all context
            firmwareContexApp.destroy();

            Runtime.getRuntime().halt(0);
        }
    }
}
