package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence;

public interface DataSource {

    void configure();

    void open();

    void close();

    boolean test();
}
