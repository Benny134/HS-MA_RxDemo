package de.hs_mannheim.demo.benny.rxdemo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Benny on 21.04.2016.
 */
public class Cache {
    private static Cache ourInstance = new Cache();

    private List<Integer> cache = new ArrayList<>();

    public static Cache getInstance() {
        return ourInstance;
    }

    private Cache() {
    }

    public Observable<Integer> getCache(){
        if(!cache.isEmpty()){
            return Observable.from(cache);
        }

        return Observable.empty();
    }

    public void cachItem(Integer store){
        cache.add(store);
    }

    public void invalidCache(){
        cache.clear();
    }
}
