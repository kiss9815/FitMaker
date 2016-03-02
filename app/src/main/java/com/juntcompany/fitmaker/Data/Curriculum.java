package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-20.
 */
public class Curriculum {
    @SerializedName("curri_id")
    public int curriculum_id;//o
    @SerializedName("curri_name")
    public String curriculum_name;//o
    @SerializedName("curri_photourl")
    public String curriculum_image;//o

    @SerializedName("curri_info")
    public String curriculum_info;//o

    public ArrayList<Course> courses;
}
