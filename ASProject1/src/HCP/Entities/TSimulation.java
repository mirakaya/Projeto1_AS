package HCP.Entities;

import HCP.Enums.SimulationState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


/**
 * HCP Starter Thread
 */
public class TSimulation extends Thread {

    private final ServerSocket serverSocket = new ServerSocket(8080);
    private TSimulationRunner simulation;

    public TSimulation() throws IOException {
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            System.out.println("Waiting for CCP GUI");
            Socket socket = serverSocket.accept();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Waiting for CCP GUI Parameters");
            int[] simulationParameters = (int[]) inputStream.readObject();


            int adultCount = simulationParameters[0];
            int childCount = simulationParameters[1];
            int seatCount = simulationParameters[2];
            int maxEvaluationDelay = simulationParameters[3];
            int maxTreatmentDelay = simulationParameters[4];
            int maxPaymentDelay = simulationParameters[5];
            int maxMoveDelay = simulationParameters[6];
            SimulationState state = null;

            System.out.println("Number of seats: " + seatCount);
            System.out.println("Waiting for simulation to start");

            while (state != SimulationState.END) {
                state = (SimulationState) inputStream.readObject();

                switch (state) {
                    case START -> {
                        simulation = new TSimulationRunner(
                                childCount, adultCount, seatCount,
                                maxEvaluationDelay, maxTreatmentDelay, maxPaymentDelay,
                                maxMoveDelay
                        );
                        simulation.start();
                    }
                    case SUSPEND -> simulation.pause();
                    case RESUME -> simulation.resume();
                    case STOP, END -> simulation.stop();
                    case AUTO -> simulation.setAuto(true);
                    case MANUAL -> simulation.setAuto(false);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
