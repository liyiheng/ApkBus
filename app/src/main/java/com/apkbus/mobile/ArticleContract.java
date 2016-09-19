package com.apkbus.mobile;

import com.apkbus.mobile.bean.Blog;
import com.apkbus.mobile.bean.FirstBean;

import java.util.List;

/**
 * Created by liyiheng on 16/9/19.
 */
public interface ArticleContract {
    interface View extends BaseView<Presenter> {
        void updateData(List<FirstBean> data);
        void updateData2(List<Blog> data);

    }

    interface Presenter extends BasePresenter {
        void initData();

    }
}
