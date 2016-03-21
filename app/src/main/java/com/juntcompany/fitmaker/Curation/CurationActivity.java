package com.juntcompany.fitmaker.Curation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.juntcompany.fitmaker.Curation.Result.CurationResultFragment;
import com.juntcompany.fitmaker.R;

public class CurationActivity extends AppCompatActivity {

    public static final String CURATION_MESSAGE = "curation_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curation);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Intent intent = getIntent();
        boolean isResult = intent.getBooleanExtra(CURATION_MESSAGE, false);
        if(isResult == true){
            Fragment f = new CurationResultFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.curation_container, f);
            ft.commit();
        }else {

            Fragment f = new CurationQuestion1Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.curation_container, f);
            ft.commit();
        }
    }

    boolean isBackPressed = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        if(getSupportFragmentManager().findFragmentById(R.id.))
    }
}
