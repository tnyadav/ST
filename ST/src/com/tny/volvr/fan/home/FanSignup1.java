package com.tny.volvr.fan.home;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

import com.tny.utils.CheckUser;
import com.tny.utils.SignupDetail;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.volvr.search.R;

public class FanSignup1 extends Activity {

	private EditText username;
	private EditText password;
	private EditText conformpassword;
	private EditText email;
	private Button back,next;
	String strImgepath="";
	String emailformat="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	Context context;
	Handler mHandler = new Handler();
	private static String t="HomeStripperSignup1";
	String strName,strUsername,strPassword,strConformPassword,strEmail;
	SharedPreferences mySharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fan_signup_1);
		context=this;
		mySharedPreferences = getSharedPreferences("SIGNUP_1", Activity.MODE_PRIVATE);
		setContent();
	}

	private void setContent()
	{
	//	name=(EditText)findViewById(R.id.stripper_signup1_name);
	//	Util.textFormatterCapitalizeFirstLetter(name);
		username=(EditText)findViewById(R.id.fan_signup1_user_name);
		username.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				// TODO Auto-generated method stub
				if (!hasFocus) {
					
					CheckUser.checkDetail(context, username, "check_username");
				
				}
			}
		});
		
		password=(EditText)findViewById(R.id.fan_signup1_password);
		Util1.textPasswordShowText(password);
		conformpassword=(EditText)findViewById(R.id.fan_signup1_conformpassword);
		Util1.textPasswordShowText(password);
		email=(EditText)findViewById(R.id.fan_signup1_email);
		email.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				// TODO Auto-generated method stub
				if (!hasFocus) {
					
					CheckUser.checkDetail(context, email, "check_user_email");
				
				}
			}
		});
		
		//imagepath=(EditText)findViewById(R.id.fan_signup1_image);
		back=(Button)findViewById(R.id.fan_signup1_back);
		back.setOnClickListener(listener);
		next=(Button)findViewById(R.id.fan_header_next);
		next.setOnClickListener(listener);
		//picture=(Button)findViewById(R.id.fan_signup1_selectimage);
		//picture.setOnClickListener(listener);
	}
	
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.fan_header_next:
				next();	
				break;
			case R.id.fan_signup1_back:
				onBackPressed();	
				break;

		/*	case R.id.fan_signup1_selectimage:
				Intent i = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, 1);
				break;*/

			default:
				break;
			}

		}
	};

	private void next()
	{
		
		strUsername=username.getText().toString();
		strPassword=password.getText().toString();
		strConformPassword=conformpassword.getText().toString();
		strEmail=email.getText().toString().trim();
		
		if (strUsername.length()<4||strUsername.length()>20) {
			UIUtils.showMessage(context, "Message", "Your user name must be at least 4 characters, and not beyond 20 characters long.");
			return;
		} else if (strPassword.equals("")) {
			UIUtils.showMessage(context, "Message", "You have to input your user password.");
			return;
		}else if (strConformPassword.equals("")) {
			UIUtils.showMessage(context, "Message", "You have to input your confirm password.");
			return;
		}else if (strPassword.length()<4||strPassword.length()>20) {
			UIUtils.showMessage(context, "Message", "Your password must be at least 4 characters, and not beyond 20 characters long.");
			return;
		}
		else if (!strPassword.equals(strConformPassword)) {
			UIUtils.showMessage(context, "Message", "Password not match.");
			return;
		}else if (strEmail.equals("")) {
			UIUtils.showMessage(context, "Message", "You have to input your email address.");
			return;
		}else if(!Pattern.compile(emailformat).matcher(strEmail).matches())
		{
			UIUtils.showMessage(context, "Message", "The email address provided is incorrect. Please verify and try again.");
			return;
		}
		Intent i=new Intent(context, FanSignup2.class);
		startActivity(i);
	}

	@Override
	protected void onPause() {
		super.onPause();
		SignupDetail.S_UserName=username.getText().toString();
		SignupDetail.S_Password=password.getText().toString();
		SignupDetail.S_Email=email.getText().toString().trim();

	}
}

