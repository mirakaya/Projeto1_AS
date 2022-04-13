package HCP.Main;

import HCP.Entities.CallCenter;
import HCP.Entities.Patient;
import HCP.Enums.PatientAge;
import HCP.Monitors.CCH.MCCHall;
import HCP.Monitors.EH.MEntranceHall;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MCCHall cch = new MCCHall();
        MEntranceHall eh = new MEntranceHall(4);
        CallCenter cc = new CallCenter(eh, cch);
        Patient[] childPatients = new Patient[15];
        Patient[] adultPatients = new Patient[15];

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i] = new Patient(eh, cch, PatientAge.CHILD);
            adultPatients[i] = new Patient(eh, cch, PatientAge.ADULT);
        }

        cc.start();

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i].start();
            adultPatients[i].start();
        }

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i].join();
            adultPatients[i].join();
        }

        cch.informExit();
        cc.join();
    }
}
