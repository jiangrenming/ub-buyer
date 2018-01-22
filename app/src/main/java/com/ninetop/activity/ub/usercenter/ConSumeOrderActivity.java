package com.ninetop.activity.ub.usercenter;

import android.widget.ListView;

import com.ninetop.UB.ConSumListBean;
import com.ninetop.UB.ConsumeListAdapter;
import com.ninetop.UB.UbSellerService;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/15.
 */
public class ConSumeOrderActivity extends BaseActivity {

    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.lv_order_list)
    ListView lvOrderList;
    private ListView listView;
    private UbSellerService ubSellerService;
    protected ConSumListBean ConSumListBean;
    private ConsumeListAdapter consumeListAdapter;
    private List<ConSumListBean> datalist;
    protected int getLayoutId() {
        return R.layout.ub_activity_consume_order;
    }
    protected void initView() {
        super.initView();
        hvTitle.setTitle("消费账单");
        ubSellerService=new UbSellerService(this);
        listView=(ListView)this.findViewById(R.id.lv_order_list);
        datalist=new ArrayList<>();
        consumeListAdapter=new ConsumeListAdapter(ConSumeOrderActivity.this,datalist);
        listView.setAdapter(consumeListAdapter);
    }
    protected void initData() {
        super.initData();
        getServerData();
    }
    protected void getServerData(){
     ubSellerService.getConsumeList(new CommonResultListener<List<ConSumListBean>>(this) {
         @Override
         public void successHandle(List<ConSumListBean> result) throws JSONException {
             datalist.addAll(result);
             consumeListAdapter.notifyDataSetChanged();
         }
     });
    }

}
