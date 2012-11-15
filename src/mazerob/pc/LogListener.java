/* This source code is released under the new BSD license, a copy of the
 * license is in the distribution directory. */

package mazerob.pc;

import lejos.pc.comm.NXTCommLogListener;

/**
 * Logger for the Bluetooth communication link.
 *
 * <p>It is registered with a {@link mazerob.pc.Robot} instance.</p>
 *
 * @author Pedro I. López
 *
 */
class LogListener implements NXTCommLogListener {

    /** Logs Bluetooth connection event.  
     *
     * @param message Message to show when logging a Bluetooth
     *                connection event.
     */
    public void logEvent(String message) {
        System.out.println("PC Log.listener: "+message);

    }

    /** Logs Bluetooth connection exception event. 
     *
     * @param throwable Exception thrown by the connection attempt.
     *
     * */
    public void logEvent(Throwable throwable) {
        System.out.println("PC Log.listener - stack trace: ");
         throwable.printStackTrace();
    }
}
