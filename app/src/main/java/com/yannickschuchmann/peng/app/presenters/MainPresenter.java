package com.yannickschuchmann.peng.app.presenters;

import android.content.Intent;
import butterknife.Bind;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.views.activities.ProfileActivity;
import com.yannickschuchmann.peng.app.views.helpers.CharacterImage;
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

        final CurrentUser currentUser = CurrentUser.getInstance(mView.getContext());

        mService.getUser(currentUser.getUserId(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                currentUser.setUser(user);

                if (user.getNick() == null || user.getNick().equals("")) {
                    Intent intent;
                    intent = new Intent(mView.getContext(), ProfileActivity.class);
                    intent.putExtra("showEditDialog", true);
                    mView.startActivityWithAnimation(intent);
                }

                CharacterImage ci = new CharacterImage(mView.getContext(), user);
                mView.setImage(ci.getDrawable());
                mView.setNick(user.getNick());
                mView.setSlogan(user.getSlogan());
                mView.setFriendsCount(user.getFriendsCount());
                mView.setDuelsCount(user.getDuelsCount());
                mView.setRank(user.getRank());
                mView.setOpenDuels(user.getOpenDuels());
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
