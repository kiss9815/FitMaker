package com.juntcompany.fitmaker.SpecificCurriculum;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by EOM on 2016-02-21.
 */
public class SpecificCurriculumViewPagerAdapter extends FragmentPagerAdapter{
    private static final int TAB_COUNT = 3; // 탭 개수

    public SpecificCurriculumViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new CourseFragment();
            case 1 :
                return new CourseFragment();
            case 2 :
                return new CourseFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
