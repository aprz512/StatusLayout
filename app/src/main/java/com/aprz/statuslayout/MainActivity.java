package com.aprz.statuslayout;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.aprz.statuslayout.status.StatusLayoutManager;

public class MainActivity extends BaseActivity {

    private StatusLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLayoutManager = new StatusLayoutManager.Builder(this)
                .setContentViewLayoutId(R.layout.activity_main_content)
                .setEmptyViewLayoutId(R.layout.standard_empty)
                .setLoadingViewLayoutId(R.layout.standard_loading)
                .setErrorViewLayoutId(R.layout.standard_error)
                .setNetErrorViewLayoutId(R.layout.standard_network_error)
                .setInitListener(this)
                .build();
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.ll_root);
        mainLayout.addView(mLayoutManager.getStatusLayout());
        mLayoutManager.showLoadingView();
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
                mLayoutManager.showContentView();
                break;
            case R.id.action_empty:
                mLayoutManager.showEmptyView();
                break;
            case R.id.action_loading:
                mLayoutManager.showLoadingView();
                break;
            case R.id.action_error:
                mLayoutManager.showErrorView();
                break;
            case R.id.action_neterror:
                mLayoutManager.showNetworkErrorView();
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
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutManager.showLoadingView();
            }
        });
    }
}
