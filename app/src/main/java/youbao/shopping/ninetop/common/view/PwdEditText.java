package youbao.shopping.ninetop.common.view;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import youbao.shopping.ninetop.activity.Validatable;

import youbao.shopping.R;

public class PwdEditText extends LinearLayout implements Validatable{

	private EditText et_password;
	private ImageView iv_show_pwd;
	private View view_line;
	private Context context;
	private boolean showPassword = false;

	public PwdEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_edit_pwd, this);
		
		iv_show_pwd = (ImageView) view.findViewById(R.id.iv_show_pwd);
		et_password = (EditText) view.findViewById(R.id.et_password);
		view_line = view.findViewById(R.id.view_line);

		changePwdStatus();
		iv_show_pwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPassword = !showPassword;
				changePwdStatus();
			}
		});
	}

	private void changePwdStatus(){
		if (showPassword) {
			et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			iv_show_pwd.setImageResource(R.mipmap.password_show);
		} else {
			et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			iv_show_pwd.setImageResource(R.mipmap.password_hidden);
		}

	}
	public void setHintText(String hintText){
		et_password.setHint(hintText);
	}
	public String getText(){
		return et_password.getText().toString().trim();
	}

	public void hideLine(){
		view_line.setVisibility(View.GONE);
	}

	@Override
	public String validate() {
		String value = getText();
		if(value.length() == 0)
			return "密码不能空";

		if(value.length() < 6)
			return "密码长度不能小于6";

		return null;
	}
}
