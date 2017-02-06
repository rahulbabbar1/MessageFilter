package com.cfd.messagefilter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import eu.long1.spacetablayout.SpaceTabLayout;
import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    SpaceTabLayout tabLayout;
    String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        addCategoriesToRealm();
        AllSmsLoader allSmsLoader = new AllSmsLoader(this);
        getLoaderManager().initLoader(0,new Bundle(),allSmsLoader);
    }

    private void addCategoriesToRealm() {
        if (realm.where(SMSCategory.class).count() == 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    String[] categories = getResources().getStringArray(R.array.Categories);
                    for (int i = -1; i < categories.length-1; i++) {
                        SMSCategory smsCategory = realm.createObject(SMSCategory.class);
                        smsCategory.setCategoryId(i);
                        smsCategory.setCategoryName(categories[i]);
                        smsCategory.setSmss(new RealmList<SMS>());
                    }
                }
            });
        }

    }
}
