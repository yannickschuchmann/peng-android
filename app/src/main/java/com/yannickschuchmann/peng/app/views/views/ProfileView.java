package com.yannickschuchmann.peng.app.views.views;

/**
 * Created by yannick on 30.06.15.
 */
public interface ProfileView extends MVPView {
    public void setNick(String nick);
    public void setSlogan(String slogan);
    public void setImage();
}
