package HCP.Main;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

//        Thread entry = new Thread(); // SocketServer instaciado na thread
//        entry.start();
//        entry.join();


//        MLogger log = null;
//
//        int port = 8080;
//        ServerSocket ss = new ServerSocket(port);
//        Socket socket = ss.accept();
//        ObjectInputStream is;
//
//        boolean first_cycle = true;
//
//        while (true) {
//            System.out.println("Server Connected");
//            is = new ObjectInputStream(socket.getInputStream());
//            Object obj = (Object) is.readObject();
//            System.out.println("Obj - " + obj);
//
//            if (first_cycle == true) {
//
//                int [] data = (int[]) obj;
//
//                first_cycle = false;
//                log = new MLogger(data[0] + data[1]);
//
//                TSimStarter simStarter = new TSimStarter(data, log);
//                simStarter.start();
//
//            } else {
//                SimulationState stateSimulation = (SimulationState) obj;
//                log.createContent( stateSimulation.toString(), AvailableHalls.STT);
//
//            }
//
//        }

    }


}
