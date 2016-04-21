package de.hs_mannheim.demo.benny.rxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import rx.schedulers.TimeInterval;

public class CacheActivity extends AppCompatActivity {
    private ArrayList<Long> data = new ArrayList<>();
    private ArrayList<Long> cache = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);

        initSuggestionsView();
        initObserver();
        refresh();
    }

    private void initSuggestionsView(){
        ListView autoCompletionSuggestions = (ListView) findViewById(R.id.lstCachingItems);

        autoCompletionSuggestions.setAdapter(new ArrayAdapter<Long>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data));
    }

    private void initObserver(){
        for(int i = 0; i < 100; i++){
            cache.add((long)(Math.random() * 100));
        }

        Observable<Long> cachingObserver = Observable.from(data);
    }

    public void onClickRefresh(View view){
        refresh();
    }

    public void refresh(){
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.tglWithCaching);
        data.clear();
        if(toggleButton.isChecked()){
            data.addAll(cache);
        }else{
            for(int i = 0; i < 100; i++){
                data.add((long)(Math.random() * 100));
            }
        }

        ((ArrayAdapter<String>)((ListView)findViewById(R.id.lstCachingItems)).getAdapter()).notifyDataSetChanged();
    }
}
