package com.yannickschuchmann.peng.app.views.views;

import com.yannickschuchmann.peng.model.entities.Duel;
import com.yannickschuchmann.peng.model.entities.User;

/**
 * Created by yannick on 30.06.15.
 */
public interface DuelAdapterView extends MVPView {
    public void onDuelClicked(Duel duel);
}
