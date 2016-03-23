package com.juntcompany.fitmaker.play.giveUp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Login.LoginActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
import com.juntcompany.fitmaker.R;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayGiveUpDialogFragment extends DialogFragment {


    private static final String NETWORK_RESULT = "Network logout result";

    public PlayGiveUpDialogFragment() {
        // Required empty public constructor
        setStyle(STYLE_NO_TITLE, R.style.MyDialog);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_give_up_dialog, container, false);
        Button btn = (Button)view.findViewById(R.id.btn_no);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        btn = (Button)view.findViewById(R.id.btn_yes); // 운동포기 backpress
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
                dismiss();
            }
        });


        return view;
    }

}
