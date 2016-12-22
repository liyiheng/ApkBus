package com.apkbus.mobile.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.event.ScrollSignal;
import com.apkbus.mobile.utils.RxBus;
import com.luolc.emojirain.EmojiRainLayout;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChristmasFragment extends BaseFragment {


    public ChristmasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_christmas, container, false);
    }

    private EmojiRainLayout mContainer;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // bind view
        mContainer = (EmojiRainLayout) view.findViewById(R.id.group_emoji_container);

        // add emoji sources
        mContainer.addEmoji(R.drawable.emoji_1_3);
        mContainer.addEmoji(R.drawable.emoji_2_3);
        mContainer.addEmoji(R.drawable.emoji_3_3);
        mContainer.addEmoji(R.drawable.emoji_4_3);
        mContainer.addEmoji(R.drawable.emoji_5_3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Subscription subscription = RxBus.getInstance().toSubscription(ScrollSignal.class, new Action1<ScrollSignal>() {
            @Override
            public void call(ScrollSignal scrollSignal) {
                if (scrollSignal.tabPosition == 0) {
                    mContainer.startDropping();
                    Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            mContainer.stopDropping();
                        }
                    });
                }
            }
        });
        mSubscriptions.add(subscription);
    }
}
