package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.application.repository.impl.DispositivoRepositoryImpl;
import com.sonda.transporte.consola.agente.firmware.application.service.Service;
import com.sonda.transporte.consola.agente.firmware.application.service.impl.DispositivoServiceImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

public class ServiceFactory {

    private DispositivoServiceImpl dispositivoService;

    public static final UtilLogger log = UtilLogger.getLogger(ServiceFactory.class);

    private static ServiceFactory serviceFactory;

    public static ServiceFactory getFactoryInstance() {
        if(serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public Service getService(Class<? extends Service> clazz){
        Service service = null;
        if(clazz.equals(DispositivoServiceImpl.class)) {
            if(dispositivoService == null){
                dispositivoService = new DispositivoServiceImpl();
            }
            DispositivoRepositoryImpl dispositivoRepository =
                    (DispositivoRepositoryImpl) RepositoryFactory.getFactoryInstance().getDataRepository(DispositivoRepositoryImpl.class);
            dispositivoService.setDispositivoRepository(dispositivoRepository);

            return dispositivoService;
        }else{
            log.error("La clase [%s] no es un servicio", clazz.getSimpleName());
        }
        return service;
    }

}
