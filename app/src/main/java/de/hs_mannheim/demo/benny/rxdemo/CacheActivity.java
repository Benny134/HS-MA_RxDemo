package de.hs_mannheim.demo.benny.rxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;

public class CacheActivity extends AppCompatActivity {
    private ArrayList<Integer> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);

        initListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    private void initListView(){
        ListView cachingItemsList = (ListView) findViewById(R.id.lstCachingItems);

        cachingItemsList.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data));
    }

    public void onClickInvalidCache(View view){
        Cache.getInstance().invalidCache();
    }

    public void onClickRefresh(View view){
        refresh();
    }

    public void refresh(){
        data.clear();
        final ArrayAdapter<String> cachingItemsAdapter = ((ArrayAdapter<String>)((ListView)findViewById(R.id.lstCachingItems)).getAdapter());

        Cache.getInstance().getCache()
                .switchIfEmpty(NetworkCaller.getInstance().requestApi())
                .onBackpressureBuffer(200)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Observable", e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        data.add(integer);
                        cachingItemsAdapter.notifyDataSetChanged();
                    }
                });
    }
}
