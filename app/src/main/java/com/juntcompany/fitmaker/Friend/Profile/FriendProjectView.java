package com.juntcompany.fitmaker.Friend.Profile;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.R;

import java.util.zip.Inflater;

/**
 * Created by EOM on 2016-03-16.
 */
public class FriendProjectView extends FrameLayout{


    public FriendProjectView(Context context) {
        super(context);
        init();
    }

    TextView textProjectName;
    Project project;
    private void init(){
        inflate(getContext(), R.layout.view_friend_project, this);
        textProjectName = (TextView)findViewById(R.id.text_project_name);


    }

    public void setData(Project project){
        this.project = project;

        textProjectName.setText(project.projectName);

    }
}
