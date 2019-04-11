package com.aprz.statuslayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aprz.statuslayout.manager.StatusView;
import com.aprz.statuslayout.manager.StatusViewBuilder;

import java.util.Random;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/4/11
 * <p>
 * Class desc:
 */
public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        RecyclerView recyclerView = findViewById(R.id.rv_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(new MyAdapter());
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private StatusView mStatusView;
        private ImageView mHeader;

        public MyHolder(@NonNull StatusView statusView) {
            super(statusView);
            mHeader = itemView.findViewById(R.id.iv_pic);
            mStatusView = statusView;
        }

        public void bind() {
            mStatusView.showLoadingView();
            long time = new Random(5).nextInt(3) * 1000L;
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (mStatusView == null || mStatusView.getContext() == null) {
                    return;
                }
                mHeader.setImageResource(R.mipmap.ic_launcher);
                mStatusView.showContentView();
            }, time);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_grid, viewGroup, false);

            StatusView build = new StatusViewBuilder()
                    .replace(inflate)
                    .hideContentIfShowStatus(true)
                    .empty(new EmptyTypeView())
                    .error(new ErrorTypeView())
                    .networkError(new NetworkErrorTypeView())
                    .loading(new LoadingTypeView())
                    .build();

            return new MyHolder(build);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            myHolder.bind();
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }
}
