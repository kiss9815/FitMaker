package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EOM on 2016-03-02.
 */
public class ProjectRequestResult { //post
    public String message;
    @SerializedName("project_id")
    public int projectId;
}
