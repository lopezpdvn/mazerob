/* This source code is released under the new BSD license, a copy of the
 * license is in the distribution directory. */

package mazerob.nxt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.RuntimeException;
import lejos.nxt.Button;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import mazerob.conn.CommandCode;

/**
 * Main NXT application
 *
 * @author Pedro I. López
 *
 */
public class RobotApp {
    /**
     * Manages the {@link mazerob.nxt.Robot} remote method invocation
     *
     * <p>
     * <ol>
     * <li>Stablishes a Bluetooth connection with the PC (see {@link
     * mazerob.pc.Robot})</li>
     * <li>Gets a {@link mazerob.nxt.Robot} instance with configuration
     * received from the PC (see {@link mazerob.pc.Robot})</li>
     * <li>Enters the main loop where it waits for {@link
     * mazerob.pc.Robot} to invoke methods from {@link
     * mazerob.nxt.Robot} </li>
     * <li>When {@link mazerob.nxt.Robot#scan} is invoked, it sends the {@link
     * lejos.robotics.RangeReadings} object through the Bluetooth link to the
     * {@link mazerob.pc.Robot} instance
     * <li>When {@link mazerob.nxt.Robot#end} is invoked, it closes the
     * Bluetooth connection and wait for program termination (user must
     * press red button on NXT)</li>
     * </ol>
     * </p>
     *
     */
    public static void main(String [] args)  throws Exception {
        final String EXIT_MSG = "Press red button to end program";
        final String WAITING_MSG = "Waiting for connection...";
        final String CONNECTED_MSG = "Connected";
        final int WAIT_DRAIN_TIME = 100;
        DataInputStream dis;
        DataOutputStream dos;
        BTConnection btc;
        CommandCode commandCode;
        CommandCode[] commandCodeValues = CommandCode.values();
        Robot robot;

        System.out.println(WAITING_MSG);
        btc = Bluetooth.waitForConnection();
        System.out.println(CONNECTED_MSG);
        dis = btc.openDataInputStream();
        dos = btc.openDataOutputStream();

        robot = new Robot(dis.readDouble(),
                                dis.readDouble(),
                                dis.readBoolean(),
                                dis.readDouble(),
                                dis.readDouble(),
                                dis.readDouble());

        try {
            while(true) {
                commandCode = commandCodeValues[dis.readInt()];

                switch(commandCode) {
                    case TRANSLATE_FORWARD:
                        robot.translateForward();
                        break;
                    case TRANSLATE_BACKWARD:
                        robot.translateBackward();
                        break;
                    case ROTATE_RIGHT:
                        robot.rotateRight();
                        break;
                    case ROTATE_LEFT:
                        robot.rotateLeft();
                        break;
                    case SCAN:
                        robot.scan().dumpObject(dos);
                        dos.flush();
                        break;
                    case END:
                    default:
                        robot.end();
                }

            }
        }
        catch(IOException e) {
            System.out.println(e);
        }
        catch(RuntimeException e) {
            System.out.println(EXIT_MSG);
        }
        finally {
            dis.close();
            dos.close();
            Thread.sleep(WAIT_DRAIN_TIME); // wait for data to drain
            btc.close();
            while (true) if (Button.ENTER.isDown()) break;
        }

    }
}
