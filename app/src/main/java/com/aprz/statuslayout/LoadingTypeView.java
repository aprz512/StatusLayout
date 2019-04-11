package com.aprz.statuslayout;

import android.view.LayoutInflater;
import android.view.View;

import com.aprz.statuslayout.manager.StatusTypeView;
import com.aprz.statuslayout.manager.StatusView;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/4/11
 * <p>
 * Class desc:
 */
public class LoadingTypeView implements StatusTypeView {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, StatusView parent) {
        return layoutInflater.inflate(R.layout.standard_loading, parent, false);
    }
}
