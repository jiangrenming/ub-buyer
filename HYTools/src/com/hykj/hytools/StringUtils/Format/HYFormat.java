package com.hykj.hytools.StringUtils.Format;

public interface HYFormat {
	/**
	 * @param str
	 * @return
	 */
	public boolean isEmail(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isMobilePhone(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isLandlinePhone(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isIDCard(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isNum(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isFloat(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isDateyyMMdd(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isDateyyMMddHHmmss(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isDateyyMMddHHmm(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isTimeHHHmmss(String str);
	/**
	 * @param str
	 * @return
	 */
	public boolean isTimeHHmm(String str);
}
