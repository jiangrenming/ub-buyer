package youbao.shopping.ninetop.UB.product;

/**
 * Created by huangjinding on 2017/5/23.
 */
public class FirstProductBean {
    public String id;
    public String name;
    public int count_month;
    public int price;
    public String icon1;

    public int getCount_month() {
        return count_month;
    }

    public void setCount_month(int count_month) {
        this.count_month = count_month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
