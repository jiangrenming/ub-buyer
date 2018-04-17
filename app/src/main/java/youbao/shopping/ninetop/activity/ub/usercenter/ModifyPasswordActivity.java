package youbao.shopping.ninetop.activity.ub.usercenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import youbao.shopping.ninetop.UB.UbUserService;
import youbao.shopping.ninetop.activity.user.LoginActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.util.ValidateUtil;
import youbao.shopping.ninetop.common.view.AuthCodeEditText;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.common.view.PwdEditText;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.R.id.tv_get_code;

/**
 * Created by huangjinding on 2017/4/17.
 */
public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.et_code)
    EditText tvGetCode;
    @BindView(R.id.tv_get_code)
    TextView etCode;


    @BindView(R.id.pet_new_password)
    PwdEditText petNewPassword;
    @BindView(R.id.pet_old_password)
    PwdEditText petOldPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private UbUserService ubUserService;

    public ModifyPasswordActivity() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_shuruxingmima;
    }


    protected void initView() {
        super.initView();
        ubUserService = new UbUserService(this);

        hvHead.setTitle("修改密码");
        petNewPassword.setHintText("请输入新密码");
        petOldPassword.setHintText("请再输入一次");

    }

    private void inputChangeHandle() {
        int textColor = R.color.text_gray;
        textColor = R.color.text_red;
        boolean enable = false;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
    }

    private int remainSecond = 60;

    private Timer countDownTimer;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            etCode.setText(remainSecond + "S");
            remainSecond--;
            if (remainSecond <= 0) {
                etCode.setEnabled(true);
                etCode.setText("获取验证码");
                etCode.setTextColor(Tools.getColorValueByResId(ModifyPasswordActivity.this, R.color.text_red));
                countDownTimer.cancel();// 取消
                remainSecond = 60;
            }

        };
    };
    private void changeUITime(){
        countDownTimer = new Timer();
        countDownTimer.schedule(new TimerTask() {
          @Override
          public void run() {
              handler.sendMessage(new Message());
          }
      }, 0, 1000);
        etCode.setEnabled(false);
        etCode.setTextColor(Tools.getColorValueByResId(ModifyPasswordActivity.this, R.color.text_gray2));
  }

    @OnClick({tv_get_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case tv_get_code:
                changeUITime();

                String mMobile = GlobalInfo.saveMobile;
                if ("".equals(mMobile)) {
                    showToast("手机号不能为空");
                } else {
                    if (mMobile.length() != 11) {
                        showToast("请输入11位数的和手机号码");
                    } else {
                        boolean isMobile = ValidateUtil.isMobilePhone(mMobile);
                        if (!isMobile) {
                            showToast("手机号码格式不正确");
                        } else {
                            ubUserService.getPwdCode(GlobalInfo.saveMobile, new CommonResultListener<String>(this) {
                                @Override
                                public void successHandle(String result) throws JSONException {
                                    showToast("验证码已发送");
                                }
                            });
                        }
                    }
                }
                break;
            case R.id.btn_login:
                String mobile = GlobalInfo.saveMobile;
                String code = tvGetCode.getText().toString().trim();
                String passwordNew = petNewPassword.getText();
                String passwordOld = petOldPassword.getText();
                if ("".equals(mobile) && "".equals(code) && "".equals(passwordNew) && "".equals(passwordOld)) {
                    showToast("请输入完整信息");
                } else if ("".equals(mobile)) {
                    showToast("请输入手机号");
                } else if ("".equals(code)) {
                    showToast("请输入验证码");
                } else if ("".equals(passwordNew)) {
                    showToast("请输入新的密码");
                } else if ("".equals(passwordOld)) {
                    showToast("请再输入一次");
                } else {
                    ubUserService.modifyPassword(mobile, code, passwordNew, passwordOld, new CommonResultListener<String>(this) {
                        @Override
                        public void successHandle(String result) throws JSONException {
                            showToast("修改成功");
                            finish();
                            startActivity(LoginActivity.class);
                        }
                    });
                }
                break;
        }
    }
}
