package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.derby;

import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.DataSource;

public interface DerbyDataSource extends DataSource {

    void init();

    boolean reconnect();

    String getPoolName();

}
