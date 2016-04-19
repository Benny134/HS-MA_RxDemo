package de.hs_mannheim.demo.benny.rxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    List<String> fruits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fruits.add("Traube");

        Observable<OnTextChangeEvent> autoCompletion = WidgetObservable.text((EditText) findViewById(R.id.txtAutoCompletion));

        autoCompletion
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<OnTextChangeEvent>() {
                    @Override
                    public void call(OnTextChangeEvent onTextChangeEvent) {
                        search(onTextChangeEvent.text().toString());
                    }
                });
    }

    private void search(String text){
        if (text.isEmpty()) return;

        EditText autoCompletionTextField = (EditText) findViewById(R.id.txtAutoCompletion);
        autoCompletionTextField.setText("HAHA");
    }
}