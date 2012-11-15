/* This source code is released under the new BSD license, a copy of the
 * license is in the distribution directory. */

package mazerob.nxt;

import java.lang.RuntimeException;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RotatingRangeScanner;
import lejos.robotics.RangeReadings;
import lejos.robotics.navigation.DifferentialPilot;
import mazerob.conn.RemotelyControllable;

/**
 * Low level Robot functionality specified by {@link
 * mazerob.conn.RemotelyControllable}
 *
 * @author Pedro I. López
 *
 */
class Robot implements RemotelyControllable {

    /** Return only when the whole movement is complete */
    static final boolean DISPLACEMENT_IMMEDIATE_RETURN = false;

    /** Abstraction of the Robot's ultrasonic sensor */
    RotatingRangeScanner scanner;

    /** Instance of the Pilot mechanism to control the Robot movements */
    DifferentialPilot pilot;

    /** Magnitude of translation movement in mm specified by the
     * {@code translationMagnitude} argument in {@link
     * mazerob.nxt.Robot#Robot}
     */
    double translationMagnitude;

    /** Magnitude of rotation movement in degrees specified by the
     * {@code rotationMagnitude} argument in {@link
     * mazerob.nxt.Robot#Robot}
     */
    double rotationMagnitude;

    /**
     * @param wheelDiameter Diameter of the tires in mm
     * @param trackWidth Distance between center of right tire and
     * center of left tire in mm
     * @param reverse If true, the NXT robot moves forward when the
     * motors are running backward
     * @param rotationSpeed Rotation speed of the vehicle, in degrees
     * per second
     * @param translationMagnitude Magnitude of translation in mm
     * @param rotationMagnitude Magnitude of rotation in degrees
     */
    public Robot(double wheelDiameter,
                      double trackWidth,
                      boolean reverse,
                      double rotationSpeed,
                      double translationMagnitude,
                      double rotationMagnitude) {
        final String CONFIGURED_MSG = "Configuration complete";

        this.translationMagnitude = translationMagnitude;
        this.rotationMagnitude = rotationMagnitude;
        pilot = new DifferentialPilot(wheelDiameter, trackWidth,
            Motor.B, Motor.C, reverse);
        pilot.setRotateSpeed(rotationSpeed);
        scanner = new RotatingRangeScanner(Motor.A,
            new UltrasonicSensor(SensorPort.S1));
        scanner.setAngles( SCANNING_ANGLES );

        System.out.println(CONFIGURED_MSG);
    }

    /**
     * Translate forward {@link Robot#translationMagnitude} mm.  Wait
     * until done, see {@link Robot#DISPLACEMENT_IMMEDIATE_RETURN}
     */
    public void translateForward() {
        pilot.travel(translationMagnitude, DISPLACEMENT_IMMEDIATE_RETURN);
    }

    /**
     * Translate backward {@link Robot#translationMagnitude} mm.  Wait
     * until done, see {@link Robot#DISPLACEMENT_IMMEDIATE_RETURN}
     */
    public void translateBackward() {
        pilot.travel(-translationMagnitude, DISPLACEMENT_IMMEDIATE_RETURN);
    }

    /**
     * Rotate to the right {@link Robot#rotationMagnitude} mm.  Wait
     * until done, see {@link Robot#DISPLACEMENT_IMMEDIATE_RETURN}
     */
    public void rotateRight() {
        pilot.rotate(rotationMagnitude, DISPLACEMENT_IMMEDIATE_RETURN);
    }

    /**
     * Rotate to the left {@link Robot#rotationMagnitude} mm.  Wait
     * until done, see {@link Robot#DISPLACEMENT_IMMEDIATE_RETURN}
     */
    public void rotateLeft() {
        pilot.rotate(-rotationMagnitude, DISPLACEMENT_IMMEDIATE_RETURN);
    }

    /**
     * End the connection and exit the {@link mazerob.nxt.RobotApp}
     * program
     *
     * @exception RuntimeException
     */
    public void end() throws RuntimeException {
        System.out.println(CLOSING_CONN_MSG);
        throw new RuntimeException();
    }

    /**
     * Scan the environment for object detection
     *
     * <p>Scanning angles specified by {@link
     * Robot#SCANNING_ANGLES}.</p>
     *
     * @return A set of {@link lejos.robotics.RangeReadings} taken the
     * angles specified.
     */
    public RangeReadings scan() {
        RangeReadings rangeValues = scanner.getRangeValues();
        rangeValues.printReadings();
        return rangeValues;
    }

}
