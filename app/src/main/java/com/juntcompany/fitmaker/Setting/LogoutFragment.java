package com.juntcompany.fitmaker.Setting;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends DialogFragment {


    public LogoutFragment() {
        // Required empty public constructor
        setStyle(STYLE_NO_TITLE, 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        Button btn = (Button)view.findViewById(R.id.btn_no);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        btn = (Button)view.findViewById(R.id.btn_yes); // 로그아웃하기
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    NetworkManager.getInstance().logout(getContext(), new NetworkManager.OnResultListener<JoinResult>() {
                        @Override
                        public void onSuccess(Request request, JoinResult result) {

                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //..로그아웃 처리....
                dismiss();
            }
        });


        return view;
    }

}
