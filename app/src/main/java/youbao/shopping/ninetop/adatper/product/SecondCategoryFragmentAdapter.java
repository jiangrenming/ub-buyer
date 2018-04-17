package youbao.shopping.ninetop.adatper.product;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import youbao.shopping.R;
import youbao.shopping.ninetop.base.DefaultBaseAdapter;
import youbao.shopping.ninetop.bean.product.category.CateProductBean;
import youbao.shopping.ninetop.common.util.Tools;

import java.util.List;

/**
 * @date: 2017/1/3
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class SecondCategoryFragmentAdapter extends DefaultBaseAdapter {
    private Context context;

    private List<CateProductBean> dataList;

    public SecondCategoryFragmentAdapter(List datas, Context ctx) {
        super(datas, ctx);
        this.context = ctx;
        this.dataList = datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_second_category_gridview, null);
            holder.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
            holder.tv_product_desc = (TextView) convertView.findViewById(R.id.tv_product_desc);
            holder.iv_product_price = (TextView) convertView.findViewById(R.id.iv_product_price);
            holder.tv_quality_mark = (TextView) convertView.findViewById(R.id.tv_quality_mark);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Tools.ImageLoaderShow(context, dataList.get(position).picUrl, holder.iv_product_image);
        //描述信息
        holder.tv_product_desc.setText(dataList.get(position).name);
        holder.iv_product_price.setText(dataList.get(position).price);
        holder.tv_quality_mark.setText(dataList.get(position).qualityScore);

        return convertView;
    }

    public class ViewHolder {
        public ImageView iv_product_image;
        public TextView tv_product_desc;
        public TextView iv_product_price;
        public TextView tv_quality_mark;
    }

    public void addData(List<CateProductBean> datas) {
        addData(0, datas);
    }

    public void addData(int position, List<CateProductBean> datas) {
        if (datas != null && datas.size() > 0) {
            dataList.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public List<CateProductBean> getDatas(){
        return dataList;
    }
}
