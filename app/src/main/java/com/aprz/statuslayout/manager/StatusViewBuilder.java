package com.aprz.statuslayout.manager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
    private View mContentView;

    public StatusViewBuilder target(View target) {
        mContentView = target;
        return this;
    }

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

    public StatusView build() {
        StatusView wrapper = new StatusView(mContentView.getContext());
        ViewGroup.LayoutParams lp = mContentView.getLayoutParams();
        if (lp != null) {
            wrapper.setLayoutParams(lp);
        }
        if (mContentView.getParent() != null) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            int index = parent.indexOfChild(mContentView);
            parent.removeView(mContentView);
            parent.addView(wrapper, index);
        }
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(layoutParams);
        wrapper.setContenView(mContentView, mHideIfShowStatus)
                .setEmpty(mEmptyAdapter)
                .setError(mErrorAdapter)
                .setLoading(mLoadingAdapter)
                .setNetworkError(mNetworkErrorAdapter);
        return wrapper;
    }
}
