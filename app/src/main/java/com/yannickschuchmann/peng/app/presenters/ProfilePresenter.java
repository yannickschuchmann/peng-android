package com.yannickschuchmann.peng.app.presenters;

import android.content.Intent;
import android.widget.Toast;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.views.activities.TutorialPageActivity;
import com.yannickschuchmann.peng.app.views.helpers.CharacterImage;
import com.yannickschuchmann.peng.app.views.views.ProfileView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.DuelService;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yannick on 30.06.15.
 */
public class ProfilePresenter extends Presenter {

    private ProfileView mView;
    private int mUserId;
    private User mUser;
    private UserService mService;
    private DuelService mDuelService;
    public boolean redirectToTutorial = false;

    public ProfilePresenter(ProfileView view, int userId) {
        mView = view;
        mUserId = userId;
    }

    @Override
    public void start() {
        mService = new RestSource().getRestAdapter().create(UserService.class);
        mDuelService = new RestSource().getRestAdapter().create(DuelService.class);

        if (mView.isCurrentUser()) {
            mUser = CurrentUser.getInstance(mView.getContext()).getUser();
            setupView(mUser);
        } else {
            mView.showLoading();
            mService.getUser(mUserId, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    mView.hideLoading();
                    mUser = user;
                    setupView(mUser);
                }

                @Override
                public void failure(RetrofitError error) {
                    mView.hideLoading();
                    mView.onToolbarBackClicked();
                }
            });

        }
    }

    @Override
    public void stop() {
    }

    private void setupView(User user) {
        mView.setNick(user.getNick());
        mView.setSlogan(user.getSlogan());
        CharacterImage ci = new CharacterImage(mView.getContext(), user);
        mView.setImage(ci.getDrawable());

        mView.setToolbarTitle(user.getNick());

        mView.setLastDuels(user.getLastDuels());
    }

    public void onUserEdited(String nick, String slogan) {
        CurrentUser currentUser = CurrentUser.getInstance(mView.getContext());
        User user = currentUser.getUser();
        user.setNick(nick);
        user.setSlogan(slogan);
        currentUser.setUser(user);

        mView.showLoading();
        mService.updateUser(user.getId(), user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {

                mView.hideLoading();
                if(redirectToTutorial){
                    Intent intent;
                    intent = new Intent(mView.getContext(), TutorialPageActivity.class);
                    mView.startActivityWithAnimation(intent);
                }else {
                    setupView(user);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void postDuel(String bet) {
        User opponent = mUser;
        mView.showLoading();
        mDuelService.postDuel(CurrentUser.getInstance(mView.getContext()).getUserId(), opponent.getId(), bet, new Callback<Duel>() {
            @Override
            public void success(Duel duel, Response response) {
                mView.startDuelActivity(duel);
                mView.hideLoading();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
