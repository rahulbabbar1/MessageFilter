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

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by rahul on 25/12/16.
 */

public class FragmentList extends Fragment {
    View recyclerView;
    String TAG = this.getClass().getSimpleName();
    CustomRecyclerAdapter customRecyclerAdapter;
    int category;

    public void setCategory(int category) {
        this.category = category;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "category:"+category);
        customRecyclerAdapter = new CustomRecyclerAdapter(category);
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
        recyclerView.setAdapter(customRecyclerAdapter);
    }

//    private String getDate(String time) {
//        if (time == null)
//            return "null";
//        Calendar calendar = Calendar.getInstance();
//        TimeZone tz = TimeZone.getDefault();
//        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        Log.d(TAG, "getDate() called with: time = [" + time + "]");
//        java.util.Date currenTimeZone = new java.util.Date(Long.parseLong(time));
//        return sdf.format(currenTimeZone);
//    }
}


