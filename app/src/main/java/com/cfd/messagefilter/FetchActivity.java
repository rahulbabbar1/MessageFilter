package com.cfd.messagefilter;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cfd.messagefilter.models.SMS;
import com.cfd.messagefilter.models.SMSCategory;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by rahul on 6/2/17.
 */
public class FetchActivity extends AppCompatActivity {
    private Realm realm;
    private static final String TAG = FetchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);
        getPermission(new String[]{Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS,Manifest.permission.READ_CONTACTS,Manifest.permission.RECEIVE_SMS});
    }

    void init(){
        realm = Realm.getDefaultInstance();
        addCategoriesToRealm();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppPref", 0); // 0 - for private mode
        if (pref.getBoolean("FirstRun", true)) {
            AllSmsLoader allSmsLoader = new AllSmsLoader(FetchActivity.this);
            Bundle b = new Bundle();
            getLoaderManager().initLoader(0,b,allSmsLoader);
        } else {
            Classifier classifier = new Classifier(this);
            classifier.classifyAllDefaultCategoryMesssages();
        }
        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
        finish();
    }

    private void addCategoriesToRealm() {
        if (realm.where(SMSCategory.class).count() == 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    String[] categories = getResources().getStringArray(R.array.Categories);
                    for (int i = -1; i < categories.length - 1; i++) {
                        SMSCategory smsCategory = new SMSCategory();
                        smsCategory.setId(i);
                        smsCategory.setName(categories[i + 1]);
                        smsCategory.setSmss(new RealmList<SMS>());
                        realm.copyToRealm(smsCategory);
                    }
                }
            });
        }
    }

    private void getPermission(String[] permissions) {
        boolean isAllowed = true;
        ArrayList<String> permissionToBeAsked = new ArrayList<String>();
        for (String permission: permissions) {
            if (isPermissionAllowed(permission)) {
                Log.d(TAG, "if getPermission() called with: permission = [" + permission + "]");
            } else {
                Log.d(TAG, "else getPermission() called with: permission = [" + permission + "]");
                isAllowed = false;
                permissionToBeAsked.add(permission);
            }
        }
        if(isAllowed){
            Log.d(TAG, "getPermission() called with: permissions = [" + Arrays.toString(permissions) + "]");
            init();
        } else {
            String[] finalPermissions = new String[permissionToBeAsked.size()];
            finalPermissions = permissionToBeAsked.toArray(finalPermissions);
            requestPermission(finalPermissions);
        }
    }

    private boolean isPermissionAllowed(String permission) {

        int result = ContextCompat.checkSelfPermission(this, permission);
        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestPermission(String[] permission) {

//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//            //If the user has denied the permission previously your code will come to this block
//            //Here you can explain why you need this permission
//            //Explain here why you need this permission
////
//        }
        ActivityCompat.requestPermissions(this, permission, 123);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
                //Displaying a toast
                //Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                //Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }


}
