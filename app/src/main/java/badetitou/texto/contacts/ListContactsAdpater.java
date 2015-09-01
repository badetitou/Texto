package badetitou.texto.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import badetitou.texto.Data.Contact;
import badetitou.texto.R;

/**
 * Created by badetitou on 01/09/2015.
 */
public class ListContactsAdpater extends BaseAdapter {

    private List<Contact> contacts;
    private LayoutInflater inflater;


    public ListContactsAdpater(List<Contact> contacts, LayoutInflater inflater) {
        this.contacts = contacts;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_contacts, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.username = (TextView) view.findViewById(R.id.contacts_name);
            viewHolder.phoneNumber = (TextView) view.findViewById(R.id.contacts_number);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.phoneNumber.setText(contacts.get(position).getPhoneNumber());
        viewHolder.username.setText(contacts.get(position).getName());

        return view;
    }

    private class ViewHolder {
        public TextView username;
        public TextView phoneNumber;
    }
}
