package youbao.shopping.ninetop.activity.ub.product;

import android.os.Bundle;
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
import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/30.
 */
public class ProductCategoryActivity extends PullRefreshBaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    private String productCategoryName;
    private String  productCategoryId;
    private String  franchiseeId;
    private UbProductService ubProductService;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_product_category;
    }

    @Override
    protected void initView() {
        super.initView();
        ubProductService = new UbProductService(this);
        productCategoryName = getIntentValue(IntentExtraKeyConst.PRODUCT_CATEGORY_NAME);
        productCategoryId = getIntentValue(IntentExtraKeyConst.PRODUCT_CATEGORY_ID);
        franchiseeId = getIntentValue(IntentExtraKeyConst.FRANCHISEE_ID);
        hvHead.setTitle(productCategoryName);
        ListView list=(ListView)refreshLayout.getPullableView();
        list.setDividerHeight(0);
        listAdapter=new UbProductListAdapter(ProductCategoryActivity.this,dataList);
        list.setAdapter(listAdapter);
        getServerData();
    }


    protected void getServerData() {
        int themeId=Integer.parseInt(productCategoryId);
        int franchisee =  Integer.parseInt(franchiseeId);
        ubProductService.postFranchiseeCategory(themeId, franchisee, 1, 15, new CommonResultListener<List<ProductSearchBean>>(this) {
            @Override
            public void successHandle(List<ProductSearchBean> result) throws JSONException {
                if(result.size()==0){
                    return;
                }
                dataList.addAll(result);
           //     showToast(result.toString());
                listAdapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
