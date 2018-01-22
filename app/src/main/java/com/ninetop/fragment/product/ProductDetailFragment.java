package com.ninetop.fragment.product;

import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.ninetop.base.BaseActivity;
import com.ninetop.base.ProductListAdapter;
import com.ninetop.base.Viewable;
import com.ninetop.bean.product.ProductRecommendPagingBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.TextConstant;
import com.ninetop.common.view.ScrollViewWithListView;
import com.ninetop.fragment.BaseFragment;
import com.ninetop.service.impl.ProductService;
import com.ninetop.service.listener.CommonResultListener;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by jill on 2016/11/25.
 */

public class ProductDetailFragment extends BaseFragment {
    @BindView(R.id.wv_detail)
    WebView wvDetail;
    @BindView(R.id.lv_recommend_list)
    ScrollViewWithListView lvRecommendList;
    @BindView(R.id.sv_product_detail)
    ScrollView svProductDetail;
    private ProductService productService;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_detail;
    }

    @Override
    protected void initView() {
        super.initView();

        String productCode = ((BaseActivity)getContext()).getIntentValue(IntentExtraKeyConst.PRODUCT_CODE);
        productService = new ProductService((Viewable) getContext());
        productService.getProductRecommend(productCode, "1", new CommonResultListener<ProductRecommendPagingBean>((Viewable) getContext()) {
            @Override
            public void successHandle(ProductRecommendPagingBean result) {
                ProductListAdapter productListAdapter = new ProductListAdapter(getContext(), result.dataList);
                lvRecommendList.setAdapter(productListAdapter);
            }
        });

        productService.getProductHTMLContent(productCode, new CommonResultListener<String>((Viewable) getContext()) {
            @Override
            public void successHandle(String result) {
                initWebView(result);
            }
        });
    }

    private void initWebView(String html){
        if(html == null && html.length() == 0)
            return;

        String style = "";
        if(!isHtmlContentHasStyle(html)){
            style = TextConstant.HTTP_STYLE;
        }

        String url = TextConstant.HTTP_BODY_START + html + style + TextConstant.HTTP_BODY_END;
        wvDetail.setScrollbarFadingEnabled(true);
        wvDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        wvDetail.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);

        wvDetail.loadData(url, "text/html;charset=UTF-8", null);
    }

    public void scrollToTop(){
        svProductDetail.smoothScrollTo(0,0);
    }

    private boolean isHtmlContentHasStyle(String content){
        return content.indexOf(" style=\"") != -1 ;
    }
}
