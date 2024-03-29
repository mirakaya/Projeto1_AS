package HCP.Monitors.WTH;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.MHallNumber;
import HCP.Monitors.Simulation.MSimulationController;

import java.util.HashMap;

/**
 * Implementation of the Waiting Hall.
 */
public class MWaitingHall implements IWTHPatient, IWTHCallCenter {
    private final HashMap<PatientAge, WaitingHallSplit> ageSplits = new HashMap<>();
    private final MHallNumber hallNumber = new MHallNumber();

    public MWaitingHall(
            int childCount, int adultCount,
            int seatsPerAge, int mdwSeatsPerAge,
            MSimulationController controller
    ) {
        ageSplits.put(
                PatientAge.CHILD,
                new WaitingHallSplit(
                        childCount, seatsPerAge, mdwSeatsPerAge,
                        hallNumber, AvailableHalls.WTR1, controller
                )
        );

        ageSplits.put(
                PatientAge.ADULT,
                new WaitingHallSplit(
                        adultCount, seatsPerAge, mdwSeatsPerAge,
                        hallNumber, AvailableHalls.WTR2 ,controller
                )
        );
    }


    @Override
    public int waitWTRFree(PatientAge age, PatientEvaluation evaluation) throws InterruptedException {
        return ageSplits.get(age).waitWTRFree(age, evaluation);
    }

    @Override
    public void waitMDWCall(int wtn, PatientAge age, PatientEvaluation evaluation) throws InterruptedException {
        ageSplits.get(age).waitMDWCall(wtn, age, evaluation);
    }

    @Override
    public void informWTRFree(PatientAge age) throws InterruptedException {
        ageSplits.get(age).informWTRFree(age);
    }

    @Override
    public void informMDWFree(PatientAge age) throws InterruptedException {
        ageSplits.get(age).informMDWFree(age);
    }
}
