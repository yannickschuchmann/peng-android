package com.yannickschuchmann.peng.app.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.transition.Slide;

import com.yannickschuchmann.peng.app.views.fragments.TutorialPageFragment;

import java.util.List;


public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mSlides;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<String> slides) {
        super(fm);
        mSlides = slides;
    }

    @Override
    public Fragment getItem(int position) {
        return TutorialPageFragment.newInstace(mSlides.get(position));
    }

    @Override
    public int getCount() {
        return mSlides.size();
    }
}