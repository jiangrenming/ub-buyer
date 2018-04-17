package youbao.shopping.ninetop.activity.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.UserInfo;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.UserCenterService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * @date: 2016/11/11
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class ModifyNicknameActivity extends BaseActivity {
    private final UserCenterService userCenterService;
    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.et_modify_nickname)
    EditText etModifyNickname;
    private ArrayList<File> files=new ArrayList<>();

    public ModifyNicknameActivity(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_nickname;
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvTitle.setTitle("修改昵称");
    }
    @OnClick(R.id.button)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                modifyNickname();
                break;
        }
    }
    private void modifyNickname() {
        String nikeName = etModifyNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nikeName)){
            showToast("昵称不能为空");
            return;
        }
        fixName(nikeName);
    }

    private void fixName(String nikeName) {
        if (files!=null){
            files.clear();
        }
        userCenterService.fixUserInfo(nikeName, null, files, new CommonResultListener<UserInfo>(this) {
            @Override
            public void successHandle(UserInfo userInfo) throws JSONException {
                showToast("修改昵称成功");
                finish();
            }
        });
    }
}
