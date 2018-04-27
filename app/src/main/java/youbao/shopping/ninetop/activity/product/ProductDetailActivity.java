package youbao.shopping.ninetop.activity.product;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import youbao.shopping.ninetop.activity.order.shopcart.ShopcartActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.MessageEvent;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.fragment.FragmentContext;
import youbao.shopping.ninetop.fragment.product.ProductDetailFragment;
import youbao.shopping.ninetop.fragment.product.ProductFragmentPagerAdapter;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;
import youbao.shopping.ninetop.fragment.product.ProductInfoFragment;


/**
 * Created by jill on 2016/11/11.
 */

public class ProductDetailActivity extends BaseActivity implements FragmentContext {
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.v_line_info)
    View vLineInfo;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.v_line_detail)
    View vLineDetail;
    @BindView(R.id.tv_review)
    TextView tvReview;
    @BindView(R.id.v_line_review)
    View vLineReview;
    @BindView(R.id.vp_product_detail)
    ViewPager vpProductDetail;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.tv_cart_num)
    TextView tvCartNum;

    private View[] lineArray = null;
    private TextView[] textViewArray = null;

    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }
    private ProductFragmentPagerAdapter pagerAdapter;

    @Override
    protected void initView() {
        super.initView();
        initViewPaper();
    }

    private void initViewPaper(){
        pagerAdapter = createPagerAdapter();

        vpProductDetail.setAdapter(pagerAdapter);

        vpProductDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                changeProductDisplay(position);
            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageScrollStateChanged(int position) {}
        });

        changeProductDisplay(0);
        vpProductDetail.setCurrentItem(0);
    }

    protected ProductFragmentPagerAdapter createPagerAdapter(){
        return new ProductFragmentPagerAdapter(getSupportFragmentManager());
    }

    @OnClick({R.id.iv_back, R.id.ll_product_info, R.id.ll_product_detail, R.id.ll_product_review,
            R.id.ll_add_shop_cart, R.id.ll_buy_now, R.id.ll_show_shop_cart, R.id.ll_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_product_info:
                changeProductDisplay(0);
                vpProductDetail.setCurrentItem(0);
                break;
            case R.id.ll_product_detail:
                changeProductDisplay(1);
                vpProductDetail.setCurrentItem(1);
                break;
            case R.id.ll_product_review:
                changeProductDisplay(2);
                vpProductDetail.setCurrentItem(2);
                break;
            case R.id.ll_add_shop_cart:
                addShopCart();
                break;
            case R.id.ll_buy_now:
                addOrder();
                break;
            case R.id.ll_show_shop_cart:
                Map<String,String> map = new HashMap<>();
                map.put(IntentExtraKeyConst.SHOW_BACK, "1");
                startActivity(ShopcartActivity.class, map);
                break;
            case R.id.ll_top:
                ((ProductDetailFragment)pagerAdapter.getItem(1)).scrollToTop();
                break;
        }
    }

    public void changeProductDisplay(int index){
        int visible = index == 1 ? View.VISIBLE  : View.GONE;
        llTop.setVisibility(visible);

        if(textViewArray == null){
            lineArray = new View[]{vLineInfo, vLineDetail, vLineReview};
            textViewArray = new TextView[]{tvInfo, tvDetail, tvReview};
        }

        for(int i=0;i<textViewArray.length;i++){
            if(i == index){
                lineArray[i].setVisibility(View.VISIBLE);
                textViewArray[i].setTextSize(18);
                textViewArray[i].setTextColor(Tools.getColorValueByResId(this, R.color.text_red));
                continue;
            }
            lineArray[i].setVisibility(View.INVISIBLE);
            textViewArray[i].setTextSize(16);
            textViewArray[i].setTextColor(Tools.getColorValueByResId(this, R.color.text_gray));
        }
    }

    @Override
    public void setCurrentItem(int position) {
        vpProductDetail.setCurrentItem(position);
    }

    private void addShopCart(){
        ProductInfoFragment productInfoFragment = (ProductInfoFragment)pagerAdapter.getItem(0);
        productInfoFragment.addShopCart();
    }

    private void addOrder(){
        ProductInfoFragment productInfoFragment = (ProductInfoFragment)pagerAdapter.getItem(0);
        productInfoFragment.buyNow();
    }

    public void setShopCartCount(int count){
        if(count <= 0){
            tvCartNum.setVisibility(View.GONE);
        }else {
            tvCartNum.setText(count + "");
            tvCartNum.setVisibility(View.VISIBLE);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEvent(MessageEvent event){
        if (event != null){
            setShopCartCount(event.nCount);
        }
    }
}
