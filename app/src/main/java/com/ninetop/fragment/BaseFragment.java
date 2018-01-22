package com.ninetop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninetop.base.BaseActivity;
import com.ninetop.base.Viewable;

import java.util.Map;

import butterknife.ButterKnife;

/**
 * @date: 2016/11/21
 * @author: Shelton
 * @Description:Fragment的父类
 */
public abstract class BaseFragment extends Fragment implements Viewable{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected abstract int getLayoutId();

    protected void initView(){}

    @Override
    public void refresh() {
    }

    @Override
    public String getIntentValue(String key) {
        return getTargetActivity().getIntentValue(key);
    }

    @Override
    public void removeLoading() {
        getTargetActivity().removeLoading();
    }

    @Override
    public void addLoading() {
        getTargetActivity().addLoading();
    }

    @Override
    public void showToast(String message) {
        getTargetActivity().showToast(message);
    }

    public BaseActivity getTargetActivity(){
        return (BaseActivity)getContext();
    }

    @Override
    public void startActivity(Class clazz) {
        getTargetActivity().startActivity(clazz);
    }

    @Override
    public void startActivity(Class clazz, Map<String, String> map) {
        getTargetActivity().startActivity(clazz, map);
    }
}
