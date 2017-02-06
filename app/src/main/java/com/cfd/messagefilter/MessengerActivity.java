package com.cfd.messagefilter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.Random;

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
    private ChatView mChatView;
    String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        //User id
        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = "You";

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = "Emily";

        me = new User(myId, myName, myIcon);
        you = new User(yourId, yourName, yourIcon);

        mChatView = (ChatView) findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.cyan900));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //Click Send Button


        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new message
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(mChatView.getInputText())
                        .hideIcon(true)
                        .build();
                //Set to chat view
                mChatView.send(message);
                //Reset edit text
                mChatView.setInputText("");

                //Receive message
                final Message receivedMessage = new Message.Builder()
                        .setUser(you)
                        .setRightMessage(false)
                        .setMessageText(ChatBot.talk(me.getName(), message.getMessageText()))
                        .build();

                // This is a demo bot
                // Return within 3 seconds
                int sendDelay = (new Random().nextInt(4) + 1) * 1000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.receive(receivedMessage);
                    }
                }, sendDelay);
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
}
