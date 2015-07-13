package com.yannickschuchmann.peng.app.views.views;

import android.content.Context;
import android.content.Intent;
import com.yannickschuchmann.peng.app.views.views.MVPView;
import com.yannickschuchmann.peng.model.entities.Duel;

/**
 * Created by yannick on 13.07.15.
 */
public interface SensorView extends MVPView {
    public void setupView(Duel duel);
}
