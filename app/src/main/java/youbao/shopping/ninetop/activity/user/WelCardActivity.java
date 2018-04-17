package youbao.shopping.ninetop.activity.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.util.ToastUtils;
import youbao.shopping.ninetop.common.util.ValidateUtil;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.UserCenterService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * @date: 2016/11/11
 * @author: Shelton
 * @version: 1.1.3
 * @Description:福利卡界面
 */
public class WelCardActivity extends BaseActivity implements View.OnClickListener {

    private final UserCenterService userCenterService;
    @BindView(R.id.et_input_number)
    EditText et_input_number;
    @BindView(R.id.btn_charge)
    Button btn_charge;
    @BindView(R.id.hv_title)
    HeadView hvTitle;

    private String input_number;

    public WelCardActivity(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcard;
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitleBar();
    }
    private void initTitleBar() {
        hvTitle.setTitle("福利卡");
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_charge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_charge:
                RequestCharge();
                break;
        }
    }

    private void RequestCharge() {
        input_number = et_input_number.getText().toString().trim();
        if (TextUtils.isEmpty(input_number)) {
            ToastUtils.showCenter(this, "卡号不能为空");
            return;
        }
        if (!ValidateUtil.isFreeca(input_number)) {
            ToastUtils.showCenter(this, "您输入的卡号有误请重新输入");
            return;
        }
        topUp(input_number);
    }

    private void topUp(String input_number) {
        userCenterService.topUpFreeCharge(input_number, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                showToast("充值成功");
                et_input_number.setText("");
            }
        });
    }
}
