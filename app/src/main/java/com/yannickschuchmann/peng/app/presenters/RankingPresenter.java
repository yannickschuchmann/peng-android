package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.views.RankingView;

/**
 * Created by yannick on 30.06.15.
 */
public class RankingPresenter extends Presenter {

    private RankingView mView;

    public RankingPresenter(RankingView view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.setToolbarTitle(mView.getContext().getString(R.string.toolbarTitleHighscore));
    }

    @Override
    public void stop() {
    }

}
