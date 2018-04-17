package youbao.shopping.ninetop.activity.ub.usercenter;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.view.HeadView;

import butterknife.BindView;
import youbao.shopping.R;

import static youbao.shopping.R.id.hv_head;

/**
 * Created by huangjinding on 2017/4/17.
 */
public class ThirdActivity extends BaseActivity{
    @BindView(hv_head)
     HeadView hvHead;

    public ThirdActivity() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_bangding;
    }


    protected void initView() {
        super.initView();
       hvHead.setTitle("第三方绑定");
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_lijilianxi:
//                Toast.makeText(AboutPlatActivity.this, "请稍等", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//    }
}
