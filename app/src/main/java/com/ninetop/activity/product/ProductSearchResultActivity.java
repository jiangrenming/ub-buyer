package com.ninetop.activity.product;

import android.widget.ListView;

import com.ninetop.base.ProductListAdapter;
import com.ninetop.base.PullRefreshBaseActivity;
import com.ninetop.bean.product.ProductPagingBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.ProductSortBarView;
import com.ninetop.common.view.SelectChangedListener;
import com.ninetop.common.view.bean.SortBean;
import com.ninetop.service.impl.ProductService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import youbao.shopping.R;


/**
 * @date: 2016/11/9
 * @author: jill
 */
public class ProductSearchResultActivity<ProductBean> extends PullRefreshBaseActivity {


    @BindView(R.id.hv_head)
    HeadView hvHead;

    @BindView(R.id.psb_bar)
    ProductSortBarView psbBar;

    private ProductService productService;
    private String searchKey = null;
    private String searchOrder = "ASC";
    private String searchColumn = "overall";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_search_result;
    }

    @Override
    protected void initView() {
        super.initView();

        productService = new ProductService(this);

        ListView list = (ListView) refreshLayout.getPullableView();
        list.setDividerHeight(0);
        listAdapter = new ProductListAdapter(ProductSearchResultActivity.this, dataList);
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

    protected void getServerData(){
        productService.searchByKey(searchKey, currentPage + "", searchColumn, searchOrder,
            new CommonResultListener<ProductPagingBean>(this) {
                @Override
                public void successHandle(ProductPagingBean result) throws JSONException {
                    if(result == null || result.dataList == null || result.dataList.size() == 0)
                        return;
                    dataList.addAll(result.dataList);
                    listAdapter.notifyDataSetChanged();
                }
            }
        );
    }
}

