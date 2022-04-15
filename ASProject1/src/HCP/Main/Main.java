package HCP.Main;

import HCP.Entities.CallCenter;
import HCP.Entities.Nurse;
import HCP.Entities.Patient;
import HCP.Enums.PatientAge;
import HCP.Monitors.CCH.MCCHall;
import HCP.Monitors.EH.MEntranceHall;
import HCP.Monitors.EVH.MEvaluationHall;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int nChildPatients = 50;
        final int nAdultPatients = 50;
        final int nPatients = nChildPatients + nAdultPatients;

        MCCHall cch = new MCCHall();
        MEntranceHall eh = new MEntranceHall(4);
        MEvaluationHall evh = new MEvaluationHall(nPatients, 1000);

        CallCenter cc = new CallCenter(eh, cch);
        Nurse nurse = new Nurse(evh);
        Patient[] childPatients = new Patient[nChildPatients];
        Patient[] adultPatients = new Patient[nAdultPatients];

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i] = new Patient(eh, cch, evh, PatientAge.CHILD);
            adultPatients[i] = new Patient(eh, cch, evh, PatientAge.ADULT);
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
