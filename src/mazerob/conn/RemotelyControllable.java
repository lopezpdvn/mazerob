/* This source code is released under the new BSD license, a copy of the
 * license is in the distribution directory. */

package mazerob.conn;

import java.io.IOException;
import lejos.robotics.RangeReadings;

/**
 * Specification of functionality of a maze solving robot ({@link
 * mazerob.pc.Robot} and {@link mazerob.nxt.Robot})
 *
 * @author Pedro I. López
 *
 */
public interface RemotelyControllable {
    /**
     * Message showed when the Bluetooth connection is closed
     */
    static final String CLOSING_CONN_MSG = "Closing BT Connection";

    /**
     * Array of angles at which range readings are to be taken by the
     * implementation of the {@link mazerob.conn.RemotelyControllable#scan}
     * method.
     */
    static final float[] SCANNING_ANGLES = {0f, 45f, 90f, 135f, 180f};

    /** Translate a specific distance in a straight line
     *
     * <p>A positive distance causes forward motion, a negative distance
     * translates backward.</p>
     *
     * @param distance The distance to move
     *
     * @throws IOException
     *
     */
    public void translate(double distance) throws IOException;

    /** Translate forward */
    public void translateForward() throws IOException;

    /** Translate backward */
    public void translateBackward() throws IOException;

    /** Rotate through specific angle
     *
     * @param angle The wanted angle of rotation in degrees
     *
     * @throws IOException
     *
     */
    public void rotate(double angle) throws IOException;

    /** Rotate to the right */
    public void rotateRight() throws IOException;

    /** Rotate to the left */
    public void rotateLeft() throws IOException;

    /** Scan the environment for object detection 
     *
     * <p>Scanning angles specified by {@link
     * mazerob.conn.RemotelyControllable#SCANNING_ANGLES}.</p>
     *
     * @return A set of {@link lejos.robotics.RangeReadings} taken the
     * angles specified.
     *
     * @throws IOException
     *
     */
    public RangeReadings scan() throws IOException;

    /** End the connection/program */
    public void end() throws IOException;
}
