package com.aprz.statuslayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aprz.statuslayout.status.StatusLayout;

public class MainActivity extends BaseActivity {

    private StatusLayout mStatusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStatusLayout = findViewById(R.id.sl_main);
        mStatusLayout.setEmptyLayoutId(R.layout.standard_empty)
                .setLoadingLayoutId(R.layout.standard_loading)
                .setErrorLayoutId(R.layout.standard_error)
                .setNetworkErrorLayoutId(R.layout.standard_network_error)
                .build();
        mStatusLayout.setInitListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_content:
                mStatusLayout.showContentView();
                break;
            case R.id.action_empty:
                mStatusLayout.showEmptyView();
                break;
            case R.id.action_loading:
                mStatusLayout.showLoadingView();
                break;
            case R.id.action_error:
                mStatusLayout.showErrorView();
                break;
            case R.id.action_neterror:
                mStatusLayout.showNetworkErrorView();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void initErrorView(View errorView) {
        super.initErrorView(errorView);
        View retryButton = errorView.findViewById(R.id.btn_retry);
        retryButton.setOnClickListener(v -> {
            mStatusLayout.showLoadingView();
            new Handler().postDelayed(() -> mStatusLayout.showContentView(), 3000);
        });
    }
}
