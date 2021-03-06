package com.juntcompany.fitmaker.Data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-21.
 */
public class Course implements Serializable{

   @SerializedName("course_id")
  public int courseId;
    @SerializedName("course_seq")
    public int courseSeq; // 메인에서 매일매일 하는 코스 날로 사용

  public int course_day_image; //x 내가 리스트뷰에 이미지 넣으려고 추가한 것, 서버 데이터와 별개
    public String course_name;
   public List<Content> contents;

    public boolean isFinish;
    public boolean isSelectable;


    public String badgeName; // 메인에서 뷰홀더 setData에서 사용하려고 변수 만듬

    @Override
    public String toString() {
        return "courseid : "+courseId +", courseSeq : " + courseSeq;

    }
}
