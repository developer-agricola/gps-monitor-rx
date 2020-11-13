package cl.gps.monitor.gateway.gps.configuration.util;

import java.io.InterruptedIOException;

public class UtilLogger {

    private static boolean isDefault;

    private UtilLogger() {
    }

    //TODO write doc and comment about de java logging default mode
    public static void configure(String configFileName, boolean isInternal) {
        try {
            if(!isInternal){
                // set custom path
                System.setProperty("java.util.logging.config.file", configFileName);
            } else{
                isDefault = true;
                //
                System.out.println("Ignoring configuration logging file [" + configFileName + "]... ");
                System.out.println("Preserve to default configuration java logging");
            }


        } catch (Exception e) {
            if (e instanceof InterruptedIOException || e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Could not read configuration logging file  [" + configFileName + "].");
            System.out.println("Ignoring configuration logging file [" + configFileName + "]");
            e.printStackTrace();
        }
    }

    public static boolean IsDefaultConfig(){
        return isDefault;
    }
}
