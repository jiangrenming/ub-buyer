package com.ninetop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hykj.myviewlib.pulltorefresh.pullableview.PullToRefreshLayout;

import youbao.shopping.R;
import com.ninetop.base.BaseActivity;

import com.ninetop.base.DefaultBaseAdapter;
import com.ninetop.base.PullRefreshable;
import com.ninetop.base.Viewable;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @date: 2016/11/21
 */
public abstract class PullRefreshFragment<T> extends BaseFragment implements PullToRefreshLayout.OnPullListener, PullRefreshable, Viewable {

    public PullToRefreshLayout refreshLayout;

    public int currentPage = 1;

    protected List<T> dataList = new ArrayList<>();

    protected DefaultBaseAdapter listAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.prl_refresh);
        initView();
        if(refreshLayout != null) {
            refreshLayout.setPullDownEnable(true);
            refreshLayout.setPullUpEnable(true);
            refreshLayout.setOnPullListener(this);
        }
        return view;
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        currentPage = 1;
        dataList.clear();
        listAdapter.notifyDataSetChanged();
        getServerData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        currentPage++;

        getServerData();
    }

    public void refreshEnd(boolean hasNext, boolean loadSuccess){
        if(refreshLayout == null)
            return;
        refreshLayout.setPullUpEnable(hasNext);
        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

        if(!loadSuccess && currentPage > 1) {
            currentPage--;
        }
    }

    protected abstract void getServerData();

}
