package com.ninetop.base;

import android.widget.BaseAdapter;

import com.hykj.myviewlib.pulltorefresh.pullableview.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import youbao.shopping.R;

/**
 *
 * 可以下拉刷新，上拉加载更多的Activity基类
 * Created by jill on 2016/12/9.
 */

public abstract class PullRefreshBaseActivity<T> extends BaseActivity implements PullToRefreshLayout.OnPullListener, PullRefreshable {

    public PullToRefreshLayout refreshLayout;

    public int currentPage = 1;

    protected List<T> dataList = new ArrayList<>();

    protected BaseAdapter listAdapter;

    @Override
    protected void initView() {
        super.initView();

        refreshLayout = (PullToRefreshLayout) findViewById(R.id.prl_refresh);

        if(refreshLayout == null)
            return;

        refreshLayout.setPullDownEnable(true);
        refreshLayout.setPullUpEnable(false);
        refreshLayout.setOnPullListener(this);
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
