package com.yannickschuchmann.peng.app.views.activities;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Bind;
import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.views.LoadingView;

/**
 * Created by yannick on 15.07.15.
 */
public class LoadingActivity extends TransitionActivity implements LoadingView {
    @Bind(R.id.progress_overlay)
    FrameLayout overlay;

    @Override
    public void showLoading() {
        overlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        overlay.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
