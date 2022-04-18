package HCP.Main;

import HCP.Entities.TSimStarter;
import HCP.Enums.AvailableHalls;
import HCP.Enums.HCPOrders;
import HCP.Enums.PatientEvaluation;
import HCP.Enums.SimulationState;
import HCP.Monitors.MLogger;
import HCP.Monitors.SendToHCP_GUI.ISendToHCP_GUI;
import HCP.Monitors.SendToHCP_GUI.MSendToHCP_GUI;
import HCP.Monitors.Simulation.Simulation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {




        MLogger log = null;

        int port = 8080;
        ServerSocket ss = new ServerSocket(port);
        Socket socket = ss.accept();
        ObjectInputStream is;

        boolean first_cycle = true;

        while (true) {
            System.out.println("Server Connected");
            is = new ObjectInputStream(socket.getInputStream());
            Object obj = (Object) is.readObject();
            System.out.println("Obj - " + obj);

            if (first_cycle == true) {

                int [] data = (int[]) obj;

                first_cycle = false;
                log = new MLogger(data[0] + data[1]);

                TSimStarter simStarter = new TSimStarter(data, log);
                simStarter.start();

            } else {
                SimulationState stateSimulation = (SimulationState) obj;
                log.createContent( stateSimulation.toString(), AvailableHalls.STT);

            }

        }

    }


}
