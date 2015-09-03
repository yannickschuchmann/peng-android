package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.views.views.SettingsView;

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
