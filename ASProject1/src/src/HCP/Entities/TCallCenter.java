package HCP.Entities;

import HCP.Monitors.CCH.Call;
import HCP.Monitors.CCH.ICCHallCallCenter;
import HCP.Monitors.EH.IEntranceHallCallCenter;
import HCP.Monitors.WTH.IWTHCallCenter;

/**
 * Thread representing the Call Center Entity
 */
public class TCallCenter extends Thread {
    private final IEntranceHallCallCenter eh;
    private final ICCHallCallCenter cch;
    private final IWTHCallCenter wth;

    public TCallCenter(
            IEntranceHallCallCenter eh, ICCHallCallCenter cch,
            IWTHCallCenter wth
    ) {
        this.eh = eh;
        this.cch = cch;
        this.wth = wth;

        setDaemon(true);
    }

    @Override
    public void run() {
        Call nextCall = null;

        while (nextCall != Call.EXIT_CALL) {
            cch.waitManualOrder();
            nextCall = cch.waitCall();

            switch (nextCall.getType()) {
                case EV_PATIENT_LEFT -> eh.informEvaluationRoomFree();
                case WTR_PATIENT_LEFT -> wth.informWTRFree(nextCall.getAge());
                case MDW_PATIENT_LEFT -> wth.informMDWFree(nextCall.getAge());
                case EXIT -> System.out.println("All patients treated! Exiting!");
            }
        }
    }
}
