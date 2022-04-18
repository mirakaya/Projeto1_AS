package HCP.Main;


import HCP.Entities.TSimulation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        TSimulation simulationThread = new TSimulation();
        simulationThread.start();
        simulationThread.join();

    }


}
