package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.security.PublicKey;

/**
 * Created by EOM on 2016-02-22.
 */
public class Content {
    public int content_id;  //0
    @SerializedName("contents_seq")
    public String content_status; //o
    public String content_name; //0
    public String content_type; // ex반복운동 //0
    @SerializedName("contents_url")
    public String content_youtube; //0
    public String content_time; //0
    @SerializedName("contents_count")
    public String content_set_count; // 현재 진행 중인 세트 //0
    public String content_set; //운동 시 마쳐야 하는 세트 //0
    @SerializedName("contents_target")
    public String content_benefit_part; //o
    public String content_info; //o
    @SerializedName("contents_notice")
    public String content_caution; //o
    public String content_voiceurl; //o

    }
