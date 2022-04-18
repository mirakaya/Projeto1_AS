package HCP.Entities;

import HCP.Monitors.MLogger;
import HCP.Monitors.SendToHCP_GUI.MSendToHCP_GUI;
import HCP.Monitors.Simulation.Simulation;

/**
 * Simulation Starter Thread
 */
public class TSimStarter extends Thread {

    private int [] data;
    private final MLogger log;

    public TSimStarter(int[] data, MLogger log ) {

        this.data = data;
        this.log = log;
        setDaemon(true);
    }

    @Override
    public void run() {


        Simulation simulation = new Simulation(
                data[1], data[0], data[2],
                data[3], data[4], data[5],
                data[6], log
        );

        simulation.start();


        try {
            simulation.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

