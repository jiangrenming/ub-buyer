package youbao.shopping.ninetop.activity.ub.product;

import android.widget.ListView;

import youbao.shopping.ninetop.UB.product.ProductSearchBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.base.PullRefreshBaseActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

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
    private  String freeId;
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
        freeId = getIntentValue(IntentExtraKeyConst.FRANCHISEEID);
        getServerData();

    }
   //商品搜索

    @Override
    protected void getServerData() {
        productService.getSearch(searchKey,freeId,new CommonResultListener<List<ProductSearchBean>>(this) {
            @Override
           public void successHandle(List<ProductSearchBean> result) throws JSONException {
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
           }
       });
    }
}


