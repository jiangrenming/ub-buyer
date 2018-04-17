package youbao.shopping.ninetop.activity.ub.usercenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import youbao.shopping.ninetop.UB.UbUserCenterService;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/4.
 */
public class ModifyNicknameActivity extends BaseActivity {
    private final UbUserCenterService ubUserCenterService;
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.et_modify_nickname)
    EditText etModifyNickname;
    @BindView(R.id.button)
    Button button;
    private ArrayList<File> files = new ArrayList<>();

    public ModifyNicknameActivity() {
        ubUserCenterService = new UbUserCenterService(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_modify_nickname;
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvHead.setTitle("修改昵称");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.hv_head, R.id.et_modify_nickname, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hv_head:
                break;
            case R.id.et_modify_nickname:

                break;
            case R.id.button:
                modifyNickname();
                break;
        }
    }
    private void modifyNickname(){
        String nickName=etModifyNickname.getText().toString().trim();
        if(TextUtils.isEmpty(nickName)){
            showToast("昵称不能为空");
            return;
        }
        fixName(nickName);
    }

    private void fixName(String nickName){
        if(files!=null){
            files.clear();
        }
        ubUserCenterService.updateUserInfo(nickName, 0, null, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result){
                showToast("昵称修改成功");
                finish();
            }
        });
    }
}
