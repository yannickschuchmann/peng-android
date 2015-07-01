package com.yannickschuchmann.peng.app.views.views;

import com.yannickschuchmann.peng.model.entities.User;

/**
 * Created by yannick on 30.06.15.
 */
public interface ToolbarBackView extends MVPView {
    public void onToolbarBackClicked();
    public void setToolbarTitle(String title);
}