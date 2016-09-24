package com.apkbus.mobile.apis;

/**
 * 22801	查询不到相关数据
 * 22808	uid不允许为空
 * 22809	token不允许为空
 * 22810	用户未登录或token已过期
 * 22811	用户数据项不允许为空
 * 
 * 22812	用户数据value不允许为空
 * 22813	用户数据项必须符合base64编码
 * 22814	用户数据值必须符合base64编码
 * 22815	用户数据项长度超过最大限制
 * 22816	用户数据项的值长度超过最大限制
 * Created by liyiheng on 16/9/24.
 */

public enum MobError {
    NOT_FOUND(22801, "查询不到相关数据"),
    NULL_UID(22808, "uid不允许为空"),
    NULL_TOKEN(22809, "token不允许为空"),
    INVALID_TOKEN(22810, "用户未登录或token已过期"),
    NULL_USER_ITEM(22811, "用户数据项不允许为空"),
    NULL_USER_ITEM_VALUE(22812, "用户数据value不允许为空"),
    UN_BASE64_USER_ITEM(22813, "用户数据项必须符合base64编码"),
    UN_BASE64_USER_ITEM_VALUE(22814, "用户数据值必须符合base64编码"),
    TOO_LONG_USER_ITEM(22815, "用户数据项长度超过最大限制"),
    TOO_LONG_USER_ITEM_VALUE(22816, "用户数据项的值长度超过最大限制");


    private int code;
    private String msg;


    MobError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
