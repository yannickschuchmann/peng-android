package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.presenters.MainPresenter;
import com.yannickschuchmann.peng.app.views.adapters.DuelsAdapter;
import com.yannickschuchmann.peng.app.views.views.DuelAdapterView;
import com.yannickschuchmann.peng.app.views.views.MainView;
import com.yannickschuchmann.peng.model.entities.Duel;

import java.util.List;


public class MainActivity extends LoadingActivity implements MainView, DuelAdapterView {

    @Bind(R.id.user_nick)
    TextView mNick;
    @Bind(R.id.user_slogan)
    TextView mSlogan;
    @Bind(R.id.user_friends_count)
    TextView mFriends;
    @Bind(R.id.user_duels_count)
    TextView mDuels;
    @Bind(R.id.user_ranking)
    TextView mRank;
    @Bind(R.id.user_image)
    ImageView mImage;
    @Bind(R.id.open_duels)
    LinearLayout mOpenDuels;

    int mBackPressed = 0;

    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter = new MainPresenter(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainPresenter.start();
    }



    @Override
    protected void onPause() {
        super.onPause();
        mBackPressed = 0;
        //mImage.setImageDrawable(null);
        //mOpenDuels.setLayoutAnimationListener(null);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @OnClick(R.id.button_new_duel)
    public void onNewDuelClick() {
        Intent intent = new Intent(getContext(), NewDuelActivity.class);
        startActivityWithAnimation(intent);
    }

    @OnClick(R.id.button_settings)
    public void onSettingsClick() {
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivityWithAnimation(intent, true);
    }

    @OnClick(R.id.button_go_to_ranking)
    public void onRankingClick() {
        Intent intent = new Intent(getContext(), RankingActivity.class);
        startActivityWithAnimation(intent);
    }

    @OnClick(R.id.user_image)
    public void onImageClick() {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("userId", CurrentUser.getInstance(this).getUserId());
        startActivityWithAnimation(intent);
    }

    @Override
    public void onBackPressed() {
        mBackPressed++;
        if (mBackPressed == 1) {
            Toast.makeText(getApplicationContext(), R.string.toastOnBackPressedForExit, Toast.LENGTH_SHORT).show();
        } else if (mBackPressed >= 2) {
            super.onBackPressed();
        }
    }

    public void setImage(Drawable image) {
        mImage.setImageDrawable(image);
    }

    public void setNick(String value) {
        mNick.setText(value);
    }

    public void setSlogan(String value) {
        mSlogan.setText(value);
    }

    public void setFriendsCount(int value) {
        mFriends.setText(String.valueOf(value));
    }

    public void setDuelsCount(int value) {
        mDuels.setText(String.valueOf(value));
    }

    public void setRank(int value) {
        mRank.setText(String.valueOf(value));
    }

    public void setOpenDuels(List<Duel> duels) {
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

    public void onDuelClicked(Duel duel) {
        Intent intent = new Intent(getContext(), SensorActivity.class);
        intent.putExtra("duelId", duel.id);
        startActivityWithAnimation(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }


}
