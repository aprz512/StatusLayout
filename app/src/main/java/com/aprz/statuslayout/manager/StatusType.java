package com.aprz.statuslayout.manager;

import android.support.annotation.IntDef;

import static com.aprz.statuslayout.manager.StatusType.CONTENT;
import static com.aprz.statuslayout.manager.StatusType.EMPTY;
import static com.aprz.statuslayout.manager.StatusType.ERROR;
import static com.aprz.statuslayout.manager.StatusType.LOADING;
import static com.aprz.statuslayout.manager.StatusType.NETWORK_ERROR;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/4/11
 * <p>
 * Class desc:
 */

@IntDef(value = {
        LOADING,
        CONTENT,
        ERROR,
        NETWORK_ERROR,
        EMPTY,
})
public @interface StatusType {

    int LOADING = 1;
    int CONTENT = 2;
    int ERROR = 3;
    int NETWORK_ERROR = 4;
    int EMPTY = 5;

}
