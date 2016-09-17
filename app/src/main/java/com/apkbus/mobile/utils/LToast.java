package com.apkbus.mobile.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by liyiheng on 16/9/14.
 */
public class LToast {
    public static void show(Context context, String text) {
        if (TextUtils.isEmpty(text)) return;
        if (toast == null) {
            // Must use context of the application to prevent memory leaks.
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    public static void show(Context context, String text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, duration);
        }
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }

    private static Toast toast;
}
