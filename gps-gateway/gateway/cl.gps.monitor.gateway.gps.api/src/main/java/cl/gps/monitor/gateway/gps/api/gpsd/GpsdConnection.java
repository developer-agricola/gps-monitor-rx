package cl.gps.monitor.gateway.gps.api.gpsd;

import cl.gps.monitor.gateway.gps.api.GpsMonitorException;

import java.io.Closeable;
import java.io.IOException;

public interface GpsdConnection extends Closeable {

    /**
     * Returns the URI for this connection.
     *
     * @return this connection URI
     */
    public GpsdURI getURI();

    /**
     * Sends and array of bytes to a CommConnection
     *
     * @param message
     *              the array of bytes to send to the CommConnection
     * @throws GpsMonitorException
     * @throws IOException
     */
    public void sendMessage(byte[] message) throws GpsMonitorException, IOException;

    /**
     * Sends and array of bytes to a CommConnection and returns an array of bytes
     * that represents the 'response' to the command. If the timeout is exceeded
     * before any bytes are read on the InputStream null is returned. This is
     * meant to be used in common command/response type situations when communicating
     * with serial devices
     *
     * @param command
     *            the array of bytes to send to the CommConnection
     * @param timeout
     *            the maximum length of time to wait before returning a null
     *            response in the event no response is ever returned.
     * @return an array of bytes representing the response
     * @throws GpsMonitorException
     * @throws IOException
     */
    public byte[] sendCommand(byte[] command, int timeout) throws GpsMonitorException, IOException;

    public byte[] sendCommand(byte[] command, int timeout, int demark) throws GpsMonitorException, IOException;

    /**
     * Reads all bytes that are waiting in the serial port buffer and returns them in
     * an array. This can be used to read unsolicited messages from an attached
     * serial device.
     *
     * @return the array of bytes buffered on the InputStream if any
     * @throws GpsMonitorException
     * @throws IOException
     */
    public byte[] flushSerialBuffer() throws GpsMonitorException, IOException;

    @Override
    public void close() throws IOException;
}
