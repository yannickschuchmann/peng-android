package com.yannickschuchmann.peng.app.views.views;

import com.yannickschuchmann.peng.model.entities.Duel;

/**
 * Created by yannick on 30.06.15.
 */
public interface NewDuelFriendsView extends LoadingToolbarBackView {
    public void startDuelActivity(Duel duel);
}
