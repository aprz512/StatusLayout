package com.aprz.statuslayout.manager;

import android.view.LayoutInflater;
import android.view.View;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/4/11
 * <p>
 * Class desc:
 */
public interface StatusTypeView {

    /**
     * 加载状态页面
     *
     * @param layoutInflater 布局填充器
     * @param parent         父布局
     * @return view
     */
    View onCreateView(LayoutInflater layoutInflater, StatusView parent);

}
