package com.apkbus.mobile.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by liyiheng on 16/9/29.
 */

public abstract class ScrollToEndListener extends RecyclerView.OnScrollListener {


    /**
     * load more
     */
    public abstract void onLoadMore();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
        int itemCount = mLayoutManager.getItemCount();


        int lastVisiblePosition = 0;
        if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisiblePosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            //} else if (mLayoutManager instanceof GridLayoutManager) {
            //   lastVisiblePosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mLayoutManager;
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            for (int i : lastPositions) {
                if (i >= lastVisiblePosition) {
                    lastVisiblePosition = i;
                }
            }
        } else {
            lastVisiblePosition = mLayoutManager.getItemCount() - 1;
        }
        if (lastVisiblePosition + 1 == itemCount) {
            onLoadMore();
        }
    }

}
