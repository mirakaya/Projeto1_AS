import HCP.Enums.SimulationState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class testeMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Socket s = new Socket("127.0.0.1", 9090);

        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(SimulationState.AUTO);

        while (true){

        }

    }

}
