package com.cfd.messagefilter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by rahul on 6/2/17.
 */
public class fetchActivity extends AppCompatActivity {
    private Realm realm;
    private static final String TAG = fetchActivity.class.getSimpleName();

    //    public static Map<String, List<SmsData>>[] convList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppPref", 0); // 0 - for private mode
        if (pref.getBoolean("FirstRun", true)) {
            realm = Realm.getDefaultInstance();
            addCategoriesToRealm();
            AllSmsLoader allSmsLoader = new AllSmsLoader(fetchActivity.this);
            Bundle b = new Bundle();
            getLoaderManager().initLoader(0,b,allSmsLoader);
        }
        Classifier classifier = new Classifier(this);
        classifier.classifyAllDefaultCategoryMesssages();
        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
    }

    private void addCategoriesToRealm() {
        if (realm.where(SMSCategory.class).count() == 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    String[] categories = getResources().getStringArray(R.array.Categories);
                    for (int i = -1; i < categories.length - 1; i++) {
                        SMSCategory smsCategory = realm.createObject(SMSCategory.class);
                        smsCategory.setId(i);
                        smsCategory.setName(categories[i + 1]);
                        smsCategory.setSmss(new RealmList<SMS>());
                    }
                }
            });
        }
    }
}
