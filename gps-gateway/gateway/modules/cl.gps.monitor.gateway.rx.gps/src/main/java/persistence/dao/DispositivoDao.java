package com.sonda.transporte.consola.agente.firmware.infrastructure.persistence.dao;

import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.DispositivoDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.FirmwareDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.PosicionDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.exceptions.DataAccessException;

import java.io.File;
import java.util.List;

/**
 * Created by daniel.carvajal on 20-06-2018.
 */
public interface DispositivoDao extends Dao {

    DispositivoDTO findDispositivoPorDefecto() throws DataAccessException;

    DispositivoDTO saveDispositivo(DispositivoDTO dispositivoDTO) throws DataAccessException;

    FirmwareDTO saveFirmware(FirmwareDTO firmwareDTO) throws  DataAccessException;

    FirmwareDTO findFirmwareById(long id) throws DataAccessException;

    boolean updateFirware(FirmwareDTO firmwareDTO) throws DataAccessException;

    boolean updateFirware(long firmwareId, String estadoId, boolean notificado) throws DataAccessException;

    FirmwareDTO savePaqueteFirmware(FirmwareDTO firmwareDTO) throws DataAccessException;

    List<FirmwareDTO> findAllFirmwarePorAplicar() throws  DataAccessException;

    File findFirmwareScript(FirmwareDTO firmwareDTO) throws DataAccessException;

    File findPaqueteFirmware(FirmwareDTO firmwareDTO) throws DataAccessException;

    boolean openPaqueteFirmware(FirmwareDTO firmwareDTO) throws DataAccessException;

    Double getVelocidadPromedio() throws DataAccessException;

    PosicionDTO getLastPosicion() throws  DataAccessException;

    boolean checkPaquetFirmware(FirmwareDTO firmwareDto) throws DataAccessException;

}
