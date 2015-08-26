package badetitou.texto.conversation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Telephony;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import badetitou.texto.Data.SMS;
import badetitou.texto.R;

/**
 * Created by Benoit on 23/08/2015.
 */
public class ListConversationAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<SMS> SMSes;
    private Context context;

    public ListConversationAdapter(Context context, List<SMS> SMSes) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.SMSes = SMSes;
    }

    @Override
    public int getCount() {
        return SMSes.size();
    }

    @Override
    public Object getItem(int position) {
        return SMSes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_sms, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.body = (TextView) view.findViewById(R.id.text_sms);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        SMS sms = SMSes.get(position);
        viewHolder.body.setText(sms.getBody());

        if (sms.getType() == Telephony.Sms.MESSAGE_TYPE_INBOX){
            viewHolder.body.setBackground(context.getResources().getDrawable(R.drawable.bubble_a));
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)viewHolder.body.getLayoutParams();
            params.gravity = Gravity.START | Gravity.CENTER;
            viewHolder.body.setLayoutParams(params);
        } else {
            viewHolder.body.setBackground(context.getResources().getDrawable(R.drawable.bubble_b));
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)viewHolder.body.getLayoutParams();
            params.gravity = Gravity.END | Gravity.CENTER;
            viewHolder.body.setLayoutParams(params);
        }
        return view;
    }

    private class ViewHolder {
        public TextView body;
    }
}