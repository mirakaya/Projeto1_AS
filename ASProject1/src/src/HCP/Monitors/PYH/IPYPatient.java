package HCP.Monitors.PYH;

/**
 * Methods used by the Patient to interact with the Evaluation Hall
 */
public interface IPYPatient {

    /**
     * Waits for the cashier to process payment
     */
    void waitPayment(int id);
}
