package youbao.shopping.ninetop.activity.ub.usercenter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import youbao.shopping.ninetop.UB.order.HotCityBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.bean.address.AddressBean;
import youbao.shopping.ninetop.bean.address.PickViewCityBean;
import youbao.shopping.ninetop.bean.address.PickerViewData;
import youbao.shopping.ninetop.bean.address.ProvinceBean;
import youbao.shopping.ninetop.common.ActivityActionHelper;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.IntentExtraValueConst;
import youbao.shopping.ninetop.common.constant.SharedKeyConstant;
import youbao.shopping.ninetop.common.util.MySharedPreference;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.R.id.hv_head;

/**
 * Created by huangjinding on 2017/4/18.
 */
public class SelectedCityActivity extends BaseActivity {
    @BindView(hv_head)
    HeadView hvHead;
    @BindView(R.id.et_selectcity)
    EditText etSelectCity;
    @BindView(R.id.tv_city)
    TextView tvCityDingWei;
    @BindView(R.id.tv_city_name)
    TextView tvCityNameNow;
    @BindView(R.id.ll_search_history_city)
    LinearLayout llSearchHistory;
    @BindView(R.id.gv_city_hot)
    GridView gvCityHot;
    @BindView(R.id.tv_currentcity)
    TextView tvCurrentcity;

    private UbProductService ubProductService;

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    //2级
    private ArrayList<ArrayList<PickViewCityBean>> options2Items = new ArrayList<>();
    //3级
    private ArrayList<ArrayList<ArrayList<PickerViewData>>> options3Items = new ArrayList<>();

    private List<HotCityBean> dataList;
    private CityAdapter cityAdapter;

    public SelectedCityActivity() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_selectcity;
    }

    protected void initView() {
        super.initView();
        ubProductService = new UbProductService(this);
        hvHead.setTitle("城市选择");
        getHotCity();
        String city = etSelectCity.getText().toString().trim();
        changeSearchTag(city);
        initHistoryTag();
        dataList = new ArrayList<>();
        cityAdapter = new CityAdapter(SelectedCityActivity.this, dataList);
        gvCityHot.setAdapter(cityAdapter);
        getCityName();
    }

    private void getCityName() {
        String currentCity = MySharedPreference.get(SharedKeyConstant.CITY_NAME, "", SelectedCityActivity.this);
        if ("".equals(currentCity)) {
            //定位失败默认设置杭州市
            tvCityNameNow.setText("杭州市");
        } else {
            tvCityNameNow.setText(currentCity);
        }
        tvCurrentcity.setText(currentCity);
    }

    private String getSharedPreferenceKey() {
        String value = getIntentValue(IntentExtraKeyConst.ACTIVITY_FROM);
        if (IntentExtraValueConst.ACTIVITY_FROM_UNIONSHOP.equals(value)) {
            return SharedKeyConstant.SEARCH_CITY_UNION_SHOP;
        } else {
            return SharedKeyConstant.SEARCH_CITY;
        }
    }

    private void initHistoryTag() {
        List<String> historyList = MySharedPreference.getList(getSharedPreferenceKey(), null, this);
        llSearchHistory.removeAllViews();
        if (historyList == null || historyList.size() == 0) {
            return;
        }
        for (final String str : historyList) {
            TextView textView = new TextView(this);
            textView.setText(str);
            textView.setBackgroundResource(R.drawable.bg_radius_gray);
            textView.setTextColor(Tools.getColorValueByResId(this, R.color.text_gray));
            textView.setTextSize(14);
            textView.setPadding(Tools.dip2px(this, 10), Tools.dip2px(this, 5), Tools.dip2px(this, 10), Tools.dip2px(this, 5));
            textView.setSingleLine(true);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);  // , 1是可选写的
            lp.setMargins(0, 0, 20, 0);
            textView.setLayoutParams(lp);

            llSearchHistory.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeSearchTag(str);
                    goMainActivity();
                }
            });
        }
    }

    private void getHotCity() {
        ubProductService.getHotCity(new CommonResultListener<List<HotCityBean>>(this) {
            @Override
            public void successHandle(List<HotCityBean> result) throws JSONException {
                dataList.addAll(result);
                cityAdapter.notifyDataSetChanged();
            }
        });
    }

    private void changeSearchTag(String searchText) {
        if (searchText.length() == 0) {
            return;
        }

        selectSelectCity(searchText);
        MySharedPreference.saveListAddOneValue(getSharedPreferenceKey(), searchText, SelectedCityActivity.this);
    }

    private void selectSelectCity(String city) {
        String value = getIntentValue(IntentExtraKeyConst.ACTIVITY_FROM);
        if (IntentExtraValueConst.ACTIVITY_FROM_UNIONSHOP.equals(value)) {
            GlobalInfo.unionshopSelectCity = city;
        } else {
            GlobalInfo.ubSelectCity = city;
            GlobalInfo.franchiseeId = "1";
        }
    }

    private void goMainActivity() {
        String value = getIntentValue(IntentExtraKeyConst.ACTIVITY_FROM);
        if (IntentExtraValueConst.ACTIVITY_FROM_UNIONSHOP.equals(value)) {
            ActivityActionHelper.goToMain(this, 2);
        } else {
            ActivityActionHelper.goToMain(this);
        }
    }

    @OnClick({R.id.et_selectcity, R.id.btn_city_more,
            R.id.btn_select_city, R.id.tv_city
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select_city:
                String cityName = etSelectCity.getText().toString().trim();
                if ("".equals(cityName)) {
                    showToast("搜索为空");
                    return;

                }
                changeSearchTag(cityName);
                goMainActivity();
                break;
            case R.id.btn_city_more:
                Tools.hideInput(this, getWindow().peekDecorView());
                String addressJson = Tools.getAddressJson(SelectedCityActivity.this);
                if (!TextUtils.isEmpty(addressJson)) {
                    jxJson(addressJson);
                }
                setOption();
                break;
            case R.id.tv_city_name:
                String cityNameLocal = MySharedPreference.get(SharedKeyConstant.CITY_NAME, "", SelectedCityActivity.this);
                if ("".equals(cityNameLocal)) {
                    tvCityDingWei.setText("杭州市");
                } else {
                    tvCityDingWei.setText(cityNameLocal);
                }
                changeSearchTag(tvCityNameNow.getText().toString());
                goMainActivity();
                break;
        }
    }

    //定位与城市选择
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
        //pwOptions.setLabels("省", "市", "区");
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
                etSelectCity.setText(tx.endsWith(",") ? new StringBuilder(tx).deleteCharAt(tx.length() - 1).toString() : tx);
                int type = 0;
                String lastCounty = null;
                String country;
                String one = options1Items.get(options1).getPickerViewText();
                String two = options2Items.get(options1).get(option2).getPickerViewText();
                String three = options3Items.get(options1).get(option2).get(options3).getPickerViewText().trim();
                if (two.endsWith("市辖区")) {
                    type = 1;
                }
                if (two.endsWith("省直辖县级")) {
                    type = 2;
                }
                if (!two.endsWith("市辖区") && !two.endsWith("省直辖县级")) {
                    type = 3;
                }
                switch (type) {
                    case 1:
                        if (two.endsWith("市辖区")) {
                            lastCounty = one;
                        } else if (three.endsWith("市辖区") || three.length() == 0) {
                            lastCounty = two;
                        } else if (two.endsWith("市辖区")) {
                            lastCounty = one;
                        } else {
                            lastCounty = three;
                        }
                        break;
                    case 2:
                        if (two.endsWith("省直辖县级")) {
                            lastCounty = three;
                            MySharedPreference.save(SharedKeyConstant.DISTRICT, lastCounty, SelectedCityActivity.this);
                            country = two;
                            MySharedPreference.save(SharedKeyConstant.CITY_NAME, country, SelectedCityActivity.this);
                        }
                        break;
                    case 3:
                        if ("".equals(three)) {
                            lastCounty = one;
                            MySharedPreference.save(SharedKeyConstant.DISTRICT, lastCounty, SelectedCityActivity.this);
                            country = two;
                            MySharedPreference.save(SharedKeyConstant.CITY_NAME, country, SelectedCityActivity.this);
                        } else {
                            if (three.endsWith("市辖区")) {
                                lastCounty = two;
                                MySharedPreference.save(SharedKeyConstant.CITY_NAME, lastCounty, SelectedCityActivity.this);
                                country = "";
                                MySharedPreference.save(SharedKeyConstant.DISTRICT, country, SelectedCityActivity.this);
                            }
                            if (!three.endsWith("市辖区")) {
                                lastCounty = two;
                                MySharedPreference.save(SharedKeyConstant.CITY_NAME, lastCounty, SelectedCityActivity.this);
                                country = three;
                                MySharedPreference.save(SharedKeyConstant.DISTRICT, country, SelectedCityActivity.this);
                            }
                        }
                        break;
                }
                changeSearchTag(lastCounty);
                goMainActivity();
            }
        });
    }

    private class CityAdapter extends BaseAdapter {
        private SelectedCityActivity activity;
        private List<HotCityBean> dataList;

        CityAdapter(SelectedCityActivity activity, List<HotCityBean> dataList) {
            this.activity = activity;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridCityHolder gridCityHolder;
            if (convertView == null) {
                gridCityHolder = new GridCityHolder();
                convertView = LayoutInflater.from(activity).inflate(R.layout.ub_item_hot_city, parent, false);
                gridCityHolder.cityName = (TextView) convertView.findViewById(R.id.tv_text);
                convertView.setTag(gridCityHolder);
            } else {
                gridCityHolder = (GridCityHolder) convertView.getTag();
            }
            final HotCityBean bean = dataList.get(position);
            gridCityHolder.cityName.setText(bean.name);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeSearchTag(bean.getName());
                    goMainActivity();
                }
            });

            return convertView;
        }
        class GridCityHolder {
            TextView cityName;
        }

    }
}