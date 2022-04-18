package HCP.Monitors.CCH;

import HCP.Enums.PatientAge;

/**
 * Methods used by the patient to interact with
 * Call Center.
 */
public interface ICCHallPatient {

    /**
     * Informs the Call Center that a
     * patient left the Evaluation Hall.
     */
    void informLeftEVHall() throws InterruptedException;

    /**
     * Informs the Call Center that a
     * patient left the Waiting Hall Room.
     */
    void informLeftWTR(PatientAge age) throws InterruptedException;

    /**
     * Informs the Call Center that a
     * patient left its MDW Seat.
     */
    void informLeftMDW(PatientAge age) throws InterruptedException;

    /**
     * Informs the Call Center that a patient left
     * a Medical Room.
     * @param age Age of the patient.
     */
    void informLeftMDR(PatientAge age) throws InterruptedException;
}
