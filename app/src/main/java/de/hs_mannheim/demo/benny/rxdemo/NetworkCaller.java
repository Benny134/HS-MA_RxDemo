package de.hs_mannheim.demo.benny.rxdemo;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Benny on 21.04.2016.
 */
public class NetworkCaller {
    private static NetworkCaller ourInstance = new NetworkCaller();

    public static NetworkCaller getInstance() {
        return ourInstance;
    }

    private NetworkCaller() {
    }

    public Observable<Integer> requestApi(){
        final Cache cache = Cache.getInstance();

        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i = 0; i < 100; i++){
                    subscriber.onNext((int)(Math.random() * 100));
                }
            }
        }).doOnEach(new Action1<Notification<? super Integer>>() {
            @Override
            public void call(Notification<? super Integer> notification) {
                cache.cachItem((Integer)notification.getValue());
            }
        });
    }
}
