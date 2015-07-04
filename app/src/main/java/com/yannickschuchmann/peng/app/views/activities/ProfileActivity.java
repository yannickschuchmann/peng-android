package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.ProfilePresenter;
import com.yannickschuchmann.peng.app.views.components.BackToolbar;
import com.yannickschuchmann.peng.app.views.fragments.EditUserDialogFragment;
import com.yannickschuchmann.peng.app.views.views.ProfileView;
import com.yannickschuchmann.peng.app.views.views.ToolbarBackView;
import com.yannickschuchmann.peng.model.entities.User;


public class ProfileActivity extends TransitionActivity implements ProfileView, ToolbarBackView, EditUserDialogFragment.EditUserDialogListener {

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
    BackToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        mUserEdit.setVisibility(isCurrentUser() ? View.VISIBLE : View.INVISIBLE);

        mPresenter = new ProfilePresenter(this, getIntent().getExtras().getInt("userId"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
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

    private boolean isCurrentUser() {
        return getIntent().getExtras().getInt("userId") == CurrentUser.getInstance(getContext()).getUserId();
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
}
