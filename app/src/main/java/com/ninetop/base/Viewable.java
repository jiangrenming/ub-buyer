package com.ninetop.base;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

/**
 * Created by jill on 2016/11/22.
 */

public interface Viewable {

    /**
     * 显示提示
     * @param message
     */
    void showToast(String message);

    void addLoading();

    void removeLoading();

    String getIntentValue(String key);

    void refresh();

    void startActivity(Class clazz);

    void startActivity(Class clazz, Map<String,String> map);

    BaseActivity getTargetActivity();
}
