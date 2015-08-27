package badetitou.texto.overview;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import badetitou.texto.Data.SMS;
import badetitou.texto.R;

/**
 * Texto
 * Created by Benoit on 23/08/2015.
 */
public class ListOverviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<SMS> SMSes;

    public ListOverviewAdapter(Context context, List<SMS> SMSes) {
        inflater = LayoutInflater.from(context);
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
            view = inflater.inflate(R.layout.list_overview_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.user = (TextView) view.findViewById(R.id.user);
            viewHolder.body = (TextView) view.findViewById(R.id.body);
            viewHolder.date = (TextView) view.findViewById(R.id.date);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        SMS sms = SMSes.get(position);
        viewHolder.body.setText(sms.getBody());
        if (sms.getUser().compareTo("") == 0) {
            viewHolder.user.setText(sms.getPhoneNumber());
        } else {
            viewHolder.user.setText(sms.getUser());
        }
        viewHolder.date.setText(DateUtils.getRelativeTimeSpanString(sms.getDate(), new Date().getTime(), DateUtils.DAY_IN_MILLIS));

        return view;
    }

    private class ViewHolder {
        public TextView user, body, date;
    }
}
