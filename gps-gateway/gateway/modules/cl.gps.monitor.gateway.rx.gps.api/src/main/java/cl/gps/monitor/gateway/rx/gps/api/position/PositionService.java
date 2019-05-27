package cl.gps.monitor.gateway.rx.gps.api.position;

/**
 * This interface provides methods getting a geographic position.
 * The OSGI Position class represents a geographic location, based on the WGS84 System
 * (World Geodetic System 1984).
 * <p>
 * Position(Measurement lat, Measurement lon, Measurement alt, Measurement speed, Measurement track)
 * <p>
 * The interface also return a NmeaPosition, subclass of Position
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @see cl.gps.monitor.gateway.rx.gps.api.position NmeaPosition
 * @see org.osgi.util.position.Position Position
 * @see org.osgi.util.measurement.Measurement Measurement
 */
public interface PositionService {

    /**
     * Returns the current geographic position.<br>
     * The org.osgi.util.measurement.Measurement class is used to represent the values that make up a position:
     * <ul>
     * <li>getLongitude() : returns the longitude of this position in radians.
     * <li>getLatitude() : returns the latitude of this position in radians.
     * <li>getSpeed() : returns the ground speed of this position in meters per second.
     * <li>getTrack() : Returns the track of this position in radians as a compass heading. The track is the
     * extrapolation of
     * previous previously measured positions to a future position.
     * </ul>
     *
     * @see org.osgi.util.position.Position Position
     */
    NmeaPosition getPosition();

    /**
     * Returns the current NMEA geographic position.
     * <ul>
     * <li>getLongitude() : returns the longitude of this position in degrees.
     * <li>getLatitude() : returns the latitude of this position in degrees.
     * <li>getSpeedKmh() : returns the ground speed of this position in km/h.
     * <li>getSpeedMph() : returns the ground speed of this position in mph.
     * <li>getTrack() : Returns the track of this position in degrees as a compass heading.
     * </ul>
     *
     * @see cl.gps.monitor.gateway.rx.gps.api.position NmeaPosition
     */
    NmeaPosition getNmeaPosition();

    /**
     * Returns the current NMEA time from GGA or ZDA sentence
     */
    String getNmeaTime();

    /**
     * Returns the current NMEA date from ZDA sentence
     */
    String getNmeaDate();

    /**
     * Returns true if a valid geographic position has been received.
     */
    boolean isLocked();

    /**
     * Returns the last sentence received from the gps.
     */
    String getLastSentence();

    /**
     * Registers position listener
     *
     * @param listenerId       - listener ID as {@link String}
     * @param positionListener - position listener as {@link PositionListener}
     */
    void registerListener(String listenerId, PositionListener positionListener);

    /**
     * Unregisters position listener
     *
     * @param listenerId - listener ID as {@link String}
     */
    void unregisterListener(String listenerId);
}
