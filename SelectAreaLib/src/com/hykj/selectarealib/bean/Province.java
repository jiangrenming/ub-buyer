package com.hykj.selectarealib.bean;

public class Province {

	private int provinceid;
	private String provincename;

	public Province(int provinceid, String provincename) {
		super();
		this.provinceid = provinceid;
		this.provincename = provincename;
	}

	public Province() {
		super();
	}

	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	@Override
	public String toString() {
		return "Province [provinceid=" + provinceid + ", provincename="
				+ provincename + "]";
	}
}
