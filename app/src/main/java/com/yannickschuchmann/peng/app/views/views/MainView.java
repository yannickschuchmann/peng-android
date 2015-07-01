package com.yannickschuchmann.peng.app.views.views;

/**
 * Created by yannick on 30.06.15.
 */
public interface MainView extends MVPView {
    public void setNick(String nick);
    public void setSlogan(String slogan);
    public void setRank(int rank);
    public void setFriendsCount(int count);
    public void setDuelsCount(int count);
}
