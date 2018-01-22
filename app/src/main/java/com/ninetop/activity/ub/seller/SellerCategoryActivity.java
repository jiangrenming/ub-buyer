package com.ninetop.activity.ub.seller;

import android.widget.ListView;

import com.ninetop.UB.SellerBean;
import com.ninetop.UB.SellerSearchListAdapter;
import com.ninetop.UB.UbSellerService;
import com.ninetop.base.PullRefreshBaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.SharedKeyConstant;
import com.ninetop.common.util.MySharedPreference;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

import static com.ninetop.config.AppConfig.INIT_CITY;

/**
 * Created by huangjinding on 2017/6/4.
 */

public class SellerCategoryActivity extends PullRefreshBaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    private UbSellerService ubSellerService;

    private String city;
    private String id;
    private String lat1;
    private String lng1;
    private String name;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_seller_list;
    }


    @Override
    protected void initView() {
        super.initView();
        name = getIntentValue(IntentExtraKeyConst.SELLER_CATEGORY_NAME);
        hvHead.setTitle(name);
        ubSellerService = new UbSellerService(this);
        ListView list = (ListView) refreshLayout.getPullableView();
        list.setDividerHeight(0);
        listAdapter = new SellerSearchListAdapter(SellerCategoryActivity.this, dataList);
        list.setAdapter(listAdapter);
        getDetail();
        getServerData();
    }

    private void getDetail() {
        city = MySharedPreference.get(SharedKeyConstant.CITY_NAME, "杭州市", this);
        lat1 = MySharedPreference.get(SharedKeyConstant.SEARCH_LAT, "30.30589", this);
        lng1 = MySharedPreference.get(SharedKeyConstant.SEARCH_LON, "120.118026", this);
        id = getIntentValue(IntentExtraKeyConst.SELLER_CATEGORY_ID);
    }

    @Override
    protected void getServerData() {
        if (city == null) {
            city = INIT_CITY;
        }
        ubSellerService.getSellerCategorySecond(id, city, lat1, lng1, new CommonResultListener<List<SellerBean>>(this) {
            @Override
            public void successHandle(List<SellerBean> result) throws JSONException {
                dataList.addAll(result);
                listAdapter.notifyDataSetChanged();
            }
        });
    }

}
