package com.apkbus.mobile.utils;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;

/**
 * Created by liyiheng on 17/1/12.
 */

public class BindingUtil {
    @BindingAdapter({"imageUrl"})
    public static void loadImg(SimpleDraweeView v, String url) {
        v.setImageURI(url);
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BindingAdapter({"time"})
    public static void setTimeText(TextView v, long l) {
        v.setText(simpleDateFormat.format(l));
    }
}
