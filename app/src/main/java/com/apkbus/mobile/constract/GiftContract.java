package com.apkbus.mobile.constract;

import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.BaseView;
import com.apkbus.mobile.bean.GiftWrapper;

import java.util.List;

/**
 * Created by liyiheng on 16/9/29.
 */

public interface GiftContract {
    interface View extends BaseView<Presenter> {
        void updateData(List<GiftWrapper.Gift> data);

        void addData(List<GiftWrapper.Gift> data);
    }

    interface Presenter extends BasePresenter {
        void initData();

        void loadMore();
    }
}
