package cl.gps.monitor.gateway.gps.position;

import cl.gps.monitor.gateway.gps.api.gpsd.GpsdURI;
import cl.gps.monitor.gateway.gps.api.position.NmeaPosition;
import cl.gps.monitor.gateway.gps.api.position.PositionListener;
import de.taimos.gpsd4java.api.IObjectListener;
import de.taimos.gpsd4java.backend.GPSdEndpoint;
import de.taimos.gpsd4java.backend.ResultParser;
import de.taimos.gpsd4java.types.ATTObject;
import de.taimos.gpsd4java.types.DeviceObject;
import de.taimos.gpsd4java.types.DevicesObject;
import de.taimos.gpsd4java.types.IGPSObject;
import de.taimos.gpsd4java.types.SKYObject;
import de.taimos.gpsd4java.types.TPVObject;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import cl.gps.monitor.gateway.gps.position.NMEAParser.Code;
import cl.gps.monitor.gateway.gps.position.NMEAParser.ParseException;

public class GpsDevice {

    private final GpsdURI uri;
    private Listener listener;
    private String lastSentence;
    private final GpsdCommunicate gpsdThread;

    private final NMEAParser nmeaParser = new NMEAParser();

    private static final int TERMINATION_TIMEOUT_MS = 3000;
    private static final int SEND_WATCH_COMMAND_MS  = 1000;

    private static final Logger logger = LoggerFactory.getLogger(GpsDevice.class);

    public GpsDevice(final GpsdConnectionFactory connFactory, final GpsdURI gpsURI, final Listener listener) throws PositionException {
        this.uri = gpsURI;
        this.listener = listener;
        this.gpsdThread = new GpsdCommunicate(connFactory, gpsURI);
    }

    public GpsdURI getGpsURI() {
        return this.uri;
    }


    public synchronized NmeaPosition getNmeaPosition() {
        return this.nmeaParser.getNmeaPosition();
    }

    public synchronized boolean isValidPosition() {
        return this.nmeaParser.isValidPosition();
    }

    public synchronized String getDateNmea() {
        return this.nmeaParser.getDateNmea();
    }

    public synchronized String getTimeNmea() {
         return this.nmeaParser.getTimeNmea();
    }

    public void disconnect() {
        this.listener = null;
        this.gpsdThread.disconnect();
    }

    public String getLastSentence() {
        return this.lastSentence;
    }

    public boolean isConnected() {
        return this.gpsdThread.isAlive();
    }

   private final class GpsdCommunicate extends Thread implements Listener {

        private GPSdEndpoint conn;

        private boolean run = true;

        public GpsdCommunicate(final GpsdConnectionFactory connFactory, final GpsdURI gpsURI) throws PositionException {
            try {
                this.conn = connFactory.createConnection(gpsURI.getHost(), gpsURI.getPort(), new ResultParser());
            } catch (Exception e) {
                throw new PositionException("Failed to create connection with gpsd tcp endpoint ", e);
            }
            //
            this.start();
        }

        @Override
        public void run() {
            conn.addListener(this);

            while (true) {
                if (!doPollWork()) {
                    closeConnection();
                    return;
                }
            }
        }

        public void disconnect() {
            run = false;
            this.interrupt();
            try {
                this.join(TERMINATION_TIMEOUT_MS);
            } catch (InterruptedException e) {
                logger.warn("Interrupted while waiting for thread termination");
                Thread.currentThread().interrupt();
            }

            if (this.isAlive()) {
                logger.warn("GPS receiver thread did not terminate after {} milliseconds", TERMINATION_TIMEOUT_MS);
                closeConnection();
            }
        }

        private void closeConnection() {
            logger.debug("closing gpsd tcp connection...");

            try {
                if (this.conn != null) {
                    this.conn.stop();
                }
            } catch (Exception e) {
                logger.warn("Failed to close gpsd tcp connection", e);
            }
            logger.debug("closing gpsd tcp connection...done");
        }

        private boolean doPollWork() {
            try {
                conn.watch(true, true);

                Thread.sleep(SEND_WATCH_COMMAND_MS);
            } catch (IOException | InterruptedException e) {
                logger.error("Error to send watch command to gpsd tcp connection", e);
                closeConnection();
            }
            return true;
        }

       @Override
       public void handleTPV(TPVObject tpvObject){
            handleNmeaSentence(tpvObject);
       }

        private void handleNmeaSentence(final IGPSObject sentence) {

           if (sentence != null) {
                logger.debug("Empty NMEA sentence detected");
                return;
            }

            if (GpsDevice.this.listener != null) {
                GpsDevice.this.listener.newNmeaSentence(sentence);
            }

            final boolean isLastPositionValid = nmeaParser.isValidPosition();

            try {
                final boolean isValid;

                synchronized (this) {
                    isValid = nmeaParser.parseSentence(sentence);
                    //TODO to implement
                    //GpsDevice.this.lastSentence = sentence;
                }

                if (isValid != isLastPositionValid && GpsDevice.this.listener != null) {
                    listener.onLockStatusChanged(isValid);
                    logger.info("{}", GpsDevice.this);
                }

            } catch (ParseException e) {
                final Code code = e.getCode();
                if (code == Code.BAD_CHECKSUM) {
                    logger.warn("NMEA checksum not valid");
                } else if (code == Code.INVALID) {
                    logger.warn("Invalid NMEA sentence: {}", sentence);
                } else {
                    logger.warn("Unrecognized NMEA sentence: {}", sentence);
                }
            } catch (Exception e) {
                logger.warn("Unexpected exception parsing NMEA sentence", e);
            }
        }
    }

    interface Listener extends PositionListener, IObjectListener {

        default void newNmeaSentence(IGPSObject sentence){}

        default void onLockStatusChanged(final boolean hasLock){}

        default void handleTPV(TPVObject tpvObject){}

        default void handleSKY(SKYObject skyObject){}

        default void handleATT(ATTObject attObject){}

        default void handleSUBFRAME(SUBFRAMEObject subframeObject){}

        default void handleDevices(DevicesObject devicesObject){}

        default void handleDevice(DeviceObject deviceObject){}
    }
}
