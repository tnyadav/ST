package com.tny.utils;

import java.io.InputStream;
import java.util.Calendar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ProjectUtils {
	
	public static String getAge(String birthday){
		String ageS="";
		try{
	    	String[] bd=birthday.split("/");
	    	Log.i("ProjectUtils", birthday);
	    	int year, month,  day;
	    	year=Integer.parseInt(bd[2]);
	    	month=Integer.parseInt(bd[1]);
	    	day=Integer.parseInt(bd[0]);
	    	
	        Calendar dob = Calendar.getInstance();
	        Calendar today = Calendar.getInstance();

	        dob.set(year, month, day); 

	        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

	        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
	            age--; 
	        }

	        Integer ageInt = new Integer(age);
	        ageS = ageInt.toString();
		}
		catch (Exception e) {
		}
	        return ageS;  
	    }
	public static Bitmap getBitmapFromURL(String src) {
        try {
        	InputStream in = new java.net.URL(src).openStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(in);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
