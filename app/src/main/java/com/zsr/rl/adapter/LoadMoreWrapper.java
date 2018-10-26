package com.zsr.rl.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class LoadMoreWrapper<T> extends RecyclerView.Adapter {
    private RecyclerView.Adapter adapter;
    private int footLayoutResId;
    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public static final int LOADING = 1;
    // 加载完成
    public static final int LOADING_COMPLETE = 2;
    // 加载到底
    public static final int LOADING_END = 3;


    public LoadMoreWrapper(List<T> data, int itemLayoutResId, int footLayoutResId) {
        this.footLayoutResId = footLayoutResId;
        this.adapter = new LoadMoreWrapperAdapter<T>(data, itemLayoutResId) {
            @Override
            void onBindViewHolder1(RecyclerView.ViewHolder viewHolder, int i, T t) {
                updateItemView(viewHolder, i, t);
            }
        };
    }

    protected abstract void updateFootView(View itemView, int loadState);

    public abstract void updateItemView(RecyclerView.ViewHolder viewHolder, int position, T t);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(footLayoutResId, viewGroup, false);
            return new FootViewHolder(view);
        }
        return adapter.onCreateViewHolder(viewGroup, i);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position == adapter.getItemCount()) {
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            footViewHolder.setLoadState(loadState);
        } else {
            adapter.onBindViewHolder(viewHolder, position);
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public void setRefresh() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {


        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setLoadState(int loadState) {
            updateFootView(itemView, loadState);
        }

    }
}
