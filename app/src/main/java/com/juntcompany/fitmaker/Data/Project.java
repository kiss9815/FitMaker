package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EOM on 2016-03-02.
 */
public class Project {
    @SerializedName("project_id")
    public int projectId;
    @SerializedName("project_name")
    public String projectName;
    @SerializedName("project_ing")
    public boolean projectIng;
}
