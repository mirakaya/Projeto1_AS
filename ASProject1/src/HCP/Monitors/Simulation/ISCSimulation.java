package HCP.Monitors.Simulation;

/**
 * Methods used by the Simulation to control
 * the shared regions.
 */
public interface ISCSimulation {

    /**
     * Pauses the threads
     */
    void pause();

    /**
     * Resumes the threads
     */
    void resume();
}
