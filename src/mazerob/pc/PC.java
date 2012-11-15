/* This source code is released under the new BSD license, a copy of the
 * license is in the distribution directory. */

package mazerob.pc;

import java.io.IOException;

/** Main PC application for the mazerob system.
 *
 * <p>Uses an instance of {@link mazerob.pc.Robot} for solving a maze with
 * the program stored at {@link mazerob.pc.MazeSolver#solveMaze}.</p>
 *
 * @author Pedro I. López
 *
 */
public class PC {

    /** Main application entry point.
     *
     * <p>Command line positional arguments to configure an instance of {@link
     * mazerob.pc.Robot}.  They are, in order (see {@link
     * mazerob.pc.Robot#Robot}):</p>
     *
     * <dl>
     * <dt>r0Name</dt><dd>The name of the NXT</dd>
     * <dt>r0Address</dt><dd>The bluetooth address of the NXT</dd>
     * <dt>r0WheelDiameter</dt><dd>Diameter of the tires in mm</dd>
     * <dt>r0TrackWidth</dt><dd>Distance between center of right tire and
     * center of left tire in mm</dd>
     * <dt>r0Reverse</dt><dd>If true, the NXT robot moves forward when the
     * motors are running backward.</dd>
     * <dt>r0RotationSpeed</dt><dd>Rotation speed of the vehicle, in degrees
     * per second</dd>
     * <dt>r0TranslationMagnitude</dt><dd>Magnitude of translation in mm</dd>
     * <dt>r0RotationMagnitude</dt><dd>Magnitude of rotation in degrees</dd>
     * </dl>
     *
     * This method gets a {@link mazerob.pc.Robot} instance configured with the
     * command line positional arguments, executes {@link
     * mazerob.pc.MazeSolver#solveMaze} and exits.
     *
     */
    public static void main(String[] args) {
        String r0Name, r0Address;
        double r0WheelDiameter, r0TrackWidth, r0RotationSpeed,
               r0TranslationMagnitude, r0RotationMagnitude;
        boolean r0Reverse;
        LogListener logListener;
        Robot r0;

        assert args.length == 8;

        r0Name = args[0];
        r0Address = args[1];
        r0WheelDiameter = Double.parseDouble(args[2]);
        r0TrackWidth = Double.parseDouble(args[3]);
        r0Reverse = Boolean.parseBoolean(args[4]);
        r0RotationSpeed = Double.parseDouble(args[5]);
        r0TranslationMagnitude = Double.parseDouble(args[6]);
        r0RotationMagnitude = Double.parseDouble(args[7]);
        System.out.println(r0Name + " : " + r0Address);
        logListener = new LogListener();
        r0 = new Robot( r0Name,
                        r0Address,
                        logListener,
                        r0WheelDiameter,
                        r0TrackWidth,
                        r0Reverse,
                        r0RotationSpeed,
                        r0TranslationMagnitude,
                        r0RotationMagnitude
                        );

        try {
            new MazeSolver().solveMaze(r0);
        } catch(Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

}
