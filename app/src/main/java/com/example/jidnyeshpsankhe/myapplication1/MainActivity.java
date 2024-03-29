package com.example.jidnyeshpsankhe.myapplication1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.jidnyeshpsankhe.myapplication1.Adapters.SimpleRecyclerViewAdapter;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements API_service.Api_results, SimpleRecyclerViewAdapter.SimpleRecyclerViewAdapterListener<SearchObject> {


    @BindView(R.id.editText) EditText term;
    @BindView(R.id.button_start) Button search;
    @BindView(R.id.upvotes_sort) ImageButton upvotes_sort;
    @BindView(R.id.downvotes_sort) ImageButton downvotes_sort;
    @BindView(R.id.recView) RecyclerView recyclerView;
    API_service mApi;
    Context homeCtx;
    @BindView(R.id.progressBar) ProgressBar pb;

    private SimpleRecyclerViewAdapter<SearchObject> recyclerAdapter;
    private static final String TAG = "Main Activity";
    ArrayList<SearchObject> result_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVariables();
        initClickListener();
    }

    private void initVariables() {
        mApi = API_service.getInstance();
        homeCtx = this;
        API_service.Api_results listener = (API_service.Api_results) homeCtx;
        mApi.initAPI(homeCtx, listener);
        search.setText(getString(R.string.Button_label));
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("term", term.getText().toString());

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String word = savedInstanceState.getString("term");
        term.setText(word);
    }

    public void initClickListener(){


        search.setOnClickListener(x -> {
            if (term.getText() != null){
                getResult(term.getText().toString());
            }});

        upvotes_sort.setOnClickListener(x -> {
            sort_upvotes(result_list);
            recyclerAdapter.notifyDataSetChanged();
        });

        downvotes_sort.setOnClickListener(x -> {
            sort_downvotes();
        });
    }

    public ArrayList<SearchObject> sort_upvotes(ArrayList<SearchObject> result_list) {
        Collections.sort(result_list, (o1, o2) -> Integer.compare(o2.getUpVotes(),o1.getUpVotes()));
        return result_list;

    }

    public void sort_downvotes() {
        Collections.sort(result_list, (o1, o2) -> Integer.compare(o2.getDownVotes(),o1.getDownVotes()));
        recyclerAdapter.notifyDataSetChanged();

    }

    private void getResult(String text) {
        mApi.getResults(text, homeCtx);
        pb.setVisibility(View.VISIBLE);
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

        title.setText(item.getDefinition());
        upvote.setText(String.valueOf(item.getUpVotes()));
        downvote.setText(String.valueOf( item.getUpVotes()));
    }

    @Override
    public void recyclerItemClicked(SearchObject item, int position, RecyclerView.ViewHolder viewHolder) {

    }
}
