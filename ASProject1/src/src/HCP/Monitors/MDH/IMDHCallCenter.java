package HCP.Monitors.MDH;

import HCP.Enums.PatientAge;

/**
 * Interface for the Call Center to interact with the Medical Hall
 */
public interface IMDHCallCenter {

    /**
     * Informs a Patient with a certain age waiting in MDW that
     * there is a free MDR for them
     * @param age Age of the patient
     */
    void informMDRFree(PatientAge age) throws InterruptedException;
}
