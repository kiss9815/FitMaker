package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by EOM on 2016-02-22.
 */
public class Content implements Serializable{

    @SerializedName("content_id")
    public int contentId;  //0
    @SerializedName("contents_seq")
    public int content_status; //o
    @SerializedName("contents_name")
    public String contentName; //0
    public String content_type; // ex반복운동 //0
    @SerializedName("contents_url")
    public String content_youtube; //0
    @SerializedName("contents_time")
    public String contentTime; //0
    @SerializedName("contents_count")
    public String content_set_count; // 세트 1회당 횟수
    @SerializedName("contents_set")
    public String contentSet; //운동 시 마쳐야 하는 세트 //0
    @SerializedName("contents_target")
    public String contentBenefitPart; //o
    @SerializedName("contents_info")
    public String contentInfo; //o
    @SerializedName("contents_notice")
    public String contentCaution; //o
    @SerializedName("content_voiceurl")
    public String contentVoiceurl; //o

    public Boolean isFinished;

    @Override
    public String toString() {
        return contentName;
    }
}
