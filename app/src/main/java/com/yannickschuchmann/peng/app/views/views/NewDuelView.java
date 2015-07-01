package com.yannickschuchmann.peng.app.views.views;

import com.yannickschuchmann.peng.model.entities.Duel;

/**
 * Created by yannick on 30.06.15.
 */
public interface NewDuelView extends ToolbarBackView {
    public void startDuelActivity(Duel duel);
}
