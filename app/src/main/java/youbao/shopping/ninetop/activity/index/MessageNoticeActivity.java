package youbao.shopping.ninetop.activity.index;

import youbao.shopping.ninetop.common.constant.UrlConstant;

/**
 * Created by jill on 2016/11/14.
 */

public class MessageNoticeActivity extends MessageOrderActivity {

    protected String getUrl(){
        return UrlConstant.MESSAGE_NOTICE_LIST;
    }

    protected String getMessageTitle(){
        return "消息通知";
    }
}
