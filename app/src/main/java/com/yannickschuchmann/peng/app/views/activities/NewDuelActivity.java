package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.NewDuelPresenter;
import com.yannickschuchmann.peng.app.views.components.BackToolbar;
import com.yannickschuchmann.peng.app.views.views.NewDuelView;
import com.yannickschuchmann.peng.model.entities.Duel;


public class NewDuelActivity extends TransitionActivity implements NewDuelView {

    NewDuelPresenter mPresenter;

    @Bind(R.id.toolbarTitle)
    BackToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_duel);

        ButterKnife.bind(this);

        mPresenter = new NewDuelPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @OnClick(R.id.duel_random)
    public void onDuelRandom() {
        mPresenter.postRandomDuel();
    }

    @OnClick(R.id.duel_friends)
    public void onDuelFriends() {
        Intent intent = new Intent(getContext(), NewDuelFriendsActivity.class);
        startActivityWithAnimation(intent);
    }

    public void startDuelActivity(Duel duel) {
        Intent intent = new Intent(getContext(), DuelActivity.class);
        intent.putExtra("duelId", duel.id);
        startActivityWithAnimation(intent);
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
