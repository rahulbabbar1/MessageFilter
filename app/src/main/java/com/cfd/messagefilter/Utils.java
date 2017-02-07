package com.cfd.messagefilter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Chirag on 07-02-2017.
 */

class Utils {
    private final Context context;
    private final Realm realm;

    Utils(Context context) {
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    String getName(String phoneNumber) {
        Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, phoneNumber);
        ContentResolver cr = context.getContentResolver();
        Cursor localCursor = cr.query(personUri,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);//use phonenumber find contact name
        String name = null;
        if (localCursor != null) {
            if (localCursor.getCount() != 0) {
                localCursor.moveToFirst();
                name = localCursor.getString(localCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
            localCursor.close();
        }
        return name;
    }

    void saveToRealm(final SMS sms) {
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//            }
//        });
    }
}
