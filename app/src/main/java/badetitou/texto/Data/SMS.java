package badetitou.texto.Data;

/**
 * Created by Benoit on 23/08/2015.
 */
public class SMS {

    public static final String ADDRESS = "address";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final String SEEN = "seen";

    public static final int MESSAGE_TYPE_INBOX = 1;
    public static final int MESSAGE_TYPE_SENT = 2;

    public static final int MESSAGE_IS_NOT_READ = 0;
    public static final int MESSAGE_IS_READ = 1;

    public static final int MESSAGE_IS_NOT_SEEN = 0;
    public static final int MESSAGE_IS_SEEN = 1;
    public static final String SMS_URI = "content://sms";


    private int threadId;
    private String body = "info";
    private String phoneNumber = "";
    private String user = "";
    /*
MESSAGE_TYPE_ALL    = 0;
MESSAGE_TYPE_INBOX  = 1;
MESSAGE_TYPE_SENT   = 2;
MESSAGE_TYPE_DRAFT  = 3;
MESSAGE_TYPE_OUTBOX = 4;
MESSAGE_TYPE_FAILED = 5; // for failed outgoing messages
MESSAGE_TYPE_QUEUED = 6;
*/
    private int type = 0;

    private long date;

    public SMS(int threadId, String phoneNumber, int type, String body, long date) {
        this.threadId = threadId;
        this.body = body;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.date = date;
    }

    public int getThreadId() {
        return threadId;
    }

    public long getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }
}
