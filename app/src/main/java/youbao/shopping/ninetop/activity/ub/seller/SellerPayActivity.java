package youbao.shopping.ninetop.activity.ub.seller;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.hykj.myviewlib.gridview.GridPasswordView;
import youbao.shopping.ninetop.UB.BalancePaySellerBean;
import youbao.shopping.ninetop.UB.QuesIsSetBean;
import youbao.shopping.ninetop.UB.UbSellerService;
import youbao.shopping.ninetop.UB.UbUserCenterService;
import youbao.shopping.ninetop.UB.UbUserInfo;
import youbao.shopping.ninetop.UB.order.UbPayInfoBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.activity.ub.question.QuestionActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.order.WeChatPayBean;
import youbao.shopping.ninetop.bean.order.WeChatPayInfoBean;
import youbao.shopping.ninetop.bean.seller.SellerDetailBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.LoginAction;
import youbao.shopping.ninetop.common.constant.SharedKeyConstant;
import youbao.shopping.ninetop.common.pay.AlipayCallBack;
import youbao.shopping.ninetop.common.pay.AlipayProcessor;
import youbao.shopping.ninetop.common.pay.WXPayHelper;
import youbao.shopping.ninetop.common.util.MySharedPreference;
import youbao.shopping.ninetop.common.util.ToastUtils;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;
import youbao.shopping.ninetop.update.StringUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Response;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/4/20.
 */
public class SellerPayActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.et_pay_price)
    EditText etPayPrice;
    @BindView(R.id.tv_seller_ratio)
    TextView tvSellerRatio;
    @BindView(R.id.tv_ratio_already)
    TextView tvSellerAlready;
    @BindView(R.id.tv_balance_alert)
    TextView tvBalanceAlert;
    @BindView(R.id.iv_pay_wechat)
    ImageView ivPayWeChat;
    @BindView(R.id.iv_pay_balance)
    ImageView ivPayBalance;
    @BindView(R.id.tv_need_pay)
    TextView tvNeedPay;
    @BindView(R.id.iv_pay_alipay)
    ImageView ivPayAliPay;
    @BindView(R.id.tv_finish_pay)
    TextView tvFinishPay;
    private String id;
    private String lat1;
    private String lng1;
    private UbSellerService ubSellerService;
    private UbProductService ubProductService;
    private UbUserCenterService ubUserCenterService;
    private int payType;
    private int pwdIsSet;
    private double balance;
    private SellerDetailBean sellerDetailBean;
    private UbUserInfo userInfo;


    public SellerPayActivity() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_shangpingzhifu;
    }

    protected void initView() {
        super.initView();
        hvHead.setTitle("");
        etPayPrice.setGravity(Gravity.RIGHT);
        etPayPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        ubProductService = new UbProductService(this);
        ubSellerService = new UbSellerService(this);
        ubUserCenterService = new UbUserCenterService(this);
        id = getIntentValue(IntentExtraKeyConst.SELLER_ID);
        lat1 = MySharedPreference.get(SharedKeyConstant.SEARCH_LAT, "30.30589", this);
        lng1 = MySharedPreference.get(SharedKeyConstant.SEARCH_LON, "120.118026", this);
        etPayPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                payPriceChangeHandle();
            }
        });
        ivPayBalance.setImageResource(R.mipmap.chiose);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void payPriceChangeHandle() {
        if (sellerDetailBean == null)
            return;
        ivPayBalance.setImageResource(R.mipmap.chiose);
        ivPayWeChat.setImageResource(R.mipmap.edit_unselect);
        ivPayAliPay.setImageResource(R.mipmap.edit_unselect);

        String value = etPayPrice.getText().toString();
        double price = getDoubleTextValue(etPayPrice);
        double ub = price * sellerDetailBean.getRatio4Count();
        LoginAction.saveUb(ub + "", SellerPayActivity.this);
        LoginAction.saveUbPay(price + "", SellerPayActivity.this);
        long ubIntValue = Math.round(Double.valueOf(ub));
        tvSellerAlready.setText(ubIntValue + "");
        tvNeedPay.setText(Math.round(Double.valueOf(value))+"");
    }

    private void getData() {
        ubSellerService.getSellerDetail(lat1, lng1, id, null, new CommonResultListener<SellerDetailBean>(this) {
            @Override
            public void successHandle(SellerDetailBean result) throws JSONException {
                if (result == null) {
                    return;
                }
                sellerDetailBean = result;
                hvHead.setTitle(result.getName());
                tvSellerName.setText(result.addr_city + result.addr_county + result.addr_detail);
                tvSellerRatio.setText("积分赠送比例: " + result.ratio);
            }
        });

        ubUserCenterService.getUserCenter(new CommonResultListener<UbUserInfo>(this) {
            @Override
            public void successHandle(UbUserInfo result) throws JSONException {
                if (result == null) {
                    return;
                }
                userInfo = result;
                balance = userInfo.balance;
                long myBalance = (long) Math.floor(balance);
                tvBalanceAlert.setText("用户余额 ：" + myBalance + "");
            }
        });

        ubProductService.getPwdIsSet(new CommonResultListener<QuesIsSetBean>(this) {
            @Override
            public void successHandle(QuesIsSetBean result) throws JSONException {
                pwdIsSet = result.is_set;
            }
        });

    }

    private void setSelectIndex(int index) {
        payType = index;
        ivPayBalance.setImageResource(R.mipmap.edit_unselect);
        ivPayWeChat.setImageResource(R.mipmap.edit_unselect);
        ivPayAliPay.setImageResource(R.mipmap.edit_unselect);
        if (index == 0) {
            ivPayBalance.setImageResource(R.mipmap.chiose);
//            double price = getDoubleTextValue(etPayPrice);
//            if (price > userInfo.balance) {
//                showToast("余额不足");
//                payType = -1;
//                return;
//            }

        } else if (index == 1) {
            ivPayAliPay.setImageResource(R.mipmap.chiose);
        } else if (index == 2) {
            ivPayWeChat.setImageResource(R.mipmap.chiose);
        }
    }


    @OnClick({R.id.rl_pay_balance, R.id.rl_pay_wechat, R.id.rl_pay_alipay, R.id.btn_confirm_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_pay_balance:
//                long payValue = getLongTextValue(etPayPrice);
//                Double balance = userInfo.getBalance();
//                if (balance == null)
//                    balance = 0.0;
//                if (balance < payValue) {
//                    showToast("余额不足");
//                    return;
//                }

                setSelectIndex(0);
                break;
            case R.id.rl_pay_alipay:
                setSelectIndex(1);
                break;
            case R.id.rl_pay_wechat:
                setSelectIndex(2);
                break;
            case R.id.btn_confirm_pay:
                selectPay();
                break;
            default:
                break;
        }
    }

    private void selectPay() {
        String editPay = etPayPrice.getText().toString().trim();
        if ("".equals(editPay) || editPay.length() == 0) {
            showToast("不能为空");
            return;
        }
        tvFinishPay.setText(editPay);
        double price = getDoubleTextValue(etPayPrice);
        if (price > -0.000001 && price < +0.000001) {
            showToast("请输入和正确的价格");
        } else {
            if (payType == 0) {
                //判断是否设置过密码
                if (pwdIsSet == 0) {
                    new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您需要设置支付密码", new MyDialogOnClick() {
                        @Override
                        public void sureOnClick(View v) {
                            startActivity(QuestionActivity.class);
                        }

                        @Override
                        public void cancelOnClick(View v) {

                        }
                    }).show();
                } else {
                    balancePay(editPay);
                }
            } else if (payType == 1) {
                final int sellerId = Integer.parseInt(id);
                ubProductService.postSellerAlipay(price, sellerId, "", new CommonResultListener<UbPayInfoBean>(this) {
                    @Override
                    public void successHandle(UbPayInfoBean result) throws JSONException {
                        new AlipayProcessor(SellerPayActivity.this).alipay(result.payinfo, new AlipayCallBack() {
                            @Override
                            public void paySuccess() {
                                showToast("支付成功");
                                Bundle mBundle = new Bundle();
                                Intent mIntent = new Intent(SellerPayActivity.this, SellerPaySuccessActivity.class);
                                mBundle.putInt(SharedKeyConstant.ORDER_ID, sellerId);
                                mIntent.putExtras(mBundle);
                                startActivity(mIntent);
                            }

                            @Override
                            public void payFailure() {

                            }
                        });
                    }
                });


            } else if (payType == 2) {
                int sellerId = Integer.parseInt(id);
                ubProductService.postSellerWechatPay(price, sellerId, "你好", new CommonResultListener<WeChatPayInfoBean>(this) {
                    @Override
                    public void successHandle(WeChatPayInfoBean result) throws JSONException {
                        final WeChatPayBean payinfo = result.getPayinfo();
                        if (payinfo != null){
                            Log.i("联盟支付=",result.getPayinfo().getAppid());
                            if (checkNessaryVlaue(payinfo)){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String appid = payinfo.getAppid();
                                        IWXAPI api = WXAPIFactory.createWXAPI(SellerPayActivity.this,appid,true);
                                        api.registerApp(appid);
                                        PayReq req = new PayReq();
                                        req.appId = appid;
                                        req.partnerId = payinfo.getPartnerid();
                                        req.prepayId = payinfo.getPrepayid();
                                        req.packageValue = payinfo.getPackageinfo();
                                        req.nonceStr = payinfo.noncestr;
                                        req.timeStamp = payinfo.timestamp;
                                        req.sign = payinfo.sign;
                                         api.sendReq(req);
                                    }
                                });
                            }else {
                                ToastUtils.showCenter("商户未注册appID或加签失败或商户id不存在，请检查返回的参数");
                                return;
                            }
                        }else {
                            Log.i("TAG","返回数据为空，请检查");
                        }
                    }

                    @Override
                    public void failHandle(String code, String message) {
                        super.failHandle(code, message);
                        Log.i("联盟支付失败=",message);
                    }

                    @Override
                    public void errorHandle(Response response, Exception e) {
                        super.errorHandle(response, e);
                        Log.i("联盟支付异常=",response.message());
                    }
                });
            }
        }
    }

    private  boolean checkNessaryVlaue(WeChatPayBean payinfo){
            if (StringUtil.isEmpty(payinfo.getAppid()) ||
                    StringUtil.isEmpty(payinfo.getPartnerid())
                    || StringUtil.isEmpty(payinfo.getSign())){
                return false;
            }
        return true;
    }


    private void balancePay(final String balancePay) {
        final Dialog dialog = new Dialog(this);
        dialog.show();
        View view = View.inflate(this.getApplicationContext(),
                R.layout.activity_pay_enter_password, null);
        final GridPasswordView psdView = (GridPasswordView) view.findViewById(R.id.gps_view);
        View closeView = view.findViewById(R.id.iv_close);
        View btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String password = psdView.getPassWord();
                String password = psdView.getPassWord();
                if (password.length() < 6) {
                    showToast("请输入6位支付密码");
                    return;
                }
                double price = getDoubleTextValue(etPayPrice);
                ubProductService.postBalancePaySeller(price, id, password, "", new CommonResultListener<BalancePaySellerBean>(SellerPayActivity.this) {
                    @Override
                    public void successHandle(BalancePaySellerBean result) throws JSONException {
                        showToast("支付成功");
                        int sellerId = Integer.parseInt(id);
                        Bundle mBundle = new Bundle();
                        Intent mIntent = new Intent(SellerPayActivity.this, SellerPaySuccessActivity.class);
                        mBundle.putInt(SharedKeyConstant.ORDER_ID, sellerId);
                        mIntent.putExtras(mBundle);
                        startActivity(mIntent);
                    }
                });

            }
        });

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow()
                .getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

}
