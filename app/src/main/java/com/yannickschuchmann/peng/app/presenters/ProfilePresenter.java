package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.views.helpers.CharacterImage;
import com.yannickschuchmann.peng.app.views.views.MainView;
import com.yannickschuchmann.peng.app.views.views.ProfileView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
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

    public ProfilePresenter(ProfileView view, int userId) {
        mView = view;
        mUserId = userId;
    }

    @Override
    public void start() {
        mService = new RestSource().getRestAdapter().create(UserService.class);

        mService.getUser(mUserId, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                mUser = user;
                setupView(user);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
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
    }

    public void onUserEdited(String nick, String slogan) {
        CurrentUser currentUser = CurrentUser.getInstance(mView.getContext());
        User user = currentUser.getUser();
        user.setNick(nick);
        user.setSlogan(slogan);
        currentUser.setUser(user);

        mService.updateUser(user.id, user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                setupView(user);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void postDuel() {
        User opponent = mUser;

        // TODO remove dummy fake
        Duel duel = new Duel();
        duel.id = opponent.id;
        mView.startDuelActivity(duel);

        // TODO implement POST
//        mService.postDuel(1, new Callback<Duel>() {
//            @Override
//            public void success(Duel duel, Response response) {
//                mView.startDuelActivity(duel);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
    }

}
