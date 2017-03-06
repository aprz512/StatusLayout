package com.aprz.statuslayout.status;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by chenpengfei on 2016/12/15.
 * ---
 * 各种状态页面的容器
 */
public class StatusLayout extends FrameLayout {


    public static final int LOADING = 1;
    public static final int CONTENT = 2;
    public static final int ERROR = 3;
    public static final int NETWORK_ERROR = 4;
    public static final int EMPTY = 5;

    /**
     * 存放布局集合
     */
    private SparseArray<View> mLayoutSparseArray = new SparseArray<>();

    /**
     * 布局管理器
     */
    private StatusLayoutManager mStatusLayoutManager;
    private StatusLayoutManager.OnStatusLayoutInitListener mInitListener;


    public StatusLayout(Context context) {
        super(context);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setStatusLayoutManager(StatusLayoutManager statusLayoutManager) {
        this.mStatusLayoutManager = statusLayoutManager;
        addAllLayoutToLayout();
    }

    private void addAllLayoutToLayout() {
        if (mStatusLayoutManager.getContentViewLayoutId() != 0) {
            addLayoutResId(mStatusLayoutManager.getContentViewLayoutId(),
                    StatusLayout.CONTENT);
        }
        if (mStatusLayoutManager.getLoadingViewLayoutId() != 0) {
            addLayoutResId(mStatusLayoutManager.getLoadingViewLayoutId(),
                    StatusLayout.LOADING);
        }

        // 这里使用两种方式加载 （一种是先把id放到集合，再 addView）
        // 一种是直接 addView
        // 估计是考虑到了 空页面 错误页面 可能不会出现的情况
        // 只有真的需要显示 空页面 等，才去将 id 放到集合，也是一种优化
        if (mStatusLayoutManager.getEmptyView() != null) {
            addView(mStatusLayoutManager.getEmptyView());
        }
        if (mStatusLayoutManager.getErrorView() != null) {
            addView(mStatusLayoutManager.getErrorView());
        }
        if (mStatusLayoutManager.getNetErrorView() != null) {
            addView(mStatusLayoutManager.getNetErrorView());
        }
    }

    private void addLayoutResId(@LayoutRes int layoutResId, int id) {
        if (mLayoutSparseArray.get(id) != null) {
            throw new IllegalStateException("你需要查看你的代码，为什么导致添加了两次相同 id 的布局？");
        }
        View resView = LayoutInflater.from(getContext()).inflate(layoutResId, this, false);
        addView(resView);
        addViewToArray(id, resView);
    }

    private void addViewToArray(int id, View resView) {
        mLayoutSparseArray.put(id, resView);
        initView(resView, id);
    }

    private void initView(View resView, int id) {
        if (mInitListener == null) {
            return;
        }
        switch (id) {
            case EMPTY:
                mInitListener.initEmptyView(resView);
                break;
            case ERROR:
                mInitListener.initErrorView(resView);
                break;
            case NETWORK_ERROR:
                mInitListener.initNetWorkErrorView(resView);
                break;
        }
    }

    /**
     * 根据ID显示隐藏布局
     */
    private void showHideViewById(int id) {
        for (int i = 0; i < mLayoutSparseArray.size(); i++) {
            int key = mLayoutSparseArray.keyAt(i);
            View valueView = mLayoutSparseArray.valueAt(i);
            //显示该view
            if (key == id) {
                valueView.setVisibility(View.VISIBLE);
            } else {
                if (valueView.getVisibility() != View.GONE) {
                    valueView.setVisibility(View.GONE);
                }
            }
        }
    }

    private boolean inflateLayout(int id) {
        boolean isShow = true;
        if (mLayoutSparseArray.get(id) != null) {
            return true;
        }
        switch (id) {
            case NETWORK_ERROR:
                if (mStatusLayoutManager.getNetErrorView() != null) {
                    View view = mStatusLayoutManager.getNetErrorView().inflate();
                    addViewToArray(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case ERROR:
                if (mStatusLayoutManager.getErrorView() != null) {
                    View view = mStatusLayoutManager.getErrorView().inflate();
                    addViewToArray(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case EMPTY:
                if (mStatusLayoutManager.getEmptyView() != null) {
                    View view = mStatusLayoutManager.getEmptyView().inflate();
                    addViewToArray(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
        }
        return isShow;
    }

    public void setInitListener(StatusLayoutManager.OnStatusLayoutInitListener initListener) {
        mInitListener = initListener;
    }

    /**
     * 显示loading
     */
    public void showLoadingView() {
        if (mLayoutSparseArray.get(LOADING) != null)
            showHideViewById(LOADING);
    }

    /**
     * 显示内容
     */
    public void showContentView() {
        if (mLayoutSparseArray.get(CONTENT) != null)
            showHideViewById(CONTENT);
    }

    /**
     * 显示空数据
     */
    public void showEmptyView() {
        if (inflateLayout(EMPTY))
            showHideViewById(EMPTY);
    }

    /**
     * 显示网络异常
     */
    public void showNetworkErrorView() {
        if (inflateLayout(NETWORK_ERROR))
            showHideViewById(NETWORK_ERROR);
    }

    /**
     * 显示异常
     */
    public void showErrorView() {
        if (inflateLayout(ERROR))
            showHideViewById(ERROR);
    }
}

