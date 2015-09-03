package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.CharacterPagerPresenter;
import com.yannickschuchmann.peng.app.views.adapters.CharactersPagerAdapter;
import com.yannickschuchmann.peng.app.views.components.BackToolbar;
import com.yannickschuchmann.peng.app.views.views.CharacterPagerView;
import com.yannickschuchmann.peng.model.entities.Character;

import java.util.List;


public class CharacterPagerActivity extends LoadingActivity implements CharacterPagerView {

    private CharactersPagerAdapter mCharactersPagerAdapter;
    CharacterPagerPresenter mPresenter;

    @Bind(R.id.toolbarTitle)
    BackToolbar mToolbar;
    @Bind(R.id.pager)
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_pager);
        ButterKnife.bind(this);

        mPresenter = new CharacterPagerPresenter(this);
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

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.updateCharacter();
    }

    @Override
    public void setPagerAdapter(final List<Character> characters, int currentIndex) {
        mCharactersPagerAdapter = new CharactersPagerAdapter(getSupportFragmentManager(), characters);
        mPager.setAdapter(mCharactersPagerAdapter);
        mPager.setCurrentItem(currentIndex, true);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onCharacterChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
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
