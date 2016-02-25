package com.juntcompany.fitmaker.Data;

import java.util.List;

/**
 * Created by EOM on 2016-02-21.
 */
public class Course {
  public int course_id;
  public int course_day; // 메인에서 매일매일 하는 코스 날로 사용
  public String course_day_image;
    public String course_name;
    List<Content> contents;
}
