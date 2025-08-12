package parser;

import org.apache.log4j.Logger;
import org.postgresql.util.Base64;

/**
 * Created by Syma on 06.02.2016.
 */
public class RequestParser {
    public static final Logger log  = Logger.getLogger(RequestParser.class);
    private String action;
    private String inputString;
    private String senderPhone;
    private String recipientPhone;
    private String[] recivedMessagesId;
    private String messageId;



    public RequestParser(String inputString) {
        this.inputString = inputString;
    }

    public String[] getRecivedMessagesId() {
        return recivedMessagesId;
    }


    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String message) {
        this.messageId = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "RequestParser{" +
                "action='" + action + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", recipientPhone='" + recipientPhone + '\'' +
                ", message='" + messageId + '\'' +
                '}';
    }

    public void parse() {
        String[] inputData = inputString.split(";");
        if (inputString.startsWith("action:get_messages")) {
            action = "get_messages";

        } else if (inputString.startsWith("action:send_message")) {
            action = "send_message";
            senderPhone = inputData[1];
            recipientPhone = inputData[2];

            messageId = inputData[3];

        } else if (inputString.startsWith("action:get_new_messages")){
            action = "get_new_messages";
            recipientPhone=inputString.split(";")[1];
        } else if (inputString.startsWith("action:set_received_status")){
            action = "set_received_status";
            recivedMessagesId = inputString.replace("action:set_received_status;","").split(";");
        }
    }


    private String validatePhoneNumber(){

        //TODO написать валидайию телефонов
      /*  try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil
                    .getInstance();
            Phonenumber phNumberProto =      phoneUtil.parse(senderPhone, "UA");
            // check if the number is valid
            boolean isValid = phoneUtil.isValidNumber();
        } catch (NumberParseException e) {
            log.error(e.getMessage());
        }*/

        return null;
    }
}
