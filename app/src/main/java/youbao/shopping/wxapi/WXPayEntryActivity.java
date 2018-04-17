package youbao.shopping.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.utils.Log;

import youbao.shopping.R;
import youbao.shopping.ninetop.activity.ub.product.route.util.ToastUtil;
import youbao.shopping.ninetop.activity.ub.seller.SellerPayActivity;
import youbao.shopping.ninetop.activity.user.AddressManagerActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;

/**
 *
 * @author jiangrenming
 * @date 2018/1/13
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxf6b408256021748a");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {}

//    int ERR_OK = 0;
//    int ERR_COMM = -1;
//    int ERR_USER_CANCEL = -2;
//    int ERR_SENT_FAILED = -3;
//    int ERR_AUTH_DENIED = -4;
//    int ERR_UNSUPPORT = -5;
//    int ERR_BAN = -6;
    @Override
    public void onResp(BaseResp baseResp) {
        Log.d(TAG, "onPayFinish, errCode = " + baseResp.errCode);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("提示");
//            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(baseResp.errCode)));
//            builder.show();
            int code = baseResp.errCode;

            switch(code)
            {
                case 0:
                    finish();
//                    Intent intent = new Intent(this, SellerPayActivity.class);
//                    startActivity(intent);
                    ToastUtil.show(this,"支付成功!");
                    break;
                case -1:
                    finish();
                    ToastUtil.show(this,"ERR_COMM!");
                    break;
                case -2:
                    finish();
                    ToastUtil.show(this,"支付取消!");
                    break;
                case -3:
                    finish();
                    ToastUtil.show(this,"ERR_SENT_FAILED!");
                    break;
                case -4:
                    finish();
                    ToastUtil.show(this,"ERR_AUTH_DENIED!");
                    break;
                case -5:
                    finish();
                    ToastUtil.show(this,"ERR_UNSUPPORT!");
                    break;
                case -6:
                    finish();
                    ToastUtil.show(this,"ERR_BAN!");
                    break;
            }
        }
    }
}
