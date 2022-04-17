package HCP.Entities;

import HCP.Monitors.EVH.IEVNurse;
import HCP.Monitors.Simulation.Simulation;

/**
 * Simulation Starter Thread
 */
public class TSimStarter extends Thread {

    private int [] data;


    public TSimStarter(int[] data) {

        this.data = data;
        setDaemon(true);
    }

    @Override
    public void run() {


        Simulation simulation = new Simulation(
                data[1], data[0], data[2],
                data[3], data[4], data[5],
                data[6]
        );
        System.out.println("here bishes");


        simulation.start();

        System.out.println("here 2");


        try {
            simulation.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

