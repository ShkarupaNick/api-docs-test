package main;

import client.Client;
import db.PSQLConnector;
import org.postgresql.util.Base64;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Syma on 06.02.2016.
 */
public class Main{

        public static Client c;
    private static String responce;
    public static void main(String[] args) {
        Thread serverThread =  new Thread(new Server());
        serverThread.start();

/*
        c = new Client();
        try {
            c.getClientListener().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        c.sendMessage("action:get_new_messages;380997085725");

*/
        //c.sendMessage("action:set_received_status;a9b407e6-f5b3-11e5-a539-00ff44467ca9;a9b407e6-f5b3-11e5-a539-00ff44467ca9;a9b42ef8-f5b3-11e5-a53e-00ff44467ca9");
        //c.sendMessage("action:get_messages");
        //c.sendMessage("action:send_message;380997085725;380509125230;037e9136-f5a2-11e5-a419-00ff44467ca9");
    }


    public static synchronized void setResponce(String responce){
        System.out.println(responce);
        Main.responce = responce;
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile("(uuid:)([^;]+)(;)").matcher(responce);
        String s = "";
        while (m.find()) {
            responce=s+m.group(2)+";";
        }

        System.out.println("\n\n\n"+s);
    }
}
