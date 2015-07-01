package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.views.views.MainView;
import com.yannickschuchmann.peng.app.views.views.SettingsView;
import com.yannickschuchmann.peng.model.entities.User;
import com.yannickschuchmann.peng.model.rest.RestSource;
import com.yannickschuchmann.peng.model.rest.services.UserService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yannick on 30.06.15.
 */
public class SettingsPresenter extends Presenter {

    private SettingsView mView;


    public SettingsPresenter(SettingsView view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.setToolbarTitle("Einstellungen");
    }

    @Override
    public void stop() {
    }

}
