package com.yannickschuchmann.peng.app;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by yannick on 15.07.15.
 */
public class BusProvider {
    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);
    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {}
}
