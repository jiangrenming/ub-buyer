package youbao.shopping.ninetop.base;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
/**
 * viewpager的自定义适配器
 *
 */
public abstract class DefaultPagerAdapter<T> extends PagerAdapter {
   
    /**
     * 数据源
     */
    public List<T> datas;
    public Context ctx;

    public DefaultPagerAdapter(List<T> datas, Context ctx){
        this.datas= datas;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        if (null==datas){
            return 0;
        }
        return datas.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public abstract Object instantiateItem(ViewGroup container, int position);
    

}
