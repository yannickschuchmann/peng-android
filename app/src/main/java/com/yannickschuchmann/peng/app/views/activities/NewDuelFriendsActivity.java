package com.yannickschuchmann.peng.app.views.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.NewDuelFriendsPresenter;
import com.yannickschuchmann.peng.app.views.components.BackToolBar;
import com.yannickschuchmann.peng.app.views.fragments.DuelBetDialogFragment;
import com.yannickschuchmann.peng.app.views.fragments.DuelBetDialogFragment.DuelBetDialogListener;
import com.yannickschuchmann.peng.app.views.fragments.UsersFragment;
import com.yannickschuchmann.peng.app.views.views.NewDuelFriendsView;
import com.yannickschuchmann.peng.app.views.views.UserAdapterView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;

public class NewDuelFriendsActivity extends LoadingActivity implements DuelBetDialogListener, NewDuelFriendsView, UserAdapterView {

    NewDuelFriendsPresenter mPresenter;

    @Bind(R.id.toolbarTitle)
    BackToolBar mToolbar;

    User mClickedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_duel_friends);

        mPresenter = new NewDuelFriendsPresenter(this);

        ButterKnife.bind(this);

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
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    public void startDuelActivity(Duel duel) {
        Intent intent = new Intent(getContext(), SensorActivity.class);
        intent.putExtra("duelId", duel.id);
        startActivityWithAnimation(intent);
    }

    @Override
    public void onItemClicked(User user) {
        mClickedUser = user;
        if (mClickedUser.getId() == CurrentUser.getInstance(getContext()).getUserId()) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    "Du kannst dich nicht selbst duellieren.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        DuelBetDialogFragment dialogFragment = DuelBetDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "DUEL_BET_DIALOG");
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
    public void onDialogPositiveClick(DuelBetDialogFragment dialog) {
        mPresenter.postFriendDuel(mClickedUser, dialog.getBet());
    }

    @Override
    public void onDialogNegativeClick(DuelBetDialogFragment dialog) {
        dialog.getDialog().cancel();
    }
}
