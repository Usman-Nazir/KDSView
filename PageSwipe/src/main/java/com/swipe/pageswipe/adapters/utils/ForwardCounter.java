package com.swipe.pageswipe.adapters.utils;

import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class ForwardCounter {
    private TimeUnit timeUnit;
    private Long startValue;
    private int position;
    private Disposable disposable;


    public ForwardCounter(Long startValue, TimeUnit timeUnit, int position) {
        this.timeUnit = timeUnit;
        this.startValue = startValue;
        this.position = position;

    }

    public abstract void onTick(long tickValue);


    public synchronized void start() {

        disposable =Observable.interval(1, timeUnit)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTick, e->{

                });

       /* Observable.zip(
                Observable.range(0, startValue.intValue()),
                Observable.interval(1, timeUnit), (integer, aLong) -> {
                    Long l = startValue - integer;
                    return l;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        onTick(aLong ,orderStatus,counter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        onFinish(position,orderStatus,counter);
                    }
                });*/
    }

    public synchronized void cancel() {
        if (disposable != null) disposable.dispose();
    }
}
