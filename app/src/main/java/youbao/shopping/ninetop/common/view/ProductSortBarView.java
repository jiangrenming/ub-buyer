package youbao.shopping.ninetop.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.bean.SortBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

public class ProductSortBarView extends LinearLayout {

    @BindView(R.id.tv_sort_default)
    TextView tvSortDefault;
    @BindView(R.id.tv_sort_price)
    TextView tvSortPrice;
    @BindView(R.id.iv_sort_price_up)
    ImageView ivSortPriceUp;
    @BindView(R.id.iv_sort_price_down)
    ImageView ivSortPriceDown;
    @BindView(R.id.tv_sort_quality)
    TextView tvSortQuality;
    @BindView(R.id.iv_sort_quality_up)
    ImageView ivSortQualityUp;
    @BindView(R.id.iv_sort_quality_down)
    ImageView ivSortQualityDown;
    private Context context;

    private static final SortBean[] SORT_ARRAY = new SortBean[]{new SortBean("1", "上新"), new SortBean("2", "价格"), new SortBean("3", "销量")};
    private static final String[] ORDER_ARRAY = new String[]{"0", "1"};

    private SortBean lastSort;

    private TextView[] textViewArray = null;
    private ImageView[] sortImageArray = null;
    private int[] unSelectResArray = new int[]{R.mipmap.category_order_down_normal, R.mipmap.category_order_up_normal
            , R.mipmap.category_order_down_normal, R.mipmap.category_order_up_normal};
    private int[] selectResArray = new int[]{R.mipmap.category_order_down_select, R.mipmap.category_order_up_select};

    private SelectChangedListener selectChangedListener;

    private boolean sortDown = true;

    public ProductSortBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_product_sort_bar, this);
        ButterKnife.bind(this, view);

        textViewArray = new TextView[]{tvSortDefault, tvSortQuality, tvSortPrice};
        sortImageArray = new ImageView[]{ivSortQualityDown, ivSortQualityUp, ivSortPriceDown, ivSortPriceUp};

        lastSort = SORT_ARRAY[0];
    }

    @OnClick({R.id.ll_sort_default, R.id.ll_sort_price, R.id.ll_sort_quality})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_sort_default:
                changeHandle(0);
                break;
            case R.id.ll_sort_price:
                changeHandle(2);
                break;
            case R.id.ll_sort_quality:
                changeHandle(1);
                break;
        }
    }

    public void setSelectChangedListener(SelectChangedListener selectChangedListener) {
        this.selectChangedListener = selectChangedListener;
    }

    public void changeHandle(int index) {
        if (index == 0) {
            sortDown = true;
        }

        if (index == 0 && lastSort == SORT_ARRAY[index]) {
            return;
        }

        if (lastSort == SORT_ARRAY[index]) {
            sortDown = !sortDown;
        } else {
            lastSort = SORT_ARRAY[index];
            sortDown = true;
        }

        changeUI(index);

        if (selectChangedListener != null) {
            selectChangedListener.changeHandle(lastSort);
        }
    }

    private void changeUI(int index) {
        int unSelectColor = Tools.getColorValueByResId(getContext(), R.color.gray_light4);
        int selectColor = Tools.getColorValueByResId(getContext(), R.color.text_red);

        for (TextView textView : textViewArray) {
            textView.setTextColor(unSelectColor);
        }

        textViewArray[index].setTextColor(selectColor);
        for (int i = 0; i < sortImageArray.length; i++) {
            sortImageArray[i].setImageResource(unSelectResArray[i]);
        }

        if (index == 0)
            return;

        int imageRes = sortDown ? selectResArray[0] : selectResArray[1];
        int downValue = sortDown ? 0 : 1;
        int indexValue = (index - 1) * 2;
        ImageView imageView = sortImageArray[indexValue + downValue];
        imageView.setImageResource(imageRes);
    }

    public String getOrder() {
        return sortDown ? ORDER_ARRAY[0] : ORDER_ARRAY[1];
    }
}
