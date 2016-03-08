package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-07.
 */
public class User {

    @SerializedName("user_name")
    public String userName;
    @SerializedName("user_photourl")
    public String userImage;

    @SerializedName("badgeCnt")
    public int badgeCount;
    @SerializedName("exctype_name")
    public String curationName;

    @SerializedName("project_history")
    public List<Project> projectHistories;

    public List<Badge> badges;


}
