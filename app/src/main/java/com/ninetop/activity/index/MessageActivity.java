package com.ninetop.activity.index;

import android.view.View;
import android.widget.TextView;

import youbao.shopping.R;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.index.message.MessageDetailBean;
import com.ninetop.bean.index.message.MessageListBean;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.IndexService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jill on 2016/11/14.
 */

public class MessageActivity extends BaseActivity {


    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_notice_time)
    TextView tvNoticeTime;
    @BindView(R.id.tv_notice_title)
    TextView tvNoticeTitle;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    private IndexService indexService;

    @Override
    protected void initData() {
        super.initData();
        hvHead.setTitle("消息中心");

        indexService = new IndexService(this);

        getServerData();
    }

    private void getServerData(){
        indexService.getMessage(new CommonResultListener<MessageListBean>(this) {
            @Override
            public void successHandle(MessageListBean result) throws JSONException {
                if(result == null)
                    return;

                MessageDetailBean noticeBean = result.noticeMsg;
                if(noticeBean != null) {
                    tvNoticeTime.setText(noticeBean.time);
                    tvNoticeTitle.setText(noticeBean.content);
                }
                MessageDetailBean orderBean = result.orderMsg;
                if(orderBean != null){
                    tvOrderTime.setText(orderBean.time);
                    tvOrderTitle.setText(orderBean.content);
                }
            }
        });
    }

    @OnClick({R.id.ll_message_notice, R.id.ll_order_notice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_message_notice:
                startActivity(MessageNoticeActivity.class);
                break;
            case R.id.ll_order_notice:
                startActivity(MessageOrderActivity.class);
                break;
        }
    }
}
