package consola.manager.cliente.mqtt.gateway.infraestructure.enums;

/**
 * Created by daniel.carvajal on 29-03-2019.
 */
public enum MessageBusStrategy {
    SECUENCE_STRATEGY("SECUENCE_STRATEGY", "Estrategia que se base en stream secuenciales"),
    ASYNCHRONOUS_STRATEGY("ASYNCHRONOUS_STRATEGY", "Estrategia que se base en stream asyncronos"),
    PARALLEL_ASYNCRONUS_STRATEGY("PARALLEL_ASYNCRONUS_STRATEGY", "Estrategia que se base en stream asyncronos");

    private final String id;
    private final String decription;

    private MessageBusStrategy(String id, String decription) {
        this.id = id;
        this.decription = decription;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return decription;
    }
}
