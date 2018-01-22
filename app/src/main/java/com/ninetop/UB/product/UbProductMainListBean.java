package com.ninetop.UB.product;

/**
 * Created by huangjinding on 2017/6/12.
 */

public class UbProductMainListBean {
    public String icon_url;
    public String name;
    public int franchisee_id;
    public int activity_id;

  public UbProductMainListBean(String image,String name,int franchiseeId,int activityId){
      this.icon_url=image;
      this.name=name;
      this.franchisee_id=franchiseeId;
      this.activity_id=activityId;
  }

}
