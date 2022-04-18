package HCP.Monitors.EH;

/**
 * Methods used by the Call Center in the Entrance Hall
 */
public interface IEntranceHallCallCenter {

    /**
     * Informs the Patient that entered first that
     * there is a free room in the Evaluation Hall
     */
    void informEvaluationRoomFree() throws InterruptedException;
}
