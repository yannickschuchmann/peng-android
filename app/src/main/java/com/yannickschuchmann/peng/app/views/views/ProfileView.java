package com.yannickschuchmann.peng.app.views.views;

import android.graphics.drawable.Drawable;
import com.yannickschuchmann.peng.model.entities.Duel;

/**
 * Created by yannick on 30.06.15.
 */
public interface ProfileView extends ToolbarBackView {
    public void setNick(String nick);
    public void setSlogan(String slogan);
    public void setImage(Drawable image);
    public void startDuelActivity(Duel duel);
}
