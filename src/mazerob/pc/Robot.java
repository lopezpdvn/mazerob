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
    /** Bluetooth NXT connector object */
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
     * @param wheelDiameter {@code wheelDiameter} argument to {@link 
     * mazerob.nxt.Robot#Robot}
     * @param trackWidth {@code trackWidth} argument to {@link
     * mazerob.nxt.Robot#Robot}
     * @param reverse {@code reverse} argument to {@link
     * mazerob.nxt.Robot#Robot}
     * @param rotationSpeed {@code rotationSpeed} argument to {@link
     * mazerob.nxt.Robot#Robot}
     * @param translationMagnitude {@code translationMagnitude} argument to
     * {@link mazerob.nxt.Robot#Robot}
     * @param rotationMagnitude {@code rotationMagnitude} argument to {@link
     * mazerob.nxt.Robot#Robot}
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
     * Invokes {@link mazerob.nxt.Robot#translate}
     *
     * @see mazerob.conn.RemotelyControllable#translate
     *
     */
    public void translate(double distance) throws IOException {
        dos.writeInt(CommandCode.TRANSLATE.ordinal());
        dos.flush();
        dos.writeDouble(distance);
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#translateForward}
     *
     * @see mazerob.conn.RemotelyControllable#translateForward
     *
     */
    public void translateForward() throws IOException {
        dos.writeInt(CommandCode.TRANSLATE_FORWARD.ordinal());
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#translateBackward}
     *
     * @see mazerob.conn.RemotelyControllable#translateBackward
     *
     */
    public void translateBackward() throws IOException {
        dos.writeInt(CommandCode.TRANSLATE_BACKWARD.ordinal());
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#rotate}
     *
     * @see mazerob.conn.RemotelyControllable#rotate
     *
     */
    public void rotate(double angle) throws IOException {
        dos.writeInt(CommandCode.ROTATE.ordinal());
        dos.flush();
        dos.writeDouble(angle);
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#rotateRight}
     *
     * @see mazerob.conn.RemotelyControllable#rotateRight
     *
     */
    public void rotateRight() throws IOException {
        dos.writeInt(CommandCode.ROTATE_RIGHT.ordinal());
        dos.flush();
    }

    /** 
     * Invokes {@link mazerob.nxt.Robot#rotateLeft}
     *
     * @see mazerob.conn.RemotelyControllable#rotateLeft
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
     * @see mazerob.conn.RemotelyControllable#scan
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
     * <p>Closes Bluetooth connection</p>
     *
     * @see mazerob.conn.RemotelyControllable#end
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
