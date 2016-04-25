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

public class EventActivity extends AppCompatActivity {
    private List<String> fruits = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        createExampleData();
        initSuggestionsView();
        initObserver();
    }

    private void initSuggestionsView(){
        ListView suggestionsList = (ListView) findViewById(R.id.lstSuggestions);
        suggestionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((EditText) findViewById(R.id.txtSearchField)).setText(((TextView)view).getText());
            }
        });

        suggestionsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, suggestions));
    }

    private void initObserver(){
        Observable<OnTextChangeEvent> autoCompletion = WidgetObservable.text((EditText) findViewById(R.id.txtSearchField));

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
        ArrayAdapter<String> suggestionsListAdapter = (ArrayAdapter<String>)((ListView) findViewById(R.id.lstSuggestions)).getAdapter();
        TextView suggestionInfo = (TextView) findViewById(R.id.lblSuggestionInfo);

        if (text.isEmpty()){
            suggestions.clear();
            suggestionsListAdapter.notifyDataSetChanged();
            suggestionInfo.setText("");
            return;
        }

        suggestions.clear();
        for(String s : fruits){
            if(s.toLowerCase().contains(text.toLowerCase())){
                suggestions.add(s);
            }
        }

        suggestionsListAdapter.notifyDataSetChanged();
        suggestionInfo.setText(suggestions.size() + " Treffer");
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
}