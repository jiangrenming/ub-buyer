package com.ninetop.activity.ub.usercenter.myWallet;

import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.ninetop.UB.UbUserCenterService;
import com.ninetop.activity.ub.bean.product.WalletBean;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/19.
 */
public class MyUInfoWalletActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_count_u)
    TextView tvCountU;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.lv_detail_u)
    ListView lvDetailU;
    private MyWalletAdapter adapter;
    private UbUserCenterService ubUserCenterService;
     private List<WalletBean> dataList;
    private TimePickerView timePickerView;
    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_u_detail;
    }


    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("积分明细");
        String uMoney=getIntentValue(IntentExtraKeyConst.U_MONEY);
        tvCountU.setText(uMoney);
        ubUserCenterService = new UbUserCenterService(this);
        dataList = new ArrayList<>();
        adapter=new MyWalletAdapter(this,dataList);
        lvDetailU.setAdapter(adapter);
        Date currentDate = new Date(System.currentTimeMillis());
        tvYear.setText((currentDate.getYear() + 1900) + "年");
        tvMonth.setText((currentDate.getMonth() + 1) + "月");
         getServerData();
    }


    protected void getServerData(){
        ubUserCenterService.getUbRecord(getSelectTime(), new CommonResultListener<List<WalletBean>>(this) {
            @Override
            public void successHandle(List<WalletBean> result) throws JSONException {
                dataList.clear();
                dataList.addAll(result);
                adapter.notifyDataSetChanged();

            }
        });
    }
    private String getSelectTime(){
        String yearStr = tvYear.getText().toString();
        yearStr = yearStr.substring(0, yearStr.length() -1);
        String monthStr = tvMonth.getText().toString();
        monthStr = monthStr.substring(0, monthStr.length() -1);
     //   showToast(yearStr + "-" + monthStr);
        return yearStr + "-" + monthStr;
    }

    @OnClick(R.id.ll_select_time)
    public void onViewClicked() {
        if(timePickerView == null) {
            createDate();
        }
        if (!timePickerView.isShowing()) {
            timePickerView.show();
        }
    }

    private void  createDate() {
        timePickerView = new TimePickerView(MyUInfoWalletActivity.this, TimePickerView.Type.YEAR_MONTH);
        timePickerView.setCyclic(true);
        timePickerView.setTime(new java.sql.Date(System.currentTimeMillis()));

        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                String year = format.format(date).substring(0, 4);
                String month = format.format(date).substring(5, 7);
                tvYear.setText(year + "年");
                tvMonth.setText(month + "月");
//                String lastTime=year+ "-" + month;
//               showToast(lastTime);
                getServerData();
                //  showToast(time);
            }

        });
    }


}
