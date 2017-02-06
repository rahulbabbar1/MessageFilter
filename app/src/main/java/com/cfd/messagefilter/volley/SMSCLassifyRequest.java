package com.cfd.messagefilter.volley;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chirag on 06-02-2017.
 */

public class SMSCLassifyRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://smsclassifier.herokuapp.com";
    private Map<String, String> params;

    public SMSCLassifyRequest(String smses, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, errorListener);
        if (smses != null && smses.length()>0) {
            params = new HashMap<>();
            params.put("data", smses);
        } else {
            Log.e("ERROR", "No messages sent");
        }
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
