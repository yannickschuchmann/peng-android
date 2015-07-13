package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.views.views.SensorView;
import com.yannickschuchmann.peng.app.views.views.SettingsView;
import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.DuelService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yannick on 30.06.15.
 */
public class SensorPresenter extends Presenter {
    private SensorView mView;
    private int mDuelId;
    private DuelService mDuelService;
    public SensorPresenter(SensorView view, int duelId) {
        mDuelId = duelId;
        mView = view;
    }

    @Override
    public void start() {
        mDuelService = new RestSource().getRestAdapter().create(DuelService.class);

        mDuelService.getDuel(mDuelId, CurrentUser.getInstance(mView.getContext()).getUserId(), new Callback<Duel>() {
            @Override
            public void success(Duel duel, Response response) {
                
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
