package youbao.shopping.ninetop.activity.ub.seller;

import android.widget.ListView;

import youbao.shopping.ninetop.UB.SellerSearchAdapter;
import youbao.shopping.ninetop.UB.UbSearchInfoBean;
import youbao.shopping.ninetop.UB.UbSellerService;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.INIT_CITY;


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
