package badetitou.texto.tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import badetitou.texto.Data.SMS;
import badetitou.texto.R;
import badetitou.texto.overview.Overview;

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
                Object[] pdus;
                try {
                    pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                        //Db operation
                        saveSmsDataToDefaulDB(context.getContentResolver(), msgs[i]);
                    }

                    // Create and display the notification
                    notification(context);
                } catch (Exception e){
                    Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show();
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

    private void notification(Context context){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.texto)
                        .setContentTitle(msg_from)
                        .setContentText(msgBody);
        Intent resultIntent = new Intent(context, Overview.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = 001;
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        mBuilder.setVibrate(new long[] {200,300,400,500});
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

}
