package youbao.shopping.ninetop.activity.user;


import android.widget.ListView;

import com.google.gson.Gson;
import youbao.shopping.ninetop.adatper.WelCardDetailedAdapter;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.DataListBean;
import youbao.shopping.ninetop.bean.user.Details;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.UserCenterService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

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
