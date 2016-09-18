package com.apkbus.mobile.bean;

import java.util.List;

/**
 * Created by liyiheng on 16/9/18.
 */
public class BeanWrapper<T> {
    private int status;
    private List<T> res;

    public List<T> getRes() {
        return res;
    }

    public void setRes(List<T> res) {
        this.res = res;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
