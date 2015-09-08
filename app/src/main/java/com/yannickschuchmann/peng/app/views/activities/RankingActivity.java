package com.yannickschuchmann.peng.app.views.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.RankingPresenter;
import com.yannickschuchmann.peng.app.views.components.BackToolbar;
import com.yannickschuchmann.peng.app.views.fragments.UsersFragment;
import com.yannickschuchmann.peng.app.views.views.RankingView;
import com.yannickschuchmann.peng.app.views.views.UserAdapterView;
import com.yannickschuchmann.peng.model.entities.User;


public class RankingActivity extends TransitionActivity implements UserAdapterView, RankingView {

    @Bind(R.id.toolbarTitle)
    BackToolbar mToolbar;

    RankingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        mPresenter = new RankingPresenter(this);

        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString("type", "ranking");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UsersFragment usersFragment = UsersFragment.newInstance(bundle);
        fragmentTransaction.replace(R.id.users_fragment, usersFragment, "USERSLISTING");
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void onItemClicked(User user) {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("userId", user.id);
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
