package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by EOM on 2016-03-01.
 */
public class CurationType {
    public String message; //o
//    public int type_id; // 서버에 프로토콜에 없음
    @SerializedName("exctype_id")
    public int typeId;
    @SerializedName("exctype_name")
    public String typeName;//o
    @SerializedName("exctype_info")
    public String typeInfo;//o
    @SerializedName("exctype_photourl")
    public String typePicture;//o


    @SerializedName("curriculum")
    public List<Curriculum> curriculums;


}
