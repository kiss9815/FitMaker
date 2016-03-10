package com.juntcompany.fitmaker.Data.Structure;

import com.google.gson.annotations.SerializedName;
import com.juntcompany.fitmaker.Data.Friend;

import java.util.List;

/**
 * Created by EOM on 2016-03-10.
 */
public class FriendSearchResult {
    public String message;
    @SerializedName("friends")
    public List<Friend> friends;
}
