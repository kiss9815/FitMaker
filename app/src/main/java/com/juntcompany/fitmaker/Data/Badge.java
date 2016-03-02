package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EOM on 2016-02-22.
 */
public class Badge {

    public int badge_id;
    public int badge_location; //o
    public String badge_name; //첫 걸음마 달성 //o
    @SerializedName("badge_photourl")
    public String badge_image; //o
//    public String badge_text; //x
    public boolean own_badge; //o

    public String badge_date; //o
}
