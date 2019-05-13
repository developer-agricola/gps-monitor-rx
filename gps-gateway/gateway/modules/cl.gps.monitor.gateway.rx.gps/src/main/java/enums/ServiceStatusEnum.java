package com.sonda.transporte.consola.agente.firmware.infrastructure.enums;

public enum ServiceStatusEnum {
        SERVICE_UP("SERVICE_UP", "Servicio Up"),
        SERVICE_WAIT("SERVICE_WAIT", "Servicio Wait"),
        SERVICE_DOWN("SERVICE_DOWN(", "Servicio Down'");

        private final String statusId;
        private final String statusDescription;

        private ServiceStatusEnum(String statusId, String statusDescription) {
            this.statusId = statusId;
            this.statusDescription = statusDescription;
        }

        public String getStatusId() {
            return statusId;
        }

        public String getStatusDescription() {
            return statusDescription;
        }
}
