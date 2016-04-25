package de.hs_mannheim.demo.benny.rxdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> fruits = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickObserverDemo(View view){
        startActivity(new Intent(this, EventActivity.class));
    }

    public void onClickCacheDemo(View view){
        startActivity(new Intent(MainActivity.this, CacheActivity.class));
    }
}