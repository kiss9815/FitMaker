package com.juntcompany.fitmaker.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
import com.juntcompany.fitmaker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    private static final String FRAGMENT_TITLE = "로그인";

    EditText edit_email, edit_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle(FRAGMENT_TITLE);

        edit_email = (EditText)view.findViewById(R.id.edit_email);
        edit_password = (EditText)view.findViewById(R.id.edit_password);

        Button btn = (Button)view.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userId = edit_email.getText().toString();
                final String password = edit_password.getText().toString();
                NetworkManager.getInstance().login(getContext(), userId, password, new NetworkManager.OnResultListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        PropertyManager.getInstance().setUserId(userId);
                        PropertyManager.getInstance().setPassword(password);
                        ////로그인
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(int error) {

                    }
                });
            }
        });

        btn = (Button)view.findViewById(R.id.btn_facebook_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //페이스북 로그인 처리.........
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_login, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                break;

            case R.id.login_login_menu:
                //게스트 입장 처리.......
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
