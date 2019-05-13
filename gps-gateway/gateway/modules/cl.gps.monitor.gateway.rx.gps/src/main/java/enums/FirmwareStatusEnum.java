package com.sonda.transporte.consola.agente.firmware.infrastructure.enums;


public enum FirmwareStatusEnum {
    // 1	 Recibido Consola
    FIRMWARE_REGISTRADO("FIRMWARE_REGISTRADO", 1, "OK", "Firmware Id[%s] registrado"),
    // 8	 Incompleta
    FIRMWARE_NO_REGISTRADO("FIRMWARE_NO_REGISTRADO", 8, "NOK", "Firmware Id[%s] NO registrado"),
    // 6	 Excepciones
    FIRMWARE_NO_REGISTRADOR_EXCEPTION("FIRMWARE_NO_REGISTRADOR_EXCEPTION", 6, "NOK", "Firmware Id[%s] NO registrado. Exception[%s] Cause[%s]"),
    // 2	 Descargada	Descargada OK
    FIRMWARE_DESCARGADO("FIRMWARE_DESCARGADO", 2, "OK", "Firmware Id[%s] URL[%s] descargado desde archiva : HttpStatus[%s]"),
    // 14	Descarga NOK
    FIRMWARE_NO_DESCARGADO("FIRMWARE_NO_DESCARGADO", 14, "NOK", "Firmware Id[%s] URL[%s] NO descargado desde archiva: HttpStatus[%s]"),
    // 2	 Descargada	Descargada OK
    FIRMWARE_EN_DISCO("FIRMWARE_EN_DISCO", 2, "OK", "Firmware Id[%s] Path[%s] Archivo[%s] escrito en disco"),
    // 8	 Incompleta
    FIRMWARE_NO_DISCO("FIRMWARE_NO_DISCO", 8, "NOK", "Firmware Id[%s] NO escrito en disco"),
    // 2	 Descargada	Descargada OK
    FIRMWARE_DESCARGADO_END_DISCO("FIRMWARE_DESCARGADO_END_DISCO", 2, "OK", "Firmware Id[%s] Archivo[%s] descargado y persistido en disco"),
    // 14	Descarga NOK
    FIRMWARE_NO_DESCARGADO_END_DISCO("FIRMWARE_NO_DESCARGADO_END_DISCO", 14, "NOK", "Firmware Id[%s] NO descargado No persistido en disco"),
    // 8	 Incompleta
    FIRMWARE_DESCARGA_INCOMPLETA("FIRMWARE_DESCARGA_INCOMPLETA", 8, "NOK", "No se ha completado la descarga del firmware [%s] a pesar de los reintentos..."),
    // 5	 Excepcion de Ruta
    FIRMWARE_NO_DESCARGADO_END_DISCO_EXCEPTION("FIRMWARE_NO_DESCARGADO_END_DISCO_EXCEPTION", 14, "NOK", "Firmware Id[%s] NO descargado NO escrito en disco. Exception[%s] Cause[%s]"),
    // 1	 Recibido Consola
    FIRMWARE_ENCONTRADO_DISCO("FIRMWARE_ENCONTRADO_DISCO", 1, "OK", "Firmware Id[%s] encontrado Archivo[%s] para aplicar"),
    //12	 Error de Paquete
    FIRMWARE_NO_ENCONTRADO_DISCO("FIRMWARE_NO_ENCONTRADO_DISCO", 12, "NOK", "Firmware Id[%s] NO encotrado"),
    // 2	 Descargada	Descargada OK
    FIRMWARE_SCRIPT_EJECUTADO_OK("FIRMWARE_SCRIPT_EJECUTADO_OK", 2, "OK", "Script[%s] estado [%s]"),
    // 8	 Incompleta
    FIRMWARE_SCRIPT_EJECUTADO_NOK("FIRMWARE_SCRIPT_EJECUTADO_NOK", 8, "OK", "Script[%s] estado [%s]"),
    // 2	 Descargada	Descargada OK
    FIRMWARE_SCRIPT_ENCONTRADO("FIRMWARE_SCRIPT_ENCONTRADO", 2, "OK", "Firmware Id[%s] se encontro script de instalacion[%s] para aplicar"),
    // 11	 Error de Estructura
    FIRMWARE_SCRIPT_NO_ENCONTRADO("FIRMWARE_SCRIPT_NO_ENCONTRADO", 11, "NOK", "Firmware Id[%s]  script de intalacion NO encotrado"),
    // 9	 Actualizada OK	Actualizacion OK
    FIRMWARE_ENCONTRADO_APLICADO("FIRMWARE_ENCONTRADO_APLICADO", 9, "OK", "Paquete de Firmware Id[%s][%s] se encontro y se aplico correctamente"),
    // 8	 Incompleta
    FIRMWARE_NO_APLICADO("FIRMWARE_NO_APLICADO", 8, "NOK", "Firmware Id[%s]  tuvo problemas al aplicarse"),
    // 6	 Excepciones
    FIRMWARE_NO_ENCONTRADO_NO_APLICADO_EXCEPTION("FIRMWARE_NO_ENCONTRADO_NO_APLICADO_EXCEPTION", 6, "NOK", "Firmware Id[%s] NO encontrado NO aplicado. Exception[%s] Cause[%s]"),
    // 6	 Excepciones
    ALL_FIRMWARE_NO_ENCONTRADO_NO_APLICADO_EXCEPTION("ALL_FIRMWARE_NO_ENCONTRADO_NO_APLICADO_EXCEPTION", 6, "NOK", "Error al buscar la lista de los firmwares por aplicar. Exception[%s] Cause[%s]"),
    // 6	 Excepciones
    FIRMWARE_NO_NOTIFICADO("FIRMWARE_NO_NOTIFICADO", 6, "NOK", "Se ha producido un error al notificar el ultimo estado del firmware Id[%s]"),
    // 6	 Excepciones
    FINAL_STATUS_FIRMWARE_NO_NOTIFICADO_EXCEPTION("FINAL_STATUS_FIRMWARE_NO_NOTIFICADO_EXCEPTION", 6, "NOK", "Error al enviar el ultimo estado para el firmware. Exception[%s] Cause[%s]"),
    // 5	 Excepcion de Ruta
    FIRMWARE_NO_REGLAS_RUTA("FIRMWARE_NO_REGLAS_RUTA", 5, "NOK", "Se han cumplido las reglas de negocio para aplicar el firmware");



    private final String id;
    private final int codigo;
    private final String tipoEstado;
    private final String descripcion;

    private FirmwareStatusEnum(String id, int codigo, String tipoEstado, String descripcion) {
        this.id = id;
        this.tipoEstado = tipoEstado;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public static FirmwareStatusEnum getStatusEnumById(String id) {
        for (FirmwareStatusEnum e : FirmwareStatusEnum.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
