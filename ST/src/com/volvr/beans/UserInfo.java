package com.volvr.beans;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfo {
public static String USERTYPE="";
Context context;
public static void clearData(Context context) {
	
	SharedPreferences mySharedPreferences1,mySharedPreferences2, mySharedPreferences3;

	mySharedPreferences1=context.getSharedPreferences("SIGNUP_1", 0);
	mySharedPreferences2 = context.getSharedPreferences("SIGNUP_2", 0);
	mySharedPreferences3 = context.getSharedPreferences("SIGNUP_3",0);
	
	 SharedPreferences.Editor editor = mySharedPreferences3.edit();
	 editor.putString("height", "");
	 editor.putString("stats", "");
	 editor.putString("agedata", "");
	 editor.putString("serveragedata","");
	 editor.putString("haircolordata", "");
	 editor.commit();
	 
			
	 SharedPreferences.Editor editor2 = mySharedPreferences2.edit();
	 editor2.putString("mZipCode", "");
	 editor2.putString("mWageRate", "");
	 editor2.putString("mPayPalId", "");
	 editor2.putInt("otherlistsize", 0);	
	 editor2.putString("strClubName", "");
	 editor2.putString("strClubAddress", "");
	 editor2.putString("strClubNumber", "");
	 editor2.commit();
}
}
