package badetitou.texto.Data;

/**
 * Created by Benoit on 23/08/2015.
 */
public class SMS {
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
