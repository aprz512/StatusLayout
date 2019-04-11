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
class StatusManager {

    static StatusView inject(View target, StatusViewBuilder builder) {
        StatusView wrapper = new StatusView(target.getContext());
        ViewGroup.LayoutParams lp = target.getLayoutParams();
        if (lp != null) {
            wrapper.setLayoutParams(lp);
        }
        if (target.getParent() != null) {
            ViewGroup parent = (ViewGroup) target.getParent();
            int index = parent.indexOfChild(target);
            parent.removeView(target);
            parent.addView(wrapper, index);
        }
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        target.setLayoutParams(layoutParams);
        wrapper.setContenView(target, builder.isHideIfShowStatus());

        wrapper.setEmpty(builder.getEmptyAdapter())
                .setError(builder.getErrorAdapter())
                .setLoading(builder.getLoadingAdapter())
                .setNetworkError(builder.getNetworkErrorAdapter());
        return wrapper;
    }

}
