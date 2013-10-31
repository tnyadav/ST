package com.tny.volvr.dancer.home;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tny.utils.SignupBean;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class DancerEditPrfile1  extends BaseFragment {
	private View view;
	private EditText name,username;
	private EditText oldpassword;
	private EditText newpassword;
	private EditText conformnewpassword;
	private EditText imagepath;
	private Button back,next;
	private Button picture;
	String strImgepath="";
	String emailformat="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	com.tny.utils.MyTextView changePwd;
	Handler mHandler = new Handler();
	private static String t="HomeStripperSignup1";
	boolean flag=false;
	 String strName,strOldPassword,strNewPassword,strconformnewpassword;
	
	SharedPreferences mySharedPreferences;
	private EditText confirmemail,contactNo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
         view       =   inflater.inflate(R.layout.home_signup_1, container, false);
         mySharedPreferences = getActivity().getSharedPreferences("SIGNUP_1", Activity.MODE_PRIVATE);
         setContent();
        return view;
	}
	private void setContent()
	{
		changePwd=(com.tny.utils.MyTextView)view.findViewById(R.id.changePwd);
		changePwd.setVisibility(View.VISIBLE);
		changePwd.setOnClickListener(listener);
		name=(EditText)view.findViewById(R.id.stripper_signup1_name);
		Util1.textFormatterCapitalizeFirstLetter(name);
		username=(EditText)view.findViewById(R.id.stripper_signup1_user_name);
		oldpassword=(EditText)view.findViewById(R.id.stripper_signup1_password);
		oldpassword.setHint("Old Password");
		oldpassword.setVisibility(View.GONE);
		Util1.textPasswordShowText(oldpassword);
		newpassword=(EditText)view.findViewById(R.id.stripper_signup1_conformpassword);
		newpassword.setHint("New Password");
		newpassword.setVisibility(View.GONE);
		Util1.textPasswordShowText(newpassword);
		conformnewpassword=(EditText)view.findViewById(R.id.stripper_signup1_email);
		conformnewpassword.setHint("Confirm Password");
		conformnewpassword.setVisibility(View.GONE);
		confirmemail=(EditText)view.findViewById(R.id.stripper_signup1_confirmemail);
		confirmemail.setVisibility(View.GONE);
		contactNo=(EditText)view.findViewById(R.id.stripper_signup1_contactno);
		Util1.textPasswordShowText(conformnewpassword);
		conformnewpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		imagepath=(EditText)view.findViewById(R.id.stripper_signup1_image);

		back=(Button)view.findViewById(R.id.stripper_signup1_back);
		back.setOnClickListener(listener);
		next=(Button)view.findViewById(R.id.stripper_header_next);
		next.setOnClickListener(listener);
		picture=(Button)view.findViewById(R.id.stripper_signup1_selectimage);
		picture.setOnClickListener(listener);
		setValue();
	}
	private void setValue() {
		name.setText(StripperInfo.user_fullname);
		username.setText(StripperInfo.user_name);
		username.setEnabled(false);
		contactNo.setText(fileName(StripperInfo.user_phone));
		imagepath.setText(fileName(StripperInfo.user_avatar));
	}
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.stripper_header_next:
				next();	
				break;
			case R.id.stripper_signup1_back:
				getActivity().onBackPressed();
				break;
				
			case R.id.changePwd:
				if(flag==false)
				{
					flag=true;
					oldpassword.setVisibility(View.VISIBLE);
					newpassword.setVisibility(View.VISIBLE);
					conformnewpassword.setVisibility(View.VISIBLE);
				}
				else
				{
					flag=false;
					oldpassword.setVisibility(View.GONE);
					newpassword.setVisibility(View.GONE);
					conformnewpassword.setVisibility(View.GONE);
				}
				break;

			case R.id.stripper_signup1_selectimage:
				Intent i = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				getActivity().startActivityForResult(i, 1);
				break;

			default:
				break;
			}

		}
	};

	private void next()
	{
		  strName=name.getText().toString();
		//  strUsername=username.getText().toString();
		  strOldPassword=oldpassword.getText().toString();
		  strNewPassword=newpassword.getText().toString();
		  strconformnewpassword=conformnewpassword.getText().toString().trim();
		 
		  if (strName.equals("")) {
			UIUtils.showMessage(getActivity(), "Message", "You have to input your name.");
			return;
		  }
		  if(flag==true)
		  {
		   if (strOldPassword.equals("")) {
			  UIUtils.showMessage(getActivity(), "Message", "You have to input your current password.");
			return;
		}else if (strNewPassword.equals("")) {
			UIUtils.showMessage(getActivity(), "Message", "You have to input your new password.");
			return;
		}else if (strNewPassword.length()<4||strNewPassword.length()>20) {
			UIUtils.showMessage(getActivity(), "Message", "Password must contain at 4 ~ 20 of chracters");
			return;
		}else if (strconformnewpassword.equals("")) {
			UIUtils.showMessage(getActivity(), "Message", "You have to input your new password.");
			return;
		}else if (!strOldPassword.equals(StripperInfo.PASSWORD)) {
			UIUtils.showMessage(getActivity(), "Message", "Current Password not match.");
			return;
		}
		else if (!strNewPassword.equals(strconformnewpassword)) {
			UIUtils.showMessage(getActivity(), "Message", "Password not match.");
			return;
		}
	}
		  mActivity.pushFragments(AppConstants.TAB_HOME, new DancerEditPrfile2(),true,true);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1&&resultCode==getActivity().RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
			cursor.moveToFirst(); 
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			strImgepath = cursor.getString(columnIndex);
			SignupBean.mPicture=strImgepath;
			Log.i(t, strImgepath);
			cursor.close();             
			imagepath.setText(fileName(strImgepath));
			//		            imgUpload.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}else {
			
		}
	}

	public String fileName(String f) {
		Log.i("fileName", ""+f);
		Uri u = Uri.parse(f);

		File f1 = new File("" + u);
		return f1.getName();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 strName=name.getText().toString();
		 
		 strNewPassword=newpassword.getText().toString();
		 
		 SharedPreferences.Editor editor = mySharedPreferences.edit();
		 editor.putString("mName", strName);
		// editor.putString("mUserName", strUsername);
		 editor.putString("mPassword", strNewPassword);
		
		 editor.putString("mPicture", strImgepath);
		 editor.commit();
		 
	}
	
}
