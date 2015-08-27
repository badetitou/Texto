package badetitou.texto.tools;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import badetitou.texto.Data.SMS;

/**
 * Texto
 *
 * Created by Benoit on 27/08/2015.
 */
public class SmsReceiver extends BroadcastReceiver {

    private String msg_from;
    private String msgBody;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_DELIVER")) {
            // récupérer SMS
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;

            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                try {
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                        //Db operation
                        saveSmsDataToDefaulDB(context.getContentResolver(), msgs[i]);
                    }
                } catch (Exception e){
                    Toast.makeText(context, "Unknown eroor", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    private void saveSmsDataToDefaulDB( ContentResolver contentResolver, SmsMessage sms )
    {
        ContentValues values = new ContentValues();
        values.put( SMS.ADDRESS, sms.getOriginatingAddress());
        values.put( SMS.DATE, sms.getTimestampMillis());
        values.put( SMS.READ, SMS.MESSAGE_IS_NOT_READ);
        values.put( SMS.STATUS, sms.getStatus());
        values.put( SMS.TYPE, SMS.MESSAGE_TYPE_INBOX);
        values.put( SMS.SEEN, SMS.MESSAGE_IS_NOT_SEEN);
        values.put(SMS.BODY, sms.getMessageBody());
        contentResolver.insert( Uri.parse(SMS.SMS_URI), values);
    }

}
