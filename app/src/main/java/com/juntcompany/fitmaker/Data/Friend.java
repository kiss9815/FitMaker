package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EOM on 2016-02-23.
 */
public class Friend {  // RankingSeasonFragment에서 사용


    public String user_history; //? 왜썻지?? //x

    @SerializedName("friend_id")
    public int friendId;
    @SerializedName("friend_name")
    public String friendName; //o
    @SerializedName("friend_email")
    public String friendEmail;
    @SerializedName("friend_photourl")
    public String friendProfile;//o

    @SerializedName("friend_state")
    public int friendState;
    @SerializedName("badgeCnt")
    public int badgeCount;
    @SerializedName("friend_hours")
    public int friendExerciseHour;//o
    @SerializedName("exctype_name")
    public String curationType;


    public int userOrder;

}
