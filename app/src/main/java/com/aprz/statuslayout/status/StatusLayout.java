package com.aprz.statuslayout.status;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.aprz.statuslayout.R;

/**
 * Created by lyl
 * ---
 * 各种状态页面的容器
 * modify at 2019年3月5日 -- 扩展并简化使用
 */
public class StatusLayout extends FrameLayout {

    private static final int NO_ID = -1;

    public static final int LOADING = 1;
    public static final int CONTENT = 2;
    public static final int ERROR = 3;
    public static final int NETWORK_ERROR = 4;
    public static final int EMPTY = 5;

    private int mLoadingLayoutId;
    private int mEmptyLayoutId;
    private int mErrorLayoutId;
    private int mNetworkErrorLayoutId;

    private ViewStub mLoadingViewStub;
    private ViewStub mEmptyViewStub;
    private ViewStub mErrorViewStub;
    private ViewStub mNetworkErrorViewStub;

    private SparseArray<View> mLayoutSparseArray = new SparseArray<>(5);

    private OnStatusLayoutInitListener mInitListener;

    public StatusLayout(Context context) {
        this(context, null);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StatusLayout);
        mLoadingLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_loading_layout, NO_ID);
        mEmptyLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_empty_layout, NO_ID);
        mErrorLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_error_layout, NO_ID);
        mNetworkErrorLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_network_error_layout, NO_ID);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        build();
    }

    /**
     * 需要将对应的 viewStub 设置为 null，
     * 因为布局只会加载一次，加载完之后，就不会再重新加载了，
     * 想要替换布局，需要将对应的元素置空
     *
     * @param emptyLayoutId 布局id
     * @return this
     */
    public StatusLayout setEmptyLayoutId(int emptyLayoutId) {
        mEmptyLayoutId = emptyLayoutId;
        removeIfExist(EMPTY);
        removeView(mEmptyViewStub);
        mEmptyViewStub = null;
        return this;
    }

    public StatusLayout setErrorLayoutId(int errorLayoutId) {
        mErrorLayoutId = errorLayoutId;
        removeIfExist(ERROR);
        removeView(mErrorViewStub);
        mErrorViewStub = null;
        return this;
    }

    public StatusLayout setNetworkErrorLayoutId(int networkErrorLayoutId) {
        mNetworkErrorLayoutId = networkErrorLayoutId;
        removeIfExist(NETWORK_ERROR);
        removeView(mNetworkErrorViewStub);
        mNetworkErrorViewStub = null;
        return this;
    }

    public StatusLayout setLoadingLayoutId(int loadingLayoutId) {
        mLoadingLayoutId = loadingLayoutId;
        removeIfExist(LOADING);
        removeView(mLoadingViewStub);
        mLoadingViewStub = null;
        return this;
    }

    private void removeIfExist(int key) {
        View view = mLayoutSparseArray.get(key);
        if (view != null) {
            mLayoutSparseArray.delete(key);
            removeView(view);
        }
    }

    /**
     * 该方法用于初始化布局，必须调用
     * 调用 setXXXLayoutId 方法之后，必须调用该方法
     */
    public void build() {
        if (mErrorViewStub != null) {
            removeView(mErrorViewStub);
        }
        if (mEmptyViewStub != null) {
            removeView(mEmptyViewStub);
        }
        if (mNetworkErrorViewStub != null) {
            removeView(mNetworkErrorViewStub);
        }
        if (mLoadingViewStub != null) {
            removeView(mLoadingViewStub);
        }
        initStatusView();
    }

    private void initStatusView() {
        addViewToArray(CONTENT, getChildAt(0));
        if (getChildCount() != 1) {
            throw new IllegalArgumentException("只能有一个child，就是 content");
        }
        mErrorViewStub = addViewStub(mErrorLayoutId);
        mNetworkErrorViewStub = addViewStub(mNetworkErrorLayoutId);
        mEmptyViewStub = addViewStub(mEmptyLayoutId);
        mLoadingViewStub = addViewStub(mLoadingLayoutId);
    }

    private ViewStub addViewStub(int layoutId) {
        if (layoutId == NO_ID) {
            return null;
        }
        ViewStub result = new ViewStub(getContext(), layoutId);
        addView(result);
        return result;
    }

    private void addViewToArray(int id, View resView) {
        mLayoutSparseArray.put(id, resView);
        notifyViewInit(resView, id);

        if (resView == null) {
            Log.e("StatusLayout", "addViewToArray --- wtf!!!" + id);
        }
    }

    private void notifyViewInit(View resView, int id) {
        if (mInitListener == null) {
            return;
        }
        switch (id) {
            case LOADING:
                mInitListener.initLoadingView(resView);
                break;
            case EMPTY:
                mInitListener.initEmptyView(resView);
                break;
            case ERROR:
                mInitListener.initErrorView(resView);
                break;
            case NETWORK_ERROR:
                mInitListener.initNetWorkErrorView(resView);
                break;
            default:
                break;
        }
    }

    /**
     * 根据ID显示隐藏布局
     */
    private void showViewById(int id) {

        for (int i = 0; i < mLayoutSparseArray.size(); i++) {
            int key = mLayoutSparseArray.keyAt(i);
            View valueView = mLayoutSparseArray.get(key);
            if (key != id) {
                if (valueView.getVisibility() != View.GONE) {
                    valueView.setVisibility(View.GONE);
                }
            }
        }
        View valueView = mLayoutSparseArray.get(id);
        valueView.setVisibility(VISIBLE);
    }

    private boolean inflateLayout(int id) {
        boolean inflated = true;
        if (mLayoutSparseArray.get(id) != null) {
            return true;
        }
        switch (id) {
            case NETWORK_ERROR:
                inflated = inflateViewStub(mNetworkErrorViewStub, id);
                break;
            case LOADING:
                inflated = inflateViewStub(mLoadingViewStub, id);
                break;
            case ERROR:
                inflated = inflateViewStub(mErrorViewStub, id);
                break;
            case EMPTY:
                inflated = inflateViewStub(mEmptyViewStub, id);
                break;
            default:
                break;
        }
        return inflated;
    }

    private boolean inflateViewStub(ViewStub viewStub, int id) {
        if (viewStub != null) {
            // 不知道出了啥bug inflate 出来的是空的
            // 不是 inflate 的是空的，而是 content 是空
            // 原因是该控件还没有完成 inflate 我就取获取 child 了
            View inflated = viewStub.inflate();
            addViewToArray(id, inflated);
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unused")
    public void setInitListener(OnStatusLayoutInitListener initListener) {
        mInitListener = initListener;
    }

    /**
     * 显示loading
     */
    @SuppressWarnings("unused")
    public void showLoadingView() {
        if (mLoadingLayoutId != NO_ID && inflateLayout(LOADING)) {
            showViewById(LOADING);
        }
    }

    /**
     * 显示内容
     */
    @SuppressWarnings("unused")
    public void showContentView() {
        if (mLayoutSparseArray.get(CONTENT) != null) {
            showViewById(CONTENT);
        }
    }

    /**
     * 显示空数据
     */
    @SuppressWarnings("unused")
    public void showEmptyView() {
        if (mEmptyLayoutId != NO_ID && inflateLayout(EMPTY)) {
            showViewById(EMPTY);
        }
    }

    /**
     * 显示网络异常
     */
    @SuppressWarnings("unused")
    public void showNetworkErrorView() {
        if (mNetworkErrorLayoutId != NO_ID && inflateLayout(NETWORK_ERROR)) {
            showViewById(NETWORK_ERROR);
        }
    }

    /**
     * 显示异常
     */
    @SuppressWarnings("unused")
    public void showErrorView() {
        if (mErrorLayoutId != NO_ID && inflateLayout(ERROR)) {
            showViewById(ERROR);
        }
    }

    public interface OnStatusLayoutInitListener {
        /**
         * 用于外部初始化空页面
         *
         * @param emptyView 空页面
         */
        default void initEmptyView(View emptyView) {
        }

        /**
         * 用于外部初始化错误页面
         *
         * @param errorView 错误页面
         */
        default void initErrorView(View errorView) {
        }

        /**
         * 用于外部初始化网络页面
         *
         * @param netWorkErrorView 网络错误页面
         */
        default void initNetWorkErrorView(View netWorkErrorView) {
        }

        /**
         * 用于外部初始化加载页面
         *
         * @param loadingView 加载页面
         */
        default void initLoadingView(View loadingView) {
        }
    }
}

