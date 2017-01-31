package com.cfd.messagefilter;

/**
 * Created by rahul on 1/2/17.
 */
public class SmsData {

    // Number from witch the sms was send
    private String number;
    // SMS text body
    private String body;

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
