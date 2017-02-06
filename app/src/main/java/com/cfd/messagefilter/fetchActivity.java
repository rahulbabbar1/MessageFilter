package com.cfd.messagefilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rahul on 6/2/17.
 */
public class fetchActivity extends AppCompatActivity{
    private static final String TAG = fetchActivity.class.getSimpleName();
    private static Context mContext;
//    public static Map<String, List<SmsData>>[] convList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        convList = new Map[5];
//        for(int i=0;i<5;i++){
//            convList[i] = new HashMap<String, List<SmsData>>();
//            List<SmsData> test = new ArrayList<SmsData>();
//            Date dt = new Date();
//
//            Log.d(TAG, "date = [" + dt.toString() + "]");
//            SmsData smsData = new SmsData("rahul","12345678","hui",1,dt.toString());
//            test.add(smsData);
//            SmsData smsData2 = new SmsData("rahul","12345678","huahhai",2,dt.toString());
//            test.add(smsData2);
//            convList[i].put("rahul",test);
//            List<SmsData> test2 = new ArrayList<SmsData>();
//            Date dt2 = new Date();
//            Log.d(TAG, "date2 = [" + dt2.toString() + "]");
//            SmsData smsData3 = new SmsData("chirag","7676881","hqjhjui",1,dt2.toString());
//            test2.add(smsData3);
//            SmsData smsData4 = new SmsData("chirag","7676881","huahkakkhai",1,dt2.toString());
//            test2.add(smsData4);
//            convList[i].put("chirag",test2);
//        }
        mContext = this;
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        AllSmsLoader allSmsLoader = new AllSmsLoader(fetchActivity.this);
        Bundle b = new Bundle();
        getLoaderManager().initLoader(0,b,allSmsLoader);
    }

    public static void notifyDataLoaded(){
        Log.d(TAG, "notifyDataLoaded() called");
        Intent i = new Intent(mContext,MainActivity.class);
        mContext.startActivity(i);
    }

}
