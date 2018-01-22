//package com.ninetop.activity.ub.util;
//
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import java.util.List;
//
//import youbao.shopping.R;
//
//
///**
// * Created by guojun on 2015/10/29.
// */
//public class CommonSpinner extends TextView implements View.OnClickListener {
//    private TextView mTvTopView;
//    private Context mContext;
//    private SpinnerPopupWindow mPopupWindow;
//    private List<String> mDataS;
//    private OnSpinnerItemSelectListener mOnSpinnerItemSelectListener;
//
//    public CommonSpinner(Context context) {
//        this(context, null);
//    }
//
//    public CommonSpinner(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CommonSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        mTvTopView = this;
//        mContext = context;
//        mPopupWindow = new SpinnerPopupWindow(mContext);
//        setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        mPopupWindow.showPopupWindow(mTvTopView);
//    }
//
//    public void setData(List<String> datas) {
//        this.mDataS = datas;
//    }
//
//    class SpinnerPopupWindow extends PopupWindow {
//        private Context mContext;
//        private ListView mListView;
//        private MyAdapter mMyAdapter;
//
//        public SpinnerPopupWindow(Context context) {
//            init(context);
//        }
//
//        private void init(Context context) {
//            mContext = context;
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.layout_spinner, null);
//            mListView = (ListView) view.findViewById(R.id.listview);
//            mListView.setItemsCanFocus(true);
//            mMyAdapter = new MyAdapter();
//            mListView.setAdapter(mMyAdapter);
//            this.setContentView(view);
//            //设置宽高
//            this.setWidth(mListView.getLayoutParams().width);
//            this.setHeight(mListView.getLayoutParams().height);
//            //让popupwindow获取焦点
//            this.setFocusable(true);
//            this.setOutsideTouchable(true);
//            this.update();
//
//            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//            ColorDrawable dw = new ColorDrawable(00000000);
//            this.setBackgroundDrawable(dw);
//            //设置动画
//            this.setAnimationStyle(R.style.SpinnerPopupWindowAnim);
//
//        }
//
//        class MyAdapter extends BaseAdapter {
////            Context myContext;
////            List<SpinnerListBean> dataList;
////            public  MyAdapter(Context context, List<SpinnerListBean> dataList){
////                this.myContext=context;
////                this.dataList=dataList;
////            }
//            @Override
//            public int getCount() {
//                return mDataS == null ? 0 : mDataS.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//            @Override
//            public View getView(final int position, View convertView, ViewGroup parent) {
//                if (convertView == null) {
//                    convertView = View.inflate(mContext, R.layout.spinner_item, null);
//                }
//                TextView bt = ViewHolder.get(convertView,R.id.tv_spinner_item);
//                bt.setText(mDataS.get(position));
//                bt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showPopupWindow(mTvTopView);
//                        mTvTopView.setText(mDataS.get(position));
//                        if (null != mOnSpinnerItemSelectListener) {
//                            mOnSpinnerItemSelectListener.onItemSelectListener(position);
//                        }
//                    }
//                });
////                 SpinnerHolder spinnerHolder;
////                if(convertView==null){
////                    spinnerHolder=new SpinnerHolder();
////                    convertView=LayoutInflater.from(myContext).inflate(R.layout.spinner_item, null);
////                    spinnerHolder.itemSpinner=(TextView)convertView.findViewById(R.id.tv_spinner_item);
////                    convertView.setTag(spinnerHolder);
////                }else {
////                    spinnerHolder=(SpinnerHolder)convertView.getTag();
////                }
////                 final SpinnerListBean bean=dataList.get(position);
////                   spinnerHolder.itemSpinner.setText(bean.name);
////               convertView.setOnClickListener(new OnClickListener() {
////                   @Override
////                   public void onClick(View v) {
////                       showPopupWindow(mTvTopView);
////                        if(null!=mOnSpinnerItemSelectListener){
////                            mOnSpinnerItemSelectListener.onItemSelectListener(position);
////                        }
////                       ToastUtils.showCenter(context,"已选择"+bean.name+bean.id);
////                   }
////               });
//                return convertView;
//            }
////            class SpinnerHolder{
////                TextView itemSpinner;
////            }
//        }
//
//        public void showPopupWindow(View parent) {
//            if (!this.isShowing()) {
//                this.showAsDropDown(parent, -(this.getWidth() -parent.getWidth())/2, 0);
//            } else {
//                this.dismiss();
//            }
//        }
//
//    }
//
//    public void setOnSpinnerItemSelectListener(OnSpinnerItemSelectListener listener) {
//        this.mOnSpinnerItemSelectListener = listener;
//    }
//
//
//    public interface OnSpinnerItemSelectListener {
//        void onItemSelectListener(int pos);
//    }
//
//}
