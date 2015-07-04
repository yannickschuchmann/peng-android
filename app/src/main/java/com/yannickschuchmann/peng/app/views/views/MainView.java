package com.yannickschuchmann.peng.app.views.views;

import android.graphics.drawable.Drawable;
import com.yannickschuchmann.peng.model.entities.Duel;

import java.util.List;

/**
 * Created by yannick on 30.06.15.
 */
public interface MainView extends MVPView {
    public void setImage(Drawable image);
    public void setNick(String nick);
    public void setSlogan(String slogan);
    public void setRank(int rank);
    public void setFriendsCount(int count);
    public void setDuelsCount(int count);
    public void setOpenDuels(List<Duel> duels);
}
