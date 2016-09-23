package com.apkbus.mobile.bean;

/**
 * Created by liyiheng on 16/9/23.
 */

public enum UserProfile {
    NICKNAME(User.ITEM_NAME);

    private String value;




    UserProfile(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }
}
