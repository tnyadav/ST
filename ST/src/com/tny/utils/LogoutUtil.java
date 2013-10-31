package com.tny.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.fedorvlasov.lazylist.ImageLoader;
import com.tny.volvr.home.TabHome;

public class LogoutUtil {

	private static SharedPreferences mSharedPrefrences;
	
	
	public static void setLoginInfo(String userName,String userPassword,Context context) {
		mSharedPrefrences=PreferenceManager.getDefaultSharedPreferences(context);
		Editor ed = mSharedPrefrences.edit();
		ed.putString("username_key",userName);
		ed.putString("userpassword_key",userPassword);
		ed.putBoolean("islogout",false);
		ed.commit();
		
	}
	
	public static void logout(Context context,ImageLoader imageLoader) {
		
		imageLoader.clearCache();
		
		mSharedPrefrences=PreferenceManager.getDefaultSharedPreferences(context);
		Editor ed = mSharedPrefrences.edit();
		ed.putBoolean("islogout",true);
		ed.commit();
		Intent intent = new Intent(context, TabHome.class);
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    context.startActivity(intent);
	    ((Activity) context).finish();
	}

	
}
