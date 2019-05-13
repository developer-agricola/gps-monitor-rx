package com.sonda.transporte.consola.agente.firmware.infrastructure.enums;

public enum TipoDispositivoEnum {
        DISPOSITIVO_CONSOLA("DISPOSITIVO_CONSOLA",  "CONSOLA");

        private final String id;
        private final String descripcion;

        private TipoDispositivoEnum(String id, String descripcion) {
            this.id = id;
            this.descripcion = descripcion;
        }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
