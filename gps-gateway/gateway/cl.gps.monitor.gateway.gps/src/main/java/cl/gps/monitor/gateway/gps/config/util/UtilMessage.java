package cl.gps.monitor.gateway.gps.config.util;

public class UtilMessage {

    private UtilMessage(){}

    public static final String INIT_SERVICE_MESSAGE           = "Init service   ............... [%s]";
    public static final String STATUS_SERVICE_MESSAGE         = "Estatus service ............... [%s] [%s]";

    public static final String CONFIG_SERVICE_MESSAGE         = "[%S] [%s]";

    public static final String SEND_EVENT                     = "Enviando evento [%s]";
    public static final String ERROR_SEND_EVENT               = "Error al enviar el evento [%s]";
    public static final String ERROR_TO_CONVERT_CLASS         = "Error al convertir [%s]";

    public static final String ERROR_FIRMWARE_NULO            = "El firmware del evento [%s] no puede ser nulo";
    public static final String ERROR_DISPOSITIVO_NULO         = "El dispositivo no puede venir nulo";
    public static final String ERROR_EVENTO_NULO              = "El evento no puede venir nulo";

    public static final String FOLDER_NOT_CREATED             = "No se pudo crear la carpeta[%s]";

}
