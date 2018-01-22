package com.ninetop.activity.user;
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
 * Created by Administrator on 2016/11/15.
 */


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.ninetop.UB.UbAddressBean;
import com.ninetop.UB.UbUserCenterService;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.address.AddressBean;
import com.ninetop.bean.address.PickViewCityBean;
import com.ninetop.bean.address.PickerViewData;
import com.ninetop.bean.address.ProvinceBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.util.Tools;
import com.ninetop.common.util.ValidateUtil;
import com.ninetop.service.listener.CommonResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

public class AddAddressActivity extends BaseActivity {

    private final UbUserCenterService userCenterService;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_number)
    EditText userNumber;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.user_address_details)
    EditText userAddressDetails;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    String add_province = null;
    String add_city = null;
    String add_country = null;
    private boolean is_check = false;
    private String isDefault = "0";
    //1级
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    //2级
    private ArrayList<ArrayList<PickViewCityBean>> options2Items = new ArrayList<>();
    //3级
    private ArrayList<ArrayList<ArrayList<PickerViewData>>> options3Items = new ArrayList<>();

    private boolean IS_EDIT = false;
    private String index;
    private UbAddressBean addressBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    public AddAddressActivity() {
        userCenterService = new UbUserCenterService(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        tvTitle.setText("添加新地址");
        addressBean = (UbAddressBean) getIntent()
                .getSerializableExtra(IntentExtraKeyConst.ADDRESSBEN);
        if (addressBean != null) {
            IS_EDIT = true;
            tvTitle.setText("修改地址");
            index = addressBean.id;
            userName.setText(addressBean.name);
            userNumber.setText(addressBean.mobile);
            userAddress.setText(TextUtils.isEmpty(addressBean.addr_county) ? addressBean.addr_province + "," + addressBean.addr_city : addressBean.addr_province + "," + addressBean.addr_city + "," + addressBean.addr_county);

            userAddressDetails.setText(addressBean.addr_detail);
            if (addressBean.is_default.equals("1")) {
                is_check = true;
            } else {
                is_check = false;
            }
            ivSelect.setSelected(is_check);
        }

    }

    @OnClick({R.id.ll_back, R.id.tv_save, R.id.user_address, R.id.iv_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_save:
                //保存
                save();
                break;
            case R.id.user_address:
                //地址
                Tools.hideInput(this, getWindow().peekDecorView());
                String addressJson = Tools.getAddressJson(AddAddressActivity.this);
                if (!TextUtils.isEmpty(addressJson)) {
                    jxJson(addressJson);
                }
                setOption();
                break;
            case R.id.iv_select:
                //设为默认
                is_check = !is_check;
                ivSelect.setSelected(is_check);
                if (is_check) {
                    isDefault = "1";
                } else {
                    isDefault = "0";
                }
                break;
        }
    }


    @Override
    protected void initData() {
        super.initData();
    }

    private void save() {
        String userN = userName.getText().toString().trim();
        String userNum = userNumber.getText().toString().trim();
        String userAdd = userAddress.getText().toString().trim();
        String userAd = userAddressDetails.getText().toString().trim();
        intended(userN, userNum, userAdd, userAd);
    }

    private void addAddress(String userN, String userNum, String add_province, String add_city, String add_country, String userAd) {
        userCenterService.addAddress(userN, userNum, add_province, add_city, add_country, userAd,isDefault, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                finish();
            }
        });
    }
    private void editAddress(String index,String userN, String userNum, String add_province, String add_city, String add_country, String userAd) {

        userCenterService.editAddress(index,userN, userNum, add_province, add_city, add_country, userAd,isDefault, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                finish();
            }
        });
    }
    private void intended(String userN, String userNum, String userAdd, String userAd) {
        if (TextUtils.isEmpty(userN)) {
            showToast("请输入您的姓名");
            return;
        }
        if (TextUtils.isEmpty(userNum)) {
            showToast("请输入您的手机号码");
            return;
        } else {
            if (!ValidateUtil.isMobilePhone(userNum)) {
                showToast("手机号码格式不正确");
                return;
            }
        }
        if (TextUtils.isEmpty(userAdd)) {
            showToast("请选择省、市、县");
            return;
        }
        if (TextUtils.isEmpty(userAd)) {
            showToast("请填写详情地址");
            return;
        }
        if(add_province==null||add_city==null||add_country==null){
            add_province=addressBean.addr_province;
            add_city=addressBean.addr_city;
            add_country=addressBean.addr_county;
        }

        if (!IS_EDIT) {
            addAddress(userN, userNum, add_province, add_city, add_country, userAd);
        } else {
            editAddress(index,userN, userNum,add_province, add_city, add_country, userAd);
        }
    }



    private void jxJson(String trim) {
        Gson gson = new Gson();
        AddressBean addressBean = gson.fromJson(trim, AddressBean.class);
        //解析bean
        preData(addressBean);
    }

    private void preData(AddressBean addressBean) {
        //    得到所有地址列表
        List<AddressBean.CityListBean> cityList = addressBean.cityList;
        if (cityList != null && cityList.size() > 0) {
            for (AddressBean.CityListBean cityListBean : cityList) {
                String p = cityListBean.p;
                if (p.length() > 5) {
                    p = p.substring(0, 5);
                }
                ArrayList<ArrayList<PickerViewData>> options3Items_02 = new ArrayList<>();
                ArrayList<PickViewCityBean> city = new ArrayList<>();
                List<AddressBean.CityListBean.CBean> c = cityListBean.c;
                if (c != null && c.size() > 0) {
                    for (int i = 0; i < c.size(); i++) {
                        ArrayList<PickerViewData> options3Items_01 = new ArrayList<>();
                        AddressBean.CityListBean.CBean cc = c.get(i);
                        String n = cc.n;
                        if (n.length() > 5) {
                            n = n.substring(0, 5);
                        }
                        city.add(new PickViewCityBean(n));
                        List<AddressBean.CityListBean.CBean.ABean> a = cc.a;
                        if (a != null && a.size() > 0) {
                            for (int j = 0; j < a.size(); j++) {
                                AddressBean.CityListBean.CBean.ABean aa = a.get(j);
                                String s = aa.s;
                                if (s.length() > 5) {
                                    s = s.substring(0, 5);
                                }
                                options3Items_01.add(new PickerViewData(s));
                            }
                        } else {
                            options3Items_01.add(new PickerViewData(""));
                        }
                        options3Items_02.add(options3Items_01);
                    }
                    //一次循环
                    options1Items.add(new ProvinceBean(p));
                    options2Items.add(city);
                    options3Items.add(options3Items_02);
                }
            }
        }
    }

    private void setOption() {
        OptionsPickerView pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        //        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        //        pvOptions.setSelectOptions(1, 1, 1);
        if (!pvOptions.isShowing()) {
            pvOptions.show();
        }


        pvOptions.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String tx = options1Items.get(options1).getPickerViewText() + ","
                        + options2Items.get(options1).get(option2).getPickerViewText() + ","
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText().trim();
                add_province = options1Items.get(options1).getPickerViewText();
                add_city = options2Items.get(options1).get(option2).getPickerViewText();
                add_country = options3Items.get(options1).get(option2).get(options3).getPickerViewText().trim();
                userAddress.setText(tx.endsWith(",") ? new StringBuilder(tx).deleteCharAt(tx.length() - 1).toString() : tx);
            }
        });
    }

}
