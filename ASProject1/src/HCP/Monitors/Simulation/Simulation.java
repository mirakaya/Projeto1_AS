package HCP.Monitors.Simulation;

/**
 * Class representing a single simulation. This class
 * allows to start a simulation with several parameters
 * and to control it.
 */
public class Simulation {

    private final int childCount;
    private final int adultCount;
    private final int maxEvaluationDelay;
    private final int maxTreatmentDelay;
    private final int maxTimeToMove;

    /**
     * Creates a simulation.
     * @param childCount Number of children patients
     * @param adultCount Number of adult patients
     * @param maxEvaluationDelay Maximum delay in milliseconds for evaluation of a patient
     * @param maxTreatmentDelay Maximum delay in milliseconds for treatment of a patient
     * @param maxTimeToMove Maximum delay in milliseconds for payment by a patient
     */
    public Simulation(
            int childCount, int adultCount, int maxEvaluationDelay,
            int maxTreatmentDelay, int maxTimeToMove
    ) {
        this.childCount = childCount;
        this.adultCount = adultCount;
        this.maxEvaluationDelay = maxEvaluationDelay;
        this.maxTreatmentDelay = maxTreatmentDelay;
        this.maxTimeToMove = maxTimeToMove;
    }

    public void start() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void stop() {

    }

    public void setAuto(boolean isAuto) {

    }

    public void moveCCManually() {

    }
}
