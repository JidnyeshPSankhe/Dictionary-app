package com.example.jidnyeshpsankhe.myapplication1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import java.util.List;

public class SimpleRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> items;
    private int itemLayout;
    private SimpleRecyclerViewAdapterListener<T> listener;

    public class SimpleRecyclerViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private T item;

        public SimpleRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            final SimpleRecyclerViewHolder holder = this;
        }

        public void setItem(T item, int position) {
            this.item = item;
            this.position = position;
        }

    }

    public interface SimpleRecyclerViewAdapterListener<T> {
        void bindItemToView(T item, int position, RecyclerView.ViewHolder viewHolder);
        void recyclerItemClicked(T item, int position, RecyclerView.ViewHolder viewHolder);
    }

    public SimpleRecyclerViewAdapter(List<T> items, int itemLayout, SimpleRecyclerViewAdapterListener<T> listener) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(itemLayout, parent, false);
        return new SimpleRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        T item = items.get(position);
        ((SimpleRecyclerViewHolder)viewHolder).setItem(item, position);
        listener.bindItemToView(item, position, viewHolder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
