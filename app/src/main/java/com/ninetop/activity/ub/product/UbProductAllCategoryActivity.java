package com.ninetop.activity.ub.product;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ninetop.UB.product.ProductSearchBean;
import com.ninetop.UB.product.UbProductCategoryClassBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.activity.ub.util.newPop.ProductPopAdapter;
import com.ninetop.activity.ub.util.newPop.SpinnerProductPopWindow;
import com.ninetop.base.PullRefreshBaseActivity;
import com.ninetop.common.ActivityActionHelper;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.ProductSortBarView;
import com.ninetop.common.view.SelectChangedListener;
import com.ninetop.common.view.bean.SortBean;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/30.
 */
public class UbProductAllCategoryActivity extends PullRefreshBaseActivity implements ProductPopAdapter.IOnItemSelectListener {
    @BindView(R.id.psb_bar)
    ProductSortBarView psbBar;
    @BindView(R.id.et_product_search)
    EditText etProductSearch;
    @BindView(R.id.iv_more_btn1)
    ImageView ivMoreBtn1;
    private SpinnerProductPopWindow mSpinnerPopWindow;
    private List<UbProductCategoryClassBean> categoryDataList;
    private UbProductService ubProductService;
    private int itemId;
    private int searchUpDown = 0;
    private int searchNum = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_search_result;
    }

    @Override
    protected void initView() {
        super.initView();
        ubProductService = new UbProductService(this);
        ListView list = (ListView) refreshLayout.getPullableView();
        list.setDividerHeight(0);
        categoryDataList = new ArrayList<>();
        listAdapter = new UbProductListAdapter(UbProductAllCategoryActivity.this, dataList);
        list.setAdapter(listAdapter);

    }

    protected void initData() {
        getServerData();
        psbBar.setSelectChangedListener(new SelectChangedListener<SortBean>() {
            @Override
            public void changeHandle(SortBean value) {
                String searchOrder = psbBar.getOrder();//上下
                String searchColumn = value.code;
                searchUpDown = Integer.parseInt(searchOrder);
                searchNum = Integer.parseInt(searchColumn);
                dataList.clear();
                currentPage = 1;
                getServerData();
            }
        });
    }

    protected void getServerData() {
        //category_id分类id（是：显示分类列表；否：显示所有商品列表）
        //当进入分类是，默认排序：sort_type=3，sort_method=0,默认月销量，由大到小
        //排序sort_type（1：上新；2：价格；3：月销量）
        //sort_method排序方式（1：上新由近到远，价格由小到大,月销量由少到多;0:与1相反）
        //category_id分类id（是：显示分类列表；否：显示所有商品列表）
        //int categoryId,int sortType,int sortMethod,int page,int pageSize
        //获取该联盟商下商品
        ubProductService.postProductInfoSelect(itemId, searchNum, searchUpDown, 1, 15, new CommonResultListener<List<ProductSearchBean>>(this) {
            @Override
            public void successHandle(List<ProductSearchBean> result) throws JSONException {
                dataList.clear();
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        });


        //获取商品分类列表数据
        ubProductService.getProductCategoryList(new CommonResultListener<List<UbProductCategoryClassBean>>(this) {
            @Override
            public void successHandle(List<UbProductCategoryClassBean> result) throws JSONException {
                categoryDataList.clear();
                categoryDataList.addAll(result);
                handlePop();
            }
        });
    }

    private void showSpinWindow() {
        mSpinnerPopWindow.setWidth(ivMoreBtn1.getWidth() * 5);
        mSpinnerPopWindow.showAsDropDown(ivMoreBtn1, ivMoreBtn1.getWidth(), ivMoreBtn1.getWidth());
    }

    private void handlePop() {
        mSpinnerPopWindow = new SpinnerProductPopWindow(this, categoryDataList);
        mSpinnerPopWindow.setItemListener(UbProductAllCategoryActivity.this);

    }

    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos <= categoryDataList.size()) {
            itemId = categoryDataList.get(pos).id;
            //   etProductSearch.setText(categoryDataList.get(pos).name);
            getServerData();
            // handleSearch(categoryDataList.get(pos).name);
        }
    }

    @OnClick({R.id.iv_icon_back, R.id.iv_search, R.id.iv_more_btn1, R.id.ll_search_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_icon_back:
                ActivityActionHelper.goToMain(UbProductAllCategoryActivity.this);
                break;
            case R.id.iv_search:
                String name = etProductSearch.getText().toString().trim();
                if (name == null) {
                    showToast("搜索参数为空！");
                    return;
                }
                // handleSearch(name);
                Map<String, String> map = new HashMap<>();
                map.put(IntentExtraKeyConst.SEARCH_KEY, name);
                startActivity(UbProductSearchResultActivity.class, map);
                break;
            case R.id.iv_more_btn1:
                showSpinWindow();
                break;
            case R.id.ll_search_edit:
                break;
        }
    }

    private void handleSearch(String key) {
        ubProductService.getSearch(key, new CommonResultListener<List<ProductSearchBean>>(this) {
            @Override
            public void successHandle(List<ProductSearchBean> result) throws JSONException {
                dataList.clear();
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    //排序sort_type（1：上新；2：价格；3：月销量）
    //sort_method排序方式（1：上新由近到远，价格由小到大,月销量由少到多;0:与1相反）
    //category_id分类id（是：显示分类列表；否：显示所有商品列表）
    private void handleSelect() {

    }


}
