package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.application.repository.Repository;
import com.sonda.transporte.consola.agente.firmware.application.repository.impl.DispositivoRepositoryImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

public class RepositoryFactory {

    private static RepositoryFactory repositoryFactory;

    private DispositivoRepositoryImpl dispositivoRepository;

    public static final UtilLogger log = UtilLogger.getLogger(RepositoryFactory.class);

    public static RepositoryFactory getFactoryInstance() {
        if(repositoryFactory == null) {
            repositoryFactory = new RepositoryFactory();
        }
        return repositoryFactory;
    }

    public Repository getDataRepository(Class<? extends Repository> clazz){
        Repository repository = null;
        if(clazz.equals(DispositivoRepositoryImpl.class)){
            if(dispositivoRepository == null) {
                dispositivoRepository = new DispositivoRepositoryImpl();
            }
            //
            return dispositivoRepository;
        }else{
            log.error("La clase [%s] no es Repositorio", clazz.getSimpleName());
        }
        return repository;
    }

}
