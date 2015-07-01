package com.yannickschuchmann.peng.app.views.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.NewDuelFriendsPresenter;
import com.yannickschuchmann.peng.app.views.fragments.UsersFragment;
import com.yannickschuchmann.peng.app.views.views.NewDuelFriendsView;
import com.yannickschuchmann.peng.app.views.views.UserAdapterView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;

public class NewDuelFriendsActivity extends TransitionActivity implements NewDuelFriendsView, UserAdapterView {

    NewDuelFriendsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_duel_friends);

        mPresenter = new NewDuelFriendsPresenter(this);

        Bundle bundle = new Bundle();
        bundle.putString("type", "friends");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UsersFragment usersFragment = new UsersFragment();
        usersFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.users_fragment, usersFragment, "USERSLISTING");
        fragmentTransaction.commit();
    }

    @Override
    public void startDuelActivity(Duel duel) {
        Intent intent = new Intent(getContext(), DuelActivity.class);
        intent.putExtra("duelId", duel.id);
        startActivityWithAnimation(intent);
    }

    @Override
    public void onItemClicked(User user) {
        mPresenter.postFriendDuel(user);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
