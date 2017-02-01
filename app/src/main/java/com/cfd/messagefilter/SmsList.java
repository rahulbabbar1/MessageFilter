package com.cfd.messagefilter;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rahul on 1/2/17.
 */
public class SmsList extends ListActivity {
    String address;
    String TAG = SmsList.class.getSimpleName();
    public static ListAdapter listAdapter ;
    Map<String, List<SmsData> > convList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<SmsData> smsList = new ArrayList<SmsData>();
         convList = new HashMap<String, List<SmsData>>();
        listAdapter = new ListAdapter(this, smsList);
        setListAdapter(listAdapter);
        AllSmsLoader allSmsLoader = new AllSmsLoader(SmsList.this);
        Bundle b = new Bundle();
        getLoaderManager().initLoader(0,b,allSmsLoader);

        new Thread(new Runnable() {
            public void run() {

                // a potentially  time consuming task
            }
        }).start();

        //List<SmsData> smsList = new ArrayList<SmsData>();
        //Map<String, SmsData> smsList = new HashMap<String, SmsData>();
//        Map<String, List<SmsData>> convList =  new HashMap<String, List<SmsData>>();
//        Uri uri = Uri.parse("content://sms/inbox");
//        Cursor c= getContentResolver().query(uri, null, null ,null,null);
//        startManagingCursor(c);
//        // Read the sms data and store it in the list
//        if(c.moveToFirst()) {
//            for(int i=0; i < c.getCount(); i++) {
//                SmsData sms = new SmsData();
//                address = c.getString(c.getColumnIndexOrThrow("address")).toString();
//                sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
//                sms.setNumber(address);
//                Log.d(TAG, "onCreate() called with:"+ c.getString(c.getColumnIndexOrThrow("body")).toString() +" savedInstanceState = [" + c.getString(0) + "]");
//                sms.setId(c.getString(0));
//                List<SmsData> smsList;
//                if(convList.containsKey(address)){
//                    smsList = convList.get(address);
//                }
//                else {
//                    smsList = new ArrayList<SmsData>();
//                }
//                smsList.add(sms);
//                c.moveToNext();
//            }
//        }
//        c.close();

//        Uri uri = Telephony.Sms.Conversations.CONTENT_URI;
//        //String[] projection = {"Conversations._ID", "Conversations.ADDRESS", "Conversations.BODY"};
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//
//        while (cursor.moveToNext())
//        {
//            long key = cursor.getLong (cursor.getColumnIndex ("_id"));
//            long threadId = cursor.getLong (cursor.getColumnIndex ("thread_id"));
//            String address = cursor.getString (cursor.getColumnIndex ("address")); // phone #
//            long date = cursor.getLong (cursor.getColumnIndex ("date"));
//            String body = cursor.getString (cursor.getColumnIndex ("body"));
//
//            String q = String.format ("%04d %04d %10s %s %s",
//                    key, threadId, address,
//                    date,
//                    body == null ? "" : body.substring (0,Math.min(10,body.length()-1)));
//            Log.d(TAG, "onCreate() called with: savedInstanceState = [" + q + "]"); // simple wrapper for Log.d()
//        }
//
//        cursor.close();

        // Set smsList in the ListAdapter
//        Log.d(TAG," address = [" + address + "]");
//        if(convList.get(address)!=null)



    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String number = ((SmsData)getListAdapter().getItem(position)).getNumber();
        Intent i =  new Intent(SmsList.this,MessengerActivity.class);
        ArrayList<SmsData> smsList =(ArrayList<SmsData>)convList.get(number);
        i.putParcelableArrayListExtra("smsList",smsList);
        startActivity(i);
    }

    public static void showList(){
    }
}