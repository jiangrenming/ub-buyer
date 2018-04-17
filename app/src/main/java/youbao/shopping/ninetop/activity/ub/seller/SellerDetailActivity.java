package youbao.shopping.ninetop.activity.ub.seller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;
import youbao.shopping.bigkoo.convenientbanner.ConvenientBanner;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import youbao.shopping.ninetop.UB.UbSellerService;
import youbao.shopping.ninetop.activity.ub.product.route.activity.RouteActivity;
import youbao.shopping.ninetop.activity.ub.product.route.util.ToastUtil;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.seller.SellerDetailBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.constant.SharedKeyConstant;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.common.util.MySharedPreference;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/5/11.
 */
public class SellerDetailActivity extends BaseActivity {
    private static final String TAG = "SellerDetailActivity";
    @BindView(R.id.iv_my_collection)
    ImageView ivMyCollection;
    @BindView(R.id.wv_detail)
    WebView wvDetail;
    @BindView(R.id.cb_banner)
    ConvenientBanner cbBanner;
    @BindView(R.id.tv_banner_index)
    TextView tvBannerIndex;
    @BindView(R.id.tv_banner_count)
    TextView tvBannerCount;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_personal_price)
    TextView tvPersonalPrice;
    @BindView(R.id.tv_ratio_count)
    TextView tvRatioCount;
    @BindView(R.id.tv_seller_address)
    TextView tvSellerAddress;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_seller_location_distance)
    TextView tvSellerLocationDistance;
    private UbSellerService ubSellerService;
    private String[] picArray = null;
    private String id;
    private String lat1;
    private String lng1;
    private String phone;
    private String isFavor;
    private double endLatitude;
    private double endLongitude;
    private LatLng endLatLng;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_seller_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        ubSellerService = new UbSellerService(this);
        id = getIntentValue(IntentExtraKeyConst.SELLER_ID);
        lat1 = MySharedPreference.get(SharedKeyConstant.SEARCH_LAT, "30.30589", this);
        lng1 = MySharedPreference.get(SharedKeyConstant.SEARCH_LON, "120.118026", this);
        if (id == null || id.length() == 0) {
            showToast("商家不存在");
            return;
        }
    }

    @OnClick({R.id.tv_seller_phone, R.id.tv_gotoBuy, R.id.iv_back, R.id.iv_my_collection, R.id.btn_submit_order, R.id.ll_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_seller_phone:
                call();
                break;
            case R.id.tv_gotoBuy:
                jumpToGaoDe();
                break;
            case R.id.iv_my_collection:
                selectCollection();
                break;
            case R.id.btn_submit_order:
                nowOrder();
                break;
            case R.id.ll_address:
                jumpToGaoDe();
                break;
            default:
                break;

        }
    }


    private void jumpToGaoDe() {
        if (isAvilible(SellerDetailActivity.this, "com.autonavi.minimap")) {
            jumpToNav();
        } else {
            ToastUtil.show(SellerDetailActivity.this, "您尚未安装高德地图");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent mIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(mIntent);
        }
    }

    private void jumpToNav() {
        if ("".equals(mEndAddress)) {
            return;
        }
        String mEndLat = String.valueOf(endLatitude);
        if ("".equals(mEndLat)) {
            return;
        }
        String mEndLng = String.valueOf(endLongitude);
        if ("".equals(mEndLng)) {
            return;
        }
        //地理编码
        StringBuffer stringBuffer = new StringBuffer("androidamap://route?sourceApplication=").append("amap");
        stringBuffer.append("&dlat=").append(mEndLat)
                .append("&dlon=").append(mEndLng)
                .append("&dname=").append(mEndAddress)
                .append("&dev=").append(0)
                .append("&t=").append(0);//默认驾车方式
        Intent mIntent = new Intent("android.intent.action.VIEW"
                , android.net.Uri.parse(stringBuffer.toString()));
        mIntent.setPackage("com.autonavi.minimap");
        mIntent.addCategory("android.intent.category.DEFAULT");
        startActivity(mIntent);
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packageManager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSellerDetail();
    }

    protected void getSellerDetail() {
        ubSellerService.getSellerDetail(lat1, lng1, id, null, new CommonResultListener<SellerDetailBean>(this) {
            @Override
            public void successHandle(SellerDetailBean result) throws JSONException {
                if (result == null) {
                    return;
                }
                onSellerDetailHandle(result);
            }
        });
    }

    private String mEndAddress;
    protected void onSellerDetailHandle(SellerDetailBean result) {
        id = result.getId();
        tvSellerName.setText(result.getName());
        tvPersonalPrice.setText("人均消费: ￥ " +Math.round(Double.valueOf(result.getPer_consume())));
        tvRatioCount.setText("积分赠送比例：" + result.getRatio());
        tvSellerAddress.setText(result.getName());
         mEndAddress = result.getAddr_province() + result.getAddr_city() + result.getAddr_county();
        // tvAddressDetail.setText(address + (result.getAddr_detail().length() > 8 ? result.getAddr_detail().substring(0, 8) + "..." : result.getAddr_detail()));
        tvAddressDetail.setText(mEndAddress + result.getAddr_detail());
        int index = result.getDistance().length();
        Log.i("定位的位置:",result.toString());
        if (index == 3) {
            tvSellerLocationDistance.setText("0" + result.getDistance() + " KM");
        } else {
            tvSellerLocationDistance.setText(result.getDistance() + " KM");
        }
        phone = result.getTelephone();
        initBanner(result.getIcon());
        initWebView(result.getHtml_content());
        isFavor = result.is_favor;
        if ("1".equals(result.is_favor)) {
            ivMyCollection.setImageResource(R.mipmap.collection_red);
        } else {
            ivMyCollection.setImageResource(R.mipmap.shoucang_grey);
        }
        String mStrBDLat = result.getLatitude();
        String mStrBDLng = result.getLongitude();
        double mBDLat = Double.valueOf(mStrBDLat);
        double mBDLong = Double.valueOf(mStrBDLng);
        LatLng mBDLatLng = new LatLng( mBDLat,mBDLong);
        CoordinateConverter converter = new CoordinateConverter(SellerDetailActivity.this);
        // CoordinateType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.BAIDU);
        // mBDLatLng待转换坐标点 LatLng类型
        converter.coord(mBDLatLng);
        // 执行转换操作
        endLatLng = converter.convert();
        endLatitude = endLatLng.latitude;
        endLongitude = endLatLng.longitude;
    }
    private void initBanner(String[] banner) {
        picArray = banner;
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
        cbBanner.setAutoPlay(true);
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
    private void jumpToRoute() {
        Bundle mBundle = new Bundle();
        mBundle.putDouble(IntentExtraKeyConst.END_LAT, endLatitude);
        mBundle.putDouble(IntentExtraKeyConst.END_LNG, endLongitude);
        Intent mIntent = new Intent(SellerDetailActivity.this, RouteActivity.class);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
        SellerDetailActivity.this.finish();
    }

    private void nowOrder() {
        Intent intent = new Intent(SellerDetailActivity.this, SellerPayActivity.class);
        intent.putExtra(IntentExtraKeyConst.SELLER_ID, id);
        startActivity(intent);
    }

    private void selectCollection() {
        //1是0否
        if ("1".equals(isFavor)) {
            ubSellerService.postSellerCansel(id, new CommonResultListener(this) {
                @Override
                public void successHandle(Object result) throws JSONException {
                    isFavor = "0";
                    ivMyCollection.setImageResource(R.mipmap.shoucang_grey);
                    showToast("取消成功");
                }
            });
        } else {
            ubSellerService.postSellerAdd(id, new CommonResultListener(this) {
                @Override
                public void successHandle(Object result) throws JSONException {
                    ivMyCollection.setImageResource(R.mipmap.collection_red);
                    isFavor = "1";
                    showToast("收藏成功");
                }
            });
        }
//             ivMyCollection.setImageResource(R.mipmap.shoucang_grey);
//            if(!isSelect){
//                ubSellerService.postSellerAdd(id, new CommonResultListener<String>(this) {
//                    @Override
//                    public void successHandle(String result) throws JSONException {
//                        showToast("添加商家收藏成功");
//                        ivMyCollection.setImageResource(R.mipmap.collection_red);
//                    }
//                });
//                isSelect=true;
//            }else{
//                new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要取消收藏吗", new MyDialogOnClick() {
//                    @Override
//                    public void sureOnClick(View v) {
//                        ubSellerService.postSellerCansel(id, new CommonResultListener<String>(SellerDetailActivity.this) {
//                            @Override
//                            public void successHandle(String result) throws JSONException {
//                                showToast("取消商家收藏成功");
//                                ivMyCollection.setImageResource(R.mipmap.shoucang_grey);
//                            }
//                        });
//                        isSelect=false;
//                    }
//
//                    @Override
//                    public void cancelOnClick(View v) {
//                    }
//                }).show();
//
//            }

    }


    private void call() {
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
    }


    private void initWebView(String html) {
        if (html == null || html.length() == 0) {
            return;
        }
        String style = "";
        if (!isHtmlContentHasStyle(html)) {
            style = TextConstant.HTTP_STYLE;
        }
        String url = TextConstant.HTTP_BODY_START + html + style + TextConstant.HTTP_BODY_END;
        wvDetail.setScrollbarFadingEnabled(true);
        wvDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvDetail.loadData(url, "text/html;charset=UTF-8", null);
    }

    private boolean isHtmlContentHasStyle(String content) {
        return content.indexOf(" style=\"") != -1;
    }
}
