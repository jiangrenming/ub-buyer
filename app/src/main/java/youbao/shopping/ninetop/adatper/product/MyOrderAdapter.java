//package youbao.shopping.ninetop.adatper.product;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.hykj.dialoglib.MyDialog;
//import com.hykj.dialoglib.MyDialogOnClick;
//import youbao.shopping.ninetop.activity.order.pay.OrderPayActivity;
//import youbao.shopping.ninetop.activity.product.CommentAndShowActivity;
//import youbao.shopping.ninetop.activity.user.SeeLogistic;
//import youbao.shopping.ninetop.base.DefaultBaseAdapter;
//import youbao.shopping.ninetop.base.Viewable;
//import youbao.shopping.ninetop.bean.user.order.EvaluateBean;
//import youbao.shopping.ninetop.bean.user.order.EvaluateGoodsBean;
//import youbao.shopping.ninetop.bean.user.order.OrderBean;
//import youbao.shopping.ninetop.bean.user.order.OrderListBean;
//import youbao.shopping.ninetop.common.IntentExtraKeyConst;
//import youbao.shopping.ninetop.common.util.Tools;
//import youbao.shopping.ninetop.service.impl.OrderService;
//import youbao.shopping.ninetop.service.listener.CommonResultListener;
//
//import org.json.JSONException;
//
//import java.io.Serializable;
//import java.util.List;
//
//import youbao.shopping.R;
//
//import static youbao.shopping.ninetop.adatper.product.OrderFragmentEnum.STATUS_CANCEL;
//import static youbao.shopping.ninetop.adatper.product.OrderFragmentEnum.STATUS_E;
//import static youbao.shopping.ninetop.adatper.product.OrderFragmentEnum.STATUS_FINISH;
//import static youbao.shopping.ninetop.adatper.product.OrderFragmentEnum.STATUS_SEND;
//import static youbao.shopping.ninetop.adatper.product.OrderFragmentEnum.STATUS_WAIT_PAY;
//import static youbao.shopping.ninetop.adatper.product.OrderFragmentEnum.STATUS_WAIT_SEND;
//import static youbao.shopping.ninetop.common.IntentExtraKeyConst.EVALUATEGOODSLIST;
//import static youbao.shopping.ninetop.common.IntentExtraKeyConst.ORDER_CODE;
//
//
///**
// * @date: 2016/12/8
// * @author: Shelton
// * @version: 1.1.3
// * @Description:
// */
//
//public class MyOrderAdapter extends DefaultBaseAdapter {
//    private List<OrderListBean> dataList;
//
//    private List<EvaluateGoodsBean> evaluateGoodsList;
//    private OrderService service;
//    private String state;
//    private Activity context;
//
//    private int page = 0;
//
//    private View.OnClickListener leftListenerP;
//    private View.OnClickListener rightListenerP;
//    private View.OnClickListener rightListenerA;
//    private View.OnClickListener leftListenerS;
//    private View.OnClickListener rightListenerS;
//    private View.OnClickListener leftListenerF;
//    private View.OnClickListener rightListenerF;
//    private View.OnClickListener rightListenerC;
//    private View.OnClickListener rightListenerE;
//
//    public MyOrderAdapter(List<OrderListBean> dataList, Activity ctx, String state) {
//        super(dataList, ctx);
//        this.dataList = dataList;
//        this.context = ctx;
//        this.state = state;
//        service = new OrderService((Viewable) ctx);
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        OrderHolder holder = null;
//        if (convertView == null) {
//            holder = new OrderHolder();
//            convertView = View.inflate(ctx, R.layout.item_order_listview, null);
//            holder.tv_my_order_time = (TextView) convertView.findViewById(R.id.tv_my_order_time);
//            holder.tv_my_order_type = (TextView) convertView.findViewById(R.id.tv_my_order_type);
//            holder.iv_my_order_image = (ImageView) convertView.findViewById(R.id.iv_my_order_image);
//            holder.tv_my_order_product_name = (TextView) convertView.findViewById(R.id.tv_my_order_product_name);
//            holder.tv_my_order_product_price = (TextView) convertView.findViewById(R.id.tv_my_order_product_price);
//            holder.tv_my_order_product_count = (TextView) convertView.findViewById(R.id.tv_my_order_product_count);
//            holder.rl_content = (RelativeLayout) convertView.findViewById(R.id.rl_content);
//            holder.tv_my_order_product_total_count = (TextView) convertView.findViewById(R.id.tv_my_order_product_total_count);
//            holder.tv_my_order_product_total_price = (TextView) convertView.findViewById(R.id.tv_my_order_product_total_price);
//            holder.btn_left = (Button) convertView.findViewById(R.id.btn_left);
//            holder.btn_right = (Button) convertView.findViewById(R.id.btn_right);
//            holder.tv_refund_status = (TextView) convertView.findViewById(R.id.tv_refund_status);
//            holder.ll_pay1 = (LinearLayout) convertView.findViewById(R.id.ll_pay1);
//
//            convertView.setTag(holder);
//        } else {
//            holder = (OrderHolder) convertView.getTag();
//        }
//
//        final OrderListBean orderBean = dataList.get(position);
//
//        final String orderCode = orderBean.code;
//        final String complaintId = orderBean.complaintId;
//
//        holder.tv_my_order_time.setText(orderBean.orderedTime);
//
//        initListener(orderCode, holder, orderBean, position);
//
//        registerListener(holder);
//
//        setListener(holder, orderBean);
//
//        OrderFragmentEnum orderFragmentEnum = OrderFragmentEnum.getByKey(orderBean.orderStatus);
//
//        holder.tv_my_order_type.setText(orderFragmentEnum.tvMyOrderType);
//        holder.btn_left.setVisibility(orderFragmentEnum.btn_leftVisible);
//        holder.btn_left.setText(orderFragmentEnum.btn_leftText);
//        holder.btn_right.setVisibility(orderFragmentEnum.btn_rightVisible);
//        holder.btn_right.setText(orderFragmentEnum.btn_rightText);
//        holder.btn_left.setTextColor(orderFragmentEnum.btn_leftTextColor);
//        holder.btn_left.setBackgroundResource(orderFragmentEnum.btn_leftBg);
//        holder.btn_right.setTextColor(orderFragmentEnum.btn_rightTextColor);
//        holder.btn_right.setBackgroundResource(orderFragmentEnum.btn_rightBg);
//
//        if (orderBean.goodsList.size() > 0 && orderBean.goodsList != null) {
//            holder.tv_my_order_product_name.setText(orderBean.indexname);
//            double price = Double.parseDouble(orderBean.indexprice);
//            holder.tv_my_order_product_price.setText(Tools.saveNum(price));
//            holder.tv_my_order_product_count.setText(orderBean.indexcount);
//            Tools.ImageLoaderShow(ctx, orderBean.indexicon, holder.iv_my_order_image);
//        }
//
//        String isCommented = orderBean.isCommented;
//
//        if (isCommented.equals("Y") && orderBean.orderStatus.equals("F")) {
//            holder.btn_left.setText("查看物流");
//            holder.btn_right.setText("删除订单");
//            holder.btn_left.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    jump2Logistic(orderCode);
//                }
//            });
//            holder.btn_right.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    deleteOrders(orderCode, position);
//                }
//            });
//        }
//
//        String indexcomplaintstatus = orderBean.indexcomplaintstatus;
//        if (indexcomplaintstatus != null) {
//            switch (indexcomplaintstatus) {
//                case "P":
//                case "W":
//                    holder.tv_refund_status.setVisibility(View.VISIBLE);
//                    holder.tv_refund_status.setText("退款处理中");
//                    holder.ll_pay1.setVisibility(View.GONE);
//                    break;
//                case "B":
//                    holder.tv_refund_status.setVisibility(View.VISIBLE);
//                    holder.tv_refund_status.setText("退货中");
//                    holder.ll_pay1.setVisibility(View.GONE);
//                    break;
//                case "M":
//                    holder.tv_refund_status.setVisibility(View.VISIBLE);
//                    holder.tv_refund_status.setText("退款中");
//                    holder.ll_pay1.setVisibility(View.GONE);
//                    break;
//                case "F":
//                    holder.tv_refund_status.setVisibility(View.VISIBLE);
//                    holder.tv_refund_status.setText("退款成功");
//                    holder.ll_pay1.setVisibility(View.VISIBLE);
//                    break;
//                case "R":
//                    //TODO 维权状态返回R：拒绝
//                    holder.tv_refund_status.setVisibility(View.VISIBLE);
//                    holder.tv_refund_status.setText("退款失败");
//                    holder.ll_pay1.setVisibility(View.VISIBLE);
//                default:
//                    holder.tv_refund_status.setVisibility(View.INVISIBLE);
//                    holder.ll_pay1.setVisibility(View.VISIBLE);
//                    break;
//            }
//        }
//
//        holder.tv_my_order_product_total_count.setText(orderBean.goodsCount);
//
//        String realPay = orderBean.realPay;
//        if (realPay == null || realPay.equals("")) {
//            realPay = String.valueOf(0);
//        }
//        double pay = Double.parseDouble(realPay);
//        holder.tv_my_order_product_total_price.setText(Tools.saveNum(pay));
//
//        return convertView;
//    }
//
//
//    public class OrderHolder {
//        public TextView tv_my_order_time;
//        public TextView tv_my_order_type;
//        public ImageView iv_my_order_image;
//        public TextView tv_my_order_product_name;
//        public TextView tv_my_order_product_price;
//        public TextView tv_my_order_product_count;
//        public RelativeLayout rl_content;
//        public TextView tv_my_order_product_total_count;
//        public TextView tv_my_order_product_total_price;
//        public Button btn_left;
//        public Button btn_right;
//        public TextView tv_refund_status;
//        public LinearLayout ll_pay1;
//    }
//
//
//    private void initListener(final String orderCode, final OrderHolder holder, final OrderListBean orderBean, final int position) {
//        leftListenerP = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    showCancelOrderDialog(orderCode, holder, position);
//
//            }
//
//        };
//        rightListenerP = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ctx, OrderPayActivity.class);
//                intent.putExtra(IntentExtraKeyConst.ORDER_NO, orderCode);
//                intent.putExtra(IntentExtraKeyConst.ORDER_TOTAL, orderBean.realPay);
//                ctx.startActivity(intent);
//            }
//        };
//        rightListenerA = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //jump2Logistic(orderCode);
//                Toast.makeText(context, "已为您催单,请亲不要着急", Toast.LENGTH_SHORT).show();
//            }
//        };
//        leftListenerS = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jump2Logistic(orderCode);
//            }
//        };
//        rightListenerS = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                confirmGoods(orderCode, holder, position);
//            }
//        };
//        leftListenerF = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jump2Logistic(orderCode);
//            }
//        };
//        rightListenerF = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toEvaluateForGoods(orderCode);//去评价，获得可以评价的商品
//
//            }
//        };
//        rightListenerC = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteOrders(orderCode, position);
//            }
//        };
//        rightListenerE = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteOrders(orderCode, position);
//            }
//        };
//    }
//
//    private void showCancelOrderDialog(final String orderCode, final OrderHolder holder, final int position) {
//        new MyDialog(context, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要取消订单吗?", new MyDialogOnClick() {
//            @Override
//            public void sureOnClick(View v) {
//                cancelOrders(orderCode, holder, position);
//            }
//
//            @Override
//            public void cancelOnClick(View v) {
//            }
//        }).show();
//    }
//
//    private void registerListener(OrderHolder holder) {
//        OrderFragmentEnum.getByKey(STATUS_WAIT_PAY).registerLeftOnClickListener(leftListenerP);
//        //holder.btn_left.setOnClickListener(leftListenerP);
//        OrderFragmentEnum.getByKey(STATUS_WAIT_PAY).registerRightOnClickListener(rightListenerP);
//        //holder.btn_right.setOnClickListener(rightListenerP);
//        OrderFragmentEnum.getByKey(STATUS_WAIT_SEND).registerRightOnClickListener(rightListenerA);
//        OrderFragmentEnum.getByKey(STATUS_SEND).registerLeftOnClickListener(leftListenerS);
//        OrderFragmentEnum.getByKey(STATUS_SEND).registerRightOnClickListener(rightListenerS);
//        OrderFragmentEnum.getByKey(STATUS_FINISH).registerLeftOnClickListener(leftListenerF);
//        OrderFragmentEnum.getByKey(STATUS_FINISH).registerRightOnClickListener(rightListenerF);
//        OrderFragmentEnum.getByKey(STATUS_CANCEL).registerRightOnClickListener(rightListenerC);
//        OrderFragmentEnum.getByKey(STATUS_E).registerRightOnClickListener(rightListenerE);
//        //holder.btn_right.setOnClickListener(rightListenerE);
//    }
//
//    private void setListener(OrderHolder holder, final OrderListBean orderBean) {
//        if (leftListenerP != null) {
//            if (orderBean.orderStatus.equals(STATUS_WAIT_PAY)) {
//                holder.btn_left.setOnClickListener(leftListenerP);
//            }
//        }
//
//        if (rightListenerP != null) {
//            if (orderBean.orderStatus.equals(STATUS_WAIT_PAY)) {
//                holder.btn_right.setOnClickListener(rightListenerP);
//            }
//        }
//
//        if (rightListenerA != null) {
//            if (orderBean.orderStatus.equals(STATUS_WAIT_SEND)) {
//                holder.btn_right.setOnClickListener(rightListenerA);
//            }
//        }
//
//        if (leftListenerS != null) {
//            if (orderBean.orderStatus.equals(STATUS_SEND)) {
//                holder.btn_left.setOnClickListener(leftListenerS);
//            }
//        }
//
//        if (rightListenerS != null) {
//            if (orderBean.orderStatus.equals(STATUS_SEND)) {
//                holder.btn_right.setOnClickListener(rightListenerS);
//            }
//        }
//
//        if (leftListenerF != null) {
//            if (orderBean.orderStatus.equals(STATUS_FINISH)) {
//                holder.btn_left.setOnClickListener(leftListenerF);
//            }
//        }
//
//        if (rightListenerF != null) {
//            if (orderBean.orderStatus.equals(STATUS_FINISH)) {
//                holder.btn_right.setOnClickListener(rightListenerF);
//            }
//        }
//
//        if (rightListenerC != null) {
//            if (orderBean.orderStatus.equals(STATUS_CANCEL)) {
//                holder.btn_right.setOnClickListener(rightListenerC);
//            }
//        }
//
//        if (rightListenerE != null) {
//            if (orderBean.orderStatus.equals(STATUS_E)) {
//                holder.btn_right.setOnClickListener(rightListenerE);
//            }
//        }
//    }
//
//    //取消退款申请
//    private void cancelRefundApplication(String complaintId) {
//        service.cancelRefundApplication(complaintId, new CommonResultListener<String>((Viewable) ctx) {
//            @Override
//            public void successHandle(String result) {
//                requestAgain();
//                ((Viewable) ctx).showToast("取消订单申请");
//            }
//        });
//    }
//
//    //删除订单
//    private void deleteOrders(String orderCode, final int position) {
//        //((Viewable) ctx).showToast("删除订单");
//        service.deleteOrders(orderCode, new CommonResultListener<String>((Viewable) ctx) {
//            @Override
//            public void successHandle(String result) {
//                if (dataList != null && dataList.size() > 0) {
//                    dataList.remove(position);
//                    ((Viewable) ctx).showToast("删除订单成功");
//                    MyOrderAdapter.this.notifyDataSetChanged();
//                }
//            }
//        });
//    }
//
//    //确认收货
//    private void confirmGoods(final String orderCode, final OrderHolder holder, final int position) {
//        service.confirmGoods(orderCode, new CommonResultListener<String>((Viewable) ctx) {
//            @Override
//            public void successHandle(String result) {
//
//                requestAgain();
//                ((Viewable) ctx).showToast("确认收货成功");
//            }
//        });
//    }
//
//    //转跳到查看物流
//    private void jump2Logistic(String orderCode) {
//        Intent intent = new Intent(ctx, SeeLogistic.class);
//        intent.putExtra(ORDER_CODE, orderCode);
//        ctx.startActivity(intent);
//    }
//
//    //取消订单
//    private void cancelOrders(String orderCode, final OrderHolder holder, final int position) {
//        service.cancelOrders(orderCode, new CommonResultListener<String>((Viewable) ctx) {
//            @Override
//            public void successHandle(String result) {
//                requestAgain();
//                ((Viewable) ctx).showToast("取消订单成功");
//            }
//        });
//    }
//
//    //去评价，获得可以评价的商品
//    private void toEvaluateForGoods(String orderCode) {
//        service.getEvaluateGoods(orderCode, new CommonResultListener<EvaluateBean>((Viewable) ctx) {
//            @Override
//            public void successHandle(EvaluateBean result) throws JSONException {
//                evaluateGoodsList = result.goodsList;
//                Intent intent = new Intent(ctx, CommentAndShowActivity.class);
//                intent.putExtra(ORDER_CODE, result.orderCode);
//                intent.putExtra(EVALUATEGOODSLIST, (Serializable) evaluateGoodsList);
//                ctx.startActivity(intent);
//
//            }
//        });
//    }
//
//    //再次请求网络
//    private void requestAgain() {
//        service.getOrderList(state, page + "", new CommonResultListener<OrderBean>((Viewable) ctx) {
//            @Override
//            public void successHandle(OrderBean result) throws JSONException {
//                if (result == null) {
//                    return;
//                }
//                dataList.clear();
//                dataList.addAll(result.dataList);
//                MyOrderAdapter.this.notifyDataSetChanged();
//            }
//        });
//    }
//
//    public void clearData() {
//        dataList.clear();
//        notifyDataSetChanged();
//    }
//
//    public void addData(List<OrderListBean> datas) {
//        addData(0, datas);
//    }
//
//    public void addData(int position, List<OrderListBean> datas) {
//        if (datas != null && datas.size() > 0) {
//            dataList.addAll(position, datas);
//            notifyDataSetChanged();
//        }
//    }
//
//    public List<OrderListBean> getDatas() {
//        return dataList;
//    }
//
//}
