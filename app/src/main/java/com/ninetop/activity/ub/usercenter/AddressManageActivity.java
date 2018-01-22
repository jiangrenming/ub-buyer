package com.ninetop.activity.ub.usercenter;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ninetop.UB.UbAddressBean;
import com.ninetop.UB.UbAddressManagerAdapter;
import com.ninetop.UB.UbUserCenterService;
import com.ninetop.activity.user.AddAddressActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.SharedKeyConstant;
import com.ninetop.common.util.MySharedPreference;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/*
 _ooOoo_
 o8888888o
 88" . "88
 (| -_- |)
 O\  =  /O
 ____/`---'\____
 .'  \\|     |//  `.
 /  \\|||  :  |||//  \
 /  _||||| -:- |||||-  \
 |   | \\\  -  /// |   |
 | \_|  ''\---/''  |   |
 \  .-\__  `-`  ___/-. /
 ___`. .'  /--.--\  `. . __
 ."" '<  `.___\_<|>_/___.'  >'"".
 | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 \  \ `-.   \_ __\ /__ _/   .-` /  /
 ======`-.____`-.___\_____/___.-`____.-'======
 `=---='
 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 佛祖保佑       永无BUG
 * Created by huangjinding on 2017/5/14.
 */
public class AddressManageActivity extends BaseActivity{

    @BindView(R.id.head_view)
    HeadView headView;
    @BindView(R.id.lv_address_manage)
    ListView lvAddressManage;
    private List<UbAddressBean> result;
    private UbUserCenterService ubUserCenterService;
    private UbAddressManagerAdapter addressManagerAdapter;
   // private OnAdapterItemDeleteOrEdit onAdapterItemDeleteOrEdit;
    public AddressManageActivity(){
        ubUserCenterService=new UbUserCenterService(this);
    }
    @Override
    protected int getLayoutId(){
        return R.layout.activity_address_manage;
    }
    @Override
    protected void initView(){
        super.initView();
        initTitle();
    }
    @Override
    protected void onResume(){
        super.onResume();
        getData();
    }

    private void getData(){
        ubUserCenterService.getAddressList(new CommonResultListener<List<UbAddressBean>>(this) {
            @Override
            public void successHandle(List<UbAddressBean> result) throws JSONException {
                setAdapter(result);
            }
        });
    }
    private void setAdapter(List<UbAddressBean> result){
       this.result=result;
        addressManagerAdapter=new UbAddressManagerAdapter(result,this);
        lvAddressManage.setAdapter(addressManagerAdapter);
        addressManagerAdapter.setOnAdapterItemDeleteOrEdit(new MyListener());
        addItemSelectedHandleIfNeed();
    }
    private void addItemSelectedHandleIfNeed(){
        String canSelect=getIntentValue(IntentExtraKeyConst.CAN_SELECT);
        if("1".equals(canSelect)){
            lvAddressManage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    UbAddressBean bean=result.get(position);
                    Intent intent=new Intent();
                    Gson gson=new Gson();
                    intent.putExtra(IntentExtraKeyConst.JSON_DATA,gson.toJson(bean));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }

    }

    private class MyListener implements UbAddressManagerAdapter.OnAdapterItemDeleteOrEdit{
        @Override
        public void delete(int position){
            deleteAddress(position);
        }
        @Override
        public void editor(int position){
            startActivity(position);
        }
    }

    private void startActivity(int position){
        UbAddressBean bean=result.get(position);
        Intent intent=new Intent(AddressManageActivity.this,AddAddressActivity.class);
        intent.putExtra(IntentExtraKeyConst.ADDRESSBEN,bean);
        startActivity(intent);
    }
    private void deleteAddress(int position){
        if(result==null){
            return;
        }
        UbAddressBean bean=result.get(position);
        if(bean!=null){
            String index=bean.id;
            String token= MySharedPreference.get(SharedKeyConstant.TOKEN,"",this);
            requestDelete(index,token,position);
        }
    }
        private void requestDelete(String index,String token,final int position){
            ubUserCenterService.deleteAddress(index, new CommonResultListener<String>(this) {
                @Override
                public void successHandle(String result) throws JSONException {
                    flushUi(position);
                }
            });
        }
        private void flushUi(final int position){
            if(result!=null){
                result.remove(position);
                addressManagerAdapter.notifyDataSetChanged();
            }
        }
         private void initTitle(){
             headView.setTitle("地址管理");
             headView.setSearchImageVisible(View.GONE);
             headView.setBackClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     finish();
                 }
             });
         }
         @OnClick(R.id.button)
         public void onClick(){
             if(result!=null&&result.size()>=10){
                 showToast("地址最多只能添加10个");
             }
             startActivity(AddAddressActivity.class);
         }
//    public interface OnAdapterItemDeleteOrEdit{
//        void delete(int position);
//        void edit(int position);
//    }
//    public void setAdapaterItemDeleteOrEdit(OnAdapterItemDeleteOrEdit onAdapterItemDeleteOrEdit){
//        this.onAdapterItemDeleteOrEdit=onAdapterItemDeleteOrEdit;
//    }
}
