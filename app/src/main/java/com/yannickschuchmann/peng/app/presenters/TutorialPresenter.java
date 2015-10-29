package com.yannickschuchmann.peng.app.presenters;

import com.yannickschuchmann.peng.app.R;
import com.yannickschuchmann.peng.app.views.views.TutorialPagerView;

/**
 * Created by Simon on 25.10.2015.
 */
public class TutorialPresenter extends Presenter{

    private TutorialPagerView mView;

    public TutorialPresenter(TutorialPagerView view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.setToolbarTitle(mView.getContext().getString(R.string.toolbarTitleTutorial));

    }

    @Override
    public void stop() {
    }
}
