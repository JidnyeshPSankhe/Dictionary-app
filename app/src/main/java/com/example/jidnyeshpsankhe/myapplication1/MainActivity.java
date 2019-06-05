package com.example.jidnyeshpsankhe.myapplication1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    EditText search;
    Button btn;
    API_service mApi;
    Context homeCtx;
    private static final String TAG = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.editText);
        btn = findViewById(R.id.button_start);
        mApi = API_service.getInstance();
        homeCtx = this;
        mApi.initAPI(homeCtx);
        btn.setText("Let's Begin!");
        doSomething();
        //btn.setOnClickListener();
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
}
