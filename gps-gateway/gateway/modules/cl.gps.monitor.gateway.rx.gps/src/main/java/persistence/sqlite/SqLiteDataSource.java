package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.sqlite;

import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.DataSource;

public interface SqLiteDataSource extends DataSource {

    void init();
    
    String getPoolName();

}
