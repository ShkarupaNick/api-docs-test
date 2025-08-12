package main;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by nshkarupa on 05.02.2016.
 */
public class ClientRepository {
    private static HashMap<String, Socket> socketMap = new HashMap<String, Socket>();


    public static void addClient(String phone, Socket socket) {
        if (socketMap.get(phone) == null) {
            socketMap.put(phone, socket);
        }
    }

    public static void deleteClient(String phone) {
        if (socketMap.get(phone) != null) {
            socketMap.remove(phone);
        }
    }

    public static Socket getClient(String phone) {
        return socketMap.get(phone);
    }

    public static int getCount() {
        return socketMap.size();
    }

}
