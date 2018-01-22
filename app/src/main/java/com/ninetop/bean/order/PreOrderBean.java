package com.ninetop.bean.order;

import com.ninetop.bean.user.UserInfoBean;

import java.util.List;

/**
 * Created by jill on 2016/12/1.
 */

public class PreOrderBean {

    public UserInfoBean receiver;
    public String total;
    public List<OrderItemBean> itemList;
}
