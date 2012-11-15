/* This source code is released under the new BSD license, a copy of the
 * license is in the distribution directory. */

package mazerob.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTCommFactory;
import lejos.robotics.RangeReadings;
import mazerob.conn.CommandCode;
import mazerob.conn.RemotelyControllable;

/**
 * Remotely drives an instance of {@link mazerob.nxt.Robot} through a
 * Bluetooth connection
 *
 * @author Pedro I. López
 *
 */
public class Robot implements RemotelyControllable {
    /** Bluetooth nxt connector object */
    private NXTConnector conn;

    /** Data output stream object */
    private DataOutputStream dos;

    /** Data input stream object */
    private DataInputStream dis;

    /**
     * Connects to {@link mazerob.nxt.RobotApp} through Bluetooth and
     * sends {@link mazerob.nxt.Robot} instance configuration
     *
     * @param nxtName The name of the NXT
     * @param nxtAddr The bluetooth address of the NXT
     * @param logListener Log listener attached to the Bluetooth
     * connection
     * @param wheelDiameter Diameter of the tires in mm
     * @param trackWidth Distance between center of right tire and
     * center of left tire in mm
     * @param reverse If true, the NXT robot moves forward when the
     * motors are running backward
     * @param rotationSpeed Rotation speed of the vehicle, in degrees
     * per second
     * @param translationMagnitude Magnitude of translation in mm
     * @param rotationMagnitude Magnitude of rotation in degrees
     *
     */
    public Robot(   String nxtName,
                    String nxtAddr,
                    NXTCommLogListener logListener,
                    double wheelDiameter,
                    double trackWidth,
                    boolean reverse,
                    double rotationSpeed,
                    double translationMagnitude,
                    double rotationMagnitude) {

        conn = new NXTConnector();
        conn.addLogListener(logListener);

        boolean connected = conn.connectTo(nxtName, nxtAddr,
            NXTCommFactory.BLUETOOTH);

        if (!connected) {
            System.err.println("Failed to connect to " + nxtName +
                " (" + nxtAddr + ")");
            System.exit(1);
        }

        dos = new DataOutputStream(conn.getOutputStream());
        dis = new DataInputStream(conn.getInputStream());

        try {
            dos.writeDouble(wheelDiameter);
            dos.flush();
            dos.writeDouble(trackWidth);
            dos.flush();
            dos.writeBoolean(reverse);
            dos.flush();
            dos.writeDouble(rotationSpeed);
            dos.flush();
            dos.writeDouble(translationMagnitude);
            dos.flush();
            dos.writeDouble(rotationMagnitude);
            dos.flush();
        }
        catch(IOException e) {
            System.out.println(e);
            System.exit(1);
        }

    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#translateForward}
     *
     * @throws IOException
     *
     */
    public void translateForward() throws IOException {
        dos.writeInt(CommandCode.TRANSLATE_FORWARD.ordinal());
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#translateBackward}
     *
     * @throws IOException
     *
     */
    public void translateBackward() throws IOException {
        dos.writeInt(CommandCode.TRANSLATE_BACKWARD.ordinal());
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#rotateRight}
     *
     * @throws IOException
     *
     */
    public void rotateRight() throws IOException {
        dos.writeInt(CommandCode.ROTATE_RIGHT.ordinal());
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#rotateLeft}
     *
     * @throws IOException
     *
     */
    public void rotateLeft() throws IOException {
        dos.writeInt(CommandCode.ROTATE_LEFT.ordinal());
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#scan}
     *
     * <p>Prints the range readings to stdout</p>
     *
     * @return A set of {@link lejos.robotics.RangeReadings} taken the
     * angles specified.
     *
     * @throws IOException
     *
     */
    public RangeReadings scan() throws IOException {
        RangeReadings rangeValues = new RangeReadings(SCANNING_ANGLES.length);

        dos.writeInt(CommandCode.SCAN.ordinal());
        dos.flush();
        rangeValues.loadObject(dis);
        rangeValues.printReadings();
        return rangeValues;
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#end}
     *
     * @throws IOException
     *
     * <p>Closes Bluetooth connection</p>
     *
     */
    public void end() throws IOException {
        dos.writeInt(CommandCode.END.ordinal());
        dos.flush();
        dis.close();
        dos.close();
        conn.close();
    }

}
