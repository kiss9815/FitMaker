package com.juntcompany.fitmaker.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-20.
 */
public class Curriculum implements Serializable{
    @SerializedName("curri_id")
    public int curriculumId;//o
    @SerializedName("curri_name")
    public String curriculumName;//o
    @SerializedName("curri_photourl")
    public String curriculum_image;//o

    @SerializedName("curri_type")
    public String curriculumLevel;

    @SerializedName("curri_info")
    public String curriculum_info;//o

    public List<Course> courses;

    public List<Content> contents;

//    public Curriculum(){
//
//    }
//    public Curriculum(Parcel source) {
//        curriculumId = source.readInt();
//        curriculumName = source.readString();
//    }
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(curriculumId);
//        dest.writeString(curriculumName);
//    }
//    public static Creator<Curriculum> CREATOR =new Creator<Curriculum>() { //parceable을 하려면 CREATOR를 꼭 정의 해야함
//        @Override
//        public Curriculum createFromParcel(Parcel source) {
//            return new Curriculum(source);
//        }
//
//        @Override
//        public Curriculum[] newArray(int size) {
//            return new Curriculum[size];
//        }
//    };
}
