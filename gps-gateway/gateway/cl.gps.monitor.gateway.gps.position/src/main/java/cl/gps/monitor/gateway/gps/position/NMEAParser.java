package cl.gps.monitor.gateway.gps.position;

import cl.gps.monitor.gateway.gps.api.position.NmeaPosition;import de.taimos.gpsd4java.types.ATTObject;
import de.taimos.gpsd4java.types.DeviceObject;
import de.taimos.gpsd4java.types.DevicesObject;
import de.taimos.gpsd4java.types.IGPSObject;
import de.taimos.gpsd4java.types.SKYObject;
import de.taimos.gpsd4java.types.TPVObject;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;


/**
 * Implements NMEA sentences parser functions.
 *
 */
public class NMEAParser {

    private int fixQuality;
    private String timeNmea;
    private double dateTimeNmea;
    private double longNmea;
    private double latNmea;
    private double speedNmea;
    private double altNmea;
    private double trackNmea;
    private double dopNmea;
    private double pdopNmea;
    private double hdopNmea;
    private double vdopNmea;
    private int fix3DNmea;
    private int nrSatellites;
    private boolean validPosition;
    private char validFix = 0;
    private char latitudeHemisphere = 0;
    private char longitudeHemisphere = 0;
    private String dateNmea;

    /**
     * Fill the fields of GPS position depending of the type of the sentence
     *
     * @param sentence
     *            most recent sentence String from GPS deamon
     */
    public boolean parseSentence(IGPSObject sentence) throws ParseException {

        if (sentence instanceof ATTObject) {
            //TODO to define
        } else if (sentence instanceof DeviceObject) {
            //TODO to define
        } else if (sentence instanceof DevicesObject) {
            //TODO to define
        } else if (sentence instanceof SKYObject) {
            //TODO to define
        } else if (sentence instanceof SUBFRAMEObject) {
            //TODO to define
        } else if (sentence instanceof TPVObject) {
            parseTPVSentence((TPVObject) sentence);
        } else {
            throw new ParseException(Code.UNRECOGNIZED);
        }

        return this.validPosition;
    }

    private void parseTPVSentence(final TPVObject sentence) {

        //TODO see the no exist fields
        this.dateTimeNmea = sentence.getTimestamp();
        this.latNmea = sentence.getLatitude();
        this.longNmea = sentence.getLongitude();
        this.speedNmea = sentence.getSpeed();
        this.altNmea = sentence.getAltitude();
    }


    private void checkPosition() {
        //TODO to implement
    }

    public int getFixQuality() {
        return this.fixQuality;
    }

    public String getTimeNmea() {
        return this.timeNmea;
    }

    public String getDateNmea() {
        return this.dateNmea;
    }

    public double getLongNmea() {
        return this.longNmea;
    }

    public double getLatNmea() {
        return this.latNmea;
    }

    public double getSpeedNmea() {
        return this.speedNmea;
    }

    public double getAltNmea() {
        return this.altNmea;
    }

    public double getTrackNmea() {
        return this.trackNmea;
    }

    public double getDOPNmea() {
        return this.dopNmea;
    }

    public double getPDOPNmea() {
        return this.pdopNmea;
    }

    public double getHDOPNmea() {
        return this.hdopNmea;
    }

    public double getVDOPNmea() {
        return this.vdopNmea;
    }

    public int getFix3DNmea() {
        return this.fix3DNmea;
    }

    public int getNrSatellites() {
        return this.nrSatellites;
    }



    public NmeaPosition getNmeaPosition() {
        return new NmeaPosition(getLatNmea(), getLongNmea(), getAltNmea(), getSpeedNmea(), getTrackNmea(),
                getFixQuality(), getNrSatellites(), getDOPNmea(), getPDOPNmea(), getHDOPNmea(), getVDOPNmea(),
                getFix3DNmea(), getValidFix(), getLatitudeHemisphere(), getLongitudeHemisphere(), getDateTimeNmea());
    }

    public boolean isValidPosition() {
        return this.validPosition;
    }

    public char getValidFix() {
        return this.validFix;
    }

    public char getLatitudeHemisphere() {
        return this.latitudeHemisphere;
    }

    public char getLongitudeHemisphere() {
        return this.longitudeHemisphere;
    }

    public double getDateTimeNmea() {
        return dateTimeNmea;
    }

    public enum Code {
        INVALID,
        BAD_CHECKSUM,
        UNRECOGNIZED
    }

    public class ParseException extends Exception {

        private static final long serialVersionUID = -1441433820817330483L;
        private final Code code;

        public ParseException(final Code code) {
            this.code = code;
        }

        public Code getCode() {
            return this.code;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" longitude=");
        sb.append(this.getLongNmea());
        sb.append("\n latitude=");
        sb.append(this.getLatNmea());
        sb.append("\n altitude=");
        sb.append(this.getAltNmea());
        sb.append("\n speed=");
        sb.append(this.getSpeedNmea());
        sb.append("\n date=");
        sb.append(this.getDateNmea());
        sb.append("   time=");
        sb.append(this.getTimeNmea());
        sb.append("\n DOP=");
        sb.append(this.getDOPNmea());
        sb.append("\n 3Dfix=");
        sb.append(this.getFix3DNmea());
        sb.append("\n fixQuality=");
        sb.append(this.getFixQuality());
        return sb.toString();
    }
}
