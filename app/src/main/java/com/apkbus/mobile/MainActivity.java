package com.apkbus.mobile;

import android.os.Bundle;
import android.view.View;

import com.apkbus.mobile.utils.LToast;

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
        findViewById(R.id.textView).setOnClickListener(
                        (View v) -> {
                            Subscription s = Observable.timer(2, TimeUnit.SECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe((Long aLong) -> LToast.show(mContext, "Test"));
                            compositeSubscription.add(s);
                        }
                );

    }
}
