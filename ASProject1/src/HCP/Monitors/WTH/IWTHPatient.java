package HCP.Monitors.WTH;

import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;

/**
 * Methods used by the Patients to interact with
 * the Waiting Hall
 */
public interface IWTHPatient {

    /**
     * Method used for the patients enter the
     * Waiting Hall and wait for Room to be free.
     *
     * @param age Age of the patient
     * @return The WTN attributed to the patient.
     */
    int waitWTRFree(PatientAge age);

    /**
     * Method used for the patients to enter a waiting
     * room and wait to be called to the Medical Hall
     * @param wtn Waiting Hall Number
     * @param age Age of the patient
     * @param evaluation The evaluation of the patient
     */
    void waitMDWCall(int wtn, PatientAge age, PatientEvaluation evaluation);
}
