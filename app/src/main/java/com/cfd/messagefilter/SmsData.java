package com.cfd.messagefilter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rahul on 1/2/17.
 */
public class SmsData implements Parcelable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // Number from witch the sms was send
    private String number;
    // SMS text body
    private String body;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String id;

    private String date;

    private String name;

    private int type;
//
//    protected int mData;

    private SmsData(Parcel in) {
        //mData = in.readInt();
        String[] array = new String[5];
        in.readStringArray(array);
        this.number = array[0];
        this.name = array[1];
        this.body = array[2];
        this.type = Integer.parseInt(array[3]);
        Log.d("test", "SmsData: "+array[4]);
        this.date = array[4];
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<SmsData> CREATOR
            = new Parcelable.Creator<SmsData>() {
        public SmsData createFromParcel(Parcel in) {
            return new SmsData(in);
        }

        public SmsData[] newArray(int size) {
            return new SmsData[size];
        }
    };

    public SmsData(String name, String phoneNumber, String smsContent, int type, String date) {
        this.number = phoneNumber;
        this.name = name;
        this.body = smsContent;
        this.date = date;
        this.type = type;
    }


    public String  getId() {
        return id;    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] array = {number,name,body,String.valueOf(type),date};
        dest.writeStringArray(array);
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", number);
            jsonObject.put("name", name);
            jsonObject.put("body", body);
            jsonObject.put("type", String.valueOf(type));
            jsonObject.put("date", date);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public SmsData(String data) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonObject!=null){
            try {
                this.number = jsonObject.getString("number");
                this.body = jsonObject.getString("body");
                this.name = jsonObject.getString("name");
                this.type = Integer.parseInt(jsonObject.getString("type"));
                this.date = jsonObject.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}

