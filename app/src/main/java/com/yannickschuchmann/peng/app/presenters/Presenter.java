package com.yannickschuchmann.peng.app.presenters;

/**
 * Created by yannick on 30.06.15.
 */
public abstract class Presenter {

    /**
     * Called when the presenter is initialized
     */
    public abstract void start ();

    /**
     * Called when the presenter is stop, i.e when an activity
     * or a fragment finishes
     */
    public abstract void stop ();
}
