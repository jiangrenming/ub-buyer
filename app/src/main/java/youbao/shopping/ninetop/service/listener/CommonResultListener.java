package youbao.shopping.ninetop.service.listener;

import youbao.shopping.ninetop.base.Viewable;
import okhttp3.Response;

/**
 * Created by jill on 2016/11/22.
 */

public abstract class CommonResultListener<T> implements ShowableResultListener<T> {

    protected Viewable context;

    public CommonResultListener(Viewable context){
        this.context = context;
    }

    public void failHandle(String code, String message) {
        if(message == null || message.length() == 0 || "null".equals(message))
            return;

        if(isShowToast())
            context.showToast(message);
    }

    public void errorHandle(Response response, Exception e) {
        e.printStackTrace();
        if(isShowLoading())
            context.removeLoading();

//        if(isShowToast())
//            context.showToast("服务器出现异常");
    }

    @Override
    public boolean isShowLoading() {
        return true;
    }

    @Override
    public boolean isShowToast() {
        return true;
    }
}
