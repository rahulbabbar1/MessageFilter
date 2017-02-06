package com.cfd.messagefilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rahul on 31/1/17.
 */
public class SmsReceiver extends BroadcastReceiver{
    private static final String TAG = SmsReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
//                if (msg.getDisplayMessageBody().contains("<>")) {
//                    //DBHandler db = new DBHandler(context);
//                    String str = msg.getDisplayMessageBody().substring(2);
////                    Schedule test;
////                    test = db.getSchedule(Integer.parseInt(str));
////
////                    String smsBody = test.getTimetable();
//
//                    android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
//                    //smsManager.sendTextMessage(msg.getOriginatingAddress(), null, smsBody, null, null);
//
//                } else if (msg.getDisplayMessageBody().contains("><")) {
//                    DBHandler db = new DBHandler(context);
//                    int i = Integer.parseInt(msg.getDisplayMessageBody().substring(2, 4));
//                    String str2 = msg.getDisplayMessageBody().substring(4);
//                    Schedule test;
//                    test = db.getSchedule(Integer.parseInt(str2));
//
//                    String smsBody = test.getTimetable();
//                    String newtest = smsBody.substring(0, i) + '1' + smsBody.substring(i + 1);
//                    test.setTimetable(newtest);
//                    db.updateSchedule(test);
//                    String strdate = "";
//                    String name=test.getName();
//                    int x = i % 24;
//                    int y = i / 24;
//                    if (y == 0) {
//                        strdate = "Today";
//                    } else if (y == 1) {
//                        strdate = "Tomorrow";
//                    } else if (y == 2) {
//                        strdate = "Day after tomorrow";
//                    }
//                    android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
//                    smsManager.sendTextMessage(msg.getOriginatingAddress(), null, "Your Booking is Done for " + " " + strdate + " " + "at" + " " + x + ":00 hrs" +" with "+name, null, null);
//
//
//                }


            }
        }
    }

}
