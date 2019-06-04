package cl.gps.monitor.gateway.rx.gps.position;

import static java.util.Objects.requireNonNull;

import java.io.BufferedInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GPS Utility class, not intended to be used by the end user of Kura.<br>
 * Assuming the device talks NMEA over a serial port configured thru PositionService
 *
 */
public class GpsDevice {

    private static final Logger logger = LoggerFactory.getLogger(GpsDevice.class);

    private static final int SERIAL_TIMEOUT_MS = 2000;
    private static final int TERMINATION_TIMEOUT_MS = SERIAL_TIMEOUT_MS + 1000;

    private final GpsdCommunicate gpsdThread;
    private String lastSentence;

    private Listener listener;

    private final NMEAParser nmeaParser = new NMEAParser();

    public GpsDevice(final GpsdConnectionFactory connFactory, final Listener listener)
            throws PositionException {
        this.listener = listener;
        this.gpsdThread = new GpsdCommunicate(connFactory, commURI);
    }

    public CommURI getCommURI() {
        return this.uri;
    }

    public synchronized NmeaPosition getPosition() {
        return this.nmeaParser.getPosition();
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

    private final class GpsdCommunicate extends Thread {
        //Todo create a common interface
        private GpsdConnectionFactory conn = null;
        private boolean run = true;

        public GpsdCommunicate(final GpsdConnectionFactory connFactory, final CommURI commURI) throws PositionException {
            try {
                this.conn = connFactory;
            } catch (Exception e) {
                closeSerialPort();
                throw new PositionException("Failed to open serial port", e);
            }

            this.start();
        }

        @Override
        public void run() {
            while (true) {
                if (!doPollWork()) {
                    closeSerialPort();
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
                closeSerialPort();
            }
        }

        private void closeSerialPort() {
            logger.debug("closing serial port...");

            try {
                if (this.in != null) {
                    this.in.close();
                }
            } catch (Exception e) {
                logger.warn("Failed to close serial port InputStream", e);
            }

            try {
                if (this.conn != null) {
                    this.conn.close();
                }
            } catch (Exception e) {
                logger.warn("Failed to close serial port connection", e);
            }

            logger.debug("closing serial port...done");
        }

        private boolean doPollWork() {
            final StringBuilder readBuffer = new StringBuilder();
            int c = -1;
            while (c != 10) {
                if (!run) {
                    logger.debug("Shutdown requested, exiting");
                    return false;
                }
                try {
                    c = this.in.read();
                } catch (Exception e) {
                    logger.error("Exception in gps read - {}", e);
                    return false;
                }
                if (c == -1) {
                    logger.debug("Read timed out");
                } else if (c != 13) {
                    readBuffer.append((char) c);
                }
            }
            if (readBuffer.length() > 0) {
                final String sentence = readBuffer.toString();
                logger.debug("GPS RAW: {}", sentence);
                handleNmeaSentence(sentence);
            }
            return true;
        }

        private void handleNmeaSentence(final String sentence) {

            if (sentence.isEmpty()) {
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
                    GpsDevice.this.lastSentence = sentence;
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

        private CommURI enableTimeouts(final CommURI original) {
            return new CommURI.Builder(original.getPort()).withBaudRate(original.getBaudRate())
                    .withDataBits(original.getDataBits()).withStopBits(original.getStopBits())
                    .withFlowControl(original.getFlowControl()).withParity(original.getParity())
                    .withOpenTimeout(SERIAL_TIMEOUT_MS).withReceiveTimeout(SERIAL_TIMEOUT_MS).build();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" longitude=");
        sb.append(this.nmeaParser.getLongNmea());
        sb.append("\n latitude=");
        sb.append(this.nmeaParser.getLatNmea());
        sb.append("\n altitude=");
        sb.append(this.nmeaParser.getAltNmea());
        sb.append("\n speed=");
        sb.append(this.nmeaParser.getSpeedNmea());
        sb.append("\n date=");
        sb.append(this.nmeaParser.getDateNmea());
        sb.append("   time=");
        sb.append(this.nmeaParser.getTimeNmea());
        sb.append("\n DOP=");
        sb.append(this.nmeaParser.getDOPNmea());
        sb.append("\n 3Dfix=");
        sb.append(this.nmeaParser.getFix3DNmea());
        sb.append("\n fixQuality=");
        sb.append(this.nmeaParser.getFixQuality());
        return sb.toString();
    }

    interface Listener extends PositionListener {

        public void onLockStatusChanged(final boolean hasLock);
    }
}
