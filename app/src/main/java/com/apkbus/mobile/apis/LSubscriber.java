package com.apkbus.mobile.apis;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 继承Subscriber
 * 用于对错误码进行统一的判断
 * 类似于XVolley中,创建LCallBack继承XCallBack的做法
 * Created by liyiheng on 16/6/25.
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
            int code = -2000;
            String msg = "";
            if (err != null) {
                try {
                    JSONObject object = new JSONObject(err);
                    code = object.optInt("code");
                    msg = object.optString("message");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            switch (code) {
                case 1:// 1	invalid parameters
                    msg = "参数错误";
                    break;
            }
            if (!"".equals(msg)) {
                //SLToast.Show(SLapp.getContext(), msg);
            }
            onError(httpStatusCode,code);
        }
    }


    protected abstract void onError(int httpStatusCode,int code);
}
