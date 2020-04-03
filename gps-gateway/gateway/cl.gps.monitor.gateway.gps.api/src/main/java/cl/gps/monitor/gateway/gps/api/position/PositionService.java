package cl.gps.monitor.gateway.gps.api.position;

public interface PositionService {

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
     * @see NmeaPosition
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
