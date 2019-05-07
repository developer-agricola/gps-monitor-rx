package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.dao.Dao;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.dao.DispositivoDao;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.dao.impl.DispositivoDaoImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

public class DaoFactory {

    private static DaoFactory daoFactory;

    public static final UtilLogger log = UtilLogger.getLogger(DaoFactory.class);

    private DispositivoDaoImpl dispositivoDao;

    public static DaoFactory getFactoryInstance() {
        if(daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public Dao getDao(Class<? extends DispositivoDao> clazz){
        Dao dao = null;
        if(clazz.equals(DispositivoDaoImpl.class)){
            if(dispositivoDao == null){
                dispositivoDao = new DispositivoDaoImpl();
            }
            return dispositivoDao;
        }else{
            log.error("La clase[%s] no es un Dao", clazz.getSimpleName());
        }
        return dao;
    }

}
