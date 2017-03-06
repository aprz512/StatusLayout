package com.aprz.statuslayout;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aprz.statuslayout.status.StatusLayoutManager;

/**
 * Created by aprz on 17-3-6.
 * -- hh --
 */

public class BaseActivity extends AppCompatActivity implements StatusLayoutManager.OnStatusLayoutInitListener {

    @Override
    public void initEmptyView(View emptyView) {

    }

    @Override
    public void initErrorView(View errorView) {

    }

    @Override
    public void initNetWorkErrorView(View netWorkErrorView) {

    }
}
