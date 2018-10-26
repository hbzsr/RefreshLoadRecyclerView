package com.zsr.rl.adapter;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.zsr.rl.R;
import com.zsr.rl.databinding.LayoutRefreshFooterBinding;

import java.util.List;

public abstract class DefaultFootLoadMoreWrapper<T> extends LoadMoreWrapper<T> {
    LayoutRefreshFooterBinding binding;

    public DefaultFootLoadMoreWrapper(List data, int itemLayoutResId, int footLayoutResId) {
        super(data, itemLayoutResId, footLayoutResId);
    }

    public DefaultFootLoadMoreWrapper(List<T> data, int itemLayoutResId) {
        this(data, itemLayoutResId, R.layout.layout_refresh_footer);
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

}
