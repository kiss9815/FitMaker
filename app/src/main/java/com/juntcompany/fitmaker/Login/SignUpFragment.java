package com.juntcompany.fitmaker.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.juntcompany.fitmaker.Data.JoinResult;
import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.StartActivity;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
//        setHasOptionsMenu(true);

    }
    private static final String EMAIL_PATTERN = "\"^[_A-Za-z0-9-]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";

    private static final String FRAGMENT_TITLE = "회원 가입";

    EditText editEmail, editPassword, editName;
    DatePicker datePicker;
    String birth = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        getActivity().setTitle(FRAGMENT_TITLE);

//        actionBar.setDisplayHomeAsUpEnabled(true);

        editName = (EditText)view.findViewById(R.id.edit_name);
        editEmail = (EditText)view.findViewById(R.id.edit_email);
        editEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {///.. 액션을 뭘로 해야하지....
                    String email = editEmail.getText().toString();
                    if (email.matches(EMAIL_PATTERN)) {

                    } else {
                        Toast.makeText(getContext(), "이메일이 아닙니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
        editPassword = (EditText)view.findViewById(R.id.edit_password);

        datePicker = (DatePicker)view.findViewById(R.id.datePicker);
        datePicker.init(1990, 01, 13, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                 birth = ""+year+monthOfYear+dayOfMonth;
            }
        });

        Button btn = (Button)view.findViewById(R.id.btn_complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 가입완료 처리

                final String userId = editEmail.getText().toString();
                final String password = editPassword.getText().toString();
                String name = editName.getText().toString();
                if(birth.equals("")){
                    birth = ""+19900905;
                }
                String birthDay = birth;
                Log.i("birth", birthDay);
                try {
                    NetworkManager.getInstance().signUp(getContext(), name, userId, password, birthDay, new NetworkManager.OnResultListener<JoinResult>() {
                        @Override
                        public void onSuccess(Request request, JoinResult result) {


                            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                            Intent intent = new Intent(getContext(), LoginActivity.class);
//                            startActivity(intent);
//                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

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


    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((LoginActivity) getActivity()).getSupportActionBar();
        View view = actionBar.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.text_toolbar);
        textView.setText("회원가입");
        //textView.setLayoutParams();
    }
}
