package com.cfd.messagefilter;

import java.util.Date;

/**
 * Created by rahul on 1/2/17.
 */
public class SmsData {

    // Number from witch the sms was send
    private String number;
    // SMS text body
    private String body;

    private String id;

    private Date date;

    private String name;

    private int type;



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

}
