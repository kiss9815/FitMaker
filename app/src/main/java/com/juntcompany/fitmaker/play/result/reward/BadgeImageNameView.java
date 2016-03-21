package com.juntcompany.fitmaker.play.result.reward;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Badge;
import com.juntcompany.fitmaker.R;

/**
 * Created by EOM on 2016-03-20.
 */
public class BadgeImageNameView extends RelativeLayout {
    public BadgeImageNameView(Context context) {
        super(context);
        init(null);
    }

    public BadgeImageNameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    ImageView iconView;
    TextView titleView;
    Badge data;

    private void init(AttributeSet attrs){

        inflate(getContext(), R.layout.merge_badge_name, this);
        iconView = (ImageView)findViewById(R.id.image_reward_card);
        titleView = (TextView)findViewById(R.id.text_reward_coment);

        setImageTextData(data);

    }

    public void setImageTextData(Badge data) {
        this.data = data;

        titleView.setText(data.badgeName);
    }
}
