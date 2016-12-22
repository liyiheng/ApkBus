package com.apkbus.mobile;

import android.app.Application;
import android.graphics.Color;

import com.apkbus.mobile.apis.Constants;
import com.apkbus.mobile.utils.LImageLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.mobapi.MobAPI;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.sharesdk.framework.ShareSDK;

public class BusApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        MobAPI.initSDK(this, Constants.MOBAPI_KEY);
        ShareSDK.initSDK(this, Constants.MOBAPI_KEY);

        @SuppressWarnings("deprecation") int colorPrimary = getResources().getColor(R.color.colorPrimary);
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(0xFA, 0xFA, 0xFA))
                .setTitleBarTextColor(Color.BLACK)
                .setTitleBarIconColor(Color.BLACK)
                .setFabNornalColor(colorPrimary)
                .setFabPressedColor(Color.BLUE)
                .setCheckSelectedColor(colorPrimary)
                .build();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(false)
                .setEnablePreview(true).build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new LImageLoader(this), theme)
                .setNoAnimcation(true)
                .setFunctionConfig(functionConfig).build();
        GalleryFinal.init(coreConfig);
    }
}
