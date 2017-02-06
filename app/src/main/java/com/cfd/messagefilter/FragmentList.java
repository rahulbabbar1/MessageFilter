package com.cfd.messagefilter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by rahul on 25/12/16.
 */

public class FragmentList extends Fragment {
    View recyclerView;
    String TAG = this.getClass().getSimpleName();
    ArrayList<SmsData> arrayList;
    MyAdapter myAdapter;
    int category;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getInt("category");
        //arrayList = Utility.parseArrayList(fetchActivity.convList[category]);
        //Collections.reverse(arrayList);

        myAdapter = new MyAdapter(arrayList);

//        DbListener dbListener = new DbListener() {
//            @Override
//            public void onDataChanged() {
//                sqlDataChanged();
//                Log.d(TAG, "onDataChanged() "+ category +"called");
//            }
//
//            @Override
//            public void onError(Throwable error) {
//
//            }
//        };

        // fix_this MainActivity.mydb.setOnChangedListener(category,dbListener);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent, container, false);

        recyclerView = rootView.findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        return rootView;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(myAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mNumber;
            public TextView mSms;
            public TextView mTime;


            public ViewHolder(View v) {
                super(v);
                mNumber = (TextView) v.findViewById(R.id.number);
                mSms = (TextView) v.findViewById(R.id.sms);
                mTime = (TextView) v.findViewById(R.id.time);
            }
        }

        public MyAdapter(ArrayList myDataset) {
            mDataset = myDataset;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.link_view, parent, false);
            return (new ViewHolder(v));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            SmsData mSmsData = new SmsData(mDataset.get(position).toString());
            Log.d(TAG, "onBindViewHolder() called with: mSmsData.getId() = [" + mSmsData.getId() + "], mSmsData.getBody() = [" + mSmsData.getBody() + "] mSmsData.getDate() " + mSmsData.getDate() );
            holder.mNumber.setText(mSmsData.getId());
            holder.mSms.setText(mSmsData.getBody());
            holder.mTime.setText(getDate(mSmsData.getDate().toString()));
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public void swap(ArrayList<SmsData> datas){
            mDataset.clear();
            mDataset.addAll(datas);
            notifyDataSetChanged();
        }
    }

    private String getDate(String time) {
        if(time==null)
            return "null";
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Log.d(TAG, "getDate() called with: time = [" + time + "]");
        java.util.Date currenTimeZone=new java.util.Date(Long.parseLong(time));
        return  sdf.format(currenTimeZone);
    }

    private void sqlDataChanged(){
        // fix_this arrayList = MainActivity.mydb.getAllData(category);
        Collections.reverse(arrayList);
        Log.d(TAG, "onDataChanged() dblistener called");
        myAdapter.swap(arrayList);
    }
}


