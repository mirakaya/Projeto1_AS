package HCP.Entities;

import HCP.Enums.SimulationState;
import HCP.Monitors.Simulation.Simulation;
import HCP.gui.HCP_GUI;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * HCP Starter Thread
 */
public class TSimulation extends Thread {

    private final ServerSocket serverSocket = new ServerSocket(8080);
    private HCP_GUI gui;
    private Simulation simulation;

    public TSimulation() throws IOException {}

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            //ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            int[] simulationParameters = (int[]) inputStream.readObject();

            int adultCount = simulationParameters[0];
            int childCount = simulationParameters[1];
            int seatCount = simulationParameters[2];
            int maxEvaluationDelay = simulationParameters[3];
            int maxTreatmentDelay = simulationParameters[4];
            int maxPaymentDelay = simulationParameters[5];
            int maxMoveDelay = simulationParameters[6];
            SimulationState state = null;

            while (state != SimulationState.END) {
                state = (SimulationState) inputStream.readObject();

                switch (state) {
                    case START -> {
                        gui = new HCP_GUI();
                        simulation = new Simulation(
                                childCount, adultCount, seatCount,
                                maxEvaluationDelay, maxTreatmentDelay, maxPaymentDelay,
                                maxMoveDelay, gui
                        );
                        simulation.start();
                    }
                    case SUSPEND -> simulation.pause();
                    case RESUME -> simulation.resume();
                    case STOP -> simulation.stop();
                    case END -> {
                        simulation.stop();
                    }
                    case AUTO -> simulation.setAuto(true);
                    case MANUAL -> simulation.setAuto(false);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
