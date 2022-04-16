package HCP.Monitors.MDH;

import HCP.Enums.PatientAge;

/**
 * Methods used by the patients to interact with the Medical Hall
 */
public interface IMDHPatient {

    /**
     * Enter the MDW and waits for the Call Center to call them.
     * At the start of the simulation it will skip directly to the
     * Medical Room.
     * @param age Age of the patient.
     * @return The number of the room the patient will occupy.
     */
    int waitMDRCall(PatientAge age, int wtn);

    /**
     * Enter the MDR and wait for the Medical Appointment ot finish.
     * @param age Age of the patient
     * @param room The number of the room the patient will occupy.
     */
    void waitMDRConcluded(PatientAge age, int wtn, int room);
}
