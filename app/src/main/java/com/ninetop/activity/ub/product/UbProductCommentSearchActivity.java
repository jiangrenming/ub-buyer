package com.ninetop.activity.ub.product;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.ninetop.UB.product.UbProductService;
import com.ninetop.activity.ub.util.newPop.AbstractSpinnerAdapter;
import com.ninetop.activity.ub.util.newPop.SpinnerPopWindow;
import com.ninetop.base.PullRefreshBaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.ProductSortBarView;
import com.ninetop.common.view.SelectChangedListener;
import com.ninetop.common.view.bean.SortBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/6/29.
 */

public class UbProductCommentSearchActivity extends PullRefreshBaseActivity implements AbstractSpinnerAdapter.IOnItemSelectListener {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.psb_bar)
    ProductSortBarView psbBar;
    @BindView(R.id.et_product_search)
    EditText etProductSearch;
    @BindView(R.id.view)
    View view;


    private List<String> nameList = new ArrayList<>();
    private SpinnerPopWindow mSpinnerPopWindow;
    private UbProductService ubProductService;
    private String searchKey = null;
    private String searchOrder = "ASC";
    private String searchColumn = "overall";
    private int sortType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_search_result;
    }

    @Override
    protected void initView() {
        super.initView();

        ubProductService = new UbProductService(this);
        getNamePop();
        ListView list = (ListView) refreshLayout.getPullableView();
        list.setDividerHeight(0);
        listAdapter = new UbProductListAdapter(UbProductCommentSearchActivity.this, dataList);
        list.setAdapter(listAdapter);
    }

    protected void initData() {
        hvHead.setTitle("查询结果");
        searchKey = getIntentValue(IntentExtraKeyConst.SEARCH_KEY);
        getServerData();

        psbBar.setSelectChangedListener(new SelectChangedListener<SortBean>() {
            @Override
            public void changeHandle(SortBean value) {
                searchOrder = psbBar.getOrder();
                searchColumn = value.code;
                dataList.clear();
                currentPage = 1;
                getServerData();
            }
        });

    }

    @Override
    public void onItemClick(int pos) {
        setHero(pos);
    }

    private void setHero(int pos) {
        if (pos >= 0 && pos <= nameList.size()) {
            String value = nameList.get(pos);

            etProductSearch.setText(value);
        }
    }

    private void getNamePop() {
        nameList = new ArrayList<>();
        nameList.add("佳优保总店");
        mSpinnerPopWindow = new SpinnerPopWindow(this);
        mSpinnerPopWindow.refreshData(nameList, 0);
        mSpinnerPopWindow.setItemListener(this);
    }

    protected void getServerData() {
//        if("0".equals(searchOrder)||"1".equals(searchOrder)){
//            sortType=1;
//        }else if("1".equals())
        //1上新 2 价格3 月销排序方式（1：上新由近到远，价格由小到大,月销量由少到多;0:与1相反）
        //category_id 分类id（是：显示分类列表；否：显示所有商品列表）

//        productService.searchByKey(searchKey, currentPage + "", searchColumn, searchOrder,
//                new CommonResultListener<ProductPagingBean>(this) {
//                    @Override
//                    public void successHandle(ProductPagingBean result) throws JSONException {
//                        if(result == null || result.dataList == null || result.dataList.size() == 0)
//                            return;
//
//                        dataList.addAll(result.dataList);
//                        listAdapter.notifyDataSetChanged();
//                    }
//                }
//        );
        //ubProductService.postProductCategory(0,1,)
    }

    private void showSpinWindow(){
        mSpinnerPopWindow.setWidth(etProductSearch.getWidth());
        mSpinnerPopWindow.showAsDropDown(etProductSearch);
    }

    @OnClick({R.id.iv_icon_back, R.id.iv_search, R.id.iv_more_btn1, R.id.ll_search_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_icon_back:
                break;
            case R.id.iv_search:
                break;
            case R.id.iv_more_btn1:
                showSpinWindow();
                break;
            case R.id.ll_search_edit:
                break;
        }
    }
}

