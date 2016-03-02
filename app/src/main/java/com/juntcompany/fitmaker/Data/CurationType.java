package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by EOM on 2016-03-01.
 */
public class CurationType { // 서버에서 exctype 임
    public String message; //o
//    public int type_id; // 서버에 프로토콜에 없음

    @SerializedName("exctype_name")
    public String type_name;//o
    @SerializedName("exctype_info")
    public String type_info;//o
    @SerializedName("exctype_photourl")
    public String type_picture;//o

    @SerializedName("curriculum")
    public List<Curriculum> curriculums;


}
