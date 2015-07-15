package com.yannickschuchmann.peng.app.events;

import com.yannickschuchmann.peng.model.entities.Duel;

/**
 * Created by yannick on 15.07.15.
 */
public class ActionPostedEvent {
    public final Duel duel;

    public ActionPostedEvent(Duel duel) {
        this.duel = duel;
    }
}
