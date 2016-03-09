package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by EOM on 2016-03-02.
 */
public class ProjectResponseResult {
    public String message;
    @SerializedName("projects_ing")
    public List<Project> projects;
    public List<Course> courses;

}
