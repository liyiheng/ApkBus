package com.apkbus.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.apkbus.mobile.bean.LoginInfo;

/**
 * Created by liyiheng on 16/9/22.
 */

public class SharedPreferencesHelper {
    private SharedPreferences preferences;
    private static final String SP_NAME = "apk_bus";
    private static SharedPreferencesHelper singleton;

    private SharedPreferencesHelper(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (singleton == null) {
            synchronized (SharedPreferencesHelper.class) {
                if (singleton == null) {
                    singleton = new SharedPreferencesHelper(context);
                }
            }
        }
        return singleton;
    }


    /**
     * 保存String类型数据
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * 读取String类型数据
     *
     * @param key
     */
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    /**
     * 保存Int类型数据
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 读取Int类型数据
     *
     * @param key
     */
    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    /**
     * 保存Boolean类型数据
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 读取Boolean类型数据
     *
     * @param key
     */
    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public void setAutoRenew(boolean autoRenew) {
        putBoolean("auto_renew", autoRenew);
        needAutoRenew = autoRenew;
    }

    private boolean needAutoRenew = false;

    public boolean needAutoRenew() {
        if (needAutoRenew) return true;
        return getBoolean("auto_renew", true);
    }

    private static LoginInfo loginInfo;

    public void saveToken(LoginInfo info) {
        loginInfo = info;
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("uid", info.getUid());
        edit.putString("token", info.getToken());
        edit.commit();
    }

    public LoginInfo getToken() {
        if (loginInfo == null) {
            loginInfo = new LoginInfo();
            String uid = preferences.getString("uid", null);
            String token = preferences.getString("token", null);
            loginInfo.setToken(token);
            loginInfo.setUid(uid);
        }
        return loginInfo;
    }
}
