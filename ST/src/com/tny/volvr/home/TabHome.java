package com.tny.volvr.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class TabHome extends Activity {
	private Button stripper;
	private Button club;
	private Button fans;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(1);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stripper_search);
		stripper = (Button)findViewById(R.id.strippersearch_stripper);
		stripper.setOnClickListener(listener);
		club = (Button)findViewById(R.id.strippersearch_club);
		club.setOnClickListener(listener);
		fans =  (Button)findViewById(R.id.strippersearch_fans);
		fans.setOnClickListener(listener);

		SharedPreferences	mySharedPreferences = getSharedPreferences("SIGNUP_3", Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = mySharedPreferences.edit();  
		editor.putString("height", "");
		editor.putString("stats", "");
		editor.putString("agedata", "");
		editor.putString("serveragedata","");
		editor.putString("haircolordata", "");
		editor.commit();
		SharedPreferences	mySharedPreferences2 = getSharedPreferences("SIGNUP_2", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor2 = mySharedPreferences2.edit();
		editor2.putString("mZipCode", "");
		editor2.putString("mWageRate", "");
		editor2.putString("mPayPalId", "");
		editor2.putInt("otherlistsize", 0);	
		editor2.putString("strClubName", "");
		editor2.putString("strClubAddress", "");
		editor2.putString("strClubNumber", "");
		
		editor2.putString("mCountry", "");
		editor2.putString("mState", "");
		editor2.commit();
	}


	private OnClickListener listener        =   new View.OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.strippersearch_stripper:
				UserInfo.USERTYPE="stripper";
				Intent i=new Intent(getApplicationContext(), HomeLogin.class);
				startActivity(i);
				break;
			case R.id.strippersearch_club:
				UserInfo.USERTYPE="club";
				Intent i1=new Intent(getApplicationContext(), HomeLogin.class);
				startActivity(i1);	
				break;
			case R.id.strippersearch_fans:
				UserInfo.USERTYPE="fan";
				Intent i11=new Intent(getApplicationContext(), HomeLogin.class);
				startActivity(i11);	
				break;
			default:
				break;
			}

		}
	};
}
