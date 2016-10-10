package com.apkbus.mobile.constract;

import android.support.design.widget.TabLayout;

import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.BaseView;
import com.apkbus.mobile.bean.User;
import com.apkbus.mobile.bean.UserProfile;

/**
 * MainActivityContract
 * Created by liyiheng on 16/9/23.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void bindData(User data);
        void showAD();
    }

    interface Presenter extends BasePresenter {
        void initData();

        void setUserProfile(UserProfile item, String value);

        /**
         * Send a signal while clicking current tab.
         *
         * @param currentTab The tab clicked.
         */
        void sendScrollSignal(TabLayout.Tab currentTab);

        void onADClosed();

        void pageScrolled();
    }
}
