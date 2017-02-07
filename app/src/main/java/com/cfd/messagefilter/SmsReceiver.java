package com.cfd.messagefilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import java.util.Date;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by rahul on 31/1/17.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent recieved: " + intent.getAction());
        if (Objects.equals(intent.getAction(), SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages;
                if (pdus != null) {
                    String msgBody = "";
                    messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        if (messages[i] != null) {
                            msgBody += messages[i].getMessageBody();
                        }
                    }
                    if (messages.length > 0) {
                        final String phoneNumber = messages[0].getOriginatingAddress();
                        Realm realm = Realm.getDefaultInstance();
                        final Utils utils = new Utils(context);
                        final String finalMsgBody = msgBody;
                        final RealmList<SMS> defaultCategoryList = realm.where(SMSCategory.class).equalTo("id", -1).findFirst().getSmss();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                SMS sms = realm.createObject(SMS.class);
                                sms.setNumber(phoneNumber);
                                int maxId = realm.where(SMS.class).max("_id").intValue() + 1;
                                sms.set_id(maxId);
                                sms.setBody(finalMsgBody);
                                sms.setDate(new Date());
                                sms.setName(utils.getName(phoneNumber));
                                sms.setType(1);
                                defaultCategoryList.add(sms);
//                                utils.saveToRealm(sms);
                            }
                        });

                    }
                }
            }
        }
    }
}
