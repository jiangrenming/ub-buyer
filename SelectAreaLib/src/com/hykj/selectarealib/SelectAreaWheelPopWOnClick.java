package com.hykj.selectarealib;

/**
 * 监听点击事件
 * 
 * @author Administrator
 *
 */
public interface SelectAreaWheelPopWOnClick {
	/**
	 * 确定按钮
	 * 
	 * @param provinceId
	 * @param cityId
	 * @param regionId
	 * @param provinceName
	 * @param cityName
	 * @param regionName
	 */
	public abstract void sureOnClick(int provinceId, int cityId, int regionId,
			String provinceName, String cityName, String regionName);

	/**
	 * 取消按钮
	 */
	public abstract void cancelOnClick();
}
