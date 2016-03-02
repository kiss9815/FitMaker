package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by EOM on 2016-03-02.
 */
public class OnProjectResult {
    public String message;
    @SerializedName("list")
    public ArrayList<Project> projects;
}
