package com.apkbus.mobile.utils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.apkbus.mobile.R;

/**
 * Created by liyiheng on 16/9/19.
 */
public class SwipeRefresh {
    private SwipeRefresh() {
    }

    public static void initSwipeRefreshLayout(SwipeRefreshLayout view, Context context) {
        view.setProgressViewOffset(false, 0, DensityUtil.dp2px(context, 30));
        view.setColorSchemeResources(R.color.colorPrimary);
    }
}
