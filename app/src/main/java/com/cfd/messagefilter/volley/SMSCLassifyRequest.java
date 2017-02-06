package com.cfd.messagefilter.volley;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chirag on 06-02-2017.
 */

public class SMSCLassifyRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://smsclassifier.herokuapp.com";
    private Map<String, String> params;

    public SMSCLassifyRequest(List<String> smses, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, errorListener);
        if (smses.size()>0) {
            params = new HashMap<>();
            JSONArray smss = new JSONArray();
            for (String sms:smses) {
                smss.put(sms);
            }
            params.put("data", smss.toString());
        } else {
            Log.e("ERROR", "No messages sent");
        }
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
