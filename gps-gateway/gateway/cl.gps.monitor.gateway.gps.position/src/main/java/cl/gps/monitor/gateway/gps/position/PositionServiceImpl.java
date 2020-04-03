package cl.gps.monitor.gateway.gps.position;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cl.gps.monitor.gateway.gps.api.gpsd.GpsdURI;
import cl.gps.monitor.gateway.gps.api.position.NmeaPosition;
import cl.gps.monitor.gateway.gps.api.position.PositionListener;
import cl.gps.monitor.gateway.gps.api.position.PositionLockedEvent;
import cl.gps.monitor.gateway.gps.api.position.PositionLostEvent;
import cl.gps.monitor.gateway.gps.api.position.PositionService;
import de.taimos.gpsd4java.types.IGPSObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PositionServiceImpl implements PositionService, GpsDevice.Listener {

    private GpsdConnectionFactory connectionFactory;
    //private EventAdmin eventAdmin;

    //private GpsDeviceTracker gpsDeviceTracker;
    //private ModemGpsStatusTracker modemGpsStatusTracker;

    private final Map<String, PositionListener> positionListeners = new ConcurrentHashMap<>();

    private PositionServiceOptions options;
    private GpsDevice gpsDevice;

    private boolean hasLock;
    private NmeaPosition staticNmeaPosition;

    private static final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);


    public void setConnectionFactory(final GpsdConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void unsetConnectionFactory(final GpsdConnectionFactory connectionFactory) {
        this.connectionFactory = null;
    }

    /*public void setEventAdmin(final EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }*/

    /*public void unsetEventAdmin(final EventAdmin eventAdmin) {
        this.eventAdmin = null;
    }*/

    /*public void setGpsDeviceTracker(final GpsDeviceTracker tracker) {
        this.gpsDeviceTracker = tracker;
        tracker.setListener(this);
    }*/

    /*public void unsetGpsDeviceTracker(final GpsDeviceTracker tracker) {
        tracker.setListener(null);
        this.gpsDeviceTracker = null;
    }*/

    /*public void setModemGpsStatusTracker(final ModemGpsStatusTracker modemGpsDeviceTracker) {
        this.modemGpsStatusTracker = modemGpsDeviceTracker;
        modemGpsDeviceTracker.setListener(this);
    }*/

    /*public void unsetModemGpsStatusTracker(final ModemGpsStatusTracker modemGpsDeviceTracker) {
        modemGpsDeviceTracker.setListener(null);
        this.modemGpsStatusTracker = null;
    }*/

    protected void activate(final Map<String, Object> properties) {
        logger.debug("Activating PositionServiceImpl"); //TODO describe the service

        final PositionServiceOptions newOptions = new PositionServiceOptions(properties);

        /*if (this.options.isStatic()) {
            setStaticPosition(this.options.getStaticLatitude(), this.options.getStaticLongitude(),
                    this.options.getStaticAltitude());
            setLock(true);
        } else {*/
        this.gpsDevice = openGpsDevice(options.getGpsDeviceUri());
        //}

        setStaticPosition(0, 0, 0);

        logger.info("Activating PositionServiceImpl... Done.");
    }

    @Override
    public NmeaPosition getNmeaPosition() {
        if (this.gpsDevice != null) {
            return this.gpsDevice.getNmeaPosition(); //Get actual position
        } else {
            return this.staticNmeaPosition; // Get default position
        }
    }

    @Override
    public boolean isLocked() {
        /*if (!this.options.isEnabled()) {
            return false;
        }
        if (this.options.isStatic()) {
            return true;
        }
        return this.gpsDevice != null && this.gpsDevice.isValidPosition();*/
        return true;
    }

    @Override
    public String getNmeaTime() {
        if (this.gpsDevice != null) {
            return this.gpsDevice.getTimeNmea();
        } else {
            return null;
        }
    }

    @Override
    public String getNmeaDate() {
        if (this.gpsDevice != null) {
            return this.gpsDevice.getDateNmea();
        } else {
            return null;
        }
    }

    @Override
    public void registerListener(String listenerId, PositionListener positionListener) {
        this.positionListeners.put(listenerId, positionListener);
    }

    @Override
    public void unregisterListener(String listenerId) {
        this.positionListeners.remove(listenerId);
    }

    @Override
    public String getLastSentence() {
        if (this.gpsDevice != null) {
            return this.gpsDevice.getLastSentence();
        } else {
            return null;
        }
    }

    protected GpsDevice getGpsDevice() {
        return this.gpsDevice;
    }

    protected PositionServiceOptions getPositionServiceOptions() {
        return this.options;
    }

    private void stop() {
        //this.gpsDeviceTracker.reset();

        if (this.gpsDevice != null) {
            this.gpsDevice.disconnect();
            this.gpsDevice = null;
        }

        setStaticPosition(0, 0, 0);
        setLock(false);
    }

    private void setLock(boolean hasLock) {
        /*if (hasLock && !this.hasLock) {
            logger.debug("posting PositionLockedEvent");
            this.eventAdmin.postEvent(new PositionLockedEvent(Collections.emptyMap()));
        } else if (!hasLock && this.hasLock) {
            logger.debug("posting PositionLostEvent");
            this.eventAdmin.postEvent(new PositionLostEvent(Collections.emptyMap()));
        }
        this.hasLock = hasLock;*/
    }


    private void setStaticPosition(double latitudeDeg, double longitudeDeg, double altitudeNmea) {
        this.staticNmeaPosition = new NmeaPosition(latitudeDeg, longitudeDeg, altitudeNmea, 0, 0, 0, 0, 0, 0, 0, 0, 0, (char) 0, (char) 0, (char) 0, 0);
    }

    private GpsDevice openGpsDevice(GpsdURI uri) {
        GpsDevice gpsDevice = null;
        try {
            //uri = this.gpsDeviceTracker.track(uri); //TODO realizar seguimiento si se para el gpsd

            gpsDevice = new GpsDevice(this.connectionFactory, uri, this);
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to open GPS device: {}", uri, e);
            }
        }
        return gpsDevice;
    }

    @Override
    public void newNmeaSentence(IGPSObject sentence) {
        for (final PositionListener listener : this.positionListeners.values()) {
            ((GpsDevice.Listener) listener).newNmeaSentence(sentence);
        }
    }

    @Override
    public synchronized void onLockStatusChanged(final boolean hasLock) {
        setLock(hasLock);
    }

    /*@Override
    public void onGpsDeviceAvailabilityChanged() {
        if (!this.options.isEnabled() || this.options.isStatic()) {
            return;
        }

        updateInternal();
    }*/
}