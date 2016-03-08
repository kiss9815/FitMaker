package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EOM on 2016-02-22.
 */
public class Badge {

    public int badge_id;
//    public int badge_location; //o

    @SerializedName("badge_name")
    public String badgeName; //첫 걸음마 달성 //o
    @SerializedName("badge_photourl")
    public String badgeImage; //o

    public boolean own_badge; //o

    public String badge_date; //o
}
