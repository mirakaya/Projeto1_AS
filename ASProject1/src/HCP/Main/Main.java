package HCP.Main;

import HCP.Entities.TCallCenter;
import HCP.Entities.TNurse;
import HCP.Entities.TPatient;
import HCP.Enums.PatientAge;
import HCP.Monitors.CCH.MCCHall;
import HCP.Monitors.EH.MEntranceHall;
import HCP.Monitors.EVH.MEvaluationHall;
import HCP.Monitors.WTH.MWaitingHall;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int nChildPatients = 25;
        final int nAdultPatients = 25;
        final int nPatients = nChildPatients + nAdultPatients;

        MCCHall cch = new MCCHall();
        MEntranceHall eh = new MEntranceHall(4);
        MEvaluationHall evh = new MEvaluationHall(nPatients, 100);
        MWaitingHall wth = new MWaitingHall(nChildPatients, nAdultPatients, 2, 2);

        TCallCenter cc = new TCallCenter(eh, cch, wth);
        TNurse nurse = new TNurse(evh);
        TPatient[] childPatients = new TPatient[nChildPatients];
        TPatient[] adultPatients = new TPatient[nAdultPatients];

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i] = new TPatient(eh, cch, evh, wth, PatientAge.CHILD);
            adultPatients[i] = new TPatient(eh, cch, evh, wth, PatientAge.ADULT);
        }

        cc.start();
        nurse.start();

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i].start();
            adultPatients[i].start();
        }

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i].join();
            adultPatients[i].join();
        }
      
        cch.informExit();
        nurse.join();
        cc.join();
    }
}
