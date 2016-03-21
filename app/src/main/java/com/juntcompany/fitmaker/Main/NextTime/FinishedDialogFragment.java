package com.juntcompany.fitmaker.Main.NextTime;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.juntcompany.fitmaker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishedDialogFragment extends DialogFragment {


    public FinishedDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finished_dialog, container, false);

        Button btn = (Button) view.findViewById(R.id.btn_confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        int width = getResources().getDimensionPixelSize(R.dimen.fit_dialog_course_width);
        int height = getResources().getDimensionPixelSize(R.dimen.fit_dialog_course_height);
        getDialog().getWindow().setLayout(width, height);

    }
}