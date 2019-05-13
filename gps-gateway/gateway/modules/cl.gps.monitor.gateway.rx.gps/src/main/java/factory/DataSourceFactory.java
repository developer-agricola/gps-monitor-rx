package com.sonda.transporte.consola.agente.firmware.infrastructure.factory;

import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.DataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.derby.DerbyDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.derby.impl.DerbyDataSourceImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.filesystem.FileSystemDataSource;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.filesystem.impl.FileSystemDataSourceImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.sqlite.impl.SqLiteDataSourceImpl;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

public class DataSourceFactory {

    private SqLiteDataSourceImpl sqLiteDataSource;
    private DerbyDataSource derbyDataSource;
    private FileSystemDataSource fileSystemDataSource;

    public static final UtilLogger log = UtilLogger.getLogger(DataSourceFactory.class);

    private static DataSourceFactory dataSourceFactory;

    public static DataSourceFactory getFactoryInstance() {
        if(dataSourceFactory == null) {
            dataSourceFactory = new DataSourceFactory();
        }
        return dataSourceFactory;
    }

    public DataSource getDataSource(Class<? extends DataSource> clazz){
        DataSource dataSource = null;
        if(clazz.equals(SqLiteDataSourceImpl.class)){
            if(sqLiteDataSource == null){
                sqLiteDataSource = new SqLiteDataSourceImpl();
            }
            return sqLiteDataSource;
        }else if(clazz.equals(DerbyDataSourceImpl.class)) {
            if(derbyDataSource == null){
                derbyDataSource = new DerbyDataSourceImpl();
            }
            return derbyDataSource;
        }else if(clazz.equals(FileSystemDataSourceImpl.class)) {
            if(fileSystemDataSource == null){
                fileSystemDataSource = new FileSystemDataSourceImpl();
            }
            return fileSystemDataSource;
        }else{
            log.error("La clase[%s] no es un DataSource", clazz.getSimpleName());
        }
        return dataSource;
    }

}
