package com.sonda.transporte.consola.agente.firmware.infrastructure.enums;

public enum TipoFirmwareEnum {
        FIRMWARE_DE_INSTALACION("FIRMWARE_DE_INSTALACION",  "Firmware de Instalacion"),
        FIRMWARE_DE_ACTUALIZACION("FIRMWARE_DE_ACTUALIZACION",  "Firmware de Actualizacion"),
        FIRMWARE_DE_DESINSTALACION("FIRMWARE_DE_DESINSTALACION",  "Firmware de Desinstalacion");

        private final String id;
        private final String descripcion;

        private TipoFirmwareEnum(String id, String descripcion) {
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
