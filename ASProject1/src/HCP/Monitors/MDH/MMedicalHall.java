package HCP.Monitors.MDH;

import HCP.Enums.PatientAge;

import java.util.HashMap;

/**
 * Implementation of the Medical Hall
 */
public class MMedicalHall implements IMDHPatient, IMDHCallCenter {
    private final HashMap<PatientAge, MedicalHallSplit> ageHallSplits = new HashMap<>();

    public MMedicalHall(int childCount, int adultCount, int treatmentTime) {
        ageHallSplits.put(PatientAge.CHILD, new MedicalHallSplit(childCount, treatmentTime));
        ageHallSplits.put(PatientAge.ADULT, new MedicalHallSplit(adultCount, treatmentTime));
    }

    @Override
    public int waitMDRCall(PatientAge age, int wtn) throws InterruptedException {
        return ageHallSplits.get(age).waitMDRCall(age, wtn);
    }

    @Override
    public void waitMDRConcluded(PatientAge age, int wtn, int room) throws InterruptedException {
        ageHallSplits.get(age).waitMDRConcluded(age, wtn, room);
    }

    @Override
    public void informMDRFree(PatientAge age) throws InterruptedException {
        ageHallSplits.get(age).informMDRFree(age);
    }
}
