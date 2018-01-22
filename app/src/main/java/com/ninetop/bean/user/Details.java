package com.ninetop.bean.user;


import java.util.List;

public class Details {


    /**
     * code : SUCCESS
     * errCode : null
     * data : {"dataList":[{"freecasUseType":"A","useTime":"2016-11-28 18:58:38","userAmount":500},{"freecasUseType":"A","useTime":"2016-11-28 18:52:53","userAmount":500},{"freecasUseType":"B","useTime":"2016-11-28 17:54:16","userAmount":111},{"freecasUseType":"A","useTime":"2016-11-28 16:37:35","userAmount":100}]}
     * msg : null
     * action : null
     */

    public String code;
    public String errCode;
    public DataB data;
    public String msg;
    public String action;

    public class DataB {
        public List<DataListBean> dataList;
    }

}
