package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by EOM on 2016-03-02.
 */
public class RankingResult {
    public String message;
    @SerializedName("rankfriends")
    public ArrayList<Friend> friends;
}
