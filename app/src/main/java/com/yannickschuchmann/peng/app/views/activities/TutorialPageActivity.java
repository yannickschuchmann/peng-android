package com.yannickschuchmann.peng.app.views.activities;

/**
 * Created by Simon on 25.10.2015.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.TutorialPresenter;
import com.yannickschuchmann.peng.app.views.components.BackToolBar;
import com.yannickschuchmann.peng.app.views.fragments.TutorialPageFragment;
import com.yannickschuchmann.peng.app.views.views.TutorialPagerView;
import com.yannickschuchmann.peng.app.views.helpers.TutorialImage;

import java.util.List;

public class TutorialPageActivity extends LoadingActivity implements TutorialPagerView{

    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TutorialPresenter mPresenter;
    private int mImageID;
    private Context mContext;
    private TutorialPagerView mView;

    @Bind(R.id.toolbarTitle)
    BackToolBar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        ButterKnife.bind(this);

        mPresenter = new TutorialPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPager.clearOnPageChangeListeners();
    }

    public Context getContext() {
        return this;
    }

    @Override
    public void onToolbarBackClicked() {
        onBackPressed();
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitleText(title);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /*TEST CODE
                TutorialImage tutorialImage = new TutorialImage(mView.getContext(), position);
                TutorialPageFragment.instantiate(tutorialImage.getDrawable());
                Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
            */
            return new TutorialPageFragment();
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
