package HCP.Main;

import HCP.Entities.CallCenter;
import HCP.Entities.Nurse;
import HCP.Entities.Patient;
import HCP.Enums.PatientAge;
import HCP.Monitors.CCH.MCCHall;
import HCP.Monitors.EH.MEntranceHall;
import HCP.Monitors.EVH.MEvaluationHall;
import HCP.Monitors.Logger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MCCHall cch = new MCCHall();
        MEntranceHall eh = new MEntranceHall(4);
        MEvaluationHall evh = new MEvaluationHall(30, 0);

        Logger log = new Logger(30);

        CallCenter cc = new CallCenter(eh, cch);
        Nurse nurse = new Nurse(evh);
        Patient[] childPatients = new Patient[15];
        Patient[] adultPatients = new Patient[15];

        for (int i = 0; i < childPatients.length; i++) {
            childPatients[i] = new Patient(eh, cch, evh, PatientAge.CHILD, log);
            adultPatients[i] = new Patient(eh, cch, evh, PatientAge.ADULT, log);
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

    public void writeee(){
        return;
    }
}
