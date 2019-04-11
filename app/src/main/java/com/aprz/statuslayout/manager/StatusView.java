package com.aprz.statuslayout.manager;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lyl
 * ---
 * 各种状态页面的容器
 *
 * @since 10点24分 2019年4月11日
 */
public class StatusView extends FrameLayout {

    private static final String TAG = "StatusView";

    private SparseArray<StatusTypeView> mStatusAdapters = new SparseArray<>();
    private SparseArray<View> mStatusViewCache = new SparseArray<>();
    private View mCurrentStatusView;
    private View mContent;
    private boolean mHideIfShowStatus;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StatusView setContenView(@NonNull View content, boolean hideIfShowStatus) {
        mContent = content;
        mHideIfShowStatus = hideIfShowStatus;
        addView(content);
        return this;
    }

    @Keep
    @SuppressWarnings("unused")
    public StatusView setEmpty(StatusTypeView emptyAdapter) {
        mStatusAdapters.put(StatusType.EMPTY, emptyAdapter);
        return this;
    }

    @Keep
    @SuppressWarnings("unused")
    public StatusView setError(StatusTypeView errorAdapter) {
        mStatusAdapters.put(StatusType.ERROR, errorAdapter);
        return this;
    }

    @Keep
    @SuppressWarnings("unused")
    public StatusView setNetworkError(StatusTypeView networkErrorAdapter) {
        mStatusAdapters.put(StatusType.NETWORK_ERROR, networkErrorAdapter);
        return this;
    }

    @Keep
    @SuppressWarnings("unused")
    public StatusView setLoading(StatusTypeView loadingAdapter) {
        mStatusAdapters.put(StatusType.LOADING, loadingAdapter);
        return this;
    }

    private boolean checkAdapterExist(int type) {
        return mStatusAdapters.indexOfKey(type) >= 0;
    }

    private void showStatusView(int statusType) {
        if (!checkAdapterExist(statusType)) {
            Log.e(TAG, "Can not found the adapter.");
            return;
        }

        View cacheView = mStatusViewCache.get(statusType);

        // 获取缓存的view，没有就新创建一个，并添加到缓存
        if (cacheView == null) {
            StatusTypeView statusTypeView = mStatusAdapters.get(statusType);
            View view = statusTypeView.onCreateView(LayoutInflater.from(getContext()), this);
            mStatusViewCache.put(statusType, view);
            cacheView = view;
        }

        // 首先，如果新的状态页面与旧的不一致，移除旧的状态页面
        // 然后将新的页面添加到 parent 里面
        if (cacheView != mCurrentStatusView) {
            if (mCurrentStatusView != null && indexOfChild(mCurrentStatusView) >= 0) {
                removeView(mCurrentStatusView);
            }
            int indexOfChild = this.indexOfChild(cacheView);
            if (indexOfChild < 0) {
                // 不在 StatusLayout 里面
                addView(cacheView);
            } else if (indexOfChild != getChildCount() - 1) {
                // 不在最上层
                cacheView.bringToFront();
            }
        }

        if (mHideIfShowStatus) {
            mContent.setVisibility(INVISIBLE);
        }

        mCurrentStatusView = cacheView;
    }

    /**
     * 显示loading
     */
    @Keep
    @SuppressWarnings("unused")
    public void showLoadingView() {
        showStatusView(StatusType.LOADING);
    }

    /**
     * 显示内容
     */
    @Keep
    @SuppressWarnings("unused")
    public void showContentView() {
        if (mCurrentStatusView != null && indexOfChild(mCurrentStatusView) >= 0) {
            removeView(mCurrentStatusView);
        }
        mContent.setVisibility(VISIBLE);
    }

    /**
     * 显示空数据
     */
    @Keep
    @SuppressWarnings("unused")
    public void showEmptyView() {
        showStatusView(StatusType.EMPTY);
    }

    /**
     * 显示网络异常
     */
    @Keep
    @SuppressWarnings("unused")
    public void showNetworkErrorView() {
        showStatusView(StatusType.NETWORK_ERROR);
    }

    /**
     * 显示异常
     */
    @Keep
    @SuppressWarnings("unused")
    public void showErrorView() {
        showStatusView(StatusType.ERROR);
    }

}

