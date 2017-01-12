package com.apkbus.mobile.utils;

import android.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by liyiheng on 17/1/12.
 */

public class BindingUtil {
    @BindingAdapter({"imageUrl"})
    public static void loadImg(SimpleDraweeView v, String url) {
        v.setImageURI(url);
    }
}
