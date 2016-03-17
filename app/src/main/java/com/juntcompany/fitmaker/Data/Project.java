package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by EOM on 2016-03-07.
 */
public class Project implements Serializable{
    @SerializedName("project_id")
    public int projectId;
    @SerializedName("project_name")
    public String projectName ;
    @SerializedName("project_on")
    public boolean projectOn;

}
