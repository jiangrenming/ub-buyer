package youbao.shopping.ninetop.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;
import youbao.shopping.ninetop.UB.QuestionAskBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.UB.question.QuestionBean;
import youbao.shopping.ninetop.activity.ub.question.QuestionActivity;
import youbao.shopping.ninetop.activity.ub.question.QuestionListActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

/**
 * Created by Administrator on 2018/4/12/012.
 */

public class ResetQuestionActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_qestion1)
    TextView tvQuestion1;
    @BindView(R.id.tv_qestion2)
    TextView tvQuestion2;
    @BindView(R.id.tv_qestion3)
    TextView tvQuestion3;
    @BindView(R.id.et_q1)
    EditText etQ1;
    @BindView(R.id.et_q2)
    EditText etQ2;
    @BindView(R.id.et_q3)
    EditText etQ3;
    private static final int SELECT_QUESTION = 1;
    private UbProductService ubProductService;
    private Map<TextView, Integer> questionIdMap = new HashMap<>();

    protected void initView() {
        super.initView();
        hvHead.setTitle("安全问题");
        ubProductService=new UbProductService(this);
//        getUserPassWordSet();
//        isSetViewable();
//        setUserQuestionList();
        //先判断
    }

    @OnClick({R.id.rl_click11, R.id.rl_click22, R.id.rl_click33, R.id.btn_confirm,R.id.ll_reset_pwd})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_click11:
                intent = new Intent(this, QuestionListActivity.class);
                intent.putExtra(IntentExtraKeyConst.CAN_SELECT, "1");
                startActivityForResult(intent, SELECT_QUESTION);
                break;
            case R.id.rl_click22:
                intent = new Intent(this, QuestionListActivity.class);
                intent.putExtra(IntentExtraKeyConst.CAN_SELECT, "2");
                startActivityForResult(intent, SELECT_QUESTION);
                break;
            case R.id.rl_click33:
                intent = new Intent(this, QuestionListActivity.class);
                intent.putExtra(IntentExtraKeyConst.CAN_SELECT, "3");
                startActivityForResult(intent, SELECT_QUESTION);
                break;
            case R.id.btn_confirm:
                confirmQuestion();
                break;

        }


    }

    private void confirmQuestion() {
        String q1 = etQ1.getText().toString().trim();
        String q2 = etQ2.getText().toString().trim();
        String q3 = etQ3.getText().toString().trim();

        int newQId1 = -1;
        int newQId2 = -1;
        int newQId3 = -1;

        if (questionIdMap.containsKey(tvQuestion1)) {
            newQId1 = questionIdMap.get(tvQuestion1);
        }
        if (questionIdMap.containsKey(tvQuestion2)) {
            newQId2 = questionIdMap.get(tvQuestion2);
        }
        if (questionIdMap.containsKey(tvQuestion3)) {
            newQId3 = questionIdMap.get(tvQuestion3);
        }
        //setPwdNow();
        //下拉框判断
        if (newQId1 == -1) {
            showToast("您的第一个问题不能为空");
            return;
        }
        if (newQId2 == -1) {
            showToast("您的第二个问题不能为空");
            return;
        }
        if (newQId3 == -1) {
            showToast("您的第三个问题不能为空");
            return;
        }
        if (q1.length() == 0) {
            showToast("您的第一个答案不能为空");
            return;
        }

        if (q2.length() == 0) {
            showToast("您的第二个答案不能为空");
            return;
        }

        if (q3.length() == 0) {
            showToast("您的第三个答案不能为空");
            return;
        }

        ubProductService.postBalancePwd(newQId1, q1, newQId2, q2, newQId3, q3, new CommonResultListener(this) {
            @Override
            public void successHandle(Object result) throws JSONException {
                showToast("提交成功");
                finish();
            }
        });


    }


    private void setUserQuestionList(){
        ubProductService.getPwdAsk(new CommonResultListener<QuestionAskBean>(this) {
            @Override
            public void successHandle(QuestionAskBean result) throws JSONException {
                tvQuestion1.setText(result.q1);
                tvQuestion2.setText(result.q2);
                tvQuestion3.setText(result.q3);
                questionIdMap.put(tvQuestion1, result.q1_id);
                questionIdMap.put(tvQuestion2, result.q2_id);
                questionIdMap.put(tvQuestion3, result.q3_id);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_QUESTION:
                    questionSelectChange(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void questionSelectChange(Intent data){
        String result = data.getStringExtra(IntentExtraKeyConst.JSON_QUESTION);
        String canSelect = data.getStringExtra(IntentExtraKeyConst.CAN_SELECT);
        Gson gson = new Gson();
        Type typeToken = new TypeToken<QuestionBean>() {
        }.getType();
        QuestionBean bean = gson.fromJson(result, typeToken);
        TextView textView = getSelectQuestion(canSelect);
        textView.setText(bean.safe_question);
        questionIdMap.put(textView, bean.id);
    }
    private TextView getSelectQuestion(String selectIndex){
        if("1".equals(selectIndex)){
            return tvQuestion1;
        }else if("2".equals(selectIndex)){
            return tvQuestion2;
        }else {
            return tvQuestion3;
        }
    }



    @Override
    protected int getLayoutId() {
        return R.layout.ub_safe_resetquetion;
    }


}
