package youbao.shopping.ninetop.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import youbao.shopping.R;

/**
 * Created by Administrator on 2018/3/15/015.
 */

public class UpdateDialoger extends AlertDialog implements DialogInterface {

    protected UpdateDialoger(Context context) {
        super(context);
    }

    public static class Builder {

        private Context mContext;
        private Dialog mDialog;
        private ViewHolder mViewHolder;

        private View mView;
        private boolean hasPos = false, hasNeg = false;

        public Builder(Activity context) {
            mContext = context;
            initView();
        }

        public Builder setIcon(int resid) {
            mViewHolder.ivIcon.setImageResource(resid);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            mViewHolder.tvTitle.setText(title);
            return this;
        }

//        public Builder setTitle(CharSequence title, int color) {
//            mViewHolder.tvTitle.setText(title);
//            mViewHolder.tvTitle.setTextColor(ContextCompat.getDrawable(mContext, color));
//            return this;
//        }

        public Builder setTitle(int resid) {
            mViewHolder.tvTitle.setText(resid);
            return this;
        }

//        public Builder setTitle(int resid, int color) {
//            mViewHolder.tvTitle.setText(resid);
//            mViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
//            return this;
//        }

        public Builder setMessage(CharSequence title) {
            mViewHolder.tvMessage.setText(title);
            return this;
        }

//        public Builder setMessage(CharSequence title, int color) {
//            mViewHolder.tvMessage.setText(title);
//            mViewHolder.tvMessage.setTextColor(ContextCompat.getColor(mContext, color));
//            return this;
//        }

        public Builder setMessage(int resid) {
            mViewHolder.tvMessage.setText(resid);
            return this;
        }

//        public Builder setMessage(int resid, int color) {
//            mViewHolder.tvMessage.setText(resid);
//            mViewHolder.tvMessage.setTextColor(ContextCompat.getColor(mContext, color));
//            return this;
//        }

        public Builder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
            mViewHolder.tvPositiveButton.setVisibility(View.VISIBLE);
            hasPos = true;
            mViewHolder.tvPositiveButton.setText(text);
            mViewHolder.tvPositiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    if (listener != null) {
                        listener.onClick(view);
                    }
                }
            });
            return this;
        }



        public Builder setPositiveButton(CharSequence text, final View.OnClickListener listener, int color) {
            mViewHolder.tvPositiveButton.setVisibility(View.VISIBLE);
            hasPos = true;
            mViewHolder.tvPositiveButton.setText(text);
//            mViewHolder.tvPositiveButton.setTextColor(ContextCompat.getColor(mContext, color));
            mViewHolder.tvPositiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    if (listener != null) {
                        listener.onClick(view);
                    }
                }
            });
            return this;
        }

        public Builder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
            mViewHolder.tvNegativeButton.setVisibility(View.VISIBLE);
            hasNeg = true;
            mViewHolder.tvNegativeButton.setText(text);
            mViewHolder.tvNegativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    if (listener != null) {
                        listener.onClick(view);
                    }
                }
            });
            return this;
        }

//        public Builder setNegativeButton(CharSequence text, final View.OnClickListener listener, int color) {
//            mViewHolder.tvNegativeButton.setVisibility(View.VISIBLE);
//            hasNeg = true;
//            mViewHolder.tvNegativeButton.setText(text);
////            mViewHolder.tvNegativeButton.setTextColor(ContextCompat.getColor(mContext, color));
//            mViewHolder.tvNegativeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mDialog.dismiss();
//                    if (listener != null) {
//                        listener.onClick(view);
//                    }
//                }
//            });
//            return this;
//        }

        public Builder setCancelable(boolean flag) {
            mDialog.setCancelable(flag);
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean flag) {
            mDialog.setCanceledOnTouchOutside(flag);
            return this;
        }

        public Dialog create() {
            return mDialog;
        }

        public void show() {
            if (mDialog != null) {
                if (hasPos || hasNeg) {
                    mViewHolder.line1.setVisibility(View.VISIBLE);
                }
                if (hasPos && hasNeg) {
                    mViewHolder.line2.setVisibility(View.VISIBLE);
                }
                mDialog.show();
            }
        }

        public void dismiss() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }

        private void initView() {
            mDialog = new Dialog(mContext, DialogUtils.getStyle());
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_update_dialog, null);
            mViewHolder = new ViewHolder(mView);
            mDialog.setContentView(mView);

            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            lp.width = (int) (dm.widthPixels * 0.8);
            mDialog.getWindow().setAttributes(lp);
        }


        class ViewHolder {

            ImageView ivIcon;
            TextView tvTitle;
            TextView tvMessage;
            TextView tvPositiveButton, tvNegativeButton;
            FrameLayout vgLayout;
            View line1, line2;

            public ViewHolder(View view) {

                ivIcon = (ImageView) view.findViewById(R.id.dialog_icon);
                tvTitle = (TextView) view.findViewById(R.id.dialog_title);
                tvMessage = (TextView) view.findViewById(R.id.dialog_message);
                tvPositiveButton = (TextView) view.findViewById(R.id.dialog_positive);
                tvNegativeButton = (TextView) view.findViewById(R.id.dialog_negative);
                vgLayout = (FrameLayout) view.findViewById(R.id.dialog_layout);
                line1 = view.findViewById(R.id.dialog_line1);
                line2 = view.findViewById(R.id.dialog_line2);
            }
        }

    }
}
