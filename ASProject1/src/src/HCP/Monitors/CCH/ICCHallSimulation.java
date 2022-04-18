package HCP.Monitors.CCH;

/**
 * Methods used by the Simulation to interact
 * with the Call Center Hall
 */
public interface ICCHallSimulation {

    /**
     * Informs the Call Center that it should
     * exit.
     */
    void informExit();

    /**
     * Sets or unsets the automatic movement
     * of the Call Center Thread by toggling
     * the Call Center Hall operation mode.
     * @param isAuto Whether the Call Center continues or stops at the CCH
     */
    void setAuto(boolean isAuto);

    /**
     * If auto mode is disengaged, it will
     * make the Call Center move.
     */
    void manualMove();
}
