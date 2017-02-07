package com.cfd.messagefilter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by Chirag on 07-02-2017.
 */

class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {
    private int category;
    private RealmResults<SMS> smsRealmList;

    CustomRecyclerAdapter(int category) {
        Realm realm = Realm.getDefaultInstance();
        final SMSCategory smsCategory = realm.where(SMSCategory.class).equalTo("id", category).findFirst();
        smsRealmList = smsCategory.getSmss().where().findAllSorted("date", Sort.DESCENDING);
        smsRealmList.addChangeListener(new RealmChangeListener<RealmResults<SMS>>() {

            @Override
            public void onChange(RealmResults<SMS> element) {
                Log.d("CHANGE", "UPDATE");
                notifyDataSetChanged();
            }
        });
    }

//    private void fetchData(SMSCategory smsCategory){
//        smsRealmList = new RealmList<SMS>();
//        RealmResults<SMS> realmlist = ;
//        ArrayList<String> phoneNumbers = new ArrayList<String>();
//        for (SMS sms : realmlist) {
//            if(!phoneNumbers.contains(sms.getNumber())){
//                phoneNumbers.add(sms.getNumber());
//                smsRealmList.add(sms);
//            }
//        }
//        //smsRealmList = smsCategory.getSmss();
//    };


    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mNumber;
        private TextView mSms;
        private TextView mTime;
        ;
        private View mView;


        ViewHolder(View v) {
            super(v);
            mView = v.findViewById(R.id.card_view);
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
        final SMS sms = smsRealmList.get(position);
        holder.mNumber.setText(sms.getNumber());
        holder.mSms.setText(sms.getBody());
        holder.mTime.setText(sms.getDate().toString());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MessengerActivity.class);
                //intent.putExtra("id",sms.get_id());
                intent.putExtra("phone", sms.getNumber());
                intent.putExtra("cat", category);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return smsRealmList.size();
    }

}
