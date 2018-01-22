package com.ninetop.activity.index;

import android.widget.ListView;

import com.ninetop.adatper.index.MessageItemAdapter;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.index.message.MessagePagingBean;
import com.ninetop.common.constant.UrlConstant;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.IndexService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by jill on 2016/11/14.
 */

public class MessageOrderActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.lv_message_list)
    ListView lvMessageList;

    private IndexService indexService;

    protected MessageItemAdapter messageItemAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_order;
    }

    @Override
    protected void initData() {
        super.initData();

        hvHead.setTitle(getMessageTitle());

        indexService = new IndexService(this);

        getServerData();
    }

    protected void getServerData() {
        indexService.getMessageList(getUrl(), new CommonResultListener<MessagePagingBean>(this) {
            @Override
            public void successHandle(MessagePagingBean result) throws JSONException {
                getServerDataHandle(result);
            }
        });
    }

    private void getServerDataHandle(MessagePagingBean result) {
        messageItemAdapter = new MessageItemAdapter(this, result.dataList);
        lvMessageList.setAdapter(messageItemAdapter);
    }

    protected String getUrl() {
        return UrlConstant.MESSAGE_ORDER_LIST;
    }

    protected String getMessageTitle() {
        return "订单通知";
    }
}
