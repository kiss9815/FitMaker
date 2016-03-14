package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by EOM on 2016-02-22.
 */
public class Content implements Serializable{

    @SerializedName("contents_id")
    public int contentId;  //0
    @SerializedName("contents_seq")
    public int content_status; //o
    @SerializedName("contents_name")
    public String contentName; //0
    public String content_type; // ex반복운동 //0
    @SerializedName("contents_url")
    public String contentYoutube; //0

    @SerializedName("thumbnail_url")
    public String contentYoutubeThumbNail;

    @SerializedName("contents_time")
    public int contentTime; //0
    @SerializedName("contents_count")
    public int contentCount; // 세트 1회당 횟수
    @SerializedName("contents_set")
    public int contentSet; //운동 시 마쳐야 하는 세트 //0
    @SerializedName("contents_target")
    public String contentBenefitPart; //o
    @SerializedName("contents_info")
    public String contentInfo; //o
    @SerializedName("contents_notice")
    public String contentCaution; //o
    @SerializedName("content_voiceurl")
    public String contentVoiceurl; //o



    public boolean isFinished;
    public boolean isSelectable;

    @Override
    public String toString() {
        return contentName;
    }
}
