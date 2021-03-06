# StatusLayout
基于：https://github.com/chenpengfei88/StatusLayout 扩展并简化代码。

一个用于管理多种状态页面切换的管理器。

状态页面：StatusLayout ---- 包括 空页面/数据页面/错误页面/加载页面/网络错误页面等等。

使用：
```xml
    <com.aprz.statuslayout.status.StatusLayout
        android:id="@+id/sl_main"
        app:empty_layout="@layout/standard_loading"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="内容页面" />

    </com.aprz.statuslayout.status.StatusLayout>
```

新增使用方式：
```java

public class EmptyTypeView implements StatusTypeView {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, StatusView parent) {
        return layoutInflater.inflate(R.layout.standard_empty, parent, false);
    }
}

StatusView mStatusView = new StatusViewBuilder()
                 .replace(findViewById(R.id.content))
                 .hideContentIfShowStatus(true)
                 .empty(new EmptyTypeView())
                 .error(new ErrorTypeView())
                 .networkError(new NetworkErrorTypeView())
                 .loading(new LoadingTypeView())
                 .build();
```

效果图如下：

![](https://github.com/aprz512/StatusLayout/blob/master/statuslayout.gif)