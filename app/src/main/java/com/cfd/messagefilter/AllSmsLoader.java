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

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;
import com.cfd.messagefilter.volley.SMSCLassifyRequest;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by rahul on 1/2/17.
 */
class AllSmsLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private Context context;
    private Realm realm;
    private RealmList<SMS> smss;
    AllSmsLoader(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        realm = Realm.getDefaultInstance();
        smss = realm.where(SMSCategory.class).equalTo("name","Default").findFirst().getSmss();
        final String SMS_ALL = "content://sms/";
        Uri uri = Uri.parse(SMS_ALL);
        String[] projection = new String[]{"_id", "thread_id", "address", "person", "body", "date", "type"};
        return new CursorLoader(context, uri, projection, null, null, "date desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        while (cursor.moveToNext()) {
            final String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            final int type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
            if ((type != 3) && (phoneNumber.length() >= 1)) {
                String name = null;
                //String person = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                final int _id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                final int threadId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("thread_id")));
                final String smsContent = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                final Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("date"))));
                Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, phoneNumber);
                ContentResolver cr = context.getContentResolver();
                Cursor localCursor = cr.query(personUri,
                        new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);//use phonenumber find contact name
                if ( localCursor != null ) {
                    if (localCursor.getCount() != 0) {
                        localCursor.moveToFirst();
                        name = localCursor.getString(localCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    }
                    localCursor.close();
                }
                final String finalName = name;
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        SMS sms = realm.createObject(SMS.class);
                        sms.setNumber(phoneNumber);
                        sms.set_id(_id);
                        sms.setBody(smsContent);
                        sms.setDate(date);
                        sms.setThreadId(threadId);
                        sms.setName(finalName);
                        sms.setType(type);
                        smss.add(sms);
                    }
                });
            }
        }
        Classifier classifier = new Classifier(context);
        classifier.classifyAllDefaultCategoryMesssages();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        //simpleCursorAdapter.swapCursor(null);
    }
}