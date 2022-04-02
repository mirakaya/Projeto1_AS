package HCP.Entities;

import HCP.Monitors.CCH.ICCHPatient;
import HCP.Monitors.EH.IEHPatient;

public class ChildPatient extends Thread {
    private final ICCHPatient cch;
    private final IEHPatient eh;

    public ChildPatient(ICCHPatient cch, IEHPatient eh) {
        this.cch = cch;
        this.eh = eh;
    }

    @Override
    public void run() {
        eh.enterChild();
        cch.informChildLeftEH();
        cch.informTreated();
    }
}
