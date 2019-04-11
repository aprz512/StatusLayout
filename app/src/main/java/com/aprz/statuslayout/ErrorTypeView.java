package com.aprz.statuslayout;

import android.os.Handler;
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
public class ErrorTypeView implements StatusTypeView {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, StatusView parent) {
        View errorView = layoutInflater.inflate(R.layout.standard_error, parent, false);

        View retryButton = errorView.findViewById(R.id.btn_retry);
        retryButton.setOnClickListener(v -> {
            parent.showLoadingView();
            new Handler().postDelayed(parent::showContentView, 3000);
        });

        return errorView;
    }
}
