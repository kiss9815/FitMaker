package com.juntcompany.fitmaker;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juntcompany.fitmaker.Data.Structure.LoginRequest;
import com.juntcompany.fitmaker.Login.LoginActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler mHandler = new Handler(Looper.getMainLooper());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);


        if (PropertyManager.getInstance().getUserId() != null) {
            String userId = PropertyManager.getInstance().getUserId();
            String password = PropertyManager.getInstance().getPassword();

            try {
                NetworkManager.getInstance().login(getApplicationContext(), userId, password, new NetworkManager.OnResultListener<LoginRequest>() {
                    @Override
                    public void onSuccess(Request request, LoginRequest result) {

                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
    }
}
