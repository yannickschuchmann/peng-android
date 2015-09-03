package com.yannickschuchmann.peng.app.views.views;

import com.yannickschuchmann.peng.model.entities.Duel;

/**
 * Created by yannick on 13.07.15.
 */
public interface SensorView extends MVPView {
    public void receiveAction(Duel duel, boolean update);
    public void setupView(Duel duel, boolean update);
}
