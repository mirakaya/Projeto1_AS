package HCP.Monitors.WTH;

import HCP.Enums.PatientAge;

/**
 * Method used by the Call Center to interact
 * with the Waiting Hall.
 */
public interface IWTHCallCenter {

    /**
     * Inform the next patient in queue
     * that a Waiting Hall Room is free.
     */
    void informWTRFree(PatientAge age);

    /**
     * Inform the next patient in a
     * Waiting Hall room that a MDW Seat is free.
     */
    void informMDWFree(PatientAge age);
}
