package youbao.shopping.ninetop.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;

import youbao.shopping.ninetop.UB.cartshop.UbShopCartBean;
import youbao.shopping.ninetop.UB.product.New.ShopCartItemListBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.activity.ub.product.UbProductActivity;
import youbao.shopping.ninetop.activity.ub.seller.UbSellerActivity;
import youbao.shopping.ninetop.activity.ub.shopcart.UbShopCartActivity;
import youbao.shopping.ninetop.activity.user.UserCenterActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.MessageEvent;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.util.ClickUtils;
import youbao.shopping.ninetop.service.impl.ProductService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import youbao.shopping.R;
import youbao.shopping.ninetop.update.Constants;
import youbao.shopping.ninetop.update.UpdateChecker;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnClickListener, Viewable {
    TabHost tabHost;
    FrameLayout layout_1;
    FrameLayout layout_2;
    FrameLayout layout_3;
    FrameLayout layout_4;
    TextView image_1;
    TextView image_2;
    TextView image_3;
    TextView image_4;
    TextView tv_cart_num;

    private Map<Integer, View[]> tabMap = null;

    private FrameLayout[] layoutArray = null;
    private int franchiseeId;
    private String franchiseeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        getId();
        initView();
        initAction();
        getShopCartCount();
    }


    private void getId() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        franchiseeId = bundle.getInt(IntentExtraKeyConst.FRANCHID);
        franchiseeName = bundle.getString(IntentExtraKeyConst.FRANCHNAME);
    }
    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }


    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void initView() {
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        layout_1 = (FrameLayout) findViewById(R.id.layout_1);
        layout_2 = (FrameLayout) findViewById(R.id.layout_2);
        layout_3 = (FrameLayout) findViewById(R.id.layout_3);
        layout_4 = (FrameLayout) findViewById(R.id.layout_4);
        image_1 = (TextView) findViewById(R.id.iv_index);
        image_2 = (TextView) findViewById(R.id.iv_category);
        image_3 = (TextView) findViewById(R.id.iv_shopcart);
        image_4 = (TextView) findViewById(R.id.iv_user_center);
        tv_cart_num = (TextView) findViewById(R.id.tv_cart_num);

        layoutArray = new FrameLayout[]{layout_1, layout_2, layout_3, layout_4};

        tabMap = new HashMap<>();
        tabMap.put(1, new View[]{layout_1, image_1});
        tabMap.put(2, new View[]{layout_2, image_2});
        tabMap.put(3, new View[]{layout_3, image_3});
        tabMap.put(4, new View[]{layout_4, image_4});
        Bundle bundle = new Bundle();
        bundle.putInt(IntentExtraKeyConst.FRANCHID, franchiseeId);
        bundle.putString(IntentExtraKeyConst.FRANCHNAME, franchiseeName);
        mIntent = new Intent(this, UbProductActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        mIntent.putExtras(bundle);

        overridePendingTransition(0, 0);
        tabHost.addTab(tabHost.newTabSpec("1").setIndicator("1").setContent(mIntent));
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator("2").setContent(new Intent(this, UbSellerActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator("3").setContent(new Intent(this, UbShopCartActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("4").setIndicator("4").setContent(new Intent(this, UserCenterActivity.class)));
        // 初始化按钮颜色
        initSelectTab();

    }
    Intent mIntent;
    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initSelectTab() {
        int index = 1;
        String selectedIndex = this.getIntent().getStringExtra(IntentExtraKeyConst.MAIN_SELECTED_INDEX);
        if (selectedIndex != null && selectedIndex.length() > 0) {
            index = Integer.parseInt(selectedIndex);
        }
        setSelectedIndex(index);
    }

    private void initAction() {
        for (View view : layoutArray) {
            view.setOnClickListener(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initSelectTab();
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < layoutArray.length; i++) {
            if (v == layoutArray[i]) {
                int selectIndex = i + 1;
                setSelectedIndex(selectIndex);
                break;
            }
        }
    }

    public void setSelectedIndex(int index) {
        setTabSelected(index);
        tabHost.setCurrentTabByTag(index + "");
    }

    private void setTabSelected(int index) {
        if (tabMap == null)
            return;

        for (Integer key : tabMap.keySet()) {
            boolean selected = false;
            if (key == index) {
                selected = true;
            }
            View[] viewArray = tabMap.get(key);
            for (View view : viewArray) {
                view.setSelected(selected);
            }
        }
    }

    public void setShopCartCount(int count) {
        if (count <= 0) {
            tv_cart_num.setVisibility(View.GONE);
        } else {
            tv_cart_num.setVisibility(View.VISIBLE);
            tv_cart_num.setText(String.valueOf(count));
        }
    }

    private void getShopCartCount() {
        UbProductService ubProductService = new UbProductService(MainActivity.this);
        ubProductService.getShopList("", new CommonResultListener<List<UbShopCartBean>>(this) {
            @Override
            public void successHandle(List<UbShopCartBean> result) throws JSONException {
                int count = 0 ;
                if (result != null && result.size() >0){
                    for (int i = 0; i < result.size(); i++) {
                        UbShopCartBean ubShopCartBean = result.get(i);
                        List<ShopCartItemListBean> shopCartItemList =  ubShopCartBean.getShopCartItemList();
                        for (int j = 0; j < shopCartItemList.size(); j++) {
                            count += shopCartItemList.get(i).amount;
                        }
                    }
                }
                Log.i("获取购物车数据",count+"");
                setShopCartCount(count);
            }
        });

    }

    @Override
    public void showToast(String message) {
    }

    @Override
    public void addLoading() {
    }

    @Override
    public void removeLoading() {
    }

    @Override
    public String getIntentValue(String key) {
        return null;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void startActivity(Class clazz) {
    }

    @Override
    public void startActivity(Class clazz, Map<String, String> map) {
    }

    @Override
    public BaseActivity getTargetActivity() {
        return (BaseActivity) getCurrentActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("是否注销","已经注销");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEvent(MessageEvent event){
        Log.i("商品数量2",event.nCount+"");
        if (event != null){
            if (event.nCount >0 ){
                tv_cart_num.setVisibility(View.VISIBLE);
                if (event.type ==0 ){   //--->代表购物车里总数量
                    tv_cart_num.setText(String.valueOf(event.nCount));
                }else if (event.type == 1 ){  //删除单个商品
                    String str = tv_cart_num.getText().toString();
                    int mCount = Integer.valueOf(str);
                    if (mCount <= 0){
                        tv_cart_num.setText(String.valueOf(0));
                        tv_cart_num.setVisibility(View.GONE);
                    }else {
                        tv_cart_num.setText(String.valueOf(mCount-event.nCount));
                    }
                }else if (event.type == 2){  //购物车里减
                    String str = tv_cart_num.getText().toString();
                    int mCount = Integer.valueOf(str);
                    tv_cart_num.setText(String.valueOf(mCount-event.nCount));
                }else  if (event.type == 3){  //购物车里的增
                    String str = tv_cart_num.getText().toString();
                    int mCount = Integer.valueOf(str);
                    tv_cart_num.setText(String.valueOf(mCount+event.nCount));
                }else  if (event.type == 4){  //添加购物车
                    String str = tv_cart_num.getText().toString();
                    int mCount = Integer.valueOf(str);
                    tv_cart_num.setText(String.valueOf(mCount+event.nCount));
                }
            }else {
                tv_cart_num.setVisibility(View.GONE);
            }
        }
    }
}
