package controller;

import db.PSQLConnector;
import db.SqlRepository;
import org.apache.log4j.Logger;
import parser.RequestParser;

import java.sql.*;
import java.util.UUID;

/**
 * Created by Syma on 06.02.2016.
 */
public class ActionController {
    public static final Logger log = Logger.getLogger(ActionController.class);
    private RequestParser parser;
    private String action;
    private String result;


    public ActionController(RequestParser p) {
        this.parser = p;
        this.action = p.getAction();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void executeAction() {
        PSQLConnector c = new PSQLConnector();
        Connection connection = c.getConnection();
        StringBuilder stringBuilder = new StringBuilder("");


        switch (action) {
            case "send_message":
                try {
                    PreparedStatement stm = connection.prepareStatement(SqlRepository.sendMessageSQL);
                    stm.setObject(1, UUID.fromString(parser.getMessageId()));
                    stm.setString(2, parser.getSenderPhone());
                    stm.setString(3, parser.getRecipientPhone());
                    result = "result:" + stm.executeUpdate() + ";";
                    stm.close();
                } catch (SQLException e) {
                    result = e.getMessage();
                }

                break;
            case "get_messages":
                try {
                    ResultSet resultSet = c.execQuery(SqlRepository.getMessagesSQL);
                    log.debug("SQL --> " +SqlRepository.getMessagesSQL);
                    stringBuilder.append("action:get_messages;");
                    while (resultSet.next()) {
                        stringBuilder.append("{").append(resultSet.getString(1)).append(";");
                        stringBuilder.append(resultSet.getString(2)).append("}");

                    }
                    result = stringBuilder.toString().equals("") ? "null" : stringBuilder.toString();
                    log.debug(result);
                } catch (SQLException e) {
                    result = e.getMessage();
                }
                break;
            case "get_new_messages":
                try {
                    ResultSet resultSet = c.execQuery("   select uuid, sender_phone_number, recipient_phone_number, sending_date, m.message_content from message_log ml \n" +
                            "    join message m on m.message_id = ml.message_id and upper(ml.delivering_status) = 'NEW' and recipient_phone_number = '" + parser.getRecipientPhone() + "'");
                    log.debug("SQL -->    select sender_phone_number, recipient_phone_number, sending_date, m.message_content from message_log ml \njoin message m on m.message_id = ml.message_id and upper(ml.delivering_status) = 'NEW'");
                    stringBuilder = new StringBuilder("");
                    while (resultSet.next()) {
                        stringBuilder.append("{uuid:").append(resultSet.getString(1)).append(";");
                        stringBuilder.append("sender_phone_number:").append(resultSet.getString(2)).append(";");
                        stringBuilder.append("sending_date:").append(resultSet.getString(4)).append(";");
                        stringBuilder.append("message_content:").append(resultSet.getString(5)).append("}");
                    }
                    result = stringBuilder.toString();
                } catch (SQLException e) {
                    result = e.getMessage();
                }
                break;
            case "set_received_status":
                try {

                    String sql = "update message_log  set delivering_status = 'RECEIVED' where uuid  in (";


                    StringBuilder b = new StringBuilder(sql);
                    for (String s: parser.getRecivedMessagesId()) {
                        b.append("'").append(s).append("'::uuid,");
                    }
                    log.info(b.substring(0,b.length()-1)+")");

                    result = "result:" + c.execQuery(b.substring(0,b.length()-1)+")") + ";";
                } catch (SQLException e) {
                    result = e.getMessage();
                }
                break;
        }
        c.close();
    }
}
