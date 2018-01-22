package com.ninetop.activity.ub.seller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.ninetop.UB.SellerBean;
import com.ninetop.UB.SellerCategoryBean;
import com.ninetop.UB.SellerListAdapter;
import com.ninetop.UB.UbSellerService;
import com.ninetop.UB.product.ProductBannerBean;
import com.ninetop.activity.ub.banner.UbWebViewActivity;
import com.ninetop.activity.ub.product.HuangChangeActivity;
import com.ninetop.activity.ub.product.MyViewPagerAdapter;
import com.ninetop.activity.ub.usercenter.SelectedCityActivity;
import com.ninetop.activity.ub.util.MyScrollView;
import com.ninetop.base.GlobalInfo;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.IntentExtraValueConst;
import com.ninetop.common.constant.SharedKeyConstant;
import com.ninetop.common.util.MySharedPreference;
import com.ninetop.common.util.Tools;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.xudaojie.qrcodelib.CaptureActivity;
import youbao.shopping.R;

import static com.ninetop.config.AppConfig.BASE_IMAGE_URL;


/**
 * Created by huangjinding on 2017/4/19.
 */
public class UbSellerActivity extends HuangChangeActivity implements ViewPager.OnPageChangeListener, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {
    private static final String TAG = "UbSellerActivity";
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.cb_banner)
    ConvenientBanner cbBanner;
    @BindView(R.id.viewPager)
    ViewPager ViewPager;
    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.tv_select_city)
    TextView tvSelectCity;
    @BindView(R.id.ed_seller_name)
    EditText edSellerName;
    @BindView(R.id.sv_ubseller)
    MyScrollView scrollView;
    private int height;
    private List<ProductBannerBean> imageList = null;
    private ListView listView;
    private UbSellerService ubSellerService;
    private SellerListAdapter listAdapter;
    private List<SellerBean> dataList;
    private ViewGroup points;//小圆点指示器
    private ImageView[] ivPoints;//小圆点图片集合
    private int totalPage;//总的页数
    private int mPageSize = 10;//每页显示的最大数量
    private List<SellerCategoryBean> listData;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private static final int BAI_DU_READ_PHONE_STATE = 100;
    //声明mLocationClient对象
    private AMapLocationClient mLocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private String[] mPermissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();
    private String mCity;
    private String mSelectCity;
    private Double mLatitude;
    private Double mLongitude;
    private String mDistrict;
    private GeocodeSearch mGeocodeSearch;
    private int successNum = 0;
    private int failNum = 0;
    private int permissionNum = 0;

    public UbSellerActivity() {

    }

    @Override
    protected int getLayoutId() {

        return R.layout.ub_lianmeng;
    }

    protected void initView() {
        super.initView();
        edSellerName.setHint("请输入商家名称/服务类型搜索");
        iniViews();
        initViewPaper();

    }

    private void iniViews() {
        ViewPager = (ViewPager) findViewById(R.id.viewPager);
        //初始化小圆点指示器
        points = (ViewGroup) findViewById(R.id.points);
        ubSellerService = new UbSellerService(this);
        listView = (ListView) this.findViewById(R.id.vp_seller_list);
        dataList = new ArrayList<>();
        listData = new ArrayList<>();
        listAdapter = new SellerListAdapter(UbSellerActivity.this, dataList);
        listView.setAdapter(listAdapter);

        mGeocodeSearch = new GeocodeSearch(this);
    }

    protected void initViewPaper() {
        scrollView.smoothScrollTo(0, 0);
        ubSellerService.getBannerImages(new CommonResultListener<List<ProductBannerBean>>(this) {
            @Override
            public void successHandle(List<ProductBannerBean> result) throws JSONException {
                if (result == null || result.size() == 0) {
                    return;
                }
                imageList = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    ProductBannerBean bannerBean = result.get(i);
                    imageList.add(bannerBean);
                }
                initBanner();

            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        scrollView.addOnScrollChangedListener(this);
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
    protected void onResume() {
        super.onResume();
        //获取权限并获得当i安位置
        getPermission();
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
                    ActivityCompat.requestPermissions(UbSellerActivity.this, permissions, BAI_DU_READ_PHONE_STATE);
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
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAI_DU_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "所有权限以授予");
                    getLocation();
                } else {
                    String mStringToast = "权限被禁止手动打开设置页面";
                    permissionNum++;
                    firstToast(mStringToast, permissionNum, 2);
                    setDefaultCity();
                }
                break;
        }
    }

    private void getLocation() {
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果
        mLocationOption.setOnceLocation(false);
//        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(60000);
        //设置是否返回地址信息
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置，默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //设置请求超时
        mLocationOption.setHttpTimeOut(30000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
        Log.e(TAG, "开启定位");
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //设置当前所在的城市
        readCityName(aMapLocation);
    }

    private void readCityName(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mLatitude = aMapLocation.getLatitude();
                MySharedPreference.save(SharedKeyConstant.SEARCH_LAT, String.valueOf(mLatitude), this);
                mLongitude = aMapLocation.getLongitude();
                MySharedPreference.save(SharedKeyConstant.SEARCH_LON, String.valueOf(mLongitude), this);
                //定位成功但无法获取城市名称和区名时
                if ("".equals(mCity)) {
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
                    MySharedPreference.save(SharedKeyConstant.SEARCH_LAT, String.valueOf(mLatitude), this);
                    mLongitude = aMapLocation.getLongitude();
                    MySharedPreference.save(SharedKeyConstant.SEARCH_LON, String.valueOf(mLongitude), this);
                    mCity = aMapLocation.getCity();
                    MySharedPreference.save(SharedKeyConstant.CITY_NAME, mCity, this);
                    mDistrict = aMapLocation.getDistrict();
                }
                //定位成功可以获取所有信息
            } else {
                String mStringToast = "定位失败" + aMapLocation.getErrorCode();
                failNum++;
                firstToast(mStringToast, failNum, 1);
                setDefaultCity();
            }
        } else {
            String mStringToast = "定位失败？";
            failNum++;
            firstToast(mStringToast, failNum, 1);
            setDefaultCity();
        }
        Log.e(TAG, "经度：" + mLatitude + "纬度：" + mLongitude + "城市名称：" + mCity + "区名：" + mDistrict);
        setSelectedCity();
    }

    private void setSelectedCity() {
        mSelectCity = GlobalInfo.unionshopSelectCity;
        if (TextUtils.isEmpty(mSelectCity)) {
            mSelectCity = mCity;
            if (TextUtils.isEmpty(mCity)) {
                mSelectCity = "杭州市";
            }
        }
        tvSelectCity.setText(mSelectCity);
        getServerData();
        handler.sendEmptyMessageDelayed(0, 50000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!mSelectCity.equals(mCity)) {
                mSelectCity = mCity;
                tvSelectCity.setText(mSelectCity);
                getServerData();
                mSelectCity = "";
                GlobalInfo.unionshopSelectCity = mSelectCity;
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
                    Toast.makeText(UbSellerActivity.this, mStringToast, Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (number == 1) {
                    Toast.makeText(UbSellerActivity.this, mStringToast, Toast.LENGTH_SHORT).show();
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

    private void initBanner() {
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.drawable.ktv1);
        List<String> netImages = new ArrayList<>();
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
                    Intent intent = new Intent(UbSellerActivity.this, SellerDetailActivity.class);
                    intent.putExtra(IntentExtraKeyConst.SELLER_ID, imageList.get(position).pic_value + "");
                    startActivity(intent);
                } else if (bean.type == 2) {
                    Intent intent = new Intent(UbSellerActivity.this, UbWebViewActivity.class);
                    intent.putExtra(IntentExtraKeyConst.WEB_VIEW_URL, imageList.get(position).pic_value + "");
                    startActivity(intent);
                } else {
                    showToast("无链接");
                }
            }
        });
    }

    protected void getServerData() {
        ubSellerService.getSellerList(mSelectCity, 1, 15, new CommonResultListener<List<SellerBean>>(this) {
            @Override
            public void successHandle(List<SellerBean> result) throws JSONException {
                dataList.clear();
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        });

        ubSellerService.getSellerCategoryFirst(new CommonResultListener<List<SellerCategoryBean>>(this) {
            @Override
            public void successHandle(List<SellerCategoryBean> result) throws JSONException {
                setData(result);
            }
        });

    }

    private void setData(List<SellerCategoryBean> result) {
        listData.clear();
        listData.addAll(result);
        listData.add(new SellerCategoryBean(0, "附近商家", ""));
        LayoutInflater inflater = LayoutInflater.from(this);
        totalPage = (int) Math.ceil(listData.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview_layout, ViewPager, false);
            MyViewAdapter myViewAdapter = new MyViewAdapter(UbSellerActivity.this, listData, i, mPageSize);
            gridView.setAdapter(myViewAdapter);
            viewPagerList.add(gridView);
        }
        setPoints();
        ViewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));
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
    public void onScrollChanged(int y) {
//        if (y <= 0) {
//            llSearch.setBackgroundColor(Color.argb(100, 255, 0, 0));
//        } else if (y > 0 && y <= height) {
//            float scale = (float) y / height;
//            //修改255，控制透明度
//            float alpha = (200 * scale);
//            llSearch.setBackgroundColor(Color.argb((int) alpha, 255, 0, 0));
//        } else {
//            llSearch.setBackgroundColor(Color.argb(200, 255, 0, 0));
//        }
    }


    //创建商家分类适配器
    private class MyViewAdapter extends BaseAdapter {
        private List<SellerCategoryBean> listData;
        private LayoutInflater inflater;
        private Context context;
        private int mIndex;//页数下标，表示第几页，从0开始
        private int mPagerSize;//每页显示的最大数量

        private MyViewAdapter(Context ctx, List<SellerCategoryBean> datalist2, int mIndex, int mPagerSize) {
            this.context = ctx;
            this.listData = datalist2;
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewViewHolder holder;
            if (convertView == null) {
                holder = new GridViewViewHolder();
                convertView = inflater.inflate(R.layout.ub_item_category_gridview, parent, false);
                holder.imgUrl = (ImageView) convertView.findViewById(R.id.iv_category_gridview_image);
                holder.proName = (TextView) convertView.findViewById(R.id.tv_category_gridview_name);
                convertView.setTag(holder);
            } else {
                holder = (GridViewViewHolder) convertView.getTag();
            }

            final int pos = position + mIndex * mPagerSize;
            final SellerCategoryBean bean = listData.get(pos);
            holder.proName.setText(bean.name);

            if (pos == (listData.size() - 1)) {
                @SuppressLint("CutPasteId")
                ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_category_gridview_image);
                imageView.setImageResource(R.mipmap.near_seller);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(SellerNearActivity.class);
                    }
                });
            } else {
                Tools.ImageLoaderShow(UbSellerActivity.this, BASE_IMAGE_URL + bean.icon_url, holder.imgUrl);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UbSellerActivity.this, SellerCategoryActivity.class);
                        intent.putExtra(IntentExtraKeyConst.SELLER_CATEGORY_ID, bean.id + "");
                        intent.putExtra(IntentExtraKeyConst.SELLER_CATEGORY_NAME, bean.name);
                        startActivity(intent);
                    }
                });
            }
            return convertView;
        }

        class GridViewViewHolder {
            private ImageView imgUrl;
            private TextView proName;

        }
    }

    @OnClick({R.id.iv_search, R.id.iv_notice, R.id.tv_select_city,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                String sellerName = edSellerName.getText().toString().trim();
                if ("".equals(sellerName)) {
                    showToast("搜索参数不能为空");
                    return;
                } else {
                    Intent intent = new Intent(UbSellerActivity.this, SellerSearchInfoActivity.class);
                    intent.putExtra(IntentExtraKeyConst.SELLER_CITY, mSelectCity);
                    intent.putExtra(IntentExtraKeyConst.SELLER_NAME, sellerName);
                    startActivity(intent);
                }
                break;
            case R.id.tv_select_city:
                Map<String, String> map = new HashMap<>();
                map.put(IntentExtraKeyConst.ACTIVITY_FROM, IntentExtraValueConst.ACTIVITY_FROM_UNIONSHOP);
                startActivity(SelectedCityActivity.class, map);
                break;
            case R.id.iv_notice:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent data) {
        super.onActivityResult(arg0, arg1, data);
        if (data != null) {

            String result = data.getStringExtra("result");
            String sellerId = result.substring("$$goUnionshopPay_".length());
            //showToast(sellerId);
            Intent intent = new Intent(UbSellerActivity.this, SellerPayActivity.class);
            intent.putExtra(IntentExtraKeyConst.SELLER_ID, sellerId);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(this);
        mLocationClient = null;
    }
}
