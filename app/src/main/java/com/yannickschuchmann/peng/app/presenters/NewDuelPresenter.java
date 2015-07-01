package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.views.views.MainView;
import com.yannickschuchmann.peng.app.views.views.NewDuelView;
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
public class NewDuelPresenter extends Presenter {

    private NewDuelView mView;
    private DuelService mService;

    public NewDuelPresenter(NewDuelView view) {
        mView = view;
    }

    public void postRandomDuel() {

        // TODO remove dummy fake
        Duel duel = new Duel();
        duel.id = 42;
        mView.startDuelActivity(duel);

        // TODO implement POST
//        mService.postRandomDuel(1, new Callback<Duel>() {
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

    @Override
    public void start() {
        mView.setToolbarTitle("Neues Duell");
        mService = new RestSource().getRestAdapter().create(DuelService.class);
    }

    @Override
    public void stop() {
    }

}
