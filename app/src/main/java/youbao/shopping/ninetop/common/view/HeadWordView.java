package youbao.shopping.ninetop.common.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.R;

/**
 * @date: 2016/11/14
 * @author: Shelton
 * @version: 1.1.3
 * @Description:带文字的titleBar
 */
public class HeadWordView extends LinearLayout {

    private String detailTitle;
    private String headTitle;
    private Context context;
    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_title_word;
    private OnClickListener backClickListener = null;
    private OnClickListener jumpToClickListener = null;
    private int backImageVisible = View.VISIBLE;
    private int tvWordVisible = View.VISIBLE;

    public HeadWordView(Context context) {
        this(context,null);
    }



    public HeadWordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_head_word, this);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title_word = (TextView) view.findViewById(R.id.tv_title_word);

        iv_back.setOnClickListener(new BackClickListener());

        //tv_title_word.setOnClickListener(new JumpClickListener());
        tv_title_word.setOnClickListener(new JumpToClickListener());

        initUI();
    }

  /*  public HeadWordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }*/

    private void initUI() {
        if(iv_back == null)
            return;
        iv_back.setVisibility(backImageVisible);
        tv_title_word.setVisibility(tvWordVisible);
        tv_title.setText(headTitle);
        tv_title_word.setText(detailTitle);
    }

    public void setTitle(String title){
       this.headTitle = title;
        initUI();
   }

    public void setDetails(String details){
        this.detailTitle = details;
        initUI();
    }

    public void setBackClickListener(OnClickListener onClickListener){
        this.backClickListener = onClickListener;
        iv_back.setOnClickListener(backClickListener);
    }

    public void setJumpToClickListener(OnClickListener onClickListener){
        this.jumpToClickListener = onClickListener;
        if(jumpToClickListener == null)
            return;

        tv_title_word.setOnClickListener(jumpToClickListener);

    }

    class BackClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            ((Activity)context).finish();
        }
    }

    class JumpToClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            //context.startActivity(intent);
        }
    }

    public void setBackImageVisible(int backImageVisible) {
        this.backImageVisible = backImageVisible;
        initUI();
    }

    public void setTvWordVisible(int tvWordVisible){
        this.tvWordVisible = tvWordVisible;
        initUI();
    }
}
