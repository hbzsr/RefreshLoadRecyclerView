package com.zsr.rl.sample;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zsr.rl.databinding.ActivityMainBinding;
import com.zsr.rl.R;
import com.zsr.rl.view.RecyclerRefreshLoadView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用源码最合适，用到了v7包的内容，可能会造成版本不一致问题
 * 源码就是 adapter listener view 这三个包内的代码，
 * 如果使用DefaultFootLoadMoreWrapper需要copy两个资源文件：progressbar_refresh.xml ; layout_refresh_footer.xml
 */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<String> data = new ArrayList<>();
    MyAdapter adapter;

    private void getData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            data.add(String.valueOf(letter));
            letter++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.rv.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        adapter = new MyAdapter(data, R.layout.item_layout, R.layout.layout_refresh_footer);
        binding.rv.setAdapter(adapter);
        binding.rv.setOnRefreshLoadListener(new RecyclerRefreshLoadView.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {
                            data.clear();
                            getData();
                            binding.rv.setRefreshing(false);
                            adapter.setRefresh();
                        });
                    }
                }, 500);
            }

            @Override
            public void onLoad() {
                if (data.size() < 52) {
                    adapter.setLoadState(MyAdapter.LOADING);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(() -> {
                                getData();
                                if (data.size() >= 52) {
                                    adapter.setLoadState(MyAdapter.LOADING_END);
                                } else {
                                    adapter.setLoadState(MyAdapter.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                }
            }
        });
    }
}
