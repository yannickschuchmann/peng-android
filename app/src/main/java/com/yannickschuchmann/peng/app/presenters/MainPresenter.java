package com.yannickschuchmann.peng.app.presenters;

import butterknife.Bind;
import com.yannickschuchmann.peng.app.views.views.MainView;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yannick on 30.06.15.
 */
public class MainPresenter extends Presenter {

    private MainView mMainView;
    private UserService mService;

    public MainPresenter(MainView mainView) {
        mMainView = mainView;
    }

    public void showNick(String nick) {
        mMainView.setNick(nick);
    }

    @Override
    public void start() {
        mService = new RestSource().getRestAdapter().create(UserService.class);

        mService.getUser(1, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                mMainView.setNick(user.getNick());
                mMainView.setSlogan(user.getSlogan());
                mMainView.setFriendsCount(user.getFriendsCount());
                mMainView.setDuelsCount(user.getDuelsCount());
                mMainView.setRank(user.getRank());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void stop() {
    }

    private void onGetUserReceived(User user) {

    }

}
