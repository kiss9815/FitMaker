package com.juntcompany.fitmaker.Login;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.juntcompany.fitmaker.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
  //                .addToBackStack(null) //빽하면 이 프래그먼트가 나옴..
                    .commit();
        }

    }




}
