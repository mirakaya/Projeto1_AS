package HCP.Monitors.EVH;

/**
 * Methods used by the Patient to interact with the Evaluation Hall
 */
public interface IEVPatient {

    /**
     * Waits for the nurse to evaluate the patient
     *
     * @return The evaluation given by the nurse
     */
    Object[] waitEvaluation() throws InterruptedException;
}
