package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by EOM on 2016-03-02.
 */
public class TypeCurriculumResult {
    @SerializedName("exctype")
    public CurationType curationType;
    @SerializedName("curriculum")
    public ArrayList<Curriculum> curriculums;

}
