package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.Bus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.CommandBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.DispositivoEventBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.FirmwareEventBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

public class BusFactory {

    private static BusFactory busFactory;

    public static final UtilLogger log = UtilLogger.getLogger(BusFactory.class);

    private CommandBus commandBus;
    private DispositivoEventBus dispositivoEventBus;
    private FirmwareEventBus firmwareEventBus;

    public static BusFactory getFactoryInstance() {
        if (busFactory == null) {
            busFactory = new BusFactory();
        }
        return busFactory;
    }

    public Bus getBus(Class<? extends Bus> clazz) {
        Bus dao = null;

        if (clazz.equals(CommandBus.class)) {
            if (commandBus == null) {
                commandBus = new CommandBus();
            }
            return commandBus;
        }else if (clazz.equals(DispositivoEventBus.class)) {
            if (dispositivoEventBus == null) {
                dispositivoEventBus = new DispositivoEventBus();
            }
            return dispositivoEventBus;
        } else if (clazz.equals(FirmwareEventBus.class)) {
            if (firmwareEventBus == null) {
                firmwareEventBus = new FirmwareEventBus();
            }
            return firmwareEventBus;
        } else {
            log.error("La clase[%s] no es un Bus", clazz.getSimpleName());
        }
        return dao;
    }
}