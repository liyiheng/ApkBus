package com.apkbus.mobile.bean;

/**
 * Created by liyiheng on 16/9/23.
 */

public class User {
    public static final String ITEM_NAME = "nickname";
    private String username;
    private String nickname;

    public static String getItemName() {
        return ITEM_NAME;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
