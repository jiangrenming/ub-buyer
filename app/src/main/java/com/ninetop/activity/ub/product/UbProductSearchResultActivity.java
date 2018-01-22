package com.ninetop.activity.ub.product;

import android.widget.ListView;

import com.ninetop.UB.product.ProductSearchBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.base.PullRefreshBaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/6/4.
 */

public class UbProductSearchResultActivity extends PullRefreshBaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    private UbProductService productService;
    private String searchKey ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_search_result_info;
    }

    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("搜索结果");
        productService = new UbProductService(this);
        ListView list = (ListView) refreshLayout.getPullableView();
        list.setDividerHeight(0);
        listAdapter = new UbProductSearchListAdapter(UbProductSearchResultActivity.this, dataList);
        list.setAdapter(listAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        searchKey = getIntentValue(IntentExtraKeyConst.SEARCH_KEY);
        getServerData();

    }
   //商品搜索

    protected void getServerData() {
        productService.getSearch(searchKey,new CommonResultListener<List<ProductSearchBean>>(this) {
            @Override
           public void successHandle(List<ProductSearchBean> result) throws JSONException {
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
           }
       });
    }
}


