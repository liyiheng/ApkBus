package com.apkbus.mobile.bean;

/**
 * Created by liyiheng on 16/9/23.
 */

public class MobWrapper<T> {
    /**
     * retCode : 200
     * msg : success
     * uid : e5b0d1b60461ea4605cf27947f739bce
     */

    private String retCode;
    private String msg;
    /**
     * Only used at the register api.
     */
    private String uid;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
