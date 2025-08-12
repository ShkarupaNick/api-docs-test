package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Syma on 06.02.2016.
 */
public class Server implements Runnable {

    private final static int PORT = 8082;
    private Socket connection;
    private ServerSocket server;

    public void run() {
        System.out.println("main.Server Started!!!");
        try {
            server = new ServerSocket(PORT, 10);
            int id = 0;
            while (true) {
                Socket clientSocket = server.accept();
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
                cliThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
