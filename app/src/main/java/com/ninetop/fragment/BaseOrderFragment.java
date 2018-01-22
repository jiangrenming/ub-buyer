package com.ninetop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Toast;

import com.hykj.myviewlib.pulltorefresh.pullableview.PullToRefreshLayout;
import com.hykj.myviewlib.pulltorefresh.pullableview.PullableListView;
import com.ninetop.UB.order.MyOrderListBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.activity.product.MyOrderActivity;
import com.ninetop.activity.ub.order.MyOrderAdapterTwo;
import com.ninetop.activity.ub.order.UbMyOrderDetailActivity;
import com.ninetop.base.Viewable;
import com.ninetop.bean.user.order.OrderListBean;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

import static com.ninetop.common.IntentExtraKeyConst.ORDER_ID;
import static com.ninetop.common.IntentExtraKeyConst.ORDER_TYPE;


/**
 * @date: 2016/12/7
 * @author: Shelton
 * @version: 1.1.3
 * @Description:orderFragment的基类
 */
public abstract class BaseOrderFragment extends Fragment {

    private OrderPageFrameLayout orderPageFrameLayout;

    protected UbProductService service;

    protected int hasData = OrderPageFrameLayout.STATUS_EMPTY;

    public static String stateF = "F";//已完成
    public static String stateP = "P";//待付款
    public static String stateA = "A";//待发货
    public static String stateS = "S";//待收货
    public static String stateG = "G";//待自取
    public static String stateQ = "Q";//全部订单


    public static String stateC = "C";//已取消
    public static String stateE = "E";//已关闭
    public static String stateD = "D";//已删除
    public static String stateR = "R";//退款中refund



    private MyOrderAdapterTwo adapter;
    private List<MyOrderListBean> dataList = new ArrayList<>();
    @BindView(R.id.listview)
    PullableListView listView;

    @BindView(R.id.prl_refresh)
    PullToRefreshLayout refreshLayout;

    private String next;

    private int page = 1;

    public BaseOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("BaseIrderFragment::onCreate" + getState() + "::" + page);
       service = new UbProductService((Viewable) getActivity());
       getServerData();

    }



    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_order_layout, null);
        ButterKnife.bind(this, view);

        initRefreshLayout();
        //getMyOrderList();
        dataList.clear();
        page = 1;
        getMyOrderList();
        return view;
    }

    private void initRefreshLayout() {
        refreshLayout.setPullUpEnable(true);
        refreshLayout.setPullDownEnable(true);

        refreshLayout.setOnPullListener(new MyOrderListener());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (orderPageFrameLayout == null) {

            orderPageFrameLayout = new OrderPageFrameLayout(getActivity()) {
                @Override
                protected View createSuccessView() {
                    return BaseOrderFragment.this.createSuccessView();
                }

                @Override
                protected int RequestOrderList() {
                    return BaseOrderFragment.this.RequestOrderList();
                }
            };
        }
        //show();
        return orderPageFrameLayout;
    }

    public void show() {
        if (orderPageFrameLayout != null) {
            orderPageFrameLayout.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderPageFrameLayout != null) {
            ViewParent parent = orderPageFrameLayout.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(orderPageFrameLayout);
            }
        }
    }

    public int checkDatas(List<OrderListBean> datas) {
        if (datas == null || datas.size() == 0) {
            return OrderPageFrameLayout.STATUS_EMPTY;
        } else {
            return OrderPageFrameLayout.STATUS_SUCCESS;
        }
    }

    protected int RequestOrderList() {
        return hasData;
    }

    protected abstract String getState();

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("BaseOrderFragment OnReSume" + getState() + page);
       /* dataList.clear();
        page = 1;
        getMyOrderList();*/
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    public void getMyOrderList() {
        service.getOrderList(getState(), new CommonResultListener<List<MyOrderListBean>>((Viewable) getActivity()) {
            @Override
            public void successHandle(List<MyOrderListBean> result) throws JSONException {
                if (result == null) {
                    return;
                }
                dataList.addAll(result);
                if (adapter == null) {
                    adapter = new MyOrderAdapterTwo(BaseOrderFragment.this.dataList, (MyOrderActivity) getActivity(), getState());
                }
                if (listView != null) {
                    //记录listview的位置
                    int scrollPos = listView.getFirstVisiblePosition();
                    View v1 = listView.getChildAt(0);
                    int scrollTop = (v1 == null) ? 0 : v1.getTop();
                    if (page == 1) listView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), UbMyOrderDetailActivity.class);
                            intent.putExtra(ORDER_ID , BaseOrderFragment.this.dataList.get(position).orderId+"");
                            intent.putExtra(ORDER_TYPE,getState());
                            //Toast.makeText(getActivity(), "position:::" + position, Toast.LENGTH_SHORT).show();
                            //MyActivityManager.getInstance().finishAllActivity();
                            startActivity(intent);
                        }
                    });
                  //listView.setSelectionFromTop(scrollPos, scrollTop);
                }
            }
        });

    }
    public void getServerData() {
        service.getOrderList(getState(), new CommonResultListener<List<MyOrderListBean>>((Viewable) getActivity()) {
            @Override
            public void successHandle(List<MyOrderListBean> result) throws JSONException {
                System.out.println("result:::+" + result);
                if (result == null) {
                    hasData = OrderPageFrameLayout.STATUS_EMPTY;
                    //return;
                } else {
                    hasData = OrderPageFrameLayout.STATUS_SUCCESS;
                }
                //hasData = checkDatas(result.dataList);

                show();
            }
        });
    }
    private class MyOrderListener implements PullToRefreshLayout.OnPullListener {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            refreshData();
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            getMoreData(next);
            refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    private void getMoreData(String next) {
        if (next == null) {
            next = String.valueOf(0);
        }
        int index = Integer.parseInt(next);

        if (index == 0) {
            Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
        } else {
            page++;
            getMyOrderList();
        }
    }

    private void refreshData() {
        page = 1;
        adapter.clearData();
        adapter.addData(dataList);
        listView.smoothScrollToPosition(0);
        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        getMyOrderList();
    }
}