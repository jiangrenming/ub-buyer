package com.ninetop.bean.product.category;

import java.util.List;
/**
 * @date: 2016/11/30
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class SecondProductBean {
    /**
     * code : SUCCESS
     * data : {"count":0,"dataList":[],"next":"0"}
     */

   // public String code;
    /**
     * count : 0
     * dataList : []
     * next : 0
     */

    //public DataBean data;

        public int count;
        public String next;
        public List<CateProductBean> dataList;

    @Override
    public String toString() {
        return "SecondProductBean{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", dataList=" + dataList +
                '}';
    }
}
