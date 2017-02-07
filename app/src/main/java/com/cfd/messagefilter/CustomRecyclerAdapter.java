package com.cfd.messagefilter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;

/**
 * Created by Chirag on 07-02-2017.
 */

class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private RealmList<SMS> smsRealmList;

    CustomRecyclerAdapter(int category) {
        Realm realm = Realm.getDefaultInstance();
        SMSCategory smsCategory = realm.where(SMSCategory.class).equalTo("id", category).findFirst();
        smsRealmList = smsCategory.getSmss();
        smsCategory.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                notifyDataSetChanged();
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mNumber;
        private TextView mSms;
        private TextView mTime;


        ViewHolder(View v) {
            super(v);
            mNumber = (TextView) v.findViewById(R.id.number);
            mSms = (TextView) v.findViewById(R.id.sms);
            mTime = (TextView) v.findViewById(R.id.time);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.link_view, parent, false);
        return (new ViewHolder(v));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SMS sms = smsRealmList.get(position);
        holder.mNumber.setText(sms.getNumber());
        holder.mSms.setText(sms.getBody());
        holder.mTime.setText(sms.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return smsRealmList.size();
    }

}