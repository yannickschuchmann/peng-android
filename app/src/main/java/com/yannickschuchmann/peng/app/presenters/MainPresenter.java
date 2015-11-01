package com.yannickschuchmann.peng.app.presenters;

import android.content.Intent;
import android.widget.Toast;
import com.yannickschuchmann.peng.app.CurrentUser;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.socket.SocketAPI;
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
        mView.showLoading();
        this.setup();
        mView.hideLoading();
    }

    public void setup() {
        User user = CurrentUser.getInstance(mView.getContext()).getUser();

        SocketAPI.getInstance(mView.getContext());

        if (!(user == null) && (user.getNick() == null || user.getNick().equals(""))) {
            Intent intent;
            intent = new Intent(mView.getContext(), ProfileActivity.class);
            intent.putExtra("showEditDialog", true);
            intent.putExtra("userId", CurrentUser.getInstance(mView.getContext()).getUserId());
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

    public void update() {
        mView.showLoading();
        mService.getUser(CurrentUser.getInstance(mView.getContext()).getUserId(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                CurrentUser.getInstance(mView.getContext()).setUser(user);
                setup();
                mView.hideLoading();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(
                        mView.getContext().getApplicationContext(),
                        R.string.toastFailureMessage,
                        Toast.LENGTH_SHORT
                ).show();
                mView.hideLoading();
            }
        });
    }


    @Override
    public void stop() {
    }

}
