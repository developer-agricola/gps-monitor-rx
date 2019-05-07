package com.sonda.transporte.consola.agente.firmware.infrastructure;

import com.sonda.transporte.consola.agente.firmware.infrastructure.config.Configuration;
import com.sonda.transporte.consola.agente.firmware.infrastructure.config.impl.*;

/**
 * Created by daniel.carvajal on 19-06-2018.
 */
public class FirmwareAgentApp {

    public static void main(String[] args) {
        FirmwareAgentApp.run(args,
                PropertiesConfigApp.class, DataSourceConfigApp.class, HttpConfigApp.class, BusConfigApp.class,
                    JobSchedulerConfigApp.class, MqttConfigApp.class, ShutdownHookConfigApp.class);
    }

    public static void run(String[] args, Class<? extends Configuration>... configurations){
        // get context
        FirmwareContextApp firmwareContexApp = FirmwareContextApp.getContextInstance();
        // init context
        firmwareContexApp.initialize(args, configurations);
    }
}
