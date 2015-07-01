package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.views.views.MainView;
import com.yannickschuchmann.peng.app.views.views.ProfileView;
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
                mView.setNick(user.getNick());
                mView.setSlogan(user.getSlogan());
                mView.setImage();

                mView.setToolbarTitle(user.getNick());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void stop() {
    }

}
