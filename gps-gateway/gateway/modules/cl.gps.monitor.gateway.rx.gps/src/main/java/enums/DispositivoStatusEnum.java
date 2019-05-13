package com.sonda.transporte.consola.agente.firmware.infrastructure.enums;

public enum DispositivoStatusEnum {
        DISPOSITIVO_REGISTRADO("DISPOSITIVO_REGISTRADO", -1, "OK", "Dispositivo Id[%s] registrado"),
        DISPOSITIVO_NO_REGISTRADO("FIRMWARE_REGISTRADO", -1, "NOK", "Dispositivo NO registrado"),
        DISPOSITIVO_EXCEPTION("DISPOSITIVO_EXCEPTION", -1, "NOK", "Dispositivo NO registrado. Exception[%s] Cause[%s]"),
        DISPOSITIVO_NO_EXISTE_POSICION("DISPOSITIVO_NO_EXISTE_POSICION", -1, "NOK", "No se encontro posicion para el dispositivo %s], se continua con la aplicacion del firmware"),
        DISPOSITIVO_VELOCIDAD_CERO("DISPOSITIVO_NO_VELOCIDAD_CERO", -1, "NOK", "La velocidad promedio es [%s] para el dispositivo [%s], se continua con la aplicacion del firmware"),
        //
        DISPOSITIVO_NO_VELOCIDAD("DISPOSITIVO_NO_VELOCIDAD", 5, "NOK", "La velocidad promedio[%s] es menor que el limite de velocidad para el dispositivo[%s]"),
        DISPOSITIVO_NO_POSICION("DISPOSITIVO_NO_POSICION", 5, "NOK",  "El dispositivo[%s] no se encuentra en la posicion para aplicar el firmware"),
        DISPOSITIVO_NO_HORARIO("DISPOSITIVO_NO_HORARIO", 5, "NOK",  "El dispositivo[%s] no se encuetra el horario para aplicar el firmware"),
        DISPOSITIVO_FIRMWARE_EXCEPTION("DISPOSITIVO_FIRMWARE_EXCEPTION", -1, "NOK",  "El dispositivo no se puede comunicar con la consola... se continua con la aplicacion del firmware...");

    private final String id;
    private final int codigo;
    private final String tipoEstado;
    private final String descripcion;

    private DispositivoStatusEnum(String id, int codigo, String tipoEstado, String descripcion) {
        this.id = id;
        this.tipoEstado = tipoEstado;
        this.codigo = codigo;
        this.descripcion = descripcion;
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
