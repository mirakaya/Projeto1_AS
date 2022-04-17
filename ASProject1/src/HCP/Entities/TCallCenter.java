package HCP.Entities;

import HCP.Monitors.CCH.Call;
import HCP.Monitors.CCH.ICCHallCallCenter;
import HCP.Monitors.EH.IEntranceHallCallCenter;
import HCP.Monitors.MDH.IMDHCallCenter;
import HCP.Monitors.WTH.IWTHCallCenter;

/**
 * Thread representing the Call Center Entity
 */
public class TCallCenter extends Thread {
    private final IEntranceHallCallCenter eh;
    private final ICCHallCallCenter cch;
    private final IWTHCallCenter wth;
    private final IMDHCallCenter mdh;

    public TCallCenter(
            IEntranceHallCallCenter eh, ICCHallCallCenter cch,
            IWTHCallCenter wth, IMDHCallCenter mdh
    ) {
        this.eh = eh;
        this.cch = cch;
        this.wth = wth;
        this.mdh = mdh;

        setDaemon(true);
    }

    @Override
    public void run() {
        Call nextCall = null;

        try {
            while (nextCall != Call.EXIT_CALL) {
                cch.waitManualOrder();
                nextCall = cch.waitCall();

                switch (nextCall.getType()) {
                    case EV_PATIENT_LEFT -> eh.informEvaluationRoomFree();
                    case WTR_PATIENT_LEFT -> wth.informWTRFree(nextCall.getAge());
                    case MDW_PATIENT_LEFT -> wth.informMDWFree(nextCall.getAge());
                    case MDR_PATIENT_LEFT -> mdh.informMDRFree(nextCall.getAge());
                    case EXIT -> System.out.println("All patients treated! Exiting!");
                }
            }
        } catch (InterruptedException ignored) {}
    }
}
