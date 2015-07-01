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

    private MainView mView;
    private UserService mService;

    public MainPresenter(MainView view) {
        mView = view;
    }

    @Override
    public void start() {
        mService = new RestSource().getRestAdapter().create(UserService.class);

        mService.getUser(1, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                mView.setNick(user.getNick());
                mView.setSlogan(user.getSlogan());
                mView.setFriendsCount(user.getFriendsCount());
                mView.setDuelsCount(user.getDuelsCount());
                mView.setRank(user.getRank());
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
