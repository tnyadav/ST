package com.tny.volvr.club.home;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tny.utils.SignupDetail;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class ClubEditProfile1 extends BaseFragment {

	private EditText name,username;
	private EditText password;
	private EditText conformpassword;
	private EditText email;
	private EditText imagepath;
	private Button back,next;
	private Button picture;
	String strImgepath="";
	String emailformat="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	//Context context;
	Handler mHandler = new Handler();
	private static String t="HomeStripperSignup1";
	String strName,strUsername,strPassword,strConformPassword,strEmail;
	private View view;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
         view       =   inflater.inflate(R.layout.home_signup_1, container, false);
       
         setContent();
        return view;
	}
	private void setContent()
	{
		name=(EditText)view.findViewById(R.id.stripper_signup1_name);
		Util1.textFormatterCapitalizeFirstLetter(name);
		name.setText(StripperInfo.user_fullname);
		username=(EditText)view.findViewById(R.id.stripper_signup1_user_name);
		username.setText(StripperInfo.user_name);
		username.setEnabled(false);
		
		password=(EditText)view.findViewById(R.id.stripper_signup1_password);
		
		password.setVisibility(View.GONE);
		conformpassword=(EditText)view.findViewById(R.id.stripper_signup1_conformpassword);
	
		conformpassword.setVisibility(View.GONE);
		email=(EditText)view.findViewById(R.id.stripper_signup1_email);
		email.setText(StripperInfo.user_email);
		email.setEnabled(false);
		
		imagepath=(EditText)view.findViewById(R.id.stripper_signup1_image);
		//imagepath.setVisibility(View.GONE);
		back=(Button)view.findViewById(R.id.stripper_signup1_back);
		back.setOnClickListener(listener);
		next=(Button)view.findViewById(R.id.stripper_header_next);
		next.setOnClickListener(listener);
		picture=(Button)view.findViewById(R.id.stripper_signup1_selectimage);
		picture.setOnClickListener(listener);
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
			case R.id.stripper_signup1_selectimage:
				Intent i = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, 1);
				break;
			default:
				break;
			}

		}
	};
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1&&resultCode==Activity.RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = mActivity.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
			cursor.moveToFirst(); 
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			strImgepath = cursor.getString(columnIndex);
			SignupDetail.S_Picture=strImgepath;

			Log.i(t, strImgepath);
			cursor.close();             
			imagepath.setText(fileName(strImgepath));
			//		            imgUpload.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}else {

		}
	}

	private void next()
	{
		strName=name.getText().toString();
		SignupDetail.S_UserFname=strName;
		if (strName.equals("")) {
			UIUtils.showMessage(mActivity, "Message", "You have to input your name.");
			return;
		}
		mActivity.pushFragments(AppConstants.TAB_HOME,
				new ClubEditProfile2(), true, true);
	}

	

	public String fileName(String f) {
		Log.i("fileName", ""+f);
		Uri u = Uri.parse(f);

		File f1 = new File("" + u);


		return f1.getName();
	}
	
}

