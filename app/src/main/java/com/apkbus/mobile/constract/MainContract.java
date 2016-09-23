package com.apkbus.mobile.constract;

import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.BaseView;
import com.apkbus.mobile.bean.User;

/**
 * MainActivityContract
 * Created by liyiheng on 16/9/23.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void bindData(User data);
    }

    interface Presenter extends BasePresenter {
        void initData();
    }
}
