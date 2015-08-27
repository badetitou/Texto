package badetitou.texto.conversation;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import badetitou.texto.R;
import badetitou.texto.overview.Overview;
import badetitou.texto.Data.SMS;

public class Conversation extends AppCompatActivity {

    String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        phoneNumber = getIntent().getStringExtra(Overview.PHONE_NUMBER);
        ListView lv = (ListView) findViewById(R.id.list_SMS);
        setTitle(getIntent().getStringExtra(Overview.NAME));
        lv.setAdapter(new ListConversationAdapter(this, getSms(getIntent().getIntExtra(Overview.THREAD_ID, 87))));
    }

    private List<SMS> getSms(int thread_id) {
        ArrayList<SMS> SMSes = new ArrayList<>();
        Uri smsUri = Uri.parse("content://sms/");

        Cursor cursor = getContentResolver().query(smsUri, null, "thread_id = ?", new String[]{thread_id + ""}, "date ASC");
        while (cursor.moveToNext()) {
            SMSes.add(new SMS(cursor.getInt(cursor.getColumnIndexOrThrow("thread_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("address")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("type")),
                    cursor.getString(cursor.getColumnIndexOrThrow("body")),
                    cursor.getLong(cursor.getColumnIndexOrThrow("date"))));
        }
        return SMSes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    public void onSendSms(View view){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, ((EditText) findViewById(R.id.editText)).getText().toString(), null, null);
        Toast.makeText(this, "Send : " + phoneNumber,Toast.LENGTH_SHORT).show();
    }
}