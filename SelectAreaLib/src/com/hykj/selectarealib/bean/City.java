package com.hykj.selectarealib.bean;

public class City {

	private int cityid;
	private String cityname;
	private int parentid;

	public City(int cityid, String cityname, int parentid) {
		super();
		this.cityid = cityid;
		this.cityname = cityname;
		this.parentid = parentid;
	}

	public City() {
		super();
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

}
