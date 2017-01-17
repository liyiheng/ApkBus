package com.apkbus.mobile.apis;


import android.util.Log;

import com.apkbus.mobile.bean.event.Logout;
import com.apkbus.mobile.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 系统级错误码参照：
 * 错误码	说明
 * 10001	appkey不合法
 * 10020	接口维护
 * 10021	接口停用
 * 200	成功
 * 应用级错误码参照：
 * 错误码	说明
 * 22801	查询不到相关数据
 * 22808	uid不允许为空
 * 22809	token不允许为空
 * 22810	用户未登录或token已过期
 * 22811	用户数据项不允许为空
 * 22812	用户数据value不允许为空
 * 22813	用户数据项必须符合base64编码
 * 22814	用户数据值必须符合base64编码
 * 22815	用户数据项长度超过最大限制
 * 22816	用户数据项的值长度超过最大限制
 * 继承Subscriber
 * 用于对错误码进行统一的判断
 * Created by liyiheng on 16/9/14.
 */
public abstract class LSubscriber<T> extends Subscriber<T> {


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            String err = null;
            try {
                err = exception.response().errorBody().string();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            int httpStatusCode = exception.code();
            Log.e("LSubscriber", "onError: httpStatusCode:  " + String.valueOf(httpStatusCode));
            Log.e("LSubscriber", "onError:   responseBody:  " + err);
            String code = "0";
            //String msg = "";
            if (err != null) {
                try {
                    JSONObject object = new JSONObject(err);
                    code = object.optString("retCode");
                   // msg = object.optString("msg");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
//            switch (httpStatusCode) {
//                case 10001:
//                    //appkey不合法
//                    break;
//                case 10020:
//                    //接口维护
//                    break;
//                case 10021:
//                    //接口停用
//                    break;
//            }
            MobError mobError = MobError.NOT_FOUND;
            switch (code) {
                case "22801":
                    mobError = MobError.NOT_FOUND;
                    break;
                case "22808":
                    mobError = MobError.NULL_UID;
                    break;
                case "22809":
                    mobError = MobError.NULL_TOKEN;
                    break;
                case "22810":
                    RxBus.getInstance().post(new Logout());
                    mobError = MobError.INVALID_TOKEN;
                    break;
                case "22811":
                    mobError = MobError.NULL_USER_ITEM;
                    break;
                case "22812":
                    mobError = MobError.NULL_USER_ITEM_VALUE;
                    break;
                case "22813":
                    mobError = MobError.UN_BASE64_USER_ITEM;
                    break;
                case "22814":
                    mobError = MobError.UN_BASE64_USER_ITEM_VALUE;
                    break;
                case "22815":
                    mobError = MobError.TOO_LONG_USER_ITEM;
                    break;
                case "22816":
                    mobError = MobError.TOO_LONG_USER_ITEM_VALUE;
                    break;
            }
            onError(httpStatusCode, mobError);
        }
    }


    protected abstract void onError(int httpStatusCode, MobError error);
}
