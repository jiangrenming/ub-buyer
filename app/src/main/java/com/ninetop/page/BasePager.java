package com.ninetop.page;

import android.content.Context;
import android.view.View;

public abstract class BasePager {
    public Context context;
    public BasePager(Context context){
        this.context = context;
    }
    /**
     * 初始化界面：确定自身View
     * @return
     */
    public abstract View initView();
    /**
     * 初始化数据
     */
    public abstract void initData();

    
}
