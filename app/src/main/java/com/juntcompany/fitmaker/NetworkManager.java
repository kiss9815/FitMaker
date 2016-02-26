package com.juntcompany.fitmaker;

import android.os.Handler;

import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.Data.Curriculum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-22.
 */
public class NetworkManager {
    private static NetworkManager instance;
    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFailure(int error);
    }

    Handler mHandler = new Handler();
    public void geCCC(int id, final OnResultListener<List<Curriculum>> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Curriculum> list = new ArrayList<Curriculum>();
                for (int i = 0; i < 3; i++) {
                    Curriculum c = new Curriculum();
                    // c...

                    list.add(c);
                }
                listener.onSuccess(list);
            }
        }, 1000);
    }

    public Curriculum getCurriculum(){

        Curriculum curriculum = new Curriculum();
        for(int i = 0; i<4 ; i++) {
            curriculum.curriculum_name = "fff : " + i;
        }
        return curriculum;
    }

    public Course getCourse(){

        Course course = new Course();
        for(int i = 0; i<4 ; i++) {
            course.course_name = "fff : " + i;
        }
        return course;
    }
}
