package com.juntcompany.fitmaker.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.Data.Friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    Handler mHandler = new Handler(Looper.getMainLooper());
    public void getCurriculum(int id, final OnResultListener<List<Curriculum>> listener) {
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

    public void getFriend(int id, String friend_name, String friend_image, int friend_exercise_hour, final OnResultListener<List<Friend>> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Friend> list = new ArrayList<Friend>();
                Random r = new Random();
                for(int i=0; i <5; i ++){
                    Friend f = new Friend();
                    f.friend_name = "name : " + i;
                    f.friend_exercise_hour = r.nextInt(50);
                    list.add(f);
                }
                listener.onSuccess(list);
            }
        },1000);
    }

    public void getCurationType(int type_id, String type_name, String type_picture, String type_info, OnResultListener<CurationType> listener ){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CurationType t = new CurationType();
//                t.type_picture = ....;
                t.type_name = "발레리나 타입";
                t.type_info = "당신은 유연하고 아름다운 선을 바라시는 타입이군요! 그런 당신에게 이 운동을 추천해 드립니다.";
            }
        }, 1000);
    }

//    public void getCourse(int )

    public void login(Context context,String userId, String password,final OnResultListener<String> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess("success");
            }
        },1000);
    }

    public void signUp(Context context, String userId, String userName, String password, final OnResultListener<String> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess("success");
            }
        },1000);
    }

    public Course getCourse(){

        Course course = new Course();
        for(int i = 0; i<4 ; i++) {
            course.course_name = "fff : " + i;
        }
        return course;
    }
}
