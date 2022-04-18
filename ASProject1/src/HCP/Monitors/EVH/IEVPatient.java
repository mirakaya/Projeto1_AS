package HCP.Monitors.EVH;

import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;

/**
 * Methods used by the Patient to interact with the Evaluation Hall
 */
public interface IEVPatient {

    /**
     * Waits for the nurse to evaluate the patient
     *
     * @return The evaluation given by the nurse
     */
    PatientEvaluation waitEvaluation(int etn, PatientAge age) throws InterruptedException;
}
