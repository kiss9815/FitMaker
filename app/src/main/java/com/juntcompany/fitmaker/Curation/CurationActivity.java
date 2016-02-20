package com.juntcompany.fitmaker.Curation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.juntcompany.fitmaker.R;

public class CurationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curation);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

//        genderFragment = (CurationGenderFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_gender);

        Fragment f = new CurationGenderFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.curation_container, f);
        ft.commit();

    }




}
