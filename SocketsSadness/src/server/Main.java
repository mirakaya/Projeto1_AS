package server;

import common.SimulationStatus;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            ServerSocket socketServer = new ServerSocket(6666);
            System.out.println("Starting the Devils Tea Server!");
            while(true) {
                (new ClientThread(socketServer.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static class ClientThread extends Thread {

        private final Socket clientSocket;

        public ClientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    clientSocket;
                    OutputStream outStream = clientSocket.getOutputStream();
                    ObjectOutputStream out = new ObjectOutputStream(outStream)
            ) {
                out.writeObject(SimulationStatus.RUNNING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
