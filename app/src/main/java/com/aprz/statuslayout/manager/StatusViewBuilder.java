package com.aprz.statuslayout.manager;

import android.view.View;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/4/11
 * <p>
 * Class desc:
 */
public class StatusViewBuilder {

    private StatusTypeView mEmptyAdapter;
    private StatusTypeView mLoadingAdapter;
    private StatusTypeView mErrorAdapter;
    private StatusTypeView mNetworkErrorAdapter;
    private boolean mHideIfShowStatus;

    public StatusViewBuilder empty(StatusTypeView emptyAdapter) {
        mEmptyAdapter = emptyAdapter;
        return this;
    }

    public StatusViewBuilder loading(StatusTypeView loadingAdapter) {
        mLoadingAdapter = loadingAdapter;
        return this;
    }

    public StatusViewBuilder error(StatusTypeView errorAdapter) {
        mErrorAdapter = errorAdapter;
        return this;
    }

    public StatusViewBuilder networkError(StatusTypeView networkErrorAdapter) {
        mNetworkErrorAdapter = networkErrorAdapter;
        return this;
    }

    public StatusViewBuilder hideContentIfShowStatus(boolean hideIfShowStatus) {
        mHideIfShowStatus = hideIfShowStatus;
        return this;
    }

    public StatusView build(View target) {
        return StatusManager.inject(target, this);
    }

    public StatusTypeView getEmptyAdapter() {
        return mEmptyAdapter;
    }

    public StatusTypeView getLoadingAdapter() {
        return mLoadingAdapter;
    }

    public StatusTypeView getErrorAdapter() {
        return mErrorAdapter;
    }

    public StatusTypeView getNetworkErrorAdapter() {
        return mNetworkErrorAdapter;
    }

    public boolean isHideIfShowStatus() {
        return mHideIfShowStatus;
    }
}
