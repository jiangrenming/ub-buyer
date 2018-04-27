package youbao.shopping.ninetop.activity.ub.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import youbao.shopping.bigkoo.convenientbanner.ConvenientBanner;
import com.google.gson.Gson;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import youbao.shopping.ninetop.UB.product.New.SingleProductSkuBean;
import youbao.shopping.ninetop.UB.product.New.SingleSkuBean;
import youbao.shopping.ninetop.UB.product.UbProductDetailBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.activity.ub.order.UbConfirmOrderActivity;
import youbao.shopping.ninetop.activity.ub.shopcart.UbShopCartActivity;
import youbao.shopping.ninetop.activity.ub.util.StatusBarUtil;
import youbao.shopping.ninetop.activity.user.LoginActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.MessageEvent;
import youbao.shopping.ninetop.common.AssembleHelper;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;
import static youbao.shopping.ninetop.config.AppConfig.FRANCHISEEID;

/**
 * Created by huangjinding on 2017/4/20.
 */
public class UbProductInfoActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_shopcart)
    ImageView ivShopcart;
    @BindView(R.id.iv_back_to)
    ImageView ivBackTo;
    @BindView(R.id.iv_shopcart_to)
    ImageView getIvShopcartTo;
    @BindView(R.id.iv_my_collection)
    ImageView ivMyCollection;
    @BindView(R.id.cb_banner)
    ConvenientBanner cbBanner;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.sv_product_info)
    ScrollView svProductInfo;
    @BindView(R.id.ll_kefu)
    LinearLayout llKefu;
    @BindView(R.id.ll_shoucang)
    LinearLayout llShoucang;
    @BindView(R.id.ll_add_shop_cart)
    LinearLayout llAddShopcart;
    @BindView(R.id.ll_change_now)
    LinearLayout llChangeNow;
    @BindView(R.id.wv_detail)
    WebView wvDetail;
    @BindView(R.id.rl_select_product_info)
    RelativeLayout rlSelectProductInfo;
    @BindView(R.id.rl_select_product_guige)
    RelativeLayout rlSelectProductGuige;
    @BindView(R.id.tv_banner_index)
    TextView tvBannerIndex;
    @BindView(R.id.tv_banner_count)
    TextView tvBannerCount;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.tv_kuaidi_price)
    TextView tvKuaiDiPrice;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_product_address)
    TextView tvProductAddress;

    private UbProductDetailBean ubProductDetailBean;
    private String[] picArray = null;
    private UbProductService ubProductService;
    private UbProductSelectInfoDialog infoDialog;
    private UbProductSelectInfoDetailDialog detailDialog;
    private SingleProductSkuBean singleProductSkuBean;
    private String phone;
    private int id;
    private int skuId;
    private long skuprice;
    private int amount;
    private String mainIcon;
    private boolean isSelect = false;
    private String franchiseeid;

    @Override
    protected void setStatusBar() {
        int mColor = getResources().getColor(R.color.text_red);
        StatusBarUtil.setColor(this, mColor);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_product_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        ubProductService = new UbProductService(this);
        getProductDetail();
    }


    private void getProductDetail() {
        String proId = getIntentValue(IntentExtraKeyConst.PRODUCT_ID);
        franchiseeid = getIntentValue(IntentExtraKeyConst.FRANCHISEEID);
        id = Integer.parseInt(proId);
        ubProductService.getProductDetail(id, 1, "",franchiseeid, new CommonResultListener<UbProductDetailBean>(this) {
            @Override
            public void successHandle(UbProductDetailBean result) throws JSONException {
                if (result == null) {
                    return;
                }

                ubProductDetailBean = result;
                onProductDetailHandle(result);
            }
        });
    }

    private void onProductDetailHandle(UbProductDetailBean result) {
        tvBrandName.setText(result.getName());
        String mUbNum = Math.round(Double.valueOf(result.getPrice_range()))+"";
        tvPrice.setText(mUbNum);
        tvKuaiDiPrice.setText(result.getBase_freight() + "");
        phone = result.getMobile() + "";
        Log.i("查看收藏状态",result.getIs_favor()+"");
        if (result.getIs_favor() == 1) {
            ivMyCollection.setImageResource(R.mipmap.collection_red);
        } else {
            ivMyCollection.setImageResource(R.mipmap.shoucang_grey);
        }
         isSelect = result.getIs_favor() == 1 ? true : false;
        initBanner(result.getIcon());
        initWebView(result.getHtml_content());
    }

    private void initWebView(String html) {
        if (html == null && html.length() == 0) {
            return;
        }
        String style = "";
        style = TextConstant.HTTP_STYLE;
        String url = TextConstant.HTTP_BODY_START + html + style + TextConstant.HTTP_BODY_END;
        wvDetail.setScrollbarFadingEnabled(true);
        wvDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvDetail.loadData(url, "text/html;charset=UTF-8", null);
    }

    private boolean isHtmlContentHasStyle(String content) {
        return content.indexOf(" style=\"") != -1;
    }

    private void initBanner(String[] banner) {
        picArray = banner;
        mainIcon = picArray[0];
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.drawable.ktv1);
        List<String> netImages = new ArrayList<>();
        for (int i = 0; i < picArray.length; i++) {
            netImages.add(BASE_IMAGE_URL + picArray[i]);
        }
        cbBanner.setData(cbBanner, netImages, localImages, new Integer[]{R.mipmap.rotation_default, R.mipmap.rotation_select});
        cbBanner.stopTurning();
        cbBanner.setPointViewVisible(false);
        cbBanner.setCanLoop(true);
        cbBanner.setAutoPlay(false);
        tvBannerCount.setText(picArray.length + "");
        tvBannerIndex.setText("1");

        cbBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                tvBannerIndex.setText((position + 1) + "");
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_shopcart, R.id.ll_kefu, R.id.ll_shoucang, R.id.ll_add_shop_cart, R.id.ll_change_now,
            R.id.rl_select_product_info, R.id.rl_select_product_guige, R.id.iv_back_to, R.id.iv_shopcart_to})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_back_to:
                finish();
                break;
            case R.id.iv_shopcart_to:
                Map<String, String> map = new HashMap<>();
                map.put(IntentExtraKeyConst.SHOW_BACK, "1");
                startActivity(UbShopCartActivity.class, map);
                break;
            case R.id.ll_kefu:
                new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定拨打 " + phone, new MyDialogOnClick() {
                    @Override
                    public void sureOnClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void cancelOnClick(View v) {

                    }
                }).show();
                break;
            case R.id.ll_shoucang:
                if (isLogin()){
                    changeStatusHandle();
                }else {
                    Intent intent = new Intent(UbProductInfoActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_add_shop_cart:
                if (isLogin()){
                    addShopCart();
                }else {
                    Intent intent = new Intent(UbProductInfoActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_change_now:
                if (isLogin()){
                    changeNow();
                }else {
                    Intent intent = new Intent(UbProductInfoActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_select_product_info:
                showDialog1();
                break;
            case R.id.rl_select_product_guige:
                showDialog2();
                break;
            default:
                break;
        }
    }

    private void changeStatusHandle() {
        if (!isSelect) {
            ubProductService.postCollection(id,franchiseeid, new CommonResultListener(this) {
                @Override
                public void successHandle(Object result) throws JSONException {
                    ivMyCollection.setImageResource(R.mipmap.collection_red);
                    isSelect = true;
                    showToast("收藏成功");
                }
            });

        } else {
            ubProductService.postCollectionCansel(id,franchiseeid, new CommonResultListener(this) {
                @Override
                public void successHandle(Object result) throws JSONException {
                    ivMyCollection.setImageResource(R.mipmap.shoucang_grey);
                   isSelect = false;
                    showToast("取消成功");
                }
            });

        }
    }

    //加入购物车
    public void addShopCart() {
        if (id == 0 || skuId == 0 || skuprice == 0) {
            showDialog2();
            return;
        }
        ubProductService.postShopcartAdd("", id, 1, franchiseeid,skuId, amount, skuprice, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) throws JSONException {
            }
        });
    }

    //立即兑换，获取快递费用
    public void changeNow() {
        if (id == 0 || skuId == 0 || skuprice == 0) {
            showDialog2();
            return;
        }
        final List<Map> productList = new ArrayList<>();
        final Map<String, Integer> map = new HashMap<>();
        if (TextUtils.isEmpty(franchiseeid)){
            franchiseeid = 1+"";
        }
        map.put("franchiseeId", Integer.valueOf(franchiseeid));
        map.put("skuId", skuId);
        map.put("amount", amount);
        productList.add(map);
        //报服务器数据返回异常，采用第二种方案，用json
        Gson gson = new Gson();
        final String jsonBeanString = gson.toJson(productList);
        ubProductService.postEMSOrder(0, 0, 0, "", productList, new CommonResultListener<JSONObject>(this) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String dataString = result.getString("data");
                Log.i("获取的商品的数据:",dataString);
                Intent intent = new Intent(UbProductInfoActivity.this, UbConfirmOrderActivity.class);
                intent.putExtra(IntentExtraKeyConst.JSON_DATA, dataString);
                intent.putExtra(IntentExtraKeyConst.PRODUCT_LIST, jsonBeanString);
                intent.putExtra(IntentExtraKeyConst.ORDER_FROM, "buy");
                intent.putExtra(IntentExtraKeyConst.ORDER_SKUID, skuId + "");
                intent.putExtra(IntentExtraKeyConst.ORDER_AMOUNT, amount + "");
                intent.putExtra(IntentExtraKeyConst.FRANCHISEEID, franchiseeid + "");
                startActivity(intent);

            }
        });
    }

    protected void showDialog1() {
        infoDialog = new UbProductSelectInfoDialog(UbProductInfoActivity.this, ubProductService
                , id);
        infoDialog.showDialog();
    }

    //商品规格选择
    protected void showDialog2() {
        detailDialog = new UbProductSelectInfoDetailDialog(UbProductInfoActivity.this, ubProductService, id, mainIcon,franchiseeid);
        detailDialog.showDialog();
        detailDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                singleProductSkuBean = detailDialog.getSelectedSkuBean();
                amount = detailDialog.getSelectedCount();
                skuId = singleProductSkuBean.skuId;
                skuprice = Math.round(Double.valueOf(singleProductSkuBean.salePrice));
                tvProductAddress.setText("已选：" + getSelectPropText(singleProductSkuBean.attrList));
            }
        });
    }

    private String getSelectPropText(List<SingleSkuBean> skuList) {
        return AssembleHelper.assembleSkuUb(skuList);
    }

}