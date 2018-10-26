package com.zsr.rl.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.zsr.rl.listener.OnRecyclerScrollListener;

public class RecyclerRefreshLoadView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    OnRefreshLoadListener onRefreshLoadListener;

    public RecyclerRefreshLoadView(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerRefreshLoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnRefreshListener(this);
        recyclerView = new RecyclerView(context);
        addView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addOnScrollListener(new OnRecyclerScrollListener() {
            @Override
            public void onLoadMore() {
                if (onRefreshLoadListener != null)
                    onRefreshLoadListener.onLoad();
            }
        });
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void setOnRefreshLoadListener(OnRefreshLoadListener onRefreshLoadListener) {
        this.onRefreshLoadListener = onRefreshLoadListener;
    }

    @Override
    public void onRefresh() {
        if (onRefreshLoadListener != null)
            onRefreshLoadListener.onRefresh();
    }

    public interface OnRefreshLoadListener {
        void onRefresh();

        void onLoad();
    }
}
