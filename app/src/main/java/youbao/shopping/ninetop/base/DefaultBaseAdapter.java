package youbao.shopping.ninetop.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/*
 */
public abstract class DefaultBaseAdapter<T> extends BaseAdapter {
    /**
     * 数据源
     */
    public List<T> datas;
    public Context ctx;

    public DefaultBaseAdapter(List<T> datas, Context ctx){
        this.datas = datas;
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
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
    
}
