package badetitou.texto.overview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import badetitou.texto.Data.SMS;
import badetitou.texto.R;
import badetitou.texto.conversation.Conversation;


public class Overview extends AppCompatActivity {

    public static String THREAD_ID = "thread_id";
    public static String NAME = "name";
    public static String PHONE_NUMBER = "phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);


    }

    @Override
    protected void onResume(){
        super.onResume();
        final List<SMS> SMSes = getList();
        ListView lv = (ListView) findViewById(R.id.list_overview);
        lv.setAdapter(new ListOverviewAdapter(getBaseContext(), SMSes));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Conversation.class);
                intent.putExtra(THREAD_ID, SMSes.get(position).getThreadId());
                if (SMSes.get(position).getUser().compareTo("") == 0){
                    intent.putExtra(NAME, SMSes.get(position).getPhoneNumber());
                } else {
                    intent.putExtra(NAME, SMSes.get(position).getUser());
                }
                intent.putExtra(PHONE_NUMBER, SMSes.get(position).getPhoneNumber());
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), getString(R.string.lol), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<SMS> getList(){
        ArrayList<SMS> al = new ArrayList<>();

        Uri smsUri = Uri.parse("content://sms/");
        Cursor cursor = getContentResolver().query(smsUri,null,null,null,"date DESC");
        while (cursor.moveToNext()){
            al.add(new SMS(cursor.getInt(cursor.getColumnIndexOrThrow("thread_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("address")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("type")),
                    cursor.getString(cursor.getColumnIndexOrThrow("body")),
                    cursor.getLong(cursor.getColumnIndexOrThrow("date"))));
        }
        for (int i = 0;i<al.size();++i){
            for (int j = i+1;j<al.size();++j){
                if (al.get(i).getThreadId() == al.get(j).getThreadId()){
                    al.remove(j);
                    j--;
                }
            }
        }
        for (int i = 0;i<al.size();++i) {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(al.get(i).getPhoneNumber()));
            Cursor phone = getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if(phone.moveToNext())
                al.get(i).setUser(phone.getString(0));
        }
        return al;
    }
}
