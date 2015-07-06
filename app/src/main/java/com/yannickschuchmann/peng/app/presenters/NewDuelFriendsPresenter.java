package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.views.views.NewDuelFriendsView;
import com.yannickschuchmann.peng.app.views.views.NewDuelView;
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
public class NewDuelFriendsPresenter extends Presenter {

    private NewDuelFriendsView mView;
    private DuelService mService;

    public NewDuelFriendsPresenter(NewDuelFriendsView view) {
        mView = view;
    }

    public void postFriendDuel(User friend) {

        mService.postDuel(CurrentUser.getInstance(mView.getContext()).getUserId(),friend.id, new Callback<Duel>() {
            @Override
            public void success(Duel duel, Response response) {
                mView.startDuelActivity(duel);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void start() {
        mView.setToolbarTitle("Fordere einen Freund");
        mService = new RestSource().getRestAdapter().create(DuelService.class);
    }

    @Override
    public void stop() {
    }

}
