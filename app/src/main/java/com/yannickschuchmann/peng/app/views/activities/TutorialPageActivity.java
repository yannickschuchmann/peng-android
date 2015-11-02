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
import com.yannickschuchmann.peng.app.views.adapters.ScreenSlidePagerAdapter;
import com.yannickschuchmann.peng.app.views.components.BackToolBar;
import com.yannickschuchmann.peng.app.views.fragments.TutorialPageFragment;
import com.yannickschuchmann.peng.app.views.views.TutorialPagerView;
import com.yannickschuchmann.peng.app.views.helpers.TutorialImage;

import java.util.ArrayList;
import java.util.List;

public class TutorialPageActivity extends LoadingActivity implements TutorialPagerView{


    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
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
        List<String> allSlides = new ArrayList<String>();

        for (int i = 1; i<=12 ; i++) {
            allSlides.add("how_to_" + String.valueOf(i));
        }



        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), allSlides);

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


}
