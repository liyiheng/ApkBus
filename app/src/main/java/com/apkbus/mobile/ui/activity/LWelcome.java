package com.apkbus.mobile.ui.activity;

import com.apkbus.mobile.R;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * Created by liyiheng on 2016/12/22.
 */

public class LWelcome extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorPrimary)
                .page(new TitlePage(R.mipmap.tree,
                        "Merry Christmas")
                )
                .page(new BasicPage(R.mipmap.tree,
                        "Merry Christmas",
                        "again.")
                        .background(R.color.colorAccent)
                )
                .page(new BasicPage(R.mipmap.tree,
                        "At last",
                        "Merry Christmas")
                )
                .swipeToDismiss(true)
                .build();
    }
}
