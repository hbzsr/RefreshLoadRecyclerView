package com.zsr.rl.sample;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zsr.rl.adapter.LoadMoreWrapper;
import com.zsr.rl.databinding.*;

import java.util.List;

public class MyAdapter extends LoadMoreWrapper<String> {
    LayoutRefreshFooterBinding binding;
    ItemLayoutBinding item;

    public MyAdapter(List<String> data, int itemLayoutResId, int footLayoutResId) {
        super(data, itemLayoutResId, footLayoutResId);
    }

    @Override
    protected void updateFootView(View itemView, int loadState) {
        binding = DataBindingUtil.bind(itemView);
        switch (loadState) {
            case LoadMoreWrapper.LOADING:
                binding.pbLoading.setVisibility(View.VISIBLE);
                binding.tvLoading.setVisibility(View.VISIBLE);
                binding.llEnd.setVisibility(View.GONE);
                break;
            case LoadMoreWrapper.LOADING_COMPLETE:
                binding.pbLoading.setVisibility(View.INVISIBLE);
                binding.tvLoading.setVisibility(View.INVISIBLE);
                binding.llEnd.setVisibility(View.GONE);
                break;
            case LoadMoreWrapper.LOADING_END:
                binding.pbLoading.setVisibility(View.GONE);
                binding.tvLoading.setVisibility(View.GONE);
                binding.llEnd.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void updateItemView(RecyclerView.ViewHolder viewHolder, int position, String str) {
        item = DataBindingUtil.bind(viewHolder.itemView);
        item.itemText.setText(str);
    }
}
