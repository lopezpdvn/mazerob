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
 * NXT Explorer abstraction
 *
 * @author Pedro I. López
 *
 * @see <a
 * href="http://www.nxtprograms.com/NXT2/explorer/steps.html">NXT Explorer</a>
 *
 */
class Robot implements RemotelyControllable {

    /** Abstraction of the Explorer's ultrasonic sensor */
    RotatingRangeScanner scanner;

    /** Instance of the Pilot mechanism to control the Explorer movements */
    DifferentialPilot pilot;

    /** Magnitude of translation in mm of methods {@link
     * mazerob.nxt.Robot#translateForward} and {@link
     * mazerob.nxt.Robot#translateBackward} specified by the
     * {@code translationMagnitude} argument in {@link
     * mazerob.nxt.Robot#Robot}
     */
    double translationMagnitude;

    /** Magnitude of rotation in degrees of methods {@link
     * mazerob.nxt.Robot#rotateRight} and {@link
     * mazerob.nxt.Robot#rotateLeft} specified by the
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
     * @param translationMagnitude Magnitude of translation in mm of methods
     * {@link mazerob.nxt.Robot#translateForward} and {@link
     * mazerob.nxt.Robot#translateBackward}
     * @param rotationMagnitude Magnitude of rotation in degrees of methods
     * {@link mazerob.nxt.Robot#rotateRight} and {@link
     * mazerob.nxt.Robot#rotateLeft}
     *
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
     * Wait until translation is done
     *
     * @see mazerob.conn.RemotelyControllable#translate
     */
    public void translate(double distance) {
        pilot.travel(distance);
    }

    /**
     * Translate forward {@link Robot#translationMagnitude} mm,  wait until
     * translation is done
     *
     * @see mazerob.conn.RemotelyControllable#translateForward
     */
    public void translateForward() {
        pilot.travel(translationMagnitude);
    }

    /**
     * Translate backward {@link Robot#translationMagnitude} mm,  wait until
     * translation is done
     *
     * @see mazerob.conn.RemotelyControllable#translateBackward
     */
    public void translateBackward() {
        pilot.travel(-translationMagnitude);
    }

    /**
     * Wait until rotation is done
     *
     * @see mazerob.conn.RemotelyControllable#rotate
     */
    public void rotate(double angle) {
        pilot.rotate(angle);
    }

    /**
     * Rotate to the right {@link Robot#rotationMagnitude} mm,  wait until
     * rotation is done
     *
     * @see mazerob.conn.RemotelyControllable#rotateRight
     */
    public void rotateRight() {
        pilot.rotate(rotationMagnitude);
    }

    /**
     * Rotate to the left {@link Robot#rotationMagnitude} mm,  wait until
     * rotation is done
     *
     * @see mazerob.conn.RemotelyControllable#rotateLeft
     */
    public void rotateLeft() {
        pilot.rotate(-rotationMagnitude);
    }

    /**
     * End the connection and exit the {@link mazerob.nxt.RobotApp}
     * program
     *
     * @see mazerob.conn.RemotelyControllable#end
     */
    public void end() throws RuntimeException {
        System.out.println(CLOSING_CONN_MSG);
        throw new RuntimeException();
    }

    /**
     * @see mazerob.conn.RemotelyControllable#scan
     */
    public RangeReadings scan() {
        RangeReadings rangeValues = scanner.getRangeValues();
        return rangeValues;
    }

}
