package HCP.Monitors.EH;

/**
 * Definition of the Interface of the Entrance Hall for the Patients
 */
public interface IEHPatient {
    /**
     * Method used for a child patient to enter the Entrance Hall.
     * If there is a free room for them, they will wait in said room
     * and are attributed a random id.
     */
    void enterChild();

    /**
     * Method used for an adult patient to enter the Entrance Hall.
     * If there is a free room for them, they will wait in said room
     * and are attributed a random id.
     */
    void enterAdult();
}
