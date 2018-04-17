package youbao.shopping.ninetop.fragment.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import youbao.shopping.bigkoo.convenientbanner.ConvenientBanner;
import youbao.shopping.ninetop.activity.order.OrderConfirmActivity;
import youbao.shopping.ninetop.activity.product.ProductDetailActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.product.PictureBean;
import youbao.shopping.ninetop.bean.product.ProductBannerListBean;
import youbao.shopping.ninetop.bean.product.ProductCommentBean;
import youbao.shopping.ninetop.bean.product.ProductDetailBean;
import youbao.shopping.ninetop.bean.product.ProductServiceBean;
import youbao.shopping.ninetop.bean.product.ProductSkuBean;
import youbao.shopping.ninetop.bean.product.ProductSkuValueBean;
import youbao.shopping.ninetop.common.ActivityActionHelper;
import youbao.shopping.ninetop.common.AssembleHelper;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.fragment.BaseFragment;
import youbao.shopping.ninetop.fragment.FragmentContext;
import youbao.shopping.ninetop.fragment.product.dialog.ProductCouponSelectDialog;
import youbao.shopping.ninetop.fragment.product.dialog.ProductPropSelectDialog;
import youbao.shopping.ninetop.service.impl.ProductService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.R.id.tv_price;

/**
 * Created by jill on 2016/11/25.
 */

public class ProductInfoFragment extends BaseFragment {

    @BindView(R.id.cb_product_logo)
    ConvenientBanner cbProductLogo;
    @BindView(R.id.tv_banner_index)
    TextView tvBannerIndex;
    @BindView(R.id.tv_banner_count)
    TextView tvBannerCount;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.tv_advt_Point)
    TextView tvAdvtPoint;
    @BindView(tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_provider_name)
    TextView tvProviderName;
    @BindView(R.id.tv_select_prop)
    TextView tvSelectProp;
    @BindView(R.id.rl_select_prop)
    RelativeLayout rlSelectProp;
    @BindView(R.id.tv_select_coupon)
    TextView tvSelectCoupon;
    @BindView(R.id.rl_select_coupon)
    RelativeLayout rlSelectCoupon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_prop)
    TextView tvProp;
    @BindView(R.id.ll_service_list)
    LinearLayout llServiceList;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.sv_product_info)
    ScrollView svProductInfo;
    @BindView(R.id.include_comment)
    View includeComment;
    @BindView(R.id.ll_comment_no_data)
    View commentNoData;
    protected ProductService productService;

    protected ProductDetailBean productDetailBean;

    private ProductPropSelectDialog propSelectDialog;

    private ProductCouponSelectDialog couponSelectDialog;

    private List<PictureBean> bannerPicList = null;

    protected Handler handler;

    private int shopCartCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_info;
    }

    @Override
    protected void initView() {
        super.initView();

        String productCode = ((BaseActivity)getContext()).getIntentValue(IntentExtraKeyConst.PRODUCT_CODE);
        if(productCode == null || productCode.length()==0){
            ((Viewable)getContext()).showToast("商品不存在");
            return;
        }

        productService = new ProductService((Viewable) getContext());
        getProductBanner(productCode);
        getProductDetail(productCode);

        setShopCartCount();
        getShopCartCount();
    }

    @OnClick({R.id.rl_select_coupon, R.id.ll_all_review, R.id.ll_more, R.id.rl_select_prop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_select_prop:
                showPropDialog();
                break;
            case R.id.rl_select_coupon:
                showCouponDialog();
                break;
            case R.id.ll_all_review:
                ((FragmentContext)getContext()).setCurrentItem(2);
                break;
            case R.id.ll_more:
                ((FragmentContext)getContext()).setCurrentItem(1);
                break;
        }
    }

    protected void getProductDetail(String productCode){
        productService.getProductDetail(productCode, new CommonResultListener<ProductDetailBean>((Viewable) getContext()) {
            @Override
            public void successHandle(ProductDetailBean result) {
                if(result == null)
                    return;

                onProductDetailHandle(result);
            }
        });
    }

    protected void onProductDetailHandle(ProductDetailBean result){
        productDetailBean = result;

        tvAdvtPoint.setText(result.advtPoint);
        tvBrandName.setText(result.name);
        tvPrice.setText(result.price);
        tvProviderName.setText("供应商：" + result.providerName);
        tvSelectProp.setText("已选：" + getSelectPropText(result.skuList));
        tvSelectCoupon.setText(result.couponName);

        tvCommentCount.setText("评论("+ result.commentCount + ")");
        if(result.commentList != null && result.commentList.size() > 0) {
            ProductCommentBean commentBean = result.commentList.get(0);

            tvName.setText(commentBean.name);
            tvContent.setText(commentBean.comment);
            tvDate.setText(commentBean.time);
            tvProp.setText(commentBean.skuDesc);

            includeComment.setVisibility(View.VISIBLE);
            commentNoData.setVisibility(View.GONE);
        }else{
            includeComment.setVisibility(View.GONE);
            commentNoData.setVisibility(View.VISIBLE);
        }

        addServiceTag(result.serviceList);
    }

    private void getProductBanner(String productCode){
        bannerPicList = null;
        productService.getProductBanner(productCode, new CommonResultListener<ProductBannerListBean>((Viewable) getContext()) {
            @Override
            public void successHandle(ProductBannerListBean result) {
                if(result == null || result.picList == null)
                    return;

                bannerPicList = result.picList;

                List<Integer> localImages = new ArrayList<Integer>();
                localImages.add(R.mipmap.demo_banner);
                List<String> netWorkImages = new ArrayList<String>();
                for(PictureBean bean : bannerPicList){
                    netWorkImages.add(bean.getPicUrl());
                }
                cbProductLogo.setData(cbProductLogo, netWorkImages, localImages, new Integer[]{R.mipmap.rotation_default, R.mipmap.rotation_select});
                cbProductLogo.stopTurning();
                cbProductLogo.setPointViewVisible(false);
                cbProductLogo.setCanLoop(true);
                cbProductLogo.setAutoPlay(false);

                tvBannerCount.setText(bannerPicList.size() + "");
                tvBannerIndex.setText("1");

                cbProductLogo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
                    public void onPageSelected(int position) {
                        tvBannerIndex.setText((position + 1) + "");
                    }
                    public void onPageScrollStateChanged(int state) {}
                });

            }
        });
    }

    private void addServiceTag(List<ProductServiceBean> beanList){
        llServiceList.removeAllViews();
        if(beanList == null || beanList.size() == 0) {
            return;
        }

        for(ProductServiceBean bean : beanList){
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);

            imageView.setPadding(Tools.dip2px(getContext(), 15), 0, 0, 0);
            imageView.setImageResource(R.mipmap.select_icon);
            llServiceList.addView(imageView);

            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(textLp);
            textView.setText(bean.getName());
            textView.setPadding(Tools.dip2px(getContext(), 5), 0,0,0);
            textView.setTextSize(11);
            textView.setTextColor(Tools.getColorValueByResId(getContext(), R.color.text_gray));
            llServiceList.addView(textView);
        }
    }

    private void showCouponDialog() {
        couponSelectDialog = new ProductCouponSelectDialog((ProductDetailActivity)
                getContext(), this, productDetailBean, productService);
        couponSelectDialog.showDialog();
    }

    private void showPropDialog() {
        String imageUrl = bannerPicList == null || bannerPicList.size() == 0 ?
                null : bannerPicList.get(0).getPicUrl();

        if(propSelectDialog == null) {
            propSelectDialog = new ProductPropSelectDialog((ProductDetailActivity)
                    getContext(), this, productDetailBean, imageUrl, productService);
        }
        propSelectDialog.showDialog();
        propSelectDialog.setShopCartCount(shopCartCount);
        propSelectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ProductSkuBean skuBean = propSelectDialog.getSelectedSkuBean();
                tvPrice.setText(skuBean.price);
                tvSelectProp.setText("已选：" + getSelectPropText(skuBean.valueList));
            }
        });
    }

    public void addShopCart(){
        String itemCode = productDetailBean.code;
        String skuId = getSelectedSkuId();
        int amount = getBuyAmount();

        productService.addShopCart(itemCode, skuId, amount, new CommonResultListener<String>((Viewable) getContext()) {
            @Override
            public void successHandle(String result) {
                shopCartCount++;
                setShopCartCount();
                ((Viewable)getContext()).showToast("加入购物车成功");
            }
        });
    }

    public void buyNow(){
        String skuId = getSelectedSkuId();
        final int amount = getBuyAmount();
        productService.buyNowProduct(skuId, amount, new CommonResultListener<JSONObject>((Viewable) getContext()) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String dataString = result.getString("data");

                Intent intent = new Intent(getContext(), OrderConfirmActivity.class);
                intent.putExtra(IntentExtraKeyConst.JSON_DATA, dataString);
                intent.putExtra(IntentExtraKeyConst.ORDER_FROM, "buy");
                intent.putExtra(IntentExtraKeyConst.PRODUCT_AMOUNT, amount + "");
                startActivity(intent);
            }
        });
    }

    private String getSelectedSkuId(){
        if(propSelectDialog == null || propSelectDialog.getSelectedSkuBean() == null){
            return productDetailBean.skuId;
        }

        ProductSkuBean skuBean = propSelectDialog.getSelectedSkuBean();
        return skuBean.skuId;
    }

    private int getBuyAmount(){
        if(propSelectDialog == null){
            return 1;
        }

        return propSelectDialog.getSelectedCount();
    }

    public void selectedPropChangeHandle(){
        if(propSelectDialog == null)
            return;

        ProductSkuBean skuBean = propSelectDialog.getSelectedSkuBean();

        String propText = getSelectPropText(skuBean.valueList);
        tvSelectProp.setText(propText);
    }

    private String getSelectPropText(List<ProductSkuValueBean> skuList){
        return  AssembleHelper.assembleSku(skuList);
    }

    int offsety = 0;
    int y = 0;
    int offsetsum = 0;
    Point point = new Point();
    private void scrollBottomHandle(){
        svProductInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    int scrollY = svProductInfo.getChildAt(svProductInfo.getChildCount()-1).getBottom()+
//                            svProductInfo.getPaddingBottom()-svProductInfo.getHeight();
//                    boolean isAtBottom = svProductInfo.getScrollY()==scrollY;
//                    if(isAtBottom){
//                        if(handler == null)
//                            handler = new Handler(Looper.getMainLooper());
//
//                        handler.postDelayed(new Runnable() {
//                            public void run() {
//                                ((FragmentContext)getContext()).setCurrentItem(1);
//                            }
//                        }, 300);
//                    }
//                }

                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        point.y = (int) event.getRawY();
                        offsetsum = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        y = (int) event.getRawY();
                        offsety = y - point.y;
                        offsetsum += offsety;
                        point.y = (int) event.getRawY();
//                        svProductInfo.scrollBy(0, -offsety);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (offsetsum < -300) {// offsetsum大于0时是往下拉，只有当显示详情页是下拉才有效果，所以这里先判断isOpen的值。
                            ((FragmentContext) getContext()).setCurrentItem(1);
                        }
                }
                return false;
            }
        });
    }

    private void getShopCartCount(){
        productService.getShopCartNum(new CommonResultListener<Integer>((Viewable)getContext()) {
            @Override
            public void successHandle(Integer result) throws JSONException {
                shopCartCount = result;
                setShopCartCount();
            }
        });
    }

    private void setShopCartCount(){
        ((ProductDetailActivity)getContext()).setShopCartCount(shopCartCount);

        if(propSelectDialog != null)
            propSelectDialog.setShopCartCount(shopCartCount);

        ActivityActionHelper.changeMainCartNum(getContext(), shopCartCount);
    }

}
