package com.juntcompany.fitmaker.Data.Structure;

import com.google.gson.annotations.SerializedName;
import com.juntcompany.fitmaker.Data.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-02.
 */
public class FriendListResult {
    public String message;

    public List<Friend> friends; //서버0
    @SerializedName("req_friends")
    public List<Friend> requestFriends;
    @SerializedName("res_friends")
    public List<Friend> responseFriends;

}
