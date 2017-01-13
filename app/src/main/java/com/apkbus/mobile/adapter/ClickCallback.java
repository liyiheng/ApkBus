package com.apkbus.mobile.adapter;

/**
 * Created by liyiheng on 17/1/13.
 */
public interface ClickCallback<T> {
    void onItemClick(T bean);

    boolean onItemLongClick(T bean);
}

