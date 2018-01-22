//package com.ninetop.activity.user;
//
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.ninetop.base.BaseActivity;
//import com.ninetop.common.util.Tools;
//import com.ninetop.service.impl.UserCenterService;
//import com.ninetop.service.listener.CommonResultListener;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import youbao.shopping.R;
//
//
//public class BalancesActivity extends BaseActivity {
//    @BindView(R.id.iv_icon_back)
//    ImageView ivIconBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.ll_title)
//    LinearLayout ll_title;
//    @BindView(R.id.iv_common_icon)
//    ImageView ivCommonIcon;
//    @BindView(R.id.tv_common_title_detail)
//    TextView tvCommonTitleDetail;
//    @BindView(R.id.tv_my_welcard_balance)
//    TextView tvMyWelCardBalance;
//    private final UserCenterService userCenterService;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.ub_activity_my_wallet;
//    }
//    public BalancesActivity(){
//        userCenterService = new UserCenterService(this);
//    }
//
//    @Override
//    protected void initView() {
//        super.initView();
//        tvCommonTitleDetail.setVisibility(View.VISIBLE);
//        ivCommonIcon.setVisibility(View.GONE);
//        tvTitle.setText("我的钱包");
//        tvCommonTitleDetail.setText("明细");
//        tvTitle.setTextColor(getResources().getColor(R.color.white));
//        tvCommonTitleDetail.setTextColor(getResources().getColor(R.color.white));
//        ll_title.setBackgroundResource(R.color.category_select);
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//        ButterKnife.bind(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getData();
//    }
//
//    private void getData() {
//        userCenterService.freeMoney(new CommonResultListener<String>(this) {
//            @Override
//            public void successHandle(String result) {
//                setMoney(result);
//            }
//        });
//    }
//    @OnClick({R.id.iv_icon_back, R.id.tv_common_title_detail})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_icon_back:
//                finish();
//                break;
//            case R.id.tv_common_title_detail:
//                startActivity(WelCardDetailedActivity.class);
//                break;
//
//
//        }
//    }
//    public void setMoney(String money) {
//        tvMyWelCardBalance.setText(Tools.saveNum(Double.valueOf(TextUtils.isEmpty(money)?0+"":money)));
//    }
//}
