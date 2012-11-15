/* This source code is released under the new BSD license, a copy of the
 * license is in the distribution directory. */

package mazerob.pc;

import java.io.IOException;
import mazerob.pc.Robot;

/**
 * Program that solves a maze.
 */
class MazeSolver {
    /**
     * Method that executes the {@link mazerob.pc.Robot} instance
     * commands for solving the maze.
     *
     * @param r {@link mazerob.pc.Robot} instance.
     */
    public static void solveMaze(Robot r) throws IOException {
        String continueMsg = "Press enter to continue";
        String exitMsg = "Press enter to exit";

        // THIS IS A DEMO, IT DOESN'T SOLVE ANY MAZE.
        r.translateForward();
        System.out.print(continueMsg); System.in.read();
        r.translateBackward();
        System.out.print(continueMsg); System.in.read();
        r.rotateRight();
        System.out.print(continueMsg); System.in.read();
        r.rotateLeft();
        System.out.print(continueMsg); System.in.read();
        r.scan();
        System.out.print(exitMsg); System.in.read();
        r.end();
        //

    }
}
