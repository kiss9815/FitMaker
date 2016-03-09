package com.juntcompany.fitmaker.Data.Structure;

import com.google.gson.annotations.SerializedName;
import com.juntcompany.fitmaker.Data.Badge;
import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.Data.User;

import java.util.List;

/**
 * Created by EOM on 2016-03-02.
 */
public class MyResult { ////////////////////////////////다시 봐야함
    public String message;

    public User user; //서버도 user
    @SerializedName("project_history")
    public List<Project> projects;
    @SerializedName("mybadges")
    public List<Badge> badges;
}
