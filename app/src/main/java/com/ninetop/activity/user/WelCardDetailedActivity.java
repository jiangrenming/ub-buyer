package com.ninetop.activity.user;


import android.widget.ListView;

import com.google.gson.Gson;
import com.ninetop.adatper.WelCardDetailedAdapter;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.user.DataListBean;
import com.ninetop.bean.user.Details;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * @date: 2016/11/11
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class WelCardDetailedActivity extends BaseActivity {

    private final UserCenterService userCenterService;
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.lv_welcard_detailed)
    ListView lvWelCrdDetailed;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcard_detailed;
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvHead.setTitle("明细");
    }
    @Override
    protected void initListener() {
        super.initListener();
    }
    public WelCardDetailedActivity(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected void initData() {
        super.initData();
        getData();
    }
    private void getData() {
        userCenterService.getDetails(new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) throws JSONException {
                Gson gson=new Gson();
                Details details = gson.fromJson(result, Details.class);
                List<DataListBean> dataList = details.data.dataList;
                setAdapter(dataList);
            }
        });

    }
    private void setAdapter(List<DataListBean> dataList) {
        lvWelCrdDetailed.setAdapter(new WelCardDetailedAdapter(dataList,this));
    }
}
