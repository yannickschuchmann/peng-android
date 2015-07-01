package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.MainPresenter;
import com.yannickschuchmann.peng.app.presenters.NewDuelPresenter;
import com.yannickschuchmann.peng.app.views.views.NewDuelView;
import com.yannickschuchmann.peng.model.entities.Duel;


public class NewDuelActivity extends TransitionActivity implements NewDuelView {

    NewDuelPresenter mNewDuelPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_duel);

        ButterKnife.bind(this);

        mNewDuelPresenter = new NewDuelPresenter(this);

    }

    @OnClick(R.id.duel_random)
    public void onDuelRandom() {
        mNewDuelPresenter.postRandomDuel();
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

}
