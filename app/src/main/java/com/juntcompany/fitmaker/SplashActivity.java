package com.juntcompany.fitmaker;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.juntcompany.fitmaker.Curation.CurationActivity;
import com.juntcompany.fitmaker.Data.JoinRequest;
import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Data.Structure.LoginRequest;
import com.juntcompany.fitmaker.GCM.RegistrationIntentService;
import com.juntcompany.fitmaker.Login.LoginActivity;
import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;


public class SplashActivity extends AppCompatActivity {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                doRealStart();
            }
        };
        setUpIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
        }
    }

    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationToken();
            if (!regId.equals("")) {
                doRealStart();
            } else {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void doRealStart() {
        //  int a = PropertyManager.getInstance().getCurationType();
        //  Toast.makeText(getApplicationContext(), "큐레이션 id" + a, Toast.LENGTH_SHORT ).show();
//////////////////////////////////////테스트
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(intent);
//////////////////////////////////////테스트

        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Handler mHandler = new Handler(Looper.getMainLooper());

       // Toast.makeText(this, "start doRealStart", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PropertyManager.getInstance().getUserId() != null && !PropertyManager.getInstance().getUserId().equals("")) {  // 이미 로컬 로그인 한 경우
                    String userId = PropertyManager.getInstance().getUserId();
                    String password = PropertyManager.getInstance().getPassword();
                    String regToken = PropertyManager.getInstance().getRegistrationToken();
                    Log.i("preference_id", "id " + userId+ ", password " + password);
                    try {
                        NetworkManager.getInstance().login(getApplicationContext(), userId, password, regToken, new NetworkManager.OnResultListener<LoginRequest>() {
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
                                Toast.makeText(SplashActivity.this, "fail code : " + code  + ", exception : " + ((cause != null)?cause.toString():""), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(SplashActivity.this, "Fail... Facebook", Toast.LENGTH_SHORT).show();
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


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

}
