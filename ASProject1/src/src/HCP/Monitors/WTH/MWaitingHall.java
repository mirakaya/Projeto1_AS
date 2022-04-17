package HCP.Monitors.WTH;

import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.MHallNumber;

import java.util.HashMap;

/**
 * Implementation of the Waiting Hall.
 */
public class MWaitingHall implements IWTHPatient, IWTHCallCenter {
    private final HashMap<PatientAge, WaitingHallSplit> ageSplits = new HashMap<>();
    private final MHallNumber hallNumber = new MHallNumber();

    public MWaitingHall(int childCount, int adultCount, int seatsPerAge, int mdwSeatsPerAge) {
        ageSplits.put(
                PatientAge.CHILD,
                new WaitingHallSplit(childCount, seatsPerAge, mdwSeatsPerAge, hallNumber)
        );

        ageSplits.put(
                PatientAge.ADULT,
                new WaitingHallSplit(adultCount, seatsPerAge, mdwSeatsPerAge, hallNumber)
        );
    }


    @Override
    public int waitWTRFree(PatientAge age) {
        return ageSplits.get(age).waitWTRFree(age);
    }

    @Override
    public void waitMDWCall(int wtn, PatientAge age, PatientEvaluation evaluation) {
        ageSplits.get(age).waitMDWCall(wtn, age, evaluation);
    }

    @Override
    public void informWTRFree(PatientAge age) {
        ageSplits.get(age).informWTRFree(age);
    }

    @Override
    public void informMDWFree(PatientAge age) {
        ageSplits.get(age).informMDWFree(age);
    }
}
