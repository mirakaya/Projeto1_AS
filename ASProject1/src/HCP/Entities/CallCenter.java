package HCP.Entities;

import HCP.Monitors.CCH.ICCHControlCenter;
import HCP.Monitors.EH.IEHControlCenter;
import HCP.Monitors.CCH.CCHCall;

public class CallCenter extends Thread {
    private final ICCHControlCenter cch;
    private final IEHControlCenter eh;

    public CallCenter(ICCHControlCenter cch, IEHControlCenter eh) {
        this.cch = cch;
        this.eh = eh;
    }

    @Override
    public void run() {
        boolean shouldRun = true;

        while(shouldRun) {
            CCHCall next = this.cch.nextCall();

            switch (next) {
                case ADULT_PATIENT_LEFT -> this.eh.informFreeAdultSeat();
                case CHILD_PATIENT_LEFT -> this.eh.informFreeChildSeat();
                case CLOSE_CC -> shouldRun = false;
            }
        }
    }
}
