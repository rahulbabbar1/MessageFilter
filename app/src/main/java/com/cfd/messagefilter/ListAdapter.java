package com.cfd.messagefilter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rahul on 1/2/17.
 */
public class ListAdapter extends ArrayAdapter<SmsData> {

    // List context
    private final Context context;
    // List values
    private static List<SmsData> smsList;

    public ListAdapter(Context context, List<SmsData> smsList) {
        super(context, R.layout.activity_main, smsList);
        this.context = context;
        this.smsList = smsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_main, parent, false);

        TextView senderNumber = (TextView) rowView.findViewById(R.id.smsNumberText);
        senderNumber.setText(smsList.get(position).getNumber());

        return rowView;
    }

    public static void updateList(List<SmsData> newlist) {
        smsList.clear();
        smsList.addAll(newlist);
    }
}

