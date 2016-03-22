package com.juntcompany.fitmaker.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.juntcompany.fitmaker.Curation.CurationActivity;
import com.juntcompany.fitmaker.Data.JoinRequest;
import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Data.Structure.LoginRequest;
import com.juntcompany.fitmaker.Data.User;
import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.StartActivity;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getContext(), "login success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private static final String NETWORK_LOGIN = "network_local_login";
    private static final String FRAGMENT_TITLE = "로그인";

    EditText edit_email, edit_password;
    Button loginButton;
    CallbackManager callbackManager;
    LoginManager loginManager;
    AccessTokenTracker tracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

//        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);


        edit_email = (EditText)view.findViewById(R.id.edit_email);
        edit_password = (EditText)view.findViewById(R.id.edit_password);

        Button btn = (Button)view.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() { // 일반 로그인 기능 버튼
            @Override
            public void onClick(View v) {
                String regToken = PropertyManager.getInstance().getRegistrationToken();
                final String userId = edit_email.getText().toString();
                final String password = edit_password.getText().toString();
                try {
                    NetworkManager.getInstance().login(getContext(), userId, password, regToken,  new NetworkManager.OnResultListener<LoginRequest>() {
                        @Override
                        public void onSuccess(Request request, LoginRequest result) {
                            Log.i(NETWORK_LOGIN, result.message);
                            User user = new User();
                            user.userId = result.userId;
                            Log.i("local_login", "user : " + userId);
                            Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                            //int curationId = PropertyManager.getInstance().getCurationType();

                            PropertyManager.getInstance().setUserId(userId);
                            PropertyManager.getInstance().setPassword(password);
                                Intent intent = new Intent(getContext(), StartActivity.class);
//                            intent.putExtra(MainActivity.USER_MESSAGE, userId);
                                startActivity(intent);

                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {
                            Toast.makeText(getContext(), "서버 에러 로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });

        loginManager = LoginManager.getInstance();
        loginButton = (Button)view.findViewById(R.id.btn_facebook_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOrLogout();
            }
        });


         btn = (Button)view.findViewById(R.id.btn_sign_up); // 아이디 없을때 회원 가입하기 페이지로 이동
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SignUpFragment())
                        .addToBackStack(null) //빽하면 이 프래그먼트가 나옴..
                        .commit();
            }
        });
        return view;
    }


    private static final String NETWORK_FACEBOOK_LOGIN = "network facebook_login";
    private void loginOrLogout() {
         AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null) { //토큰이 있는 경우는 스플래시에서 한다. 이미 가입을 한 상태이므로
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    //페이스북 로그인 성공

                    final AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    try {
                        NetworkManager.getInstance().facebookLogin(getContext(), accessToken.getToken(), new NetworkManager.OnResultListener<JoinRequest>() {

                            @Override
                            public void onSuccess(Request request, JoinRequest result) {

                                startActivity(new Intent(getContext(), StartActivity.class));
                                Toast.makeText(getContext(), "페이스북 토큰 보냄", Toast.LENGTH_SHORT).show();
                                Log.i(NETWORK_FACEBOOK_LOGIN, result.error.message);
                                Log.i("Token", accessToken.getToken());
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
            loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
            loginManager.setDefaultAudience(DefaultAudience.FRIENDS);
            loginManager.logInWithReadPermissions(this, Arrays.asList("email"));
        } else {
            loginManager.logOut();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(!callbackManager.onActivityResult(requestCode, resultCode, data)){ // 페이스북에서 결과가 내려온게 맞으면 처리하게 하려고. 그냥 super 밑에 처리해도 됨
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((LoginActivity) getActivity()).getSupportActionBar();
        View view = actionBar.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.text_toolbar);
        textView.setText("로그인");
        //textView.setLayoutParams();
    }
}
