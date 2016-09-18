package com.apkbus.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.RxAPI;
import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.FirstBean;
import com.apkbus.mobile.utils.LToast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setOnClickListener((View v) -> {
                    Subscription s = Observable.timer(2, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((Long aLong) -> LToast.show(mContext, "Test"));
                    compositeSubscription.add(s);
                }
        );

        Observable<BeanWrapper<FirstBean>> tttest = RxAPI.getInstance().getDemos();
        LSubscriber<BeanWrapper<FirstBean>> lSubscriber = new LSubscriber<BeanWrapper<FirstBean>>() {
            @Override
            public void onNext(BeanWrapper<FirstBean> firstBeanBeanWrapper) {
                StringBuilder sb = new StringBuilder();
                List<FirstBean> res = firstBeanBeanWrapper.getRes();
                for (FirstBean b : res) {
                    sb.append(b.getFulltitle()).append("\n");
                }
                tv.setText(sb);
            }

            @Override
            protected void onError(int httpStatusCode, int code) {

            }
        };
        tttest.subscribe(lSubscriber);


    }
}
