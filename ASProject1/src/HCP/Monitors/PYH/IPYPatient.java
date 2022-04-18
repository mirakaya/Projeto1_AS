package HCP.Monitors.PYH;

import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;

/**
 * Methods used by the Patient to interact with the Evaluation Hall
 */
public interface IPYPatient {

    /**
     * Waits for the cashier to process payment
     */
    void waitPayment(PatientAge age, PatientEvaluation evaluation) throws InterruptedException;
}
