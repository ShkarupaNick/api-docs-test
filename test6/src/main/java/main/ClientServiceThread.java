package main;

import controller.ActionController;
import org.apache.log4j.Logger;
import parser.RequestParser;

import java.io.*;
import java.net.Socket;

/**
 * Created by nshkarupa on 05.02.2016.
 */
public class ClientServiceThread extends Thread {
   public static final Logger log  = Logger.getLogger(ClientServiceThread.class);

    Socket clientSocket;
    int clientID = -1;
    boolean running = true;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientID = i;
    }


    public void run() {

        System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            while (running) {
                String clientCommand = (String) input.readObject();
                System.out.println("Client Says :" + clientCommand);
                RequestParser parser = new RequestParser(clientCommand);
                parser.parse();
                log.debug(parser);
                ClientRepository.addClient(parser.getSenderPhone(), clientSocket);
                ActionController actionController = new ActionController(parser);
                actionController.executeAction();
                if (clientCommand.equalsIgnoreCase("quit")) {
                    running = false;
                    System.out.print("Stopping client thread for client : " + clientID);
                } else {
                    output.writeObject(actionController.getResult());
                    //log.debug("answer: " + actionController.getResult());
                    output.flush();
                }
            }
        } catch (ClassNotFoundException e) {
           log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
