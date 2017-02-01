package com.cfd.messagefilter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by rahul on 1/2/17.
 */
public class SmsData implements Parcelable {

    // Number from witch the sms was send
    private String number;
    // SMS text body
    private String body;

    private String id;

    private Date date;

    private String name;

    private int type;

    protected int mData;

    private SmsData(Parcel in) {
        mData = in.readInt();
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

    public SmsData(String name, String phoneNumber, String smsContent, int type, Date date) {
        this.number = phoneNumber;
        this.name = name;
        this.body = smsContent;
        this.date = date;
        this.type = type;
    }


    public String  getId() {
        return id;
    }

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
        String[] array = {number,name,body,String.valueOf(type),date.toString()};
        dest.writeStringArray(array);
    }
}

