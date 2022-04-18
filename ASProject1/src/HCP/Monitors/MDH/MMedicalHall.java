package HCP.Monitors.MDH;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.Simulation.MSimulationController;

import java.util.HashMap;

/**
 * Implementation of the Medical Hall
 */
public class MMedicalHall implements IMDHPatient, IMDHCallCenter {
    private final HashMap<PatientAge, MedicalHallSplit> ageHallSplits = new HashMap<>();

    public MMedicalHall(
            int childCount, int adultCount, int treatmentTime,
            MSimulationController controller
    ) {
        ageHallSplits.put(PatientAge.CHILD, new MedicalHallSplit(
                childCount, treatmentTime,
                new AvailableHalls[]{ AvailableHalls.MDR1, AvailableHalls.MDR2},
                controller
        ));
        ageHallSplits.put(PatientAge.ADULT, new MedicalHallSplit(
                adultCount, treatmentTime,
                new AvailableHalls[]{ AvailableHalls.MDR3, AvailableHalls.MDR4},
                controller
        ));
    }

    @Override
    public int waitMDRCall(PatientAge age, int wtn, PatientEvaluation evaluation) throws InterruptedException {
        return ageHallSplits.get(age).waitMDRCall(age, wtn, evaluation);
    }

    @Override
    public void waitMDRConcluded(
            PatientAge age, int wtn,
            int room, PatientEvaluation evaluation
    ) throws InterruptedException {
        ageHallSplits.get(age).waitMDRConcluded(age, wtn, room, evaluation);
    }

    @Override
    public void informMDRFree(PatientAge age) throws InterruptedException {
        ageHallSplits.get(age).informMDRFree(age);
    }
}
