package cl.gps.monitor.gateway.gps.position;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GpsDevice {

    private final GpsdCommunicate gpsdThread;

    public GpsDevice() {
        this.gpsdThread = new GpsdCommunicate();
    }

    private final class GpsdCommunicate extends Thread {

        private boolean run = true;

        public GpsdCommunicate() {
            this.start();
        }

        @Override
        public void run() {
            while (run) {
                if (!doPollWork()) {
                    closeConnection();
                    return;
                }

                try {
                    log.info("Get position : Hello World!!!");
                    //
                    Thread.sleep(6000);
                } catch (InterruptedException e){
                    log.error("Error while get position");
                }
            }
        }

        public void disconnect() {
            run = false;
            this.interrupt();
            try {
                this.join(1000);
            } catch (InterruptedException e) {
                log.warn("Interrupted while waiting for thread termination");
                Thread.currentThread().interrupt();
            }

            if (this.isAlive()) {
                log.warn("GPS receiver thread did not terminate after {} milliseconds", 1000);
                closeConnection();
            }
        }

        private void closeConnection() {
            log.debug("closing gpsd tcp connection...");
            disconnect();
        }

        private boolean doPollWork() {
            return true;
        }
    }
}
