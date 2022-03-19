package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String args[]) {
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
            try {
                OutputStream outStream = clientSocket.getOutputStream();
                PrintWriter outWriter = new PrintWriter(outStream, true);
                outWriter.println("418 I'm a Teapot");

                outWriter.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
