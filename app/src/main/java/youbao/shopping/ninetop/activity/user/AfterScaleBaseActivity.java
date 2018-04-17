package youbao.shopping.ninetop.activity.user;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.order.SafeGuardDetailsBean;
import youbao.shopping.ninetop.common.view.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;


public abstract class AfterScaleBaseActivity extends BaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.ll_remind)
    LinearLayout llRemind;
    @BindView(R.id.tv_success_return_money_count)
    TextView tvSuccessReturnMoneyCount;
    @BindView(R.id.tv_success_return_money_time)
    TextView tvSuccessReturnMoneyTime;
    @BindView(R.id.over)
    LinearLayout over;
    @BindView(R.id.tv_apply_for_time)
    TextView tvApplyForTime;
    @BindView(R.id.tv_logistics_style)
    TextView tvLogisticsStyle;
    @BindView(R.id.logistics)
    LinearLayout logistics;
    @BindView(R.id.tv_quality)
    TextView tvQuality;
    @BindView(R.id.tv_return_money_style)
    TextView tvReturnMoneyStyle;
    @BindView(R.id.tv_return_money)
    TextView tvReturnMoney;
    @BindView(R.id.bt_logistics_message)
    Button btLogisticsMessage;
    @BindView(R.id.rl_button)
    RelativeLayout rlButton;
    @BindView(R.id.ll_close_reaon)
    LinearLayout llCloseReason;
    @BindView(R.id.ll_successful)
    LinearLayout llSuccessful;
    @BindView(R.id.tv_close_successful)
    TextView tvCloseSuccessful;
    @BindView(R.id.tv_close_reason)
    TextView tvCloseReason;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_after_scale;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        hvHead.setSearchVisible(View.GONE);
        hvHead.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @OnClick({R.id.bt_revoke, R.id.bt_logistics_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_revoke:
                revoke();
                break;
            case R.id.bt_logistics_message:
                fillLogistic();
                break;
        }
    }

    //撤销申述
    public void revoke() {
    }

    //填写物流信息
    public void fillLogistic() {
    }

    public void back() {
        finish();
    }

    public void initFoot(SafeGuardDetailsBean result) {
        tvApplyForTime.setText(result.createdtime);
        if (AfterScaleEum.RefundReason.UN_RECEIVED_GOODS.getReason().equals(result.complaintsreason)) {
            tvQuality.setText(AfterScaleEum.RefundReason.UN_RECEIVED_GOODS.getExplian());
        } else if (AfterScaleEum.RefundReason.DESCRIBE_GOODS_UNLIKE.getReason().equals(result.complaintsreason)) {
            tvQuality.setText(AfterScaleEum.RefundReason.DESCRIBE_GOODS_UNLIKE.getExplian());
        } else if (AfterScaleEum.RefundReason.QUALITY_PROBLEM.getReason().equals(result.complaintsreason)) {
            tvQuality.setText(AfterScaleEum.RefundReason.QUALITY_PROBLEM.getExplian());
        } else if (AfterScaleEum.RefundReason.OTHER.getReason().equals(result.complaintsreason)) {
            tvQuality.setText(AfterScaleEum.RefundReason.OTHER.getExplian());
        }

        if (AfterScaleEum.RefundStyle.REFUND_SCALERETURN.getStyle().equals(result.complaintstype)) {
            tvReturnMoneyStyle.setText(AfterScaleEum.RefundStyle.REFUND_SCALERETURN.getExplian());
        } else if (AfterScaleEum.RefundStyle.ONLY_REFUND.getStyle().equals(result.complaintstype)) {
            tvReturnMoneyStyle.setText(AfterScaleEum.RefundStyle.ONLY_REFUND.getExplian());
        }
        tvReturnMoney.setText(result.returnmoney);
    }
}
