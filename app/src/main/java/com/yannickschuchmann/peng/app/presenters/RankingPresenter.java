package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.views.views.MainView;
import com.yannickschuchmann.peng.app.views.views.RankingView;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by yannick on 30.06.15.
 */
public class RankingPresenter extends Presenter {

    private RankingView mView;

    public RankingPresenter(RankingView view) {
        mView =view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
    }

}
