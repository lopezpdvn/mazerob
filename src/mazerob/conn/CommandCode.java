/* This source code is released under the new BSD license, a copy of the
 * license is in the distribution directory. */

package mazerob.conn;

/** Constants that map the {@link mazerob.conn.RemotelyControllable} interface
 * methods from a {@link mazerob.pc.Robot} instance to a {@link
 * mazerob.nxt.Robot} instance.
 *
 * @author Pedro I. López
 *
 */
public enum CommandCode {

    /** Constant that maps {@link mazerob.pc.Robot#translate} to 
     *  {@link mazerob.nxt.Robot#translate}. */
    TRANSLATE,

    /** Constant that maps {@link mazerob.pc.Robot#translateForward} to 
     *  {@link mazerob.nxt.Robot#translateForward}. */
    TRANSLATE_FORWARD,

    /** Constant that maps {@link mazerob.pc.Robot#translateBackward} to 
     * {@link mazerob.nxt.Robot#translateBackward}. */
    TRANSLATE_BACKWARD,

    /** Constant that maps {@link mazerob.pc.Robot#rotate} to 
     * {@link mazerob.nxt.Robot#rotate}. */
    ROTATE,

    /** Constant that maps {@link mazerob.pc.Robot#rotateRight} to 
     * {@link mazerob.nxt.Robot#rotateRight}. */
    ROTATE_RIGHT,

    /** Constant that maps {@link mazerob.pc.Robot#rotateLeft} to 
     * {@link mazerob.nxt.Robot#rotateLeft}. */
    ROTATE_LEFT,

    /** Constant that maps {@link mazerob.pc.Robot#scan} to 
     * {@link mazerob.nxt.Robot#scan}. */
    SCAN,

    /** Constant that maps {@link mazerob.pc.Robot#end} to 
     * {@link mazerob.nxt.Robot#end}. */
    END
}
