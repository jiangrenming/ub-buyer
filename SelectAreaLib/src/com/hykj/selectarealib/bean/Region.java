package com.hykj.selectarealib.bean;

public class Region {

	private int regionid;
	private String regionname;
	private int parentid;

	public int getRegionid() {
		return regionid;
	}

	public void setRegionid(int regionid) {
		this.regionid = regionid;
	}

	public String getRegionname() {
		return regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public Region(int regionid, String regionname, int parentid) {
		super();
		this.regionid = regionid;
		this.regionname = regionname;
		this.parentid = parentid;
	}

	public Region() {
		super();
	}

}
