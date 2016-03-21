package com.juntcompany.fitmaker;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.juntcompany.fitmaker.Curation.CurationActivity;
import com.juntcompany.fitmaker.Data.JoinRequest;
import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Data.Structure.LoginRequest;
import com.juntcompany.fitmaker.Login.LoginActivity;
import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      //  int a = PropertyManager.getInstance().getCurationType();
      //  Toast.makeText(getApplicationContext(), "큐레이션 id" + a, Toast.LENGTH_SHORT ).show();
//////////////////////////////////////테스트
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(intent);
//////////////////////////////////////테스트

        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Handler mHandler = new Handler(Looper.getMainLooper());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PropertyManager.getInstance().getUserId() != null && !PropertyManager.getInstance().getUserId().equals("")) {  // 이미 로컬 로그인 한 경우
                    String userId = PropertyManager.getInstance().getUserId();
                    String password = PropertyManager.getInstance().getPassword();
                    Log.i("preference_id", "id " + userId+ ", password " + password);
                    try {
                        NetworkManager.getInstance().login(getApplicationContext(), userId, password, new NetworkManager.OnResultListener<LoginRequest>() {
                            @Override
                            public void onSuccess(Request request, LoginRequest result) {
                                // int curationId = PropertyManager.getInstance().getCurationType();

//                                if (curationId == 0) { // 큐레이션이 없으면
//                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //// ......
//                                    startActivity(intent);
//                                    finish();
//                                } else { // 로그인을 했었고 큐레이션을 한 상태면

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class); ////
                                startActivity(intent);
                                finish();

                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                } else if (accessToken == null) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(accessToken != null){
                    try {
                        NetworkManager.getInstance().facebookLogin(getApplicationContext(), accessToken.getToken(), new NetworkManager.OnResultListener<JoinRequest>() {
                            @Override
                            public void onSuccess(Request request, JoinRequest result) {
                                //int curationId = PropertyManager.getInstance().getCurationType();
//                                if (curationId == 0) { // 큐레이션이 없으면
//                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else { // 로그인을 했었고 큐레이션을 한 상태면
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                //}
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                } else if(PropertyManager.getInstance().getUserId().equals("")){ // 로그아웃을 하면 preference에 "" 을 저장
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }else { // 아무 로그인도 안한경우
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }


            }
        }, 1000);



    }
}
