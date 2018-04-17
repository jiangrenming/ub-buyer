package youbao.shopping.ninetop.activity.user;


import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import youbao.shopping.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopWindowManager {

    private static PopWindowManager popWindowManager;

    private PopWindowManager() {}

    public synchronized static PopWindowManager getInstance() {
        if (popWindowManager == null) {
            popWindowManager = new PopWindowManager();
        }
        return popWindowManager;
    }

    public interface OnPopOnClickListener{
        void listOne();
        void listTwo();
    }

    public MyPopupWindowView createPopupWindow(Activity activity,String str1,String str2) {

        return new MyPopupWindowView(activity).builderMyPopupWindowView(str1,str2,"取消");
    }

    public class MyPopupWindowView implements View.OnClickListener{
        private ViewHolder viewHolder;
        private Activity activity;
        private PopupWindow popupWindow;
        private OnPopOnClickListener onPopOnClickListener;


        private MyPopupWindowView(Activity activity) {
            this.activity = activity;
            View view = View.inflate(this.activity, R.layout.popupwindow_gender, null);
            popupWindow = new PopupWindow(view,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(this.activity.getResources().getDrawable(R.color.pop_bg_color));
            popupWindow.setAnimationStyle(R.style.my_pop_anim_style);
            if (!activity.isFinishing())
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            this.viewHolder = new ViewHolder(view);
        }

        private MyPopupWindowView builderMyPopupWindowView(String str1,String str2,String str3){
            viewHolder.tvMale.setText(str1);
            viewHolder.tvFemale.setText(str2);
            viewHolder.tvCancel.setText(str3);
            viewHolder.llMale.setOnClickListener(this);
            viewHolder.llFemale.setOnClickListener(this);
            viewHolder.llCancel.setOnClickListener(this);
            return this;
        }

        public void setOnPopOnClickListener(OnPopOnClickListener onPopOnClickListener){
            this.onPopOnClickListener=onPopOnClickListener;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_male:
                        if (onPopOnClickListener!=null){
                            onPopOnClickListener.listOne();
                            dismiss();
                        }
                    break;
                case R.id.ll_female:
                    if (onPopOnClickListener!=null){
                        onPopOnClickListener.listTwo();
                        dismiss();
                    }
                    break;
                case R.id.ll_cancel:
                    dismiss();
                    break;
            }
        }

        public void dismiss(){
            if (popupWindow!=null&&popupWindow.isShowing()){
                popupWindow.dismiss();
            }
        }

        class ViewHolder {
            @BindView(R.id.tv_male)
            TextView tvMale;
            @BindView(R.id.ll_male)
            LinearLayout llMale;
            @BindView(R.id.tv_female)
            TextView tvFemale;
            @BindView(R.id.ll_female)
            LinearLayout llFemale;
            @BindView(R.id.tv_cancel)
            TextView tvCancel;
            @BindView(R.id.ll_cancel)
            LinearLayout llCancel;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
