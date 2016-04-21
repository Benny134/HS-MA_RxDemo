package de.hs_mannheim.demo.benny.rxdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private List<String> fruits = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickObserverDemo(View view){
        startActivity(new Intent(this, ObserverActivity.class));
    }

    public void onClickCacheDemo(View view){
        startActivity(new Intent(MainActivity.this, CacheActivity.class));
    }
}