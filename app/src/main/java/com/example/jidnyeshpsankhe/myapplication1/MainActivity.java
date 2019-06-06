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
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements API_service.Api_results, SimpleRecyclerViewAdapter.SimpleRecyclerViewAdapterListener<SearchObject> {
    EditText search;
    Button btn;
    API_service mApi;
    Context homeCtx;
    ProgressBar pb;
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
        btn.setText("Search");
        doSomething();
        recyclerView = findViewById(R.id.recView);

        loadRecycle();
    }

    private void loadRecycle() {
         pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);
        //progressBar.showContextMenu();
    }

    public void doSomething(){
        btn.setOnClickListener(btn1 -> {
            //mApi.execute(search.getText().toString());
            mApi.getResults(search.getText().toString(), homeCtx);

            });
    }

    @Override
    public void getResults(List<SearchObject> result) {

        recyclerAdapter = new SimpleRecyclerViewAdapter<SearchObject>(result, R.layout.card_text, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(homeCtx));
        recyclerView.setAdapter(recyclerAdapter);
        if(!answer.isEmpty()) {
            recyclerAdapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void bindItemToView(SearchObject item, int position, RecyclerView.ViewHolder viewHolder) {
        Log.e(TAG, "item is"+item.getDownVotes());
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
