package com.aprz.statuslayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aprz.statuslayout.manager.StatusView;
import com.aprz.statuslayout.manager.StatusViewBuilder;

public class MainActivity extends AppCompatActivity {

    private StatusView mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStatusView = new StatusViewBuilder()
                .target(findViewById(R.id.content))
                .hideContentIfShowStatus(true)
                .empty(new EmptyTypeView())
                .error(new ErrorTypeView())
                .networkError(new NetworkErrorTypeView())
                .loading(new LoadingTypeView())
                .build();
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
                mStatusView.showContentView();
                break;
            case R.id.action_empty:
                mStatusView.showEmptyView();
                break;
            case R.id.action_loading:
                mStatusView.showLoadingView();
                break;
            case R.id.action_error:
                mStatusView.showErrorView();
                break;
            case R.id.action_neterror:
                mStatusView.showNetworkErrorView();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

}
