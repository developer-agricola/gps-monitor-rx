package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.application.job.ApplyFirmwareJob;
import com.sonda.transporte.consola.agente.firmware.application.job.MqttRetryConnectionJob;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import org.quartz.Job;

public class JobFactory {

    private static JobFactory jobFactory;

    private ApplyFirmwareJob applyFirmwareJob;

    private MqttRetryConnectionJob mqttRetryConnectionJob;

    public static final UtilLogger log = UtilLogger.getLogger(JobFactory.class);

    public static JobFactory getFactoryInstance() {
        if (jobFactory == null) {
            jobFactory = new JobFactory();
        }
        return jobFactory;
    }

    public Job getJob(Class<? extends Job> clazz) {
        Job job = null;

        if (clazz.equals(ApplyFirmwareJob.class)) {
            if (applyFirmwareJob == null) {
                applyFirmwareJob = new ApplyFirmwareJob();
            }
            job = applyFirmwareJob;
        }else if (clazz.equals(MqttRetryConnectionJob.class)) {
            if (mqttRetryConnectionJob == null) {
                mqttRetryConnectionJob = new MqttRetryConnectionJob();
            }
            job = mqttRetryConnectionJob;
        } else {
            log.error("La clase[%s] no es un Job", clazz.getSimpleName());
        }
        return job;
    }
}