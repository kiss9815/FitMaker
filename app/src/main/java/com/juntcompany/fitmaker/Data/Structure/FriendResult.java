package com.juntcompany.fitmaker.Data.Structure;

import com.google.gson.annotations.SerializedName;
import com.juntcompany.fitmaker.Data.Badge;
import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.Data.Project;

import java.util.List;

/**
 * Created by EOM on 2016-03-09.
 */
public class FriendResult {

    public String message;

    public Friend friend; //서버 0

    @SerializedName("project_history")
    public List<Project> friendProjects;

    @SerializedName("friend_badges")
    public List<Badge> friendBadges;
}
