package com.juntcompany.fitmaker.SpecificCurriculum;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.R;

/**
 * Created by EOM on 2016-02-21.
 */
public class CourseView extends FrameLayout{
    public CourseView(Context context) {
        super(context);
        init();
    }

    TextView textView;
    Course course;
    private void init(){
        inflate(getContext(), R.layout.view_course, this);
        textView = (TextView)findViewById(R.id.textView);

    }

    public void setData(Course course){
        this.course = course;
        textView.setText(course.course_name);
    }
}
