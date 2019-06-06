package com.example.jidnyeshpsankhe.myapplication1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.jidnyeshpsankhe.myapplication1.Adapters.SimpleRecyclerViewAdapter;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements API_service.Api_results, SimpleRecyclerViewAdapter.SimpleRecyclerViewAdapterListener<SearchObject> {

    EditText term;
    Button search;
    ImageButton upvotes_sort;
    ImageButton downvotes_sort;
    API_service mApi;
    Context homeCtx;
    ProgressBar pb;


    private API_service.Api_results listener;
    private RecyclerView recyclerView;
    private SimpleRecyclerViewAdapter<SearchObject> recyclerAdapter;
    private static final String TAG = "Main Activity";
    ArrayList<SearchObject> result_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        term = findViewById(R.id.editText);
        search = findViewById(R.id.button_start);
        upvotes_sort = findViewById(R.id.upvotes_sort);
        downvotes_sort = findViewById(R.id.downvotes_sort) ;

        mApi = API_service.getInstance();
        homeCtx = this;
        listener = (API_service.Api_results) homeCtx;
        mApi.initAPI(homeCtx,listener);
        search.setText("Search");

        initClickListener();
        recyclerView = findViewById(R.id.recView);

        loadRecycle();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
//        savedInstanceState.putBoolean("MyBoolean", true);
//        savedInstanceState.putDouble("myDouble", 1.9);
//        savedInstanceState.putInt("MyInt", 1);
//        savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
//        boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
//        double myDouble = savedInstanceState.getDouble("myDouble");
//        int myInt = savedInstanceState.getInt("MyInt");
//        String myString = savedInstanceState.getString("MyString");
    }

    private void loadRecycle() {
         pb = findViewById(R.id.progressBar);
         pb.setVisibility(View.GONE);

    }

    public void initClickListener(){
        search.setOnClickListener(x -> {
            mApi.getResults(term.getText().toString(), homeCtx);
            pb.setVisibility(View.VISIBLE);

            });
        upvotes_sort.setOnClickListener(x -> {
            Collections.sort(result_list, Collections.reverseOrder());
            recyclerAdapter.notifyDataSetChanged();
            Log.d(TAG,"The first one is"+result_list.get(0).toString());
        });

        downvotes_sort.setOnClickListener(x -> {


        });
    }

    @Override
    public void getResults(ArrayList<SearchObject> result) {
        result_list = result;
        recyclerAdapter = new SimpleRecyclerViewAdapter<>(result_list, R.layout.card_text, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(homeCtx));
        recyclerView.setAdapter(recyclerAdapter);
        if(!result_list.isEmpty()) {
            pb.setVisibility(View.GONE);
            recyclerAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void bindItemToView(SearchObject item, int position, RecyclerView.ViewHolder viewHolder) {

        TextView title = viewHolder.itemView.findViewById(R.id.textviewCardTextTitle);
        TextView upvote = viewHolder.itemView.findViewById(R.id.upvotes);
        TextView downvote = viewHolder.itemView.findViewById(R.id.downvotes);

        title.setText(item.getDefinitipn());
        upvote.setText(String.format(getString(R.string.thumbs_up), item.getUpVotes()));
        downvote.setText(String.format(getString(R.string.thumbs_down), item.getUpVotes()));
    }

    @Override
    public void recyclerItemClicked(SearchObject item, int position, RecyclerView.ViewHolder viewHolder) {

    }
}
