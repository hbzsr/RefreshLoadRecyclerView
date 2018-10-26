package com.zsr.rl.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class LoadMoreWrapperAdapter<T> extends RecyclerView.Adapter {
    private List<T> data;
    private int layoutResId;

    public LoadMoreWrapperAdapter(List<T> data, int layoutResId) {
        this.data = data;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        onBindViewHolder1(viewHolder, i,data.get(i));
    }

    abstract void onBindViewHolder1(RecyclerView.ViewHolder viewHolder, int i,T t);

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
