package com.juntcompany.fitmaker.Data.Structure;

import com.google.gson.annotations.SerializedName;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.Data.Curriculum;

import java.util.ArrayList;

/**
 * Created by EOM on 2016-03-02.
 */
public class TypeCurriculumResult {

    public String message;
    @SerializedName("exctype")
    public CurationType curationType;
    @SerializedName("curriculum")
    public ArrayList<Curriculum> curriculums;

}
