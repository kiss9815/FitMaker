package com.juntcompany.fitmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class SpecificCurriculumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_curriculum);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));


    }


}
