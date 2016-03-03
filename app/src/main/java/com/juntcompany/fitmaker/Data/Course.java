package com.juntcompany.fitmaker.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-21.
 */
public class Course {

  public int course_id;
  public int course_day; // 메인에서 매일매일 하는 코스 날로 사용
  public int course_day_image; //x 내가 리스트뷰에 이미지 넣으려고 추가한 것, 서버 데이터와 별개
    public String course_name;
    ArrayList<Content> contents;
}
