package youbao.shopping.ninetop.activity.user;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;

import youbao.shopping.ninetop.UB.UbUserCenterService;
import youbao.shopping.ninetop.UB.UbUserInfo;
import youbao.shopping.ninetop.activity.TabBaseActivity;
import youbao.shopping.ninetop.activity.product.MyOrderActivity;
import youbao.shopping.ninetop.activity.ub.product.UbProductActivity;
import youbao.shopping.ninetop.activity.ub.product.UbProductShareActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.AboutPlatActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.Camera.view.CircleImageView;
import youbao.shopping.ninetop.activity.ub.usercenter.ConSumeOrderActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.ConnectWithActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.OnlineKeFuActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.ReChargeActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.UbMyWalletActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.UbSystemSetActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.myWallet.MyBWalletActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.myWallet.MyUWalletActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.mycollection.MyCollectionActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.common.LoginAction;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 *
 */

public class UserCenterActivity extends TabBaseActivity implements View.OnClickListener {
    //圆角头像
    @BindView(R.id.rlm_user_icon)
    CircleImageView rlmUserIcon;
    @BindView(R.id.iv_notify)
    ImageView ivNotify;
    @BindView(R.id.tv_nimei_username)
    TextView tvNiMeiUserName;
    @BindView(R.id.tv_u_balance)
    TextView tvUBalance;
    //用户号码
    @BindView(R.id.tv_mobile)
    TextView tvMobile;

    @BindView(R.id.tv_account_balance)
    TextView tvAccountBalance;
    private final UbUserCenterService ubUserCenterService;
//    private int count[]=new int[5];
//    private HashMap<TextView, Integer> viewStringHashMap;
//    private TextView[] textView;

    public UserCenterActivity() {
        ubUserCenterService = new UbUserCenterService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_gerenzhongxin;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        //用户的信息
        ubUserCenterService.getUserCenter(new CommonResultListener<UbUserInfo>(this) {
            @Override
            public void successHandle(UbUserInfo result) {
                iniUserInfo(result);
            }
        });
    }

    private void iniUserInfo(UbUserInfo result) {
        if (result != null) {
            String U_Balance = String.valueOf(result.ub_point);
            String u_balance = Math.round(Double.valueOf(result.ub_point))+"";
            String account_balance = String.valueOf(result.balance);
            tvNiMeiUserName.setText(result.nick_name);
            tvNiMeiUserName.setEnabled(false);
            tvMobile.setText(result.mobile);
            LoginAction.saveMobile(result.mobile, UserCenterActivity.this);
            tvUBalance.setText(u_balance);
            tvAccountBalance.setText(account_balance);
            if (result.avatar != null) {
                try {
                    Log.i("路径", BASE_IMAGE_URL + result.avatar);
                    Tools.ImageLoaderShow(this, BASE_IMAGE_URL + result.avatar, rlmUserIcon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            tvNiMeiUserName.setText("点击登录");
            tvNiMeiUserName.setBackgroundResource(R.drawable.bg_border_gray);
        }

    }

    @OnClick({R.id.rlm_user_icon, R.id.tv_nimei_username, R.id.rl_u, R.id.rl_balance, R.id.iv_notify, R.id.rl_myOrder, R.id.rl_stay_pay_money,
            R.id.rl_stay_deliver, R.id.rl_stay_receive, R.id.rl_review,
            R.id.rl_back_goods, R.id.ll_xiaofeizhangdan, R.id.ll_lianxikefu,
            R.id.ll_address_manage, R.id.ll_woayaohezuo, R.id.ll_system_setting
            , R.id.ll_blance, R.id.ll_guanyupingtai, R.id.rl_kuaisu_chongzhi1, R.id.rl_shoucang1, R.id.rl_fuli_duihuan1
    })
    //rl_stay_pay_money
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlm_user_icon:
                startActivity(UbSystemSetActivity.class);
                break;
            case R.id.tv_nimei_username:
                startActivity(LoginActivity.class);
                break;
            case R.id.rl_u:
                startActivity(MyUWalletActivity.class);
                break;
            case R.id.rl_balance:
                startActivity(MyBWalletActivity.class);
                break;
            //通知
            case R.id.iv_notify:
                //  startActivity(MessageActivity.class);
                break;
            //系统设置
            case R.id.ll_system_setting:
                startActivity(UbSystemSetActivity.class);
                break;
            //待兑换
            case R.id.rl_stay_pay_money:
                Intent intent = new Intent(this, MyOrderActivity.class);
                intent.putExtra("order_fg", 0);
                startActivity(intent);
                break;
            //待付款
            case R.id.rl_stay_deliver:
                Intent intent1 = new Intent(this, MyOrderActivity.class);
                intent1.putExtra("order_fg", 1);
                startActivity(intent1);
                break;

            //待发货
            case R.id.rl_stay_receive:
                Intent intent2 = new Intent(this, MyOrderActivity.class);
                intent2.putExtra("order_fg", 2);
                startActivity(intent2);
                break;
            //待自取
            case R.id.rl_review:
                Intent intent3 = new Intent(this, MyOrderActivity.class);
                intent3.putExtra("order_fg", 3);
                startActivity(intent3);
                break;
            //已完成
            case R.id.rl_back_goods:
                Intent intent4 = new Intent(this, MyOrderActivity.class);
                intent4.putExtra("order_fg", 4);
                startActivity(intent4);
                break;
            //我的订单
            case R.id.rl_myOrder:
                Intent mIntent = new Intent(this, MyOrderActivity.class);
                mIntent.putExtra("order_fg", 0);
                startActivity(mIntent);
                break;

            //我的收藏
            case R.id.rl_shoucang1:
                intent = new Intent(this, MyCollectionActivity.class);
                intent.putExtra("order_fg", 0);
                startActivity(intent);
                break;
            //福利兑换
            case R.id.rl_fuli_duihuan1:
//                new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "功能等待开放", "", new MyDialogOnClick() {
//                    @Override
//                    public void sureOnClick(View v) {
//
//                    }
//                    @Override
//                    public void cancelOnClick(View v) {
//                    }
//                }).show();
//               wToast("功能等待开放");
                if(isLogin()){
                    intent = new Intent(this, UbProductShareActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            //快速充值
            case R.id.rl_kuaisu_chongzhi1:
                intent = new Intent(this, ReChargeActivity.class);
                intent.putExtra("order_fg", 2);
                startActivity(intent);
                break;

            //我的钱包
            case R.id.ll_blance:
                startActivity(UbMyWalletActivity.class);
                break;
            //地址管理
            case R.id.ll_address_manage:
                startActivity(AddressManagerActivity.class);
                break;
            //消费账单
            case R.id.ll_xiaofeizhangdan:
                startActivity(ConSumeOrderActivity.class);
                break;
            //联系客服
            case R.id.ll_lianxikefu:
                startActivity(OnlineKeFuActivity.class);
                break;
            //关于平台
            case R.id.ll_guanyupingtai:
                startActivity(AboutPlatActivity.class);
                break;
            //我要合作
            case R.id.ll_woayaohezuo:
                startActivity(ConnectWithActivity.class);
                break;
        }
    }

//    private void showDialog() {
//        new WXAttentionDialog(this).creatDialog().show();
//    }
}
