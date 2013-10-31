package com.tny.volvr.fan.home;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.FanInfo;
import com.volvr.search.R;

public class FanEditProfile1 extends BaseFragment {

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
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
         view       =   inflater.inflate(R.layout.fan_signup_1, container, false);
         mySharedPreferences = getActivity().getSharedPreferences("SIGNUP_1", Activity.MODE_PRIVATE);
         setContent();
        return view;
	}

	private void setContent()
	{
	//	name=(EditText)view.findViewById(R.id.stripper_signup1_name);
	//	Util.textFormatterCapitalizeFirstLetter(name);
		username=(EditText)view.findViewById(R.id.fan_signup1_user_name);
		username.setText(FanInfo.fanproFileInfo.user_name);
		username.setEnabled(false);
		
		password=(EditText)view.findViewById(R.id.fan_signup1_password);
		password.setVisibility(View.GONE);
		
		conformpassword=(EditText)view.findViewById(R.id.fan_signup1_conformpassword);
		conformpassword.setVisibility(View.GONE);
		
		email=(EditText)view.findViewById(R.id.fan_signup1_email);
		email.setText(FanInfo.fanproFileInfo.user_email);
		email.setEnabled(false);
		
		//imagepath=(EditText)view.findViewById(R.id.fan_signup1_image);
		back=(Button)view.findViewById(R.id.fan_signup1_back);
		back.setOnClickListener(listener);
		next=(Button)view.findViewById(R.id.fan_header_next);
		next.setOnClickListener(listener);
		//picture=(Button)view.findViewById(R.id.fan_signup1_selectimage);
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
				getActivity().onBackPressed();	
				break;

			default:
				break;
			}

		}
	};

	private void next()
	{
		
		mActivity.pushFragments(AppConstants.TAB_HOME,
				new FanEditProfile2(), true, true);
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}
}

