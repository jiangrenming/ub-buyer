package youbao.shopping.ninetop.activity.ub.question;

import android.app.Dialog;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.hykj.myviewlib.gridview.GridPasswordView;
import youbao.shopping.ninetop.UB.QuesIsSetBean;
import youbao.shopping.ninetop.UB.QuestionAskBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.UB.question.QuestionBean;
import youbao.shopping.ninetop.activity.user.ResetQuestionActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/4/17.
 */
public class QuestionActivity extends BaseActivity {


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
    private int pwdIsSet;
    private static final int SELECT_QUESTION = 1;
    private UbProductService ubProductService;
    private Map<TextView, Integer> questionIdMap = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.ub_safe_quetion;
    }

    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("安全问题");
        ubProductService=new UbProductService(this);
        getUserPassWordSet();
    }

    private void getUserPassWordSet(){
      ubProductService.getPwdIsSet(new CommonResultListener<QuesIsSetBean>(this) {
          @Override
          public void successHandle(QuesIsSetBean result) throws JSONException {
              pwdIsSet = result.is_set;
              isSetPassword(pwdIsSet);
          }
      });
    }

    //判断是否设置密码并执行下一步
    private void isSetPassword( int pwdIsSet){
        if(pwdIsSet== 1){
            //已设置
            findViewById(R.id.rl_click11).setEnabled (false);
            findViewById(R.id.rl_click22).setEnabled(false);
            findViewById(R.id.rl_click33).setEnabled(false);
            setUserQuestionList();
        }else {
            findViewById(R.id.rl_click11).setEnabled (true);
            findViewById(R.id.rl_click22).setEnabled(true);
            findViewById(R.id.rl_click33).setEnabled(true);
        }
    }


     private void setPwdNow(){
        final Dialog dialog = new Dialog(this);
        dialog.show();
        View view = View.inflate(this.getApplicationContext(), R.layout.activity_pay_enter_password, null);
        final GridPasswordView psdView = (GridPasswordView) view.findViewById(R.id.gps_view);
        View closeView = view.findViewById(R.id.iv_close);
        View button = view.findViewById(R.id.btn_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = psdView.getPassWord();
                if (password.length() < 6) {
                    showToast("请输入6位支付密码");
                    return;
                }
                postPwd(password);
            }
        });
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window=dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);
        WindowManager windowManager=this.getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
        lp.width=(int)(display.getWidth());
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.color.transparent);
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
            case R.id.ll_reset_pwd:
                resetQuestionList();
                break;
            default:
                break;
        }
    }

    private void resetQuestionList() {

        String q1=etQ1.getText().toString().trim();
        String q2=etQ2.getText().toString().trim();
        String q3=etQ3.getText().toString().trim();

        int newQId1 = -1;
        int newQId2 = -1;
        int newQId3 = -1;

        if(questionIdMap.containsKey(tvQuestion1)) {
            newQId1 = questionIdMap.get(tvQuestion1);
        }
        if(questionIdMap.containsKey(tvQuestion2)) {
            newQId2=questionIdMap.get(tvQuestion2);
        }
        if(questionIdMap.containsKey(tvQuestion3)) {
            newQId3=questionIdMap.get(tvQuestion3);
        }
        //下拉框判断
        if(newQId1==-1){
            showToast("您的第一个问题不能为空");
            return;
        }
        if(newQId2==-1){
            showToast("您的第二个问题不能为空");
            return;
        }
        if(newQId3==-1){
            showToast("您的第三个问题不能为空");
            return;
        }
        if(q1.length()==0){
            showToast("您的第一个答案不能为空");
            return;
        }

        if(q2.length()==0){
            showToast("您的第二个答案不能为空");
            return;
        }

        if(q3.length()==0){
            showToast("您的第三个答案不能为空");
            return;
        }
        ubProductService.postAlreadyAnswer(q1, q2, q3, new CommonResultListener(this) {
            @Override
            public void successHandle(Object result) throws JSONException {
                startActivity(ResetQuestionActivity.class);
                finish();
            }
        });
    }

    private void confirmQuestion(){
              String q1=etQ1.getText().toString().trim();
              String q2=etQ2.getText().toString().trim();
              String q3=etQ3.getText().toString().trim();

              int newQId1 = -1;
              int newQId2 = -1;
              int newQId3 = -1;

              if(questionIdMap.containsKey(tvQuestion1)) {
                  newQId1 = questionIdMap.get(tvQuestion1);
              }
              if(questionIdMap.containsKey(tvQuestion2)) {
                  newQId2=questionIdMap.get(tvQuestion2);
              }
              if(questionIdMap.containsKey(tvQuestion3)) {
                  newQId3=questionIdMap.get(tvQuestion3);
              }
              //下拉框判断
              if(newQId1==-1){
                  showToast("您的第一个问题不能为空");
                  return;
              }
              if(newQId2==-1){
                  showToast("您的第二个问题不能为空");
                  return;
              }
              if(newQId3==-1){
                  showToast("您的第三个问题不能为空");
                  return;
              }
              if(q1.length()==0){
                  showToast("您的第一个答案不能为空");
                  return;
              }

              if(q2.length()==0){
                  showToast("您的第二个答案不能为空");
                  return;
              }

              if(q3.length()==0){
                  showToast("您的第三个答案不能为空");
                  return;
              }

              //1表示已经设置过问题
              if(pwdIsSet==1){
                  ubProductService.postAlreadyAnswer(q1, q2, q3, new CommonResultListener(this) {
                     @Override
                     public void successHandle(Object result) throws JSONException {
                         new MyDialog(QuestionActivity.this, MyDialog.DIALOG_TWOOPTION, "回答成功", "重新设置支付密码", new MyDialogOnClick() {
                             @Override
                             public void sureOnClick(View v) {
                                 setPwdNow();
                             }

                             @Override
                             public void cancelOnClick(View v) {
                             }
                         }).show();
                     }
                 });

              }else {
                  ubProductService.postBalancePwd(newQId1, q1, newQId2, q2, newQId3, q3, new CommonResultListener(this) {
                      @Override
                      public void successHandle(Object result) throws JSONException {
                          showToast("提交成功");
                          setPwdNow();
                      }
                  });
              }
          }

          public void postPwd(String num){
              ubProductService.postSetPwd(num, new CommonResultListener(this) {
                  @Override
                  public void successHandle(Object result) throws JSONException {
                      showToast("密码设置成功！");
                      finish();
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
}