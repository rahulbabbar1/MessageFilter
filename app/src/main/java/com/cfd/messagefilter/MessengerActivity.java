package com.cfd.messagefilter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import jp.bassaer.chatmessageview.models.Message;
import jp.bassaer.chatmessageview.models.User;
import jp.bassaer.chatmessageview.utils.ChatBot;
import jp.bassaer.chatmessageview.views.ChatView;

/**
 * Created by rahul on 1/2/17.
 */
public class MessengerActivity extends Activity {
    User me;
    User you;
    private String phone;
    private int category;
    private ChatView mChatView;
    String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent data = getIntent();
        phone = data.getStringExtra("phone");
        category = data.getIntExtra("cat", 111);
        //User id

        setUser();

        setChatview();

        fetchSms(category,phone);
        //Click Send Button


        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  sendSMS(phone, mChatView.getInputText());
//                //new message
//                Message message = new Message.Builder()
//                        .setUser(me)
//                        .setRightMessage(true)
//                        .setMessageText(mChatView.getInputText())
//                        .hideIcon(true)
//                        .build();
//                //Set to chat view
//                mChatView.send(message);
//                //Reset edit text
//                mChatView.setInputText("");
//
//                //Receive message
//                final Message receivedMessage = new Message.Builder()
//                        .setUser(you)
//                        .setRightMessage(false)
//                        .setMessageText(ChatBot.talk(me.getName(), message.getMessageText()))
//                        .build();
//
//                // This is a demo bot
//                // Return within 3 seconds
//                int sendDelay = (new Random().nextInt(4) + 1) * 1000;
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mChatView.receive(receivedMessage);
//                    }
//                }, sendDelay);
            }

        });

//        Intent smsData = getIntent();
//        ArrayList<SMS> smsList =  smsData.getParcelableArrayListExtra("smsList");
//        int size = 0;
//        if(smsList!=null){
//            size = smsList.size();
//        }
//        for(int i=0;i<size;i++){
//            SMS sd = smsList.get(i);
//            User user;
//            boolean side;
//            if(sd.getType()==1){
//                user = new User(sd.getType(), sd.getName(), BitmapFactory.decodeResource(getResources(), R.drawable.ic_user));;
//                side = false;
//                Log.d(TAG, "usertype is 1");
//            }
//            else {
//                user = new User(sd.getType(), "You", BitmapFactory.decodeResource(getResources(), R.drawable.ic_user));;
//                side = true;
//                Log.d(TAG, "usertype is 2");
//            }
//            Message message = new Message.Builder()
//                    .setUser(user)
//                    .setRightMessage(side)
//                    .setMessageText(sd.getBody())
//                    .hideIcon(true)
//                    .build();
//            //Set to chat view
//            mChatView.send(message);
//        }
    }

    private void setUser() {
        int myId = 0;

        String myName = "You";

        int yourId = 1;
        String yourName = "Emily";

        me = new User(myId, myName, null);
        you = new User(yourId, yourName, null);
    }

    private void setChatview() {
        mChatView = (ChatView) findViewById(R.id.chat_view);
        mChatView.setRightBubbleColor(Color.WHITE);
        mChatView.setLeftBubbleColor(Color.rgb(66,66,66));
        mChatView.setBackgroundColor(Color.rgb(48,48,48));
        mChatView.setSendButtonColor(Color.rgb(233,30,99));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.BLACK);
        mChatView.setLeftMessageTextColor(Color.rgb(200,200,200));
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.rgb(84,110,122));
        mChatView.setDateSeparatorColor(Color.rgb(113,135,145));
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);
    }

    private void fetchSms(int category,String phone){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<SMS> smses =
                realm.where(SMSCategory.class).equalTo("id", category)
                        .findFirst()
                        .getSmss()
                        .where()
                        .equalTo("number", phone)
                        .findAll();
        for (SMS sms: smses ) {
            boolean right = true;
            User user = me;
            if(sms.getType()==1){
                user = you;
                right = false;
            }
            Message message = new Message.Builder()
                        .setUser(user)
                        .setRightMessage(right)
                        .setMessageText(sms.getBody())
                        .hideIcon(true)
                        .build();
            mChatView.send(message);
        }
    }

    private void sendSMS(String phoneNumber, String message)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, SmsActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}
