package HCP.Monitors.EH;

import HCP.Enums.PatientAge;

/**
 * Methods used by the Patient to interact
 * with the Entrance Hall
 */
public interface IEntranceHallPatient {

    /**
     * Waits for a room to be free at the entrance hall.
     * If a room is already free the method returns immediately.
     *
     * @param threadId Id of the thread for logging purposes
     * @param age Age of the patient
     */
    void waitFreeRoom(int threadId, PatientAge age);

    /**
     * Patient calls the method to enter the entrance hall
     * and waits for a call to the evaluation hall. Multiple
     * patients waiting here will leave by entrance order.
     * @param age Age of the patient
     */
    void waitEvaluationHallCall(PatientAge age);

    /**
     * Awakes one of the patients waiting to enter the entrance hall
     * @param age Age of the patient to awake
     */
    void awakeWaitingPatient(PatientAge age);
}
