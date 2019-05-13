package com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl;

import com.sonda.transporte.consola.agente.firmware.application.job.ApplyFirmwareJob;
import com.sonda.transporte.consola.agente.firmware.application.job.MqttRetryConnectionJob;
import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.ServiceStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilMessage;
import lombok.Getter;
import lombok.Setter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Objects;

/**
 * Created by daniel.carvajal on 25-06-2018.
 */

@Getter
@Setter
public class JobSchedulerConfigApp implements Configuration {

    private Scheduler schedulerFirmwareAgent;

    private ApplyFirmwareJob applyFirmwareJob;

    private MqttRetryConnectionJob mqttRetryConnectionJob;

    public static final UtilLogger log = UtilLogger.getLogger(JobSchedulerConfigApp.class);

    @Override
    public void configure() {
        try {
            log.info(UtilMessage.INIT_SERVICE_MESSAGE,
                    Scheduler.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());
            // default
            schedulerFirmwareAgent = StdSchedulerFactory.getDefaultScheduler();
            //start
            schedulerFirmwareAgent.start();

            // job
            applyFirmwareJob.initialize();
            applyFirmwareJob.schedule();
            // job
            mqttRetryConnectionJob.initialize();
            //mqttRetryConnectionJob.schedule();

            boolean isValid = schedulerFirmwareAgent.isStarted();
            if (isValid) {
                log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                        Scheduler.class.getSimpleName(), ServiceStatusEnum.SERVICE_UP.getStatusDescription());

            } else {
                log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                        Scheduler.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
            }

        }catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    Scheduler.class.getSimpleName(),ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }

    }

    @Override
    public void destroy() {
        try {
            if (Objects.nonNull(schedulerFirmwareAgent) && schedulerFirmwareAgent.isStarted()) {
                schedulerFirmwareAgent.shutdown();
            }
            log.info(UtilMessage.STATUS_SERVICE_MESSAGE,
                    Scheduler.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());

        }catch (Exception e){
            log.error(e.getMessage(), e);
            log.error(UtilMessage.STATUS_SERVICE_MESSAGE,
                    Scheduler.class.getSimpleName(), ServiceStatusEnum.SERVICE_DOWN.getStatusDescription());
        }
    }
}
