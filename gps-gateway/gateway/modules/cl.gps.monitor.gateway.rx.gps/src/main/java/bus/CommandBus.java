package com.sonda.transporte.consola.agente.firmware.infrastructure.bus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.sonda.transporte.consola.agente.firmware.application.command.GuardarFirmwareCommand;
import com.sonda.transporte.consola.agente.firmware.domain.DispositivoService;
import com.sonda.transporte.consola.agente.firmware.infrastructure.actor.DispositivoActor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Created by daniel.carvajal on 07-01-2019.
 */
@Getter
@Setter
public class CommandBus implements Bus {

    private ActorRef dispositivoActor;

    private DispositivoService dispositivoService;

    public void initialize(ActorSystem actorSystem) throws IOException {
        dispositivoActor = actorSystem.actorOf(DispositivoActor.props(dispositivoService), "dispositivoActor");
    }

    public void handle(GuardarFirmwareCommand command) {
        dispositivoActor.tell(command, ActorRef.noSender());
    }
}
