package com.apkbus.mobile.bean.event;

/**
 * Signal to notify a recycler to scroll to position 0.
 * Created by liyiheng on 16/9/28.
 */

public class ScrollSignal {
    /**
     * Position of the tab notified.
     */
    public int tabPosition;

    public ScrollSignal(int tabPosition) {
        this.tabPosition = tabPosition;
    }
}
