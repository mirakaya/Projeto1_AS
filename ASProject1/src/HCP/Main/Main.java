package HCP.Main;

import HCP.Entities.TCallCenter;
import HCP.Entities.TCashier;
import HCP.Entities.TNurse;
import HCP.Entities.TPatient;
import HCP.Enums.PatientAge;
import HCP.Monitors.CCH.MCCHall;
import HCP.Monitors.EH.MEntranceHall;
import HCP.Monitors.EVH.MEvaluationHall;
import HCP.Monitors.PYH.IPYPatient;
import HCP.Monitors.PYH.MPaymentHall;
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

        MPaymentHall pyh = new MPaymentHall(nPatients, 500);
        TCashier cashier = new TCashier(pyh);

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i] = new TPatient(eh, cch, evh, wth, pyh, PatientAge.CHILD);
            adultPatients[i] = new TPatient(eh, cch, evh, wth, pyh, PatientAge.ADULT);
        }

        cc.start();
        nurse.start();
        cashier.start();

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
