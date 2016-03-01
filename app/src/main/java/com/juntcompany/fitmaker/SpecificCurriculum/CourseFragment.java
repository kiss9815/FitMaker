package com.juntcompany.fitmaker.SpecificCurriculum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment {


    public CourseFragment() {
        // Required empty public constructor
    }



    ListView listView;
    CourseAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        mAdapter = new CourseAdapter();
        listView.setAdapter(mAdapter);


        NetworkManager.getInstance();
        for (int i =0 ; i<5 ;i++) {
            Course course = new Course();
            course.course_name = "asd " + i;
            mAdapter.add(course);
        }

        return view;
    }

}
