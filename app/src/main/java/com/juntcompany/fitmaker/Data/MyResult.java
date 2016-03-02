package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by EOM on 2016-03-02.
 */
public class MyResult { ////////////////////////////////다시 봐야함
    public String message;
    @SerializedName("exctype_name")
    public String curation_type;
    @SerializedName("project_history")
    public ArrayList<Project> projects;

    public String user_name;
    @SerializedName("user_photourl")
    public String user_image;
    @SerializedName("badgeCnt")
    public int badge_count;

    public ArrayList<Badge> badges;
}
