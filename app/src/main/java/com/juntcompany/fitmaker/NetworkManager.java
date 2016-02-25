package com.juntcompany.fitmaker;

import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.Data.Curriculum;

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
