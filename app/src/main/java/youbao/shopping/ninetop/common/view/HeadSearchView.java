package youbao.shopping.ninetop.common.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import youbao.shopping.R;
import youbao.shopping.ninetop.common.view.listener.SearchEnterListener;

public class HeadSearchView extends RelativeLayout{

	private OnClickListener backClickListener = null;

	private OnClickListener searchClickListener = null;

	private SearchEnterListener searchEnterListener = null;

	private int backImageVisible = View.VISIBLE;

	private int cancelVisible = View.VISIBLE;

	private View iv_back;

	private EditText et_search;

	private View iv_search;

	private View tv_cancel;

	private Context context;

	public HeadSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_head_search, this);

		iv_back = view.findViewById(R.id.iv_back);
		et_search = (EditText) view.findViewById(R.id.et_search);
		iv_search = view.findViewById(R.id.iv_search);
		tv_cancel = view.findViewById(R.id.tv_cancel);

		iv_back.setOnClickListener(new BackClickListener());
		tv_cancel.setOnClickListener(new BackClickListener());
		et_search.setOnKeyListener(new SearchListener());
		
		initUI();
	}

	public void setBackClickListener(OnClickListener clickListener) {
		this.backClickListener = clickListener;

		iv_back.setOnClickListener(backClickListener);
	}

	public void setSearchClickListener(OnClickListener clickListener) {
		this.searchClickListener = clickListener;
		
		if(searchClickListener == null)
			return;

		iv_search.setOnClickListener(searchClickListener);
	}

	public void setSearchListener(OnClickListener clickListener) {
		this.searchClickListener = clickListener;

		if(searchClickListener == null)
			return;

		iv_search.setOnClickListener(searchClickListener);
	}

	public void setOnEnterListener(SearchEnterListener searchEnterListener) {
		this.searchEnterListener = searchEnterListener;

		if(searchEnterListener == null)
			return;

	}

	public void setBackImageVisible(int backImageVisible) {
		this.backImageVisible = backImageVisible;
		initUI();
	}

	public void setCancelVisible(int cancelVisible) {
		this.cancelVisible = cancelVisible;
		initUI();
	}

	private void initUI(){
		if(iv_back == null)
			return;

		iv_back.setVisibility(backImageVisible);
		tv_cancel.setVisibility(cancelVisible);
	}

	public void setHint(String hintText){
		et_search.setHint(hintText);
	}


	
	class BackClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			((Activity)context).finish();
		}
		
	}

	class SearchListener implements OnKeyListener{
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
				if(searchEnterListener != null){
					searchEnterListener.onEnter(et_search.getText().toString().trim());
				}
				return true;
			}
			return false;
		}

	}

}
