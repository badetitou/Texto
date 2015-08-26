package badetitou.texto.Data;

/**
 * Created by Benoit on 23/08/2015.
 */
public class SMS {
    private int threadId;
    private String body = "info";
    private String phoneNumber = "";
    private String user = "";
    private long date;

    public SMS(int threadId, String phoneNumber, String body, long date){
        this.threadId = threadId;
        this.body = body;
        this.phoneNumber = phoneNumber;
        this.date = date;
    }

    public int getThreadId() {
        return threadId;
    }

    public long getDate(){
        return date;
    }

    public String getBody(){
        return body;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getUser(){ return user; }

    public void setUser(String user){
        this.user = user;
    }
}
