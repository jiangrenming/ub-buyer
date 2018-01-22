package com.ninetop.fragment.category;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.hykj.myviewlib.pulltorefresh.pullableview.PullToRefreshLayout;
import com.hykj.myviewlib.pulltorefresh.pullableview.PullAbleGridView;
import com.ninetop.activity.product.ProductDetailActivity;
import com.ninetop.adatper.product.SecondCategoryFragmentAdapter;
import com.ninetop.base.Viewable;
import com.ninetop.bean.product.category.CateProductBean;
import com.ninetop.bean.product.category.SecondProductBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.ProductSortBarView;
import com.ninetop.common.view.SelectChangedListener;
import com.ninetop.common.view.bean.SortBean;
import com.ninetop.service.impl.CategoryService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;


/**
 * @date: 2016/11/30
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
@SuppressLint("ValidFragment")
public class SecondCategoryFragment extends Fragment {

    @BindView(R.id.gv_second_category)
    PullAbleGridView gvSecondCategory;
    @BindView(R.id.prl_refresh)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.psb_bar)
    ProductSortBarView psbBar;

    private CategoryService categoryService;
    private int page = 1;

    private String code;
    private List<CateProductBean> dataList = new ArrayList<>();

    private String next;
    private String orderCode = "overall";
    private String orderType = "ASC";


    public static final int STATE_NORMAL = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_MORE = 2;

    private int refresh_state = STATE_NORMAL;
    private SecondCategoryFragmentAdapter adapter;

    public SecondCategoryFragment() {
    }

    public SecondCategoryFragment(String currentCode) {
        this();
        this.code = currentCode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_category, container, false);
        ButterKnife.bind(this, view);
        categoryService = new CategoryService((Viewable) getActivity());
        initRefreshLayout();

        initData();
        return view;
    }

    private void initRefreshLayout() {
        refreshLayout.setPullUpEnable(true);
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new SecondCategoryListener());
    }

    private void initData() {
        dataList.clear();
        page = 1;
        getSecondCategoryProduct();
        psbBar.setSelectChangedListener(new SelectChangedListener<SortBean>() {
            @Override
            public void changeHandle(SortBean value) {
                orderType = psbBar.getOrder();
                orderCode = value.code;
                dataList.clear();
                page = 1;
                getSecondCategoryProduct();
            }
        });
    }

    public void getSecondCategoryProduct() {
        categoryService.getActivitySecondCategoryProductList(code, page + "", orderCode, orderType, new CommonResultListener<SecondProductBean>((Viewable) getActivity()) {
            @Override
            public void successHandle(SecondProductBean result) throws JSONException {
                if (result == null) {
                    return;
                }

                dataList.addAll(result.dataList);
                next = result.next;
                List<CateProductBean> list = result.dataList;
                //System.out.println("listlistlistgggggggggggggg"+list);
                System.out.println("listlistlist.size()"+list.size());

                showData();
                //System.out.println("Second dataList:showData之后:"+ SecondCategoryFragment.this.dataList);
                System.out.println("Second dataList.size()"+ SecondCategoryFragment.this.dataList.size());
                System.out.println("page:::"+page);

                adapter.notifyDataSetChanged();

                gvSecondCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                        intent.putExtra(IntentExtraKeyConst.PRODUCT_CODE, SecondCategoryFragment.this.dataList.get(position).code);
                        startActivity(intent);
                    }
                });
            }

        });
    }

    private void showData() {
        switch (refresh_state) {
            case STATE_NORMAL:
                adapter = new SecondCategoryFragmentAdapter(dataList, getActivity());
                gvSecondCategory.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
                break;
            case STATE_REFRESH:
                //adapter.clearData();
                //dataList.clear();
                //adapter.addData(dataList);
                adapter = new SecondCategoryFragmentAdapter(dataList, getActivity());
                gvSecondCategory.setAdapter(adapter);
                gvSecondCategory.smoothScrollToPosition(0);
                //adapter.notifyDataSetChanged();
                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case STATE_MORE:
                //adapter.addData(adapter.getDatas().size(), dataList);
                //adapter = new SecondCategoryFragmentAdapter(dataList, getActivity());

                if (page == 1)
                    gvSecondCategory.setAdapter(adapter);
                //gvSecondCategory.smoothScrollToPosition(adapter.getDatas().size());

                adapter.notifyDataSetChanged();
                refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
        }

    }

    private class SecondCategoryListener implements PullToRefreshLayout.OnPullListener {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            refreshData();
            //refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
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
            //refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        } else {
            //dataList.clear();
            refresh_state = STATE_MORE;
            page++;
            getSecondCategoryProduct();
        }
    }

    private void refreshData() {
        dataList.clear();
        page = 1;
        refresh_state = STATE_REFRESH;
        getSecondCategoryProduct();
    }


}