package HCP.Monitors.CCH;

public interface ICCHPatient {

    /**
     * Informs the Call Center that a child
     * patient has left the Entrance Hall
     */
    void informChildLeftEH();

    /**
     * Informs the Call Center that an adult
     * patient has left the Entrance Hall
     */
    void informAdultLeftEH();

    /**
     * Informs the call center that a patient
     * has been treated
     */
    void informTreated();
}
