package com.ninetop.activity.ub.usercenter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetop.UB.UbUserCenterService;
import com.ninetop.activity.ub.bean.product.BalanceIntoBean;
import com.ninetop.activity.ub.seller.SellerPayActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.order.OrderAliPayBean;
import com.ninetop.bean.order.WeChatPayBean;
import com.ninetop.bean.order.WeChatPayInfoBean;
import com.ninetop.common.ActivityActionHelper;
import com.ninetop.common.pay.AlipayCallBack;
import com.ninetop.common.pay.AlipayProcessor;
import com.ninetop.common.pay.WXPayHelper;
import com.ninetop.common.util.ToastUtils;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;
import com.ninetop.update.StringUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/4/20.
 */
public class ReChargeActivity extends BaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.gv_category)
    GridView gvCategory;
    @BindView(R.id.iv_wechat)
    ImageView ivWeChat;
    @BindView(R.id.iv_alipay)
    ImageView ivAliPay;
    private UbUserCenterService ubUserCenterService;
    private FastReChargeAdapter adapter;
    private List<BalanceIntoBean> dataList;
    private int selectPosition = 0;
    private int inreachId;
    private int type;
    private boolean isAliPaySelected = false;
    private boolean isWeChatSelected = false;

    public ReChargeActivity() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_chongzhi;
    }

    protected void initView() {
        super.initView();
        hvHead.setTitle("快速充值");

        ubUserCenterService = new UbUserCenterService(this);
        dataList = new ArrayList<>();
        adapter = new FastReChargeAdapter(this, dataList);
        gvCategory.setAdapter(adapter);
        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeState(position);
                selectPosition = position;
            }
        });
        initDetail();
    }

    private void initDetail() {
        ubUserCenterService.getBalanceRecord(new CommonResultListener<List<BalanceIntoBean>>(this) {
            @Override
            public void successHandle(List<BalanceIntoBean> result) throws JSONException {
                dataList.addAll(result);
                adapter.notifyDataSetChanged();
            }
        });

    }


    private void selectTabColor(int index) {
        ivWeChat.setImageResource(R.mipmap.edit_unselect);
        ivAliPay.setImageResource(R.mipmap.edit_unselect);
        if (index == 0) {
            ivWeChat.setImageResource(R.mipmap.chiose);
            type = 0;
        } else if (index == 1) {
            ivAliPay.setImageResource(R.mipmap.chiose);
            type = 1;
        } else {

        }
    }

    @OnClick({R.id.rl_wechat, R.id.rl_alipay, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_wechat:
                selectTabColor(0);
                isWeChatSelected = true;
                break;
            case R.id.rl_alipay:
                selectTabColor(1);
                isAliPaySelected = true;
                break;
            case R.id.btn_confirm:
                if (!isAliPaySelected && !isWeChatSelected) {
                    showToast("请选择支付方式");
                } else {
                    confirmInreach();
                }
                break;
            default:
                break;
        }
    }

    private void confirmInreach() {
        // 支付宝支付
        if (type == 1) {
            ubUserCenterService.getBalanceAlpay(inreachId, new CommonResultListener<OrderAliPayBean>(this) {
                @Override
                public void successHandle(OrderAliPayBean result) throws JSONException {

                    new AlipayProcessor(ReChargeActivity.this).alipay(result.payinfo, new AlipayCallBack() {
                        @Override
                        public void paySuccess() {
                            showToast("充值成功");
                            ActivityActionHelper.goToMain(ReChargeActivity.this);
                        }

                        @Override
                        public void payFailure() {
                        }
                    });
                }
            });
        } else if (type == 0) {
            ubUserCenterService.getBalanceWechatPay(inreachId, new CommonResultListener<WeChatPayInfoBean>(this) {
                @Override
                public void successHandle(WeChatPayInfoBean result) throws JSONException {
                   // WXPayHelper.pay(ReChargeActivity.this, result.payinfo);
                    final WeChatPayBean payinfo = result.getPayinfo();
                    if (payinfo != null){
                        Log.i("联盟支付=",result.getPayinfo().getAppid());
                        if (checkNessaryVlaue(payinfo)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String appid = payinfo.getAppid();
                                    IWXAPI api = WXAPIFactory.createWXAPI(ReChargeActivity.this,appid,true);
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
            });
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

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public class FastReChargeAdapter extends BaseAdapter {
        BaseActivity activity;
        List<BalanceIntoBean> dataList;
        private int selectPosition;

        public FastReChargeAdapter(BaseActivity activity, List<BalanceIntoBean> dataList) {
            this.activity = activity;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final HolderView holderView;
            if (convertView == null) {
                holderView = new HolderView();
                convertView = LayoutInflater.from(activity).inflate(R.layout.ub_item_fast_inreach, null);
                holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_balance_count);
                holderView.tv_u_count = (TextView) convertView.findViewById(R.id.tv_balance_send);
                holderView.ll_balance = (LinearLayout) convertView.findViewById(R.id.ll_balance);

                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }
            final BalanceIntoBean bean = dataList.get(position);
            holderView.tv_name.setText(bean.amount + "元");
            holderView.tv_u_count.setText("送" + bean.ub_given + "积分");
            holderView.ll_balance.setBackgroundResource(R.drawable.bg_border_gray_radius);
            if (selectPosition == position) {
                holderView.tv_name.setTextColor(Tools.getColorByResId(activity, R.color.text_red));
                holderView.tv_u_count.setTextColor(Tools.getColorByResId(activity, R.color.text_red));
                holderView.ll_balance.setBackgroundResource(R.drawable.bg_border_red_radius);
                inreachId = bean.id;
            } else {
                holderView.tv_name.setTextColor(Tools.getColorByResId(activity, R.color.text_gray));
                holderView.tv_u_count.setTextColor(Tools.getColorByResId(activity, R.color.text_gray));
                holderView.ll_balance.setBackgroundResource(R.drawable.bg_border_gray_radius);
            }
            return convertView;
        }

        public void changeState(int pos) {
            selectPosition = pos;
            notifyDataSetChanged();
        }

        class HolderView {
            TextView tv_name;
            TextView tv_u_count;
            LinearLayout ll_balance;
        }
    }

}
