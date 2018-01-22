package com.ninetop.activity.ub.seller;

import android.widget.ListView;

import com.ninetop.UB.SellerSearchAdapter;
import com.ninetop.UB.UbSearchInfoBean;
import com.ninetop.UB.UbSellerService;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

import static com.ninetop.config.AppConfig.INIT_CITY;


/**
 * Created by huangjinding on 2017/5/11.
 */
public class SellerSearchInfoActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvTitle;
    @BindView(R.id.listview)
    ListView listview;
    private String city;
    private String name;
    private UbSellerService ubSellerService;
    private SellerSearchAdapter adapter;
    private List<UbSearchInfoBean> dataList;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_seller_search_list;
    }

    @Override
    protected void initView() {
        super.initView();
        hvTitle.setTitle("联盟商家搜索结果");
        ubSellerService = new UbSellerService(this);
        dataList=new ArrayList<>();
        adapter = new SellerSearchAdapter(SellerSearchInfoActivity.this, dataList);
        listview.setAdapter(adapter);
        getServerData();
    }
    protected void getServerData() {
        city = getIntentValue(IntentExtraKeyConst.SELLER_CITY);
        name = getIntentValue(IntentExtraKeyConst.SELLER_NAME);
        if(city==null){
            city=INIT_CITY;
        }
        ubSellerService.getSellerSearch(name, city, new CommonResultListener<List<UbSearchInfoBean>>(this) {
            @Override
            public void successHandle(List<UbSearchInfoBean> result) throws JSONException {
                hvTitle.setTitle("商家  "+"'"+name+"'"+"  搜索结果");
               dataList.addAll(result);
                adapter.notifyDataSetChanged();
            }
        });

    }

}
