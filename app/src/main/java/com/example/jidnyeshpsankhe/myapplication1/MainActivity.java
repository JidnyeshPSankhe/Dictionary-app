package com.example.jidnyeshpsankhe.myapplication1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.jidnyeshpsankhe.myapplication1.Adapters.SimpleRecyclerViewAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements API_service.Api_results, SimpleRecyclerViewAdapter.SimpleRecyclerViewAdapterListener<SearchObject> {
    EditText search;
    Button btn;
    API_service mApi;
    Context homeCtx;
    private API_service.Api_results listener;
    private RecyclerView recyclerView;
    private SimpleRecyclerViewAdapter<SearchObject> recyclerAdapter;
    private static final String TAG = "Main Activity";
    List<SearchObject> answer = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.editText);
        btn = findViewById(R.id.button_start);
        mApi = API_service.getInstance();
        homeCtx = this;
        listener = (API_service.Api_results) homeCtx;
        mApi.initAPI(homeCtx,listener);
        btn.setText("Let's Begin!");
        doSomething();
        //btn.setOnClickListener();
        answer.add(new SearchObject("Hello",2,3));
        recyclerView = findViewById(R.id.recView);
        recyclerAdapter = new SimpleRecyclerViewAdapter<>(answer, R.layout.card_text, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(homeCtx));
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void doSomething(){
//        btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "Pressed");
//            }});

        btn.setOnClickListener(btn1 -> {
            //mApi.execute(search.getText().toString());
            mApi.getResults(search.getText().toString(), homeCtx);
        });
    }

    @Override
    public void getResults(List<SearchObject> result) {
        answer = result;
        for(int i = 0; i< result.size();i++){
            Log.e(TAG, answer.get(i).getDefinitipn());
;        }
    }

    @Override
    public void bindItemToView(SearchObject item, int position, RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void recyclerItemClicked(SearchObject item, int position, RecyclerView.ViewHolder viewHolder) {

    }
}
