package HCP.Monitors.CCH;

import HCP.Enums.CallCenterCall;

/**
 * Methods used by the Call Center to interact with the
 * Call Center Hall
 */
public interface ICCHallCallCenter {

    /**
     * Waits for a manual order to move if the manual mode
     * is engaged, otherwise returns immediately.
     */
    void waitManualOrder();

    /**
     * Waits for a call from a patient to arrive or takes one out
     * if there was already one there.
     * @return Returns the next call for the Call Center to attend.
     */
    Call waitCall();
}
