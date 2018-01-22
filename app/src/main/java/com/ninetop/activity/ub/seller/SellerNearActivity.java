package com.ninetop.activity.ub.seller;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetop.UB.SellerBean;
import com.ninetop.UB.SellerCategoryBean;
import com.ninetop.UB.SellerListAdapter;
import com.ninetop.UB.SellerNearListAdapter;
import com.ninetop.UB.UbSellerService;
import com.ninetop.activity.ub.util.newPop.SellerPopAdapter;
import com.ninetop.activity.ub.util.newPop.SellerPopWindow;
import com.ninetop.base.PullRefreshBaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.SharedKeyConstant;
import com.ninetop.common.util.MySharedPreference;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static com.ninetop.config.AppConfig.INIT_CITY;

/**
 * Created by huangjinding on 2017/6/4.
 */

public class SellerNearActivity extends PullRefreshBaseActivity implements SellerPopAdapter.IOnItemSelectListener {

    @BindView(R.id.tv_seller_category_name)
    TextView tvSellerCategoryName;
    @BindView(R.id.iv_more)
    ImageView ivMore;


    private String city;
    private String lat1;
    private String lng1;
    private UbSellerService ubSellerService;
    private SellerListAdapter adapter;
    private List<SellerBean> dataList;
    private List<SellerCategoryBean> dataPopList;
    private SellerPopWindow popWindow;
    private int sellerCategoryId;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_nearby_seller;
    }

    @Override
    protected void initView() {
        super.initView();
        String name = getIntentValue(IntentExtraKeyConst.SELLER_CATEGORY_NAME);
        city = MySharedPreference.get(SharedKeyConstant.CITY_NAME, null, SellerNearActivity.this);
        lat1 = MySharedPreference.get(SharedKeyConstant.SEARCH_LAT, null, SellerNearActivity.this);
        lng1 = MySharedPreference.get(SharedKeyConstant.SEARCH_LON, null, SellerNearActivity.this);
        ubSellerService = new UbSellerService(this);
        dataList = new ArrayList<>();
        dataPopList = new ArrayList<>();
        ListView list = (ListView) refreshLayout.getPullableView();
        list.setDividerHeight(0);
        listAdapter = new SellerNearListAdapter(SellerNearActivity.this, dataList);
        list.setAdapter(listAdapter);
        getsellerDetail();
    }

    protected void getsellerDetail() {
        ubSellerService.getNearbySellerCategory(lat1, lng1, city, new CommonResultListener<List<SellerBean>>(this) {
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
                dataPopList.addAll(result);
                getNamePop();
            }
        });
    }

    @Override
    protected void getServerData() {
        if (city == null) {
            city = INIT_CITY;
        }
        if (sellerCategoryId == 0) {
            sellerCategoryId = 1;
        }
        ubSellerService.getSellerCategorySecond(sellerCategoryId + "", city, lat1, lng1, new CommonResultListener<List<SellerBean>>(this) {
            @Override
            public void successHandle(List<SellerBean> result) throws JSONException {
                dataList.clear();
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick({R.id.iv_back, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                showPop();
                break;
        }
    }

    private void getNamePop() {
        popWindow = new SellerPopWindow(this, dataPopList);
        popWindow.setItemListener(this);
        popWindow.setWidth(ivMore.getWidth() * 4);
    }

    private void showPop() {
        popWindow.showAsDropDown(ivMore, ivMore.getWidth(), ivMore.getWidth());
        popWindow.setItemListener(SellerNearActivity.this);
    }

    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos <= dataPopList.size()) {
            tvSellerCategoryName.setText(dataPopList.get(pos).getName());
            sellerCategoryId = dataPopList.get(pos).id;
            getServerData();
        }
    }

}