package com.juntcompany.fitmaker.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        getActivity().setTitle(FRAGMENT_TITLE);

        edit_email = (EditText)view.findViewById(R.id.edit_email);
        edit_password = (EditText)view.findViewById(R.id.edit_password);

        Button btn = (Button)view.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() { // 일반 로그인 기능 버튼
            @Override
            public void onClick(View v) {
                final String userId = edit_email.getText().toString();
                final String password = edit_password.getText().toString();
                try {
                    NetworkManager.getInstance().login(getContext(), userId, password, new NetworkManager.OnResultListener<LoginRequest>() {
                        @Override
                        public void onSuccess(Request request, LoginRequest result) {
                            User user = new User();
                            user.userId = result.userId;
                            Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), StartActivity.class); // sharedPreference 값 exctype 이 있으면 메인, 없으면 큐레이션으로 가자.
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
//                NetworkManager.getInstance().login(getContext(), userId, password, new NetworkManager.OnResultListener<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        PropertyManager.getInstance().setUserId(userId);
//                        PropertyManager.getInstance().setPassword(password);
//                        ////로그인
//                        startActivity(new Intent(getContext(), MainActivity.class));
//                        getActivity().finish();
//                    }
//
//                    @Override
//                    public void onFailure(int error) {
//
//                    }
//                });
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
//        loginButton.setFragment(this); // 프래그먼트일 경우 이 코드 꼭 해야함
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { // 페이스북 로그인
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                AccessToken token = AccessToken.getCurrentAccessToken();
//                if (token == null) { //토큰이 만료된 경우 로그인 매니저로 로그인
//                    loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//                        @Override
//                        public void onSuccess(LoginResult loginResult) {
//
//                        }
//
//                        @Override
//                        public void onCancel() {
//
//                        }
//
//                        @Override
//                        public void onError(FacebookException error) {
//
//                        }
//                    });
//                    Toast.makeText(getContext(), "success : " + token.getToken(), Toast.LENGTH_SHORT).show();
//                    loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
//                    loginManager.setDefaultAudience(DefaultAudience.FRIENDS);
//                    LoginManager.getInstance().logInWithReadPermissions(getActivity(), null);
//
//                }else { //토큰이 있는 경우 토큰을 서버에 전송
//                    GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            if(response.getError() == null){ // 토큰이 잘 넘어간 경우
//                                Toast.makeText(getContext(), "data : " + response.getJSONObject().toString(), Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(getContext(), "error : " + response.getError().toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    request.executeAsync();
//                }
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
//            }
//        });


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


    private void loginOrLogout() {
         AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null) { //토큰이 있는 경우는 스플래시에서 한다. 이미 가입을 한 상태이므로
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    //페이스북 로그인 성공
                    startActivity(new Intent(getContext(), StartActivity.class));
                    final AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    try {
                        NetworkManager.getInstance().facebookLogin(getContext(), accessToken.getToken(), new NetworkManager.OnResultListener<JoinResult>() {

                            @Override
                            public void onSuccess(Request request, JoinResult result) {
                                Toast.makeText(getContext(), "페이스북 토큰 보냄", Toast.LENGTH_SHORT).show();
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
}
