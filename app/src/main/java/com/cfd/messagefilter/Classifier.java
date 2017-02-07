package com.cfd.messagefilter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;
import com.cfd.messagefilter.volley.SMSClassifyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Chirag on 07-02-2017.
 */

class Classifier {
    private Context context;
    private Realm realm;

    Classifier(Context context) {
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    void classifyAllDefaultCategoryMesssages() {
        RealmList<SMS> smss = realm.where(SMSCategory.class).equalTo("id", -1).findFirst().getSmss();
        if (smss.size() > 0) {
            sendRequest(convertSmsesToString(smss));
        } else {
            Toast.makeText(context, "No messages found", Toast.LENGTH_SHORT).show();
        }
    }

    private String convertSmsesToString(RealmList<SMS> smses) {
        JSONArray smss = new JSONArray();
        for (SMS sms : smses) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", sms.get_id());
                jsonObject.put("body", sms.getBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            smss.put(jsonObject);
        }
        return smss.toString();
    }

    private void sendRequest(String smses) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Response", response);
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject jsonObject = jsonResponse.getJSONObject(i);
                        final int predictedCat = jsonObject.getInt("predicted_cat");
                        final int _id = jsonObject.getInt("id");
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                SMS sms = realm.where(SMS.class).equalTo("_id", _id).findFirst();
                                RealmList<SMS> defaultCategoryList = realm.where(SMSCategory.class).equalTo("id", -1).findFirst().getSmss();
                                boolean check = defaultCategoryList.remove(sms);
                                Log.d("Check", "bool " + check);
                                if (check) {
                                    RealmList<SMS> categoryList = realm.where(SMSCategory.class).equalTo("id", predictedCat).findFirst().getSmss();
                                    categoryList.add(sms);
                                }
                            }
                        });
                    }
                    if (MainActivity.progressBar != null) {
                        MainActivity.progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    if (MainActivity.progressBar != null) {
                        MainActivity.progressBar.setVisibility(View.GONE);
                    }
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (MainActivity.progressBar != null) {
                    MainActivity.progressBar.setVisibility(View.GONE);
                }
                Toast.makeText(context,"Error in loading", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        };
        if (MainActivity.progressBar != null) {
            MainActivity.progressBar.setVisibility(View.VISIBLE);
        }
        SMSClassifyRequest smsClassifyRequest = new SMSClassifyRequest(smses, responseListener, errorListener);
        smsClassifyRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(smsClassifyRequest);
    }
}
