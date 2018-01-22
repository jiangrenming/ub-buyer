package com.ninetop.activity.user;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ninetop.base.BaseActivity;
import com.ninetop.common.util.ToastUtils;
import com.ninetop.common.view.HeadWordView;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * @date: 2016/11/11
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class CommentFeedbackActivity extends BaseActivity {
    @BindView(R.id.et_feedback_input)
    EditText etFeedbackInput;
    @BindView(R.id.tv_feedback_word_count)
    TextView tvFeedbackWordCount;
    @BindView(R.id.hv_title)
    HeadWordView hvTitle;
    private UserCenterService service;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment_feedback;
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvTitle.setTitle("意见反馈");
        hvTitle.setDetails("提交");
        hvTitle.setJumpToClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etFeedbackInput.getText().toString().trim())) {
                    ToastUtils.showCenter(CommentFeedbackActivity.this, "请留下您的宝贵意见！");
                    return;
                }

                submitFeedback();

            }
        });

    }

    @Override
    protected void initListener() {
        super.initListener();
        etFeedbackInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = etFeedbackInput.getText().length();
                tvFeedbackWordCount.setText(500 - length + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        service = new UserCenterService(this);
    }

    private void submitFeedback() {
        String feedback = etFeedbackInput.getText().toString().trim();
        service.postFeedBack(feedback, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                ToastUtils.showCenter(CommentFeedbackActivity.this, "提交成功,感谢您的反馈!");
                finish();
            }
        });
    }
}
