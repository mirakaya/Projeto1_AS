package HCP.Entities;

import HCP.Enums.CallCenterCall;
import HCP.Monitors.CCH.ICCHallCallCenter;
import HCP.Monitors.EH.IEntranceHallCallCenter;

/**
 * Thread representing the Call Center Entity
 */
public class CallCenter extends Thread {
    private final IEntranceHallCallCenter eh;
    private final ICCHallCallCenter cch;

    public CallCenter(IEntranceHallCallCenter eh, ICCHallCallCenter cch) {
        this.eh = eh;
        this.cch = cch;

        setDaemon(true);
    }

    @Override
    public void run() {
        CallCenterCall nextCall = null;

        while (nextCall != CallCenterCall.EXIT) {
            cch.waitManualOrder();
            nextCall = cch.waitCall();

            switch (nextCall) {
                case EV_PATIENT_LEFT -> eh.informEvaluationRoomFree();
                case EXIT -> {}
            }
        }
    }
}
