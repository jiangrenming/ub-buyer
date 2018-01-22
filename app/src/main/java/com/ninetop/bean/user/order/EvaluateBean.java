package com.ninetop.bean.user.order;

import java.util.List;

/**
 * @date: 2016/12/12
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class EvaluateBean {

    public String orderCode;
    public List<EvaluateGoodsBean> goodsList;

    @Override
    public String toString() {
        return "EvaluateBean{" +
                "orderCode='" + orderCode + '\'' +
                ", goodsList=" + goodsList +
                '}';
    }
}
