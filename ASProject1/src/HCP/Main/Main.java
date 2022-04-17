package HCP.Main;

import HCP.Monitors.Simulation.Simulation;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Simulation simulation = new Simulation(
                25, 25, 4,
                100, 1000, 500,
                100
        );

        simulation.start();
        simulation.join();
    }
}
