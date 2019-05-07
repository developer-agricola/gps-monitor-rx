package com.sonda.transporte.consola.agente.firmware.infrastructure.handler;

import com.sonda.transporte.consola.agente.firmware.application.command.GuardarFirmwareCommand;
import com.sonda.transporte.consola.agente.firmware.infrastructure.bus.CommandBus;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by daniel.carvajal on 21-06-2018.
 */
@Getter
@Setter
public class CommandHandler implements Handler {

    private CommandBus commandBus;

    public static final UtilLogger log = UtilLogger.getLogger(CommandHandler.class);

    public void handle(GuardarFirmwareCommand guardarFirmwareCommand) {
        commandBus.handle(guardarFirmwareCommand);
    }
}
