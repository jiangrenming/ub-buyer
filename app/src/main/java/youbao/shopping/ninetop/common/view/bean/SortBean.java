package youbao.shopping.ninetop.common.view.bean;

/**
 * Created by jill on 2016/12/21.
 */

public class SortBean {

    public String code;
    public String name;

    public SortBean(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
