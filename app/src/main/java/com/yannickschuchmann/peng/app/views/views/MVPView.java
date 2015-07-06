package com.yannickschuchmann.peng.app.views.views;

import android.content.Intent;

/**
 * Created by yannick on 30.06.15.
 */
public interface MVPView {
    public android.content.Context getContext();
    public void startActivityWithAnimation(Intent intent);
}
