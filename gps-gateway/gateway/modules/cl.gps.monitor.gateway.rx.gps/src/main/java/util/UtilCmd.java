package com.sonda.transporte.consola.agente.firmware.infrastructure.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by daniel.carvajal on 06-07-2018.
 */
public class UtilCmd {

    public static final int PROCESS_OK = 0;
    public static final int PROCESS_NOK = 1;

    public static final UtilLogger log = UtilLogger.getLogger(UtilCmd.class);

    private UtilCmd(){}

    public static int exec(File file, String... parameters) throws IOException, InterruptedException {
        int exitCode;
        if (!file.canExecute()) {
            boolean isExec = file.setExecutable(true);
            if(isExec) {
                log.warning("El archivo [%s] no tiene permisos de ejecucion.. se cambian los permisos...", file.getName());
            }else{
                throw new IOException(
                        String.format("Se ha provocado un error al cambiar los permisos de ejecucion del archivo [%s]", file.getName()));
            }
        }
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(file.getParentFile());

        log.info("Directorio de trabajo [%s] para el script[%s]", processBuilder.directory().getAbsolutePath(), file.getName());
        log.info("Init script[%s]", file.getName());

        processBuilder.command("sh", file.getName(), parameters[0], parameters[1]);
        Process process = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        bufferedReader.lines().forEach(log::info);

        exitCode = process.waitFor();
        switch (process.exitValue()) {
            case PROCESS_OK:
                log.info("End script [%s] exitCode[%s]", file.getName(), process.exitValue());
                break;
            case PROCESS_NOK:
                log.info("End script [%s] exitCode[%s]", file.getName(), process.exitValue());
                break;
            default:
                break;
        }
        return exitCode;
    }

}
