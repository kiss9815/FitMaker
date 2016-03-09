package com.juntcompany.fitmaker.Curation;

import android.content.Context;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.R;

/**
 * Created by EOM on 2016-02-20.
 */
public class CurriculumView extends FrameLayout {
    public CurriculumView(Context context) {
        super(context);
        init();

    }

    ImageView imageView;
    Curriculum curriculum;
    private void init(){
        inflate(getContext(), R.layout.view_curriculum, this);
        imageView = (ImageView)findViewById(R.id.image_picture);

    }

    public void setData(Curriculum curriculum){  // Adapter에서 view의 데이터를 정하기 위해 만듬 // getView 에서 data 지정
        this.curriculum = curriculum;
        if(!TextUtils.isEmpty(curriculum.curriculum_image)){

        }else {
            imageView.setImageResource(Integer.parseInt(String.valueOf(R.mipmap.ic_launcher)));
        }
    }

}
