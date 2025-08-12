package db;

/**
 * Created by Administrator on 3/31/2016.
 */
public class SqlRepository {

    public final static  String sendMessageSQL = "insert into message_log (message_id, sender_phone_number, recipient_phone_number, sending_date, delivering_status)  values (?,?,?,current_timestamp,'NEW')";
    public final static  String getMessagesSQL = "select message_id, message_content from public.message";
}
