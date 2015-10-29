package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.ProfilePresenter;
import com.yannickschuchmann.peng.app.views.adapters.DuelsAdapter;
import com.yannickschuchmann.peng.app.views.components.BackToolBar;
import com.yannickschuchmann.peng.app.views.fragments.DuelBetDialogFragment;
import com.yannickschuchmann.peng.app.views.fragments.EditUserDialogFragment;
import com.yannickschuchmann.peng.app.views.views.DuelAdapterView;
import com.yannickschuchmann.peng.app.views.views.ProfileView;
import com.yannickschuchmann.peng.app.views.views.ToolbarBackView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;

import java.util.List;


public class ProfileActivity extends LoadingActivity implements DuelBetDialogFragment.DuelBetDialogListener, ProfileView, ToolbarBackView, EditUserDialogFragment.EditUserDialogListener, DuelAdapterView {

    ProfilePresenter mPresenter;

    @Bind(R.id.user_image)
    ImageView mUserImage;
    @Bind(R.id.user_nick)
    TextView mUserNick;
    @Bind(R.id.user_slogan)
    TextView mUserSlogan;
    @Bind(R.id.user_edit)
    TextView mUserEdit;
    @Bind(R.id.toolbarTitle)
    BackToolBar mToolbar;
    @Bind(R.id.last_games)
    LinearLayout mLastGames;
    @Bind(R.id.challenge_user)
    Button mChallengeUser;
    @Bind(R.id.open_duels)
    LinearLayout mOpenDuels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        if (!isCurrentUser()) {
            mUserEdit.setVisibility(View.INVISIBLE);
            mLastGames.setVisibility(View.GONE);
        } else {
            mChallengeUser.setVisibility(View.GONE);
        }
        mPresenter = new ProfilePresenter(this, getIntent().getExtras().getInt("userId"));

        if (getIntent().getBooleanExtra("showEditDialog", false)) {
            mPresenter.redirectToTutorial = true;
            User user = CurrentUser.getInstance(getContext()).getUser();
            EditUserDialogFragment dialogFragment = EditUserDialogFragment.newInstance(user.getNick(), user.getSlogan());
            dialogFragment.show(getSupportFragmentManager(), "EDIT_USER_DIALOG");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mUserImage.setImageDrawable(null);
    }

    public void startDuelActivity(Duel duel) {
        Intent intent = new Intent(getContext(), SensorActivity.class);
        intent.putExtra("duelId", duel.id);
        startActivityWithAnimation(intent);
    }

    @OnClick(R.id.user_image)
    public void onImageClick() {
        if (!isCurrentUser()) return;
        Intent intent = new Intent(getContext(), CharacterPagerActivity.class);
        intent.putExtra("userId", CurrentUser.getInstance(getContext()).getUserId());
        startActivityWithAnimation(intent);
    }

    @OnClick(R.id.user_edit)
    public void onUserEdit() {
        User user = CurrentUser.getInstance(getContext()).getUser();
        EditUserDialogFragment dialogFragment = EditUserDialogFragment.newInstance(user.getNick(), user.getSlogan());
        dialogFragment.show(getSupportFragmentManager(), "EDIT_USER_DIALOG");
    }

    @OnClick(R.id.challenge_user)
    public void onChallengeUser() {
        if (isCurrentUser()) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    R.string.toastYouCanNotFightYourself,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }else {
            DuelBetDialogFragment dialogFragment = DuelBetDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "DUEL_BET_DIALOG");
        }
    }

    public boolean isCurrentUser() {
        return getIntent().getExtras().getInt("userId") == CurrentUser.getInstance(getContext()).getUserId();
    }

    public void setLastDuels(List<Duel> duels) {
        if (mOpenDuels != null && mOpenDuels.getChildCount() > 0) {
            mOpenDuels.removeAllViews();
        }

        LinearLayout ll = mOpenDuels;
        DuelsAdapter adapter = new DuelsAdapter(getApplicationContext(), duels);
        for(int position=0; position < adapter.getItemCount(); position++){
            DuelsAdapter.DuelsRowHolder holder = adapter.onCreateViewHolder(ll, adapter.getItemViewType(position));
            adapter.onBindViewHolder(holder, position);

            ll.addView(holder.itemView);

        }
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

    @Override
    public void onDialogPositiveClick(EditUserDialogFragment dialog) {
        mPresenter.onUserEdited(dialog.getNick(), dialog.getSlogan());
    }

    @Override
    public void onDialogNegativeClick(EditUserDialogFragment dialog) {
        dialog.getDialog().cancel();
    }

    @Override
    public void onDialogPositiveClick(DuelBetDialogFragment dialog) {
            mPresenter.postDuel(dialog.getBet());
    }

    @Override
    public void onDialogNegativeClick(DuelBetDialogFragment dialog) {
        dialog.getDialog().cancel();
    }

    @Override
    public void onDuelClicked(Duel duel) {

    }
}
