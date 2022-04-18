package HCP.Monitors.EVH;

/**
 * Methods used by the Nurse to interact with the evaluation hall
 */
public interface IEVNurse {

    /**
     * Method to determine whether there are
     * patients left to attend to or not.
     * @return True if there are patients left or False if there isn't
     */
    boolean anyPatientsLeft();

    /**
     * Method used to wait for the arrival of patients.
     * Returns immediately if there are patients already waiting.
     */
    void waitPatients() throws InterruptedException;

    /**
     * Evaluates a patient, taking a certain amount of time to do so.
     */
    void evaluateNextPatient() throws InterruptedException;
}
