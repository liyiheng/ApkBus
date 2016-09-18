package com.apkbus.mobile.utils;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Implementing an Event Bus With RxJava
 * Created by liyiheng on 16/9/18.
 */
public class RxBus {
    private static volatile RxBus instance;
    private final SerializedSubject<Object, Object> subject;
//    private final SerializedSubject<Object, Object> stickySubject;

    private RxBus() {
        subject = new SerializedSubject<>(PublishSubject.create());
//         stickySubject = new SerializedSubject<>(BehaviorSubject.create());
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }
//        if (this.hasObservers()) subject.onNext(event);

    /**
     * Pass any event down to event listeners.
     */
    public void post(Object object) {
        subject.onNext(object);
    }

//    public void postStickEvent(Object event) {
//        stickySubject.onNext(event);
//    }

    private <T> Observable<T> toObservable(final Class<T> type) {
        return subject.ofType(type);
    }

    public boolean hasObservers() {
        return subject.hasObservers();
    }

//    @Deprecated
//    public boolean hasStickObservers() {
//        return stickySubject.hasObservers();
//    }

    /**
     * Subscribe
     */
    public <T> Subscription toSubscription(Class<T> type, Action1<T> action1, Scheduler scheduler) {
        return RxBus.getInstance()
                .toObservable(type).onBackpressureBuffer()
                .observeOn(scheduler)
                .subscribe(action1);
    }

    /**
     * Subscribe
     */
    public <T> Subscription toSubscription(Class<T> type, Action1<T> action1) {
        return RxBus.getInstance()
                .toObservable(type).onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * Subscribe
     */
    public <T> Subscription toSubscription(Class<T> type, Action1<T> action1, Action1<Throwable> err) {
        return RxBus.getInstance()
                .toObservable(type).onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, err);
    }

//    public <T> Subscription toStickObservable(Class<T> type, Action1<T> action1) {
//        return RxBus.getInstance()
//                .toObservable(type)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(action1);
//    }
}
