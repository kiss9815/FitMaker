package com.juntcompany.fitmaker.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
import com.juntcompany.fitmaker.R;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor

    }

    private static final String FRAGMENT_TITLE = "회원 가입";

    EditText edit_email, edit_password, edit_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        getActivity().setTitle(FRAGMENT_TITLE);

        edit_name = (EditText)view.findViewById(R.id.edit_name);
        edit_email = (EditText)view.findViewById(R.id.edit_email);
        edit_password = (EditText)view.findViewById(R.id.edit_password);

        Button btn = (Button)view.findViewById(R.id.btn_complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 가입완료 처리

                final String userId = edit_email.getText().toString();
                final String password = edit_password.getText().toString();
                String name = edit_name.getText().toString();
//
                try {
                    NetworkManager.getInstance().signUp(getContext(), new NetworkManager.OnResultListener<JoinResult>() {
                        @Override
                        public void onSuccess(Request request, JoinResult result) {

                            PropertyManager.getInstance().setUserId(userId);
                            PropertyManager.getInstance().setPassword(password);
                            startActivity(new Intent(getContext(), MainActivity.class));
//                        getActivity().finish();
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

//                NetworkManager.getInstance().signUp(getContext(), userId, name, password, new NetworkManager.OnResultListener<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        //로컬에 저장하는 과정
//                        PropertyManager.getInstance().setUserId(userId);
//                        PropertyManager.getInstance().setPassword(password);
//                        startActivity(new Intent(getContext(), MainActivity.class));
//                        getActivity().finish();
//                    }
//
//                    @Override
//                    public void onFailure(int error) {
//
//
//                    }
//                });
                getActivity().finish();
            }
        });

        return view;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //.... 코드 안됨
                getActivity().finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
