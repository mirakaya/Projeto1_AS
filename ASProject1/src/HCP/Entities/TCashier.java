package HCP.Entities;

import HCP.Monitors.PYH.IPYCashier;


/**
 * Cashier Thread
 */
public class TCashier extends Thread {
    private final IPYCashier pyh;

    public TCashier(IPYCashier pyh) {
        this.pyh = pyh;

        setDaemon(true);
    }

    @Override
    public void run() {

        try {
            while(pyh.anyPatientsLeft()) {
                pyh.waitPatients();
                pyh.processNextPatient();
            }

            System.out.println("Cashier acabou o seu trabalho, adeus");
        } catch (InterruptedException ignored) {}
    }
}

