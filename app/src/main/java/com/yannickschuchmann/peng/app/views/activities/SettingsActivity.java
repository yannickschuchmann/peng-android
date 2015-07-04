package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.SettingsPresenter;
import com.yannickschuchmann.peng.app.views.components.BackToolbar;
import com.yannickschuchmann.peng.app.views.views.SettingsView;

public class SettingsActivity extends TransitionActivity implements SettingsView {

    SettingsPresenter mPresenter;

    @Bind(R.id.toolbarTitle)
    BackToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        mPresenter = new SettingsPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void onToolbarBackClicked() {
        onBackPressed();
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitleText(title);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.edit_profil)
    public void onEditProfil() {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("userId", CurrentUser.getInstance(getContext()).getUserId());
        startActivityWithAnimation(intent);
    }

    @OnClick(R.id.edit_character)
    public void onEditCharacter() {
        Intent intent = new Intent(getContext(), CharacterPagerActivity.class);
        startActivityWithAnimation(intent);
    }

    // swap transitions
    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }
}
