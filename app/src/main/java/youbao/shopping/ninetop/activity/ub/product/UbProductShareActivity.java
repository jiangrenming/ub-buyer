package youbao.shopping.ninetop.activity.ub.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import youbao.shopping.bigkoo.convenientbanner.ConvenientBanner;
import youbao.shopping.ninetop.UB.UbUserCenterService;
import youbao.shopping.ninetop.UB.UbUserInfo;
import youbao.shopping.ninetop.activity.ub.product.route.util.ToastUtil;
import youbao.shopping.ninetop.activity.user.MyRewardsActivity;
import youbao.shopping.ninetop.base.BaseActivity;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.bean.seller.SellerDetailBean;
import youbao.shopping.ninetop.bean.user.ShareBean;
import youbao.shopping.ninetop.common.constant.UrlConstant;
import youbao.shopping.ninetop.common.util.DialogUtil;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.config.AppConfig;
import youbao.shopping.ninetop.service.impl.UserService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

public class UbProductShareActivity extends BaseActivity {

    private UbUserCenterService ubUserCenterService;
    private UbUserInfo userInfo;
    private String mobile;
    private UserService userService;

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.btn_share)
    Button btn_share;
    @BindView(R.id.tv_invitationCode)
    TextView tv_invitationCode;
    @BindView(R.id.tv_share_fuli)
    TextView tv_share_fuli;
    @BindView(R.id.rl_share)
    RelativeLayout rl_share;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ubUserCenterService = new UbUserCenterService(this);
        userService = new UserService(this);
        initShare();
//        scrollToPosition();
    }

    @Override
    protected void initData() {
        super.initData();
        hvHead.setTitle("分享有奖");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private Handler handler = new Handler();

//    /**
//     * 滑动到指定位置
//     */
//    private void scrollToPosition() {
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                slv_share.scrollTo(0, lv_share.getHeight()*1/2);
//            }
//        });
//    }

    private String TAG ="UbProductShareActivity";

    private ShareBean bean;

    private void initShare(){
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000*10);
        Log.e("分享请求数据=", "数据="+params);
        http.send(HttpRequest.HttpMethod.GET,
                AppConfig.BASE_URL+"user/MyInvitationCode"+"?token="+GlobalInfo.userToken,
                params, new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String  result =  responseInfo.result;
                        Log.e("分享数据=", "数据="+result);
                        Gson gson = new Gson();//初始化
                        bean = gson.fromJson(result, ShareBean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                        String state = bean.getStatus();
                        if(state == null){
                            return;
                        }
                        switch (state){
                            case "200":
//                                tv_invitationCode.setText(bean.getStatement());
                                tv_invitationCode.setText(bean.getInvitationCode());
                                break;
                            case "210":
                                ToastUtil.show(UbProductShareActivity.this,"网络异常");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
    }
    private Intent intent;
    @OnClick({R.id.btn_share,R.id.ll_back,R.id.tv_share_fuli})
    public void onViewClicked(View view) {//331365
        switch (view.getId()) {
            case R.id.btn_share:
//                String imageurl = "http://jiayb.com/youbao/upload/jyb.html?yqm="+"2Y8BS7";
                String imageurl = "http://jiayb.com/youbao/upload/jyb.html"+"?yqm="+bean.getInvitationCode();
                ShareBoardConfig boardConfig = new ShareBoardConfig();
                boardConfig.setMenuItemTextColor(R.color.normal_color);
                UMImage image = new UMImage(UbProductShareActivity.this, R.drawable.jiayoubao);//本地文件
                UMWeb web = new UMWeb(imageurl);
                web.setTitle("下载拿现金,邀请送好礼\n邀请码： "+bean.getInvitationCode());
                web.setThumb(image);  //缩略图
                web.setDescription("注册佳优保送6-18元现金，邀请好友再送10积分");
                new ShareAction(UbProductShareActivity.this)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.WEIXIN,
                                SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE)
                        .setCallback(umShareListener)
                        .open(boardConfig);
                break;
            case R.id.ll_back:
                finish();
                break;

            case R.id.tv_share_fuli:
                intent = new Intent(this, MyRewardsActivity.class);
                startActivity(intent);
                break;

        }

    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(UbProductShareActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(UbProductShareActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(UbProductShareActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ub_product_share;
    }


}
