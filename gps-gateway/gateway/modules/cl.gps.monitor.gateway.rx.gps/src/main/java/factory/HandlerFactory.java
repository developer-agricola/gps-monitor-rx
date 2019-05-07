package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.infrastructure.handler.*;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

public class HandlerFactory {

    private CommandHandler commandHandler;
    private DispositivoEventHandler dispositivoEventHandler;
    private DispositivoErrorHandler dispositivoErrorHandler;
    private FirmwareEventHandler firmwareEventHandler;
    private FirmwareErrorHandler firmwareErrorHandler;

    public static final UtilLogger log = UtilLogger.getLogger(HandlerFactory.class);

    private static HandlerFactory handlerFactory;

    public static HandlerFactory getFactoryInstance() {
        if(handlerFactory == null) {
            handlerFactory = new HandlerFactory();
        }
        return handlerFactory;
    }

    public Handler getHanlder(Class<? extends Handler> clazz){
        Handler handler = null;
        if(clazz.equals(CommandHandler.class)) {
            if(commandHandler == null){
                commandHandler = new CommandHandler();
            }
            return commandHandler;
        }else if(clazz.equals(DispositivoEventHandler.class)){
            if(dispositivoEventHandler == null){
                dispositivoEventHandler = new DispositivoEventHandler();
            }
            return dispositivoEventHandler;
        }else if(clazz.equals(DispositivoErrorHandler.class)){
            if(dispositivoErrorHandler == null){
                dispositivoErrorHandler = new DispositivoErrorHandler();
            }
            return dispositivoErrorHandler;
        }else if(clazz.equals(FirmwareEventHandler.class)){
            if(firmwareEventHandler == null){
                firmwareEventHandler = new FirmwareEventHandler();
            }
            return firmwareEventHandler;
        }else if(clazz.equals(FirmwareErrorHandler.class)){
            if(firmwareErrorHandler == null){
                firmwareErrorHandler = new FirmwareErrorHandler();
            }
            return firmwareErrorHandler;
        }else{
            log.error("La clase [%s] no es un handler", clazz.getSimpleName());
        }
        return handler;
    }

}
