package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.ProfilePresenter;
import com.yannickschuchmann.peng.app.views.components.BackToolbar;
import com.yannickschuchmann.peng.app.views.views.ProfileView;
import com.yannickschuchmann.peng.app.views.views.ToolbarBackView;


public class ProfileActivity extends TransitionActivity implements ProfileView, ToolbarBackView {

    ProfilePresenter mPresenter;

    @Bind(R.id.user_image)
    ImageView mUserImage;
    @Bind(R.id.user_nick)
    TextView mUserNick;
    @Bind(R.id.user_slogan)
    TextView mUserSlogan;
    @Bind(R.id.toolbarTitle)
    BackToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        mPresenter = new ProfilePresenter(this, getIntent().getExtras().getInt("userId"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @OnClick(R.id.user_image)
    public void onImageClick() {
        Intent intent = new Intent(getContext(), CharacterPagerActivity.class);
        intent.putExtra("userId", 1);
        startActivityWithAnimation(intent);
    }

    @Override
    public void setNick(String nick) {
        mUserNick.setText(nick);
    }

    @Override
    public void setSlogan(String slogan) {
        mUserSlogan.setText(slogan);
    }

    @Override
    public void setImage(Drawable image) {
        mUserImage.setImageDrawable(image);
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
