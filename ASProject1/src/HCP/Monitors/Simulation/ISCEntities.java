package HCP.Monitors.Simulation;

/**
 * Method used by the entities to check
 * if the simulation is suspended and wait
 * if that is the case.
 */
public interface ISCEntities {

    /**
     * When called if the simulation is paused, the entity
     * will wait until it is resumed to return. Otherwise,
     * the method will return immediately.
     */
    void waitIfPaused() throws InterruptedException;
}
