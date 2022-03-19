package client;

import common.SimulationStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket("127.0.0.1", 6666);
             InputStream inputStream = clientSocket.getInputStream();
             ObjectInputStream in = new ObjectInputStream(inputStream)
        ) {
            SimulationStatus status = (SimulationStatus) in.readObject();
            System.out.println(status.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
