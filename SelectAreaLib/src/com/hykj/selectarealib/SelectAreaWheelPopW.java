package com.hykj.selectarealib;

import java.util.ArrayList;
import java.util.List;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import com.hykj.selectarealib.bean.City;
import com.hykj.selectarealib.bean.Province;
import com.hykj.selectarealib.bean.Region;
import com.hykj.selectarealib.utils.GetCity;
import com.hykj.selectarealib.utils.GetProvince;
import com.hykj.selectarealib.utils.GetRegion;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

public class SelectAreaWheelPopW {

	public static int NOREGION = -999;
	private int type = 0;

	private int provinceId = -1;
	private int cityId = -1;
	private int regionId = -1;
	private String provinceName = "";
	private String cityName = "";
	private String regionName = "";
	private WheelView wheel1;
	private WheelView wheel2;
	private WheelView wheel3;
	private List<Province> provinceList = new ArrayList<Province>();
	private List<City> cityList = new ArrayList<City>();
	private List<City> cityTempList = new ArrayList<City>();
	private List<Region> regionList = new ArrayList<Region>();
	private List<Region> regionTempList = new ArrayList<Region>();
	private String[] provinceArray;
	private String[] cityArray;
	private String[] regionArray;

	Activity activity;
	PopupWindow popW;
	SelectAreaWheelPopWOnClick onClick;

	/**
	 * 初始化
	 * 
	 * @param activity
	 */
	public void getInstance(Activity activity) {
		this.activity = activity;
		provinceList = new GetProvince().getProvinces(activity);
		cityList = new GetCity().getCity(activity);
		regionList = new GetRegion().getRegion(activity);
	}

	/**
	 * 显示窗口
	 * 
	 * @param v
	 * @param onClick
	 * @return
	 */
	public PopupWindow showPopw(View v, SelectAreaWheelPopWOnClick onClick) {
		this.onClick = onClick;
		if (popW == null) {
			initPopw();
		}
		popW.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		return popW;
	}

	/**
	 * 显示窗口
	 * 
	 * @param v
	 * @param onClick
	 * @return
	 */
	public PopupWindow showPopw(View v, int type,
			SelectAreaWheelPopWOnClick onClick) {
		this.onClick = onClick;
		this.type = type;
		if (popW == null) {
			initPopw();
		}
		popW.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		return popW;
	}

	@SuppressLint({ "InlinedApi", "InflateParams" })
	@SuppressWarnings("deprecation")
	private void initPopw() {
		LayoutInflater lf = LayoutInflater.from(activity);
		View v = lf.inflate(R.layout.popw_select_area, null);
		popW = new PopupWindow(v, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		popW.setFocusable(true);
		popW.setBackgroundDrawable(new BitmapDrawable());
		wheel1 = (WheelView) v.findViewById(R.id.wheel1);
		wheel2 = (WheelView) v.findViewById(R.id.wheel2);
		wheel3 = (WheelView) v.findViewById(R.id.wheel3);
		if (type == NOREGION) { // 不要区的显示
			wheel3.setVisibility(View.GONE);
		} else {
			wheel3.setVisibility(View.VISIBLE);
		}
		wheel1.addChangingListener(new wheelListener());
		wheel2.addChangingListener(new wheelListener());
		wheel3.addChangingListener(new wheelListener());
		Button no = (Button) v.findViewById(R.id.no);
		Button yes = (Button) v.findViewById(R.id.yes);
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popW.dismiss();
				onClick.cancelOnClick();
			}
		});
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popW.dismiss();
				onClick.sureOnClick(provinceId, cityId, regionId, provinceName,
						cityName, regionName);
			}
		});
		/**
		 * ʡ��ѡȡ
		 */
		provinceArray = new String[provinceList.size()];
		int i = 0;
		for (Province a : provinceList) {
			if (a.getProvincename() != null) {
				provinceArray[i] = a.getProvincename();
			} else {
				provinceArray[i] = " ";
			}
			i++;
		}
		provinceName = provinceList.get(0).getProvincename();
		provinceId = provinceList.get(0).getProvinceid();
		// ��ʼ������
		wheel1.setViewAdapter(new ArrayWheelAdapter<String>(activity,
				provinceArray));
		wheel1.setCurrentItem(0); // ���õ�һ��);
		wheel1.setVisibleItems(7); // ���ÿɼ�����
		/**
		 * �е�ѡȡ
		 */
		for (City a : cityList) {
			if (a.getParentid() == provinceId) {
				cityTempList.add(a);
			}
		}
		int j = 0;
		cityArray = new String[cityTempList.size()];
		for (City a : cityTempList) {
			if (a.getCityname() != null) {
				cityArray[j] = a.getCityname();
			} else {
				cityArray[j] = " ";
			}
			j++;
		}
		cityName = cityTempList.get(0).getCityname();
		cityId = cityTempList.get(0).getCityid();
		// ��ʼ������
		wheel2.setViewAdapter(new ArrayWheelAdapter<String>(activity, cityArray));
		wheel2.setCurrentItem(0); // ���õ�һ��);
		wheel2.setVisibleItems(7); // ���ÿɼ�����
		/**
		 * ����ѡȡ
		 */
		for (Region a : regionList) {
			if (a.getParentid() == cityId) {
				regionTempList.add(a);
			}
		}
		int k = 0;
		regionArray = new String[regionTempList.size()];
		for (Region a : regionTempList) {
			if (a.getRegionname() != null) {
				regionArray[k] = a.getRegionname();
			} else {
				regionArray[k] = " ";
			}
			k++;
		}
		regionName = regionTempList.get(0).getRegionname();
		regionId = regionTempList.get(0).getRegionid();
		// ��ʼ������
		wheel3.setViewAdapter(new ArrayWheelAdapter<String>(activity,
				regionArray));
		wheel3.setCurrentItem(0); // ���õ�һ��);
		wheel3.setVisibleItems(7); // ���ÿɼ�����
	}

	class wheelListener implements OnWheelChangedListener {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if (wheel == wheel1) {
				provinceName = provinceList.get(newValue).getProvincename();
				provinceId = provinceList.get(newValue).getProvinceid();
				/**
				 * �е�ѡȡ
				 */
				cityTempList.clear();
				for (City a : cityList) {
					if (a.getParentid() == provinceId) {
						cityTempList.add(a);
					}
				}
				int j = 0;
				cityArray = new String[cityTempList.size()];
				for (City a : cityTempList) {
					if (a.getCityname() != null) {
						cityArray[j] = a.getCityname();
					} else {
						cityArray[j] = " ";
					}
					j++;
				}
				cityName = cityTempList.get(0).getCityname();
				cityId = cityTempList.get(0).getCityid();
				// ��ʼ������
				wheel2.setViewAdapter(new ArrayWheelAdapter<String>(activity,
						cityArray));
				/**
				 * ����ѡȡ
				 */
				regionTempList.clear();
				for (Region a : regionList) {
					if (a.getParentid() == cityId) {
						regionTempList.add(a);
					}
				}
				int k = 0;
				regionArray = new String[regionTempList.size()];
				for (Region a : regionTempList) {
					if (a.getRegionname() != null) {
						regionArray[k] = a.getRegionname();
					} else {
						regionArray[k] = " ";
					}
					k++;
				}
				regionName = regionTempList.get(0).getRegionname();
				regionId = regionTempList.get(0).getRegionid();
				// ��ʼ������
				wheel3.setViewAdapter(new ArrayWheelAdapter<String>(activity,
						regionArray));

			} else if (wheel == wheel2) {
				cityName = cityTempList.get(newValue).getCityname();
				cityId = cityTempList.get(newValue).getCityid();
				/**
				 * ����ѡȡ
				 */
				regionTempList.clear();
				for (Region a : regionList) {
					if (a.getParentid() == cityId) {
						regionTempList.add(a);
					}
				}
				int k = 0;
				regionArray = new String[regionTempList.size()];
				for (Region a : regionTempList) {
					if (a.getRegionname() != null) {
						regionArray[k] = a.getRegionname();
					} else {
						regionArray[k] = " ";
					}
					k++;
				}
				if (regionTempList.size() > 0) {
					regionName = regionTempList.get(0).getRegionname();
					regionId = regionTempList.get(0).getRegionid();
				} else {
					regionName = "";
					regionId = 0;
				}
				wheel3.setViewAdapter(new ArrayWheelAdapter<String>(activity,
						regionArray));
			} else {
				regionName = regionTempList.get(newValue).getRegionname();
				regionId = regionTempList.get(newValue).getRegionid();
			}
		}
	}
}
