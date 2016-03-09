package com.juntcompany.fitmaker.Main.MainSpinner;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.R;

/**
 * Created by EOM on 2016-03-09.
 */
public class MainSpinnerView extends FrameLayout {

    public MainSpinnerView(Context context) {
        super(context);
        init();
    }


    TextView textTitle;
    Project project;
    private void init(){
        inflate(getContext(), R.layout.view_spinner, this);
        textTitle = (TextView)findViewById(R.id.text_title);


    }
    public void setData(Project project){
        this.project = project;
        textTitle.setText(project.projectName);
    }


}
