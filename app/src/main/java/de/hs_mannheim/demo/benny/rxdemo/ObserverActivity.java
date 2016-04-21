package de.hs_mannheim.demo.benny.rxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ObserverActivity extends AppCompatActivity {
    private List<String> fruits = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        createExampleData();
        initSuggestionsView();
        initObserver();
    }

    private void initSuggestionsView(){
        ListView autoCompletionSuggestions = (ListView) findViewById(R.id.listView);
        autoCompletionSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((EditText) findViewById(R.id.txtAutoCompletion)).setText(((TextView)view).getText());
            }
        });

        autoCompletionSuggestions.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, suggestions));
    }

    private void initObserver(){
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

    private void createExampleData(){
        fruits.add("Trauben");
        fruits.add("Apfel");
        fruits.add("Äpfel");
        fruits.add("Bananen");
        fruits.add("Birnen");
        fruits.add("Tomaten");
        fruits.add("Kirschen");
        fruits.add("Melonen");
        fruits.add("Pfirsiche");
        fruits.add("Orangen");
        fruits.add("Kiwis");
    }

    private void search(String text){
        ListView autoCompletionSuggestions = (ListView) findViewById(R.id.listView);
        TextView infoSuggestions = (TextView) findViewById(R.id.lblSuggestion);

        if (text.isEmpty()){
            suggestions.clear();
            ((ArrayAdapter<String>)autoCompletionSuggestions.getAdapter()).notifyDataSetChanged();
            infoSuggestions.setText("");
            return;
        }

        suggestions.clear();
        for(String s : fruits){
            if(s.toLowerCase().contains(text.toLowerCase())){
                suggestions.add(s);
            }
        }

        ((ArrayAdapter<String>)autoCompletionSuggestions.getAdapter()).notifyDataSetChanged();
        infoSuggestions.setText(suggestions.size() + " Treffer");
    }
}