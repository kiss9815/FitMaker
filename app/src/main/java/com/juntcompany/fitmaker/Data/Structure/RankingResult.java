package com.juntcompany.fitmaker.Data.Structure;

import com.google.gson.annotations.SerializedName;
import com.juntcompany.fitmaker.Data.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-02.
 */
public class RankingResult {
    public String message;
    @SerializedName("rankfriends")
    public List<Friend> friends;
}
