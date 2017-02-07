package com.cfd.messagefilter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class SpamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spam);
        FragmentList frag = new FragmentList();
        frag.setCategory(0);
        getSupportFragmentManager().beginTransaction().add(R.id.container, frag, "SPAM").commit();
    }
}
