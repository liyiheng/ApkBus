package com.apkbus.mobile.constract;

import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.BaseView;
import com.apkbus.mobile.bean.Bean;

import java.util.List;

/**
 * Created by liyiheng on 16/9/19.
 */
public interface ArticleContract {
    interface View extends BaseView<Presenter> {
        /**
         * Update UI.
         * @param data
         */
        void updateData(List<Bean> data);

    }

    interface Presenter extends BasePresenter {
        void initData();

    }
}
