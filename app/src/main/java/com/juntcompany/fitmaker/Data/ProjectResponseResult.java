package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-02.
 */
public class ProjectResponseResult {
    public String message;
    @SerializedName("projects_ing")
    public List<ProjectsIng> projectsIng;
    public List<Course> courses;

}
