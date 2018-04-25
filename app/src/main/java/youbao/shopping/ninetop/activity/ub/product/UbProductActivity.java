package youbao.shopping.ninetop.activity.ub.product;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import youbao.shopping.bigkoo.convenientbanner.ConvenientBanner;
import youbao.shopping.bigkoo.convenientbanner.listener.OnItemClickListener;
import youbao.shopping.ninetop.UB.ProductListBean;
import youbao.shopping.ninetop.UB.product.New.SpinnerListBean;
import youbao.shopping.ninetop.UB.product.ProductBannerBean;
import youbao.shopping.ninetop.UB.product.UbProductMainListBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.activity.ub.banner.UbWebViewActivity;
import youbao.shopping.ninetop.activity.ub.seller.SellerPayActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.SelectedCityActivity;
import youbao.shopping.ninetop.activity.ub.util.MyScrollView;
import youbao.shopping.ninetop.activity.ub.util.newPop.FranchiseeAdapter;
import youbao.shopping.ninetop.activity.ub.util.newPop.SpinnerFranchiseeWindow;
import youbao.shopping.ninetop.activity.user.LoginActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.IntentExtraValueConst;
import youbao.shopping.ninetop.common.LoginAction;
import youbao.shopping.ninetop.common.constant.SharedKeyConstant;
import youbao.shopping.ninetop.common.util.JudgeObjectUtils;
import youbao.shopping.ninetop.common.util.MySharedPreference;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.ScrollViewWithListView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.xudaojie.qrcodelib.CaptureActivity;
import youbao.shopping.R;
import youbao.shopping.ninetop.update.Constants;
import youbao.shopping.ninetop.update.UpdateChecker;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/4/19.
 */
public class UbProductActivity extends HuangChangeActivity implements ViewPager.OnPageChangeListener, FranchiseeAdapter.IOnItemSelectListener, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {
    private static final String TAG = "UbProductActivity";
    @BindView(R.id.cb_banner)
    ConvenientBanner cbBanner;
    @BindView(R.id.viewPager)
    ViewPager ViewPager;
    @BindView(R.id.tv_select_city)
    TextView tvSelectCity;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.ll_edit_select)
    LinearLayout llEditSelect;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.vp_seller_list)
    ScrollViewWithListView vpSellerList;
    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.scorll_view)
    MyScrollView scrollView;
    @BindView(R.id.main_iv_share)
    ImageView main_iv_share;
    private UbProductListTwoAdapter productListAdapter;
    private List<ProductListBean> listProduct;
    private ProductBannerBean bannerBean;

    private int height;
    private ViewGroup points;//小圆点指示器
    private ImageView[] ivPoints;//小圆点图片集合
    private int totalPage;//总的页数
    private List<UbProductMainListBean> listData;//总的数据源
    private MyViewAdapter myViewAdapter;
    private UbProductService ubProductService;
    private List<ProductBannerBean> imageList = null;
    private List<SpinnerListBean> dataList;//该城市下拉框的加盟商
    private int franchiseeId;
    private SpinnerFranchiseeWindow mSpinnerPopWindow;
    private String franchiseeName;
    private String selectedFranchiseeName;
    private int type;
    private static final int BAI_DU_READ_PHONE_STATE = 100;
    //声明mLocationClient对象
    private AMapLocationClient mLocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;

    private String mCity;
    private String mSelectCity;
    private Double mLatitude;
    private Double mLongitude;
    private String mDistrict;
    // private String[] mPermissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] mPermissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();
    //经纬度转换
    private GeocodeSearch mGeocodeSearch;
    private int successNum = 0;
    private int failNum = 0;
    private int permissionNum = 0;

    @Override
    protected void onResume() {
        super.onResume();
        //获取权限并获得当位置
        getPermission();
        //获取店名
        getNamePop();
        franchiseeChangeHandle();
        if(null != mLocationClient){
            mLocationClient.startLocation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateChecker.checkForDialog(this, Constants.APP_UPDATE_SERVER_URL);//
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(null != mLocationClient){
            mLocationClient.stopLocation();
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_product_alfa;
    }

    @Override
    protected void initView() {
        super.initView();
        iniViews();
        initViewPaper();
    }

    private void iniViews() {
        ViewPager = (ViewPager) findViewById(R.id.viewPager);
        //初始化小圆点指示器
        points = (ViewGroup) findViewById(R.id.points);
        mGeocodeSearch = new GeocodeSearch(this);
        ubProductService = new UbProductService(this);
        listProduct = new ArrayList<>();
        dataList = new ArrayList<>();
        listData = new ArrayList<>();
        productListAdapter = new UbProductListTwoAdapter(UbProductActivity.this, listProduct);
        vpSellerList.setAdapter(productListAdapter);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        franchiseeId = bundle.getInt(IntentExtraKeyConst.FRANCHID);
        franchiseeName = bundle.getString(IntentExtraKeyConst.FRANCHNAME);
    }

    private void initViewPaper() {
        if (franchiseeId == 0) {
            franchiseeId = 1;
        }

    }

    @Override
    public void onScrollChanged(int y) {}


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //改变小圆圈指示器的切换效果
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 改变点点点的切换效果
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < ivPoints.length; i++) {
            if (i == selectItems) {
                ivPoints[i].setBackgroundResource(R.mipmap.banner_black);
            } else {
                ivPoints[i].setBackgroundResource(R.mipmap.banner_gray_b);
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        scrollView.addOnScrollChangedListener(this);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height = cbBanner.getHeight();
                onPageScrollStateChanged(scrollView.getScrollY());
            }
        });
        mGeocodeSearch.setOnGeocodeSearchListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        scrollView.smoothScrollTo(0, 0);
        //获取轮播图
        ubProductService.getProductBanner(new CommonResultListener<List<ProductBannerBean>>(this) {
            @Override
            public void successHandle(List<ProductBannerBean> result) throws JSONException {
                if (result == null || result.size() == 0) {
                    return;
                }
                imageList = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    bannerBean = result.get(i);
                    imageList.add(bannerBean);
                }
                initBanner();
            }
        });
    }

    private void initBanner() {
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.drawable.banner1);
        localImages.add(R.drawable.banner2);
        localImages.add(R.drawable.banner3);
        final List<String> netImages = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            netImages.add(BASE_IMAGE_URL + imageList.get(i).pic_url);
        }
        cbBanner.setData(cbBanner, netImages, localImages, new Integer[]{R.mipmap.banner_small, R.mipmap.banner_big});
        cbBanner.setCanLoop(true);
        cbBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            //当type=1为商品ID，type=2是外部URL；type=3时为没有
            public void onItemClick(int position) {
                ProductBannerBean bean = imageList.get(position);
                if (bean.type == 1) {
                    Intent intent = new Intent(UbProductActivity.this, UbProductInfoActivity.class);
                    intent.putExtra(IntentExtraKeyConst.PRODUCT_ID, imageList.get(position).pic_value + "");
                    startActivity(intent);
                } else if (bean.type == 2) {
                    Intent intent = new Intent(UbProductActivity.this, UbWebViewActivity.class);
                    intent.putExtra(IntentExtraKeyConst.WEB_VIEW_URL, imageList.get(position).pic_value + "");
                    startActivity(intent);
                } else {
                    showToast("无链接");
                }
            }
        });
    }

    private void getPermission() {
        mPermissionList.clear();
        if (Build.VERSION.SDK_INT >= 23) {
            for (String mPermission : mPermissions) {
                if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(mPermission);
                }
                if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
                    Log.e(TAG, "已经授权");
                    getLocation();
                } else {
                    //未授权,请求权限
                    Log.e(TAG, "我们需要：" + mPermission + "的权限来获取当前位置");
                    String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                    ActivityCompat.requestPermissions(UbProductActivity.this, permissions, BAI_DU_READ_PHONE_STATE);
                }
            }
        } else {
            Log.e(TAG, "Android6.0以下直接获取定位");
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BAI_DU_READ_PHONE_STATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "所有权限以授予");
                getLocation();
            } else {
                String mStringToast = "权限被禁止手动打开设置页面";
                permissionNum++;
                firstToast(mStringToast, permissionNum, 2);
                //设置默认的城市
                setDefaultCity();
            }
        }
    }

    private void getLocation() {
        //初始化定位
        if(mLocationClient==null){
            mLocationClient = new AMapLocationClient(this);
        }
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果
        mLocationOption.setOnceLocation(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(100000);
        //设置是否返回地址信息
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置，默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //设置请求超时
        mLocationOption.setHttpTimeOut(30000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(true);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
        Log.e(TAG, "启动定位");
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //设置当前所在的城市
        if(aMapLocation==null){
            return;
        }
        readCityName(aMapLocation);
    }

    private void readCityName(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mLatitude = aMapLocation.getLatitude();
                mLongitude = aMapLocation.getLongitude();
                //定位成功但无法获取城市名称和区名时
                if ("".equals(mCity)) {
                    Log.e(TAG, aMapLocation.getErrorInfo() + aMapLocation.getErrorCode());
                    if (Integer.parseInt(String.valueOf(mLatitude)) != 0 && Integer.parseInt(String.valueOf(mLongitude)) != 0) {
                        LatLonPoint mLatLonPoint = new LatLonPoint(mLatitude, mLongitude);
                        RegeocodeQuery query = new RegeocodeQuery(mLatLonPoint, 200, GeocodeSearch.AMAP);
                        mGeocodeSearch.getFromLocationAsyn(query);
                    } else {
                        String mStringToast = "定位成功";
                        successNum++;
                        firstToast(mStringToast, successNum, 0);
                        mCity = aMapLocation.getCity();
                        MySharedPreference.save(SharedKeyConstant.CITY_NAME, mCity, this);
                        mDistrict = aMapLocation.getDistrict();
                    }
                } else {
                    String mStringToast = "定位成功";
                    successNum++;
                    firstToast(mStringToast, successNum, 0);
                    mLatitude = aMapLocation.getLatitude();
                    mLongitude = aMapLocation.getLongitude();
                    mCity = aMapLocation.getCity();
                    MySharedPreference.save(SharedKeyConstant.CITY_NAME, mCity, this);
                    mDistrict = aMapLocation.getDistrict();
                }
                Log.e(TAG, "经度3：" + mLatitude + "纬度：" + mLongitude + "城市名称：" + mCity + "区名：" + mDistrict);
                //定位成功可以获取所有信息
                setSelectedCity();
            } else {
                String mStringToast = "定位失败" + aMapLocation.getErrorCode();
                failNum++;
                firstToast(mStringToast, failNum, 1);
                setDefaultCity();
                Log.e(TAG, "经度2：" + mLatitude + "纬度：" + mLongitude + "城市名称：" + mCity + "区名：" + mDistrict);
            }
        } else {
            String mStringToast = "定位失败?";
            failNum++;
            firstToast(mStringToast, failNum, 1);
            Log.e(TAG, "经度1：" + mLatitude + "纬度：" + mLongitude + "城市名称：" + mCity + "区名：" + mDistrict);
            setDefaultCity();
        }
        Log.e(TAG, "经度：" + mLatitude + "纬度：" + mLongitude + "城市名称：" + mCity + "区名：" + mDistrict);
    }

    private void setSelectedCity() {
        mSelectCity = GlobalInfo.ubSelectCity;
        if (TextUtils.isEmpty(mSelectCity)) {
            mSelectCity = mCity;
            if (TextUtils.isEmpty(mCity)) {
                mSelectCity = "杭州市";
            }
        }
        tvSelectCity.setText(mSelectCity);
        citySelectChangeHandle();
        handler.sendEmptyMessageDelayed(0, 50000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!mSelectCity.equals(mCity)) {
                mSelectCity = mCity;
                tvSelectCity.setText(mSelectCity);
                citySelectChangeHandle();
                mSelectCity = "";
                GlobalInfo.ubSelectCity = mSelectCity;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        Log.e(TAG, regeocodeResult.toString());
        mCity = regeocodeResult.getRegeocodeAddress().getCity();
        MySharedPreference.save(SharedKeyConstant.CITY_NAME, mCity, this);
        mDistrict = regeocodeResult.getRegeocodeAddress().getDistrict();
        String mStringToast = "定位成功";
        successNum++;
        firstToast(mStringToast, successNum, 0);
    }

    private void firstToast(String mStringToast, int number, int type) {
        switch (type) {
            case 0:
                if (number == 1) {
                    Log.e(TAG, mStringToast);
                }
                break;
            case 1:
                if (number == 1) {
                    Toast.makeText(UbProductActivity.this, mStringToast, Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (number == 1) {
                    Toast.makeText(UbProductActivity.this, mStringToast, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        Log.e(TAG, geocodeResult.toString());
    }

    private void setDefaultCity() {
        mLatitude = 30.30589;
        mLongitude = 120.118026;
        mCity = "杭州市";
        mSelectCity = mCity;
        MySharedPreference.save(SharedKeyConstant.CITY_NAME, mCity, this);
        mDistrict = "西湖区";
        setSelectedCity();
    }

    private void citySelectChangeHandle() {
        String latitude = String.valueOf(mLatitude);
        String longitude = String.valueOf(mLongitude);
        String coounty = mDistrict;
        ubProductService.postFranchisee(mSelectCity, coounty, latitude, longitude, new CommonResultListener<List<SpinnerListBean>>(this) {
            @Override
            public void successHandle(List<SpinnerListBean> result) throws JSONException {
                if (result == null || result.size() == 0) {
                    return;
                }
                dataList.clear();
                dataList = new ArrayList<>();
                dataList.addAll(result);
                SpinnerListBean sLBean = new SpinnerListBean();
                Iterator<SpinnerListBean> it = dataList.iterator();
                while(it.hasNext()){
                    SpinnerListBean bean = it.next();
                    if(bean.getName().contains(mDistrict)){
                        it.remove();
                        sLBean =  bean;
                    }
                }
                dataList.add(0,sLBean);
                Object strName = JudgeObjectUtils.getFieldValueByName("name",sLBean);
                if(strName == null){
                    dataList.remove(0);
                }
                type= 0;
                tvProduct.setText( dataList.get(0).getName());
                franchiseeId = dataList.get(0).getId();
                franchiseeName = dataList.get(0).getName();
                Log.e("加盟商",result.size()+"/名称="+dataList.get(0).getName()+"/id="+dataList.get(0).getId()+"/名称="+dataList.get(0).getName());
                getNamePop();
                franchiseeChangeHandle();
            }
        });
    }


    //加盟商下拉列表
    private void getNamePop() {
        mSpinnerPopWindow = new SpinnerFranchiseeWindow(this, dataList);
        mSpinnerPopWindow.setItemListener(UbProductActivity.this);
    }

    private void setData(List<UbProductMainListBean> result) {
        listData.clear();
        listData.addAll(result);
        listData.add(new UbProductMainListBean("", "全部", 0, 0));
        LayoutInflater inflater = LayoutInflater.from(this);
        int mPageSize = 10;
        totalPage = (int) Math.ceil((listData.size()) * 1.0 / mPageSize);
        List<View> viewPagerList = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview_layout, ViewPager, false);
            myViewAdapter = new MyViewAdapter(UbProductActivity.this, listData, i, mPageSize);
            gridView.setAdapter(myViewAdapter);
            viewPagerList.add(gridView);

        }
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewPagerList);
        ViewPager.setAdapter(adapter);
        setPoints();
        adapter.notifyDataSetChanged();
    }

    private void setPoints() {
        if (points != null) {
            points.removeAllViews();
        }
        if (totalPage <= 1)
            return;

        ivPoints = new ImageView[totalPage];

        for (int i = 0; i < ivPoints.length; i++) {
            ImageView imageView = new ImageView(this);
            //设置图片的宽高
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            if (i == 0) {
                imageView.setBackgroundResource(R.mipmap.banner_black);
            } else {
                imageView.setBackgroundResource(R.mipmap.banner_gray_b);
            }
            ivPoints[i] = imageView;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 20;//设置点点点view的左边距
            layoutParams.rightMargin = 20;//设置点点点view的右边距
            points.addView(imageView, layoutParams);
        }
        //设置ViewPager滑动监听
        ViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos <= dataList.size()) {
            franchiseeId = dataList.get(pos).id;
            LoginAction.saveFranchiseeId(dataList.get(pos).id + "", UbProductActivity.this);
            getFranchiseeId();
            selectedFranchiseeName = dataList.get(pos).name;
            type = 1;
            franchiseeChangeHandle();
            Log.i("获取点击列表id=",franchiseeId+"");
            if (!isLogin()) {
                return;
            }
            ubProductService.postAddFranchisee(franchiseeId, new CommonResultListener(this) {
                @Override
                public void successHandle(Object result) throws JSONException {
                    //    showToast("加盟商添加成功！");
                }
            });

        }
    }

    private void franchiseeChangeHandle() {

        if (type == 1) {
            tvProduct.setText(selectedFranchiseeName);
            tvProduct.setVisibility(View.VISIBLE);
        } else {
            tvProduct.setText(franchiseeName);
            tvProduct.setVisibility(View.VISIBLE);
        }
        Log.i("获取的地址ID=",franchiseeName+"/="+franchiseeId);
        //获取店名
        ubProductService.postProductRecomentList(1, 15, franchiseeId, new CommonResultListener<List<ProductListBean>>(this) {
            @Override
            public void successHandle(List<ProductListBean> result) throws JSONException {
                listProduct.clear();
                listProduct.addAll(result);
                productListAdapter.notifyDataSetChanged();
            }
        });
        //主题分类
        ubProductService.getProductMainList(franchiseeId, new CommonResultListener<List<UbProductMainListBean>>(this) {
            @Override
            public void successHandle(List<UbProductMainListBean> result) throws JSONException {
                listData.clear();
                Log.i("分类的数量=",result.size()+"");
                setData(result);
                myViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private int getFranchiseeId() {
        return franchiseeId;
    }
    private  Intent intent;
    @OnClick({R.id.cb_banner, R.id.viewPager, R.id.points, R.id.tv_select_city, R.id.ll_edit_select,
            R.id.iv_search, R.id.iv_scan, R.id.ll_search,R.id.main_iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_banner:
                break;
            case R.id.viewPager:
                break;
            case R.id.points:
                break;
            case R.id.ll_edit_select:
                showSpinWindow();
                break;
            case R.id.tv_select_city:
                Map<String, String> map = new HashMap<>();
                map.put(IntentExtraKeyConst.ACTIVITY_FROM, IntentExtraValueConst.ACTIVITY_FROM_UB);
                startActivity(SelectedCityActivity.class, map);
                break;
            case R.id.iv_search:
                startActivity(ProductSearchActivity.class);
                break;
            case R.id.iv_scan:
                intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.ll_search:
                break;
            case R.id.main_iv_share:
                if(isLogin()){
                    intent = new Intent(this, UbProductShareActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent data) {
        super.onActivityResult(arg0, arg1, data);
        if (data != null) {
            String result = data.getStringExtra("result");
            if (!result.startsWith("$$goUnionshopPay_")) {
                showToast("请扫描正确的二维码");
                return;
            }
            String sellerId = result.substring("$$goUnionshopPay_".length());
            //showToast(sellerId);
            Intent intent = new Intent(UbProductActivity.this, SellerPayActivity.class);
            intent.putExtra(IntentExtraKeyConst.SELLER_ID, sellerId);
            startActivity(intent);
        }
    }

    private void showSpinWindow() {
        mSpinnerPopWindow.setWidth(llEditSelect.getWidth());
        mSpinnerPopWindow.showAsDropDown(llEditSelect);
    }


    //创建商家分类适配器
    private class MyViewAdapter extends BaseAdapter {
        private List<UbProductMainListBean> listData;
        private LayoutInflater inflater;
        private Context context;
        private int mIndex;//页数下标，表示第几页，从0开始
        private int mPagerSize;//每页显示的最大数量

        MyViewAdapter(Context ctx, List<UbProductMainListBean> dataList2, int mIndex, int mPagerSize) {
            this.context = ctx;
            this.listData = dataList2;
            this.mIndex = mIndex;
            this.mPagerSize = mPagerSize;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listData.size() > (mIndex + 1) * mPagerSize ? mPagerSize : (listData.size() - mIndex * mPagerSize);
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position + mIndex * mPagerSize);
        }

        @Override
        public long getItemId(int position) {
            return position + mIndex * mPagerSize;
        }

        @SuppressLint("CutPasteId")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewViewHolder holder;
            if (convertView == null) {
                holder = new GridViewViewHolder();
                convertView = inflater.inflate(R.layout.ub_item_category_gridview, parent, false);
                holder.imgUrl = (ImageView) convertView.findViewById(R.id.iv_category_gridview_image);
                holder.proName = (TextView) convertView.findViewById(R.id.tv_category_gridview_name);
                holder.parent = (LinearLayout) convertView.findViewById(R.id.ll_category_gridview);
                convertView.setTag(holder);
            } else {
                holder = (GridViewViewHolder) convertView.getTag();
            }
            //  UbProductMainListBean bean=listData.get(pos);
            final int pos = position + mIndex * mPagerSize;
            final UbProductMainListBean bean = listData.get(pos);
            Log.i("Tag点击的对象是=",bean.activity_id+"/名称是="+bean.name+"/位置是="+pos+"/franchiseeId="+bean.franchisee_id);
            String franchiseeName = bean.name;
            holder.proName.setText(franchiseeName.length() > 4 ? franchiseeName.substring(0, 4) + "..." : franchiseeName);
            if (pos == (listData.size() - 1)) {
//                UbProductMainListBean allBean= listData.get(listData.size()-1);
//                ImageView view=allBean.icon_url.;
                ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_category_gridview_image);
                imageView.setImageResource(R.mipmap.pro_all);
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(UbProductAllCategoryActivity.class);

                    }
                });
            } else {
                Tools.ImageLoaderShow(UbProductActivity.this, BASE_IMAGE_URL + bean.icon_url, holder.imgUrl);
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Tag点击的对象是=",bean.activity_id+"/名称是="+bean.name+"/位置是="+pos+"/franchiseeId="+bean.franchisee_id);
                        Intent intent = new Intent(UbProductActivity.this, ProductCategoryActivity.class);
                        intent.putExtra(IntentExtraKeyConst.PRODUCT_CATEGORY_ID, bean.activity_id + "");
                        intent.putExtra(IntentExtraKeyConst.PRODUCT_CATEGORY_NAME, bean.name);
                        intent.putExtra(IntentExtraKeyConst.FRANCHISEE_ID, String.valueOf(bean.franchisee_id));
                        startActivity(intent);
                    }
                });
            }

            return convertView;
        }

        class GridViewViewHolder {
            ImageView imgUrl;
            TextView proName;
            LinearLayout parent;
        }

    }

    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> mInfo = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : mInfo) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }

}
