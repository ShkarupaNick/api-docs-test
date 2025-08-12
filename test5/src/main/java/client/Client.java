package client;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 3/22/2016.
 */
public class Client {
    public static final Logger log = Logger.getLogger(Client.class);

    private Socket serverSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ClientListener clientListener;

    public ClientListener getClientListener() {
        return clientListener;
    }

    public Client() {
        try {
            serverSocket = new Socket("localhost", 8082);
            output = new ObjectOutputStream(serverSocket.getOutputStream());
            clientListener = new ClientListener(serverSocket);
            clientListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        try {
            output.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
