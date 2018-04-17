package youbao.shopping.ninetop.activity.ub.usercenter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import youbao.shopping.ninetop.UB.LineMobileBean;
import youbao.shopping.ninetop.UB.OnLineAskBean;
import youbao.shopping.ninetop.UB.QuestionListAdapter;
import youbao.shopping.ninetop.UB.UbSellerService;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/4/20.
 */
public class OnlineKeFuActivity extends BaseActivity {

    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.lv_question_list)
    ListView lvQuestionList;
    private String mobile;
    private UbSellerService ubSellerService;
    private QuestionListAdapter questionListAdapter;
    private List<OnLineAskBean> dataList;

    public OnlineKeFuActivity() {
        ubSellerService = new UbSellerService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_lianxikefu;
    }

    protected void initView() {
        super.initView();
        hvTitle.setTitle("在线客服");
        View mHead = LayoutInflater.from(this).inflate(R.layout.item_mine_online_service_head, null);
        lvQuestionList.addHeaderView(mHead);
        dataList = new ArrayList<>();
        questionListAdapter = new QuestionListAdapter(OnlineKeFuActivity.this, dataList);
        lvQuestionList.setAdapter(questionListAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getSeverData();
    }

    private void getSeverData() {
        ubSellerService.getQuestion(new CommonResultListener<List<OnLineAskBean>>(this) {
            @Override
            public void successHandle(List<OnLineAskBean> result) throws JSONException {
                dataList.addAll(result);
                questionListAdapter.notifyDataSetChanged();
            }
        });


        ubSellerService.linePlatform(new CommonResultListener<LineMobileBean>(this) {
            @Override
            public void successHandle(LineMobileBean result) throws JSONException {
                mobile = result.mobile;
            }
        });
    }

    @OnClick(R.id.btn_city_more)
    public void onViewClicked() {
        call();
    }

    private void call() {
        new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "确定拨打" + mobile + "吗?", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void cancelOnClick(View v) {

            }
        }).show();
    }
}