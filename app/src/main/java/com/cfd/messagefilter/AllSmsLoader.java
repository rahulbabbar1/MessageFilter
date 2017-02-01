package com.cfd.messagefilter;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission_group.SMS;

/**
 * Created by rahul on 1/2/17.
 */
public class AllSmsLoader implements LoaderManager.LoaderCallbacks<Cursor> {
        private Context context;
    private static final String TAG = AllSmsLoader.class.getSimpleName();


        public AllSmsLoader(Context context) {
            this.context = context;
        }

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            final String SMS_ALL = "content://sms/";
            Uri uri = Uri.parse(SMS_ALL);
            String[] projection = new String[]{"_id", "thread_id", "address", "person", "body", "date", "type"};
            return new CursorLoader(context, uri, projection, null, null, "date desc");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
            Log.d(TAG, "load finished: ");
            List<String> phoneNumbers = new ArrayList<String>();
            Map<String, List<SmsData> > convList = new HashMap<String, List<SmsData>>();
            while (cursor.moveToNext()) {
                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                Log.d(TAG, "phoneNumber: "+ phoneNumber +" ");
                int type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
                if ((!phoneNumbers.contains(phoneNumber)) && (type != 3) && (phoneNumber.length()>=1)) {
                    String name = null;
                    String person = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                    String smsContent = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("date"))));
                    Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, phoneNumber);
                    ContentResolver cr = context.getContentResolver();
                    Cursor localCursor = cr.query(personUri,
                            new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                            null, null, null);//use phonenumber find contact name
                    if (localCursor.getCount() != 0) {
                        localCursor.moveToFirst();
                        name = localCursor.getString(localCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    }
                    Log.d(TAG, "person:" + person + "  name:" + name + "  phoneNumber:" + phoneNumber);
                    localCursor.close();
                    phoneNumbers.add(phoneNumber);
                    SmsData sms = new SmsData(name, phoneNumber, smsContent, type, date);

                    List<SmsData> sms_All;
                    if(convList.containsKey(phoneNumber)){
                        sms_All = convList.get(phoneNumber);
                    }
                    else {
                        sms_All = new ArrayList<SmsData>();
                    }
                    sms_All.add(sms);
                    convList.put(phoneNumber,sms_All);
                }
            }
            SmsList.listAdapter.updateList(Utility.parseList(convList));
            SmsList.listAdapter.notifyDataSetChanged();
            Log.d(TAG, "onLoadFinished() called with: cursorLoader = [" + cursorLoader + "], cursor = [" + cursor + "]");
            //simpleCursorAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> cursorLoader) {
            //simpleCursorAdapter.swapCursor(null);
        }
    }
