package youbao.shopping.ninetop.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;
import youbao.shopping.ninetop.activity.ub.product.route.util.ToastUtil;
import youbao.shopping.ninetop.adatper.RewardAdapter;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.bean.user.RewardBean;
import youbao.shopping.ninetop.common.util.DialogUtil;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.config.AppConfig;


/**
 * Created by Administrator on 2018/3/28/028.
 */

public class MyRewardsActivity extends BaseActivity{
    private RewardBean bean;
    private RewardAdapter adapter;


    private ListView lv_rewards;
    private TextView tv_share_jiangli;
    private ImageView iv_fuli_close;

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv_rewards = (ListView) findViewById(R.id.lv_rewards);
        tv_share_jiangli = (TextView) findViewById(R.id.tv_share_jiangli);
        rewardRequest();
    }

    @Override
    protected void initData() {
        super.initData();
        hvHead.setTitle("奖励明细");
    }

    @OnClick({R.id.hv_head,R.id.ll_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    private void rewardRequest() {

        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000*10);
        if(GlobalInfo.userToken!=null) {
            http.send(HttpRequest.HttpMethod.POST,
                    AppConfig.BASE_URL + "/user/MyRewards?token=" + GlobalInfo.userToken,
                    params, new RequestCallBack<String>() {

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                            Log.e("奖励明细数据=", "数据=" + result);
                            Gson gson = new Gson();
                            bean = gson.fromJson(result, RewardBean.class);
                            String state = bean.getStatus();
                            switch (state) {
                                case "200":
                                    tv_share_jiangli.setText("恭喜您获得以下奖励：");
                                    tv_share_jiangli.setVisibility(View.VISIBLE);
                                    adapter = new RewardAdapter(MyRewardsActivity.this, bean);
                                    lv_rewards.setAdapter(adapter);
                                    break;
                                case "210":
                                    tv_share_jiangli.setText("暂无奖励");
                                    tv_share_jiangli.setVisibility(View.VISIBLE);
                                ToastUtil.show(MyRewardsActivity.this,"快去邀请朋友注册吧");
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Log.e("分享请求", "失败=" + error.toString() + "/" + msg);
                        }
                    });
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rewards;

    }
}
