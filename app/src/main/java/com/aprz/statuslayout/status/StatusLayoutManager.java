package com.aprz.statuslayout.status;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

/**
 * Created by aprz on 17-3-6.
 * -- hh --
 * 扩展一下 StatusLayoutManager 能够适用多种情况
 */

public class StatusLayoutManager {

    private final Context mContext;

    // 这3个使用 ViewStub 是因为它们可能不会出现
    private final ViewStub mNetErrorView;
    private final ViewStub mEmptyView;
    private final ViewStub mErrorView;

    private final int mNetErrorViewLayoutId;
    private final int mEmptyViewLayoutId;
    private final int mErrorViewLayoutId;

    private final int mLoadingViewLayoutId;
    private final int mContentViewLayoutId;

    private final StatusLayout mStatusLayout;
    private final OnStatusLayoutInitListener mInitListener;

    public StatusLayoutManager(Builder builder) {

        this.mContext = builder.mContext;

        this.mNetErrorView = builder.mNetErrorView;
        this.mEmptyView = builder.mEmptyView;
        this.mErrorView = builder.mErrorView;

        this.mLoadingViewLayoutId = builder.mLoadingViewLayoutId;
        this.mContentViewLayoutId = builder.mContentViewLayoutId;
        this.mNetErrorViewLayoutId = builder.mNetErrorViewLayoutId;
        this.mEmptyViewLayoutId = builder.mEmptyViewLayoutId;
        this.mErrorViewLayoutId = builder.mErrorViewLayoutId;

        this.mInitListener = builder.mInitListener;

        this.mStatusLayout = new StatusLayout(mContext);
        this.mStatusLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        this.mStatusLayout.setStatusLayoutManager(this);
        this.mStatusLayout.setInitListener(this.mInitListener);

    }

    //---------------------------------   get ---------------------------------
    //---------------------------------   get ---------------------------------

    public Context getContext() {
        return mContext;
    }

    public ViewStub getNetErrorView() {
        return mNetErrorView;
    }

    public ViewStub getEmptyView() {
        return mEmptyView;
    }

    public ViewStub getErrorView() {
        return mErrorView;
    }

    public int getNetErrorViewLayoutId() {
        return mNetErrorViewLayoutId;
    }

    public int getEmptyViewLayoutId() {
        return mEmptyViewLayoutId;
    }

    public int getLoadingViewLayoutId() {
        return mLoadingViewLayoutId;
    }

    public int getErrorViewLayoutId() {
        return mErrorViewLayoutId;
    }

    public int getContentViewLayoutId() {
        return mContentViewLayoutId;
    }

    public StatusLayout getStatusLayout() {
        return mStatusLayout;
    }

    public OnStatusLayoutInitListener getInitListener() {
        return mInitListener;
    }

    //---------------------------------   get ---------------------------------
    //---------------------------------   get ---------------------------------


    // -------------------------------- 显示哪个页面 ------------------------------------
    // -------------------------------- 显示哪个页面 ------------------------------------

    /**
     * 显示loading
     */
    public void showLoadingView() {
        mStatusLayout.showLoadingView();
    }

    /**
     * 显示内容
     */
    public void showContentView() {
        mStatusLayout.showContentView();
    }

    /**
     * 显示空数据
     */
    public void showEmptyView() {
        mStatusLayout.showEmptyView();
    }

    /**
     * 显示网络异常
     */
    public void showNetworkErrorView() {
        mStatusLayout.showNetworkErrorView();
    }

    /**
     * 显示异常
     */
    public void showErrorView() {
        mStatusLayout.showErrorView();
    }

    // -------------------------------- 显示哪个页面 ------------------------------------
    // -------------------------------- 显示哪个页面 ------------------------------------


    // ---------------------------------------- Builder ------------------------------------------
    // ---------------------------------------- Builder ------------------------------------------

    public static class Builder {

        private Context mContext;

        private ViewStub mNetErrorView;
        private ViewStub mEmptyView;
        private ViewStub mErrorView;

        private int mNetErrorViewLayoutId;
        private int mEmptyViewLayoutId;
        private int mErrorViewLayoutId;

        private int mLoadingViewLayoutId;
        private int mContentViewLayoutId;

        private OnStatusLayoutInitListener mInitListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setNetErrorViewLayoutId(int netErrorViewLayoutId) {
            mNetErrorView = new ViewStub(mContext, netErrorViewLayoutId);
            mNetErrorViewLayoutId = netErrorViewLayoutId;
            return this;
        }

        public Builder setEmptyViewLayoutId(int emptyViewLayoutId) {
            mEmptyView = new ViewStub(mContext, emptyViewLayoutId);
            mEmptyViewLayoutId = emptyViewLayoutId;
            return this;
        }

        public Builder setErrorViewLayoutId(int errorViewLayoutId) {
            mErrorView = new ViewStub(mContext, errorViewLayoutId);
            mErrorViewLayoutId = errorViewLayoutId;
            return this;
        }

        public Builder setLoadingViewLayoutId(int loadingViewLayoutId) {
            mLoadingViewLayoutId = loadingViewLayoutId;
            return this;
        }

        public Builder setContentViewLayoutId(int contentViewLayoutId) {
            mContentViewLayoutId = contentViewLayoutId;
            return this;
        }

        public Builder setInitListener(OnStatusLayoutInitListener initListener) {
            mInitListener = initListener;
            return this;
        }

        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }
    }


    // ---------------------------------------- Builder ------------------------------------------
    // ---------------------------------------- Builder ------------------------------------------


    // ------------------------------------ 接口 ------------------------------------------------
    // ------------------------------------ 接口 ------------------------------------------------

    public interface OnStatusLayoutInitListener {
        void initEmptyView(View emptyView);

        void initErrorView(View errorView);

        void initNetWorkErrorView(View netWorkErrorView);
    }
}
