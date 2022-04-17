import HCP.Enums.SimulationState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class Main {

    //connection via sockets - tested

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        int port = 8080;
        ServerSocket ss = new ServerSocket(port);
        Socket socket = ss.accept();
        ObjectInputStream is;

        int num_msg = 0;

        while (true) {
            System.out.println("Server Connected");
            is = new ObjectInputStream(socket.getInputStream());
            Object obj = (Object) is.readObject();
            System.out.println("Obj - " + obj);
            num_msg ++;
        }

        /*Socket s = new Socket("127.0.0.1", 9090);

        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(SimulationState.AUTO);

        while (true){

        }
        */


        /*System.out.println("Accepting connections...");

        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

        while (true) {*/


            //ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            /*int[] info = (int[]) is.readObject();

            for (int i = 0; i < info.length; i++) {
                System.out.println(info[i]);
            }*/

           /* System.out.println(is);

        }*/


    }
}
