package HCP.Main;

import HCP.Entities.TSimStarter;
import HCP.Enums.SimulationState;
import HCP.Monitors.Simulation.Simulation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

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

                first_cycle = false;
                TSimStarter simStarter = new TSimStarter((int[]) obj);
                simStarter.start();

            } else {
                receiveState((SimulationState) obj );

            }

        }

    }

    private static void receiveState(SimulationState obj) {
        SimulationState stateSimulation = obj;
    }


}
