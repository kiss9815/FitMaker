package com.juntcompany.fitmaker.Data.Structure;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EOM on 2016-03-14.
 */
public class LoginRequest {
    @SerializedName("id")
    public int userId;

    public String message;

    Error error;
}
