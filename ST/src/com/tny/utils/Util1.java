
package com.tny.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public final class Util1 {
 
    public static void textFormatterCapitalizeFirstLetter(final EditText edEmailText1)
	{
		final TextWatcher upperCaseTextWatcher = new TextWatcher()
		{
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				if (edEmailText1.getText().toString().equals(""));
			}

			public void onTextChanged(CharSequence charSequence, int start, int before, int count)
			{
				Log.e("start", start + "");
				Log.e("before", before + "");
				if (before == 0)
				{
					String s1 = edEmailText1.getText().toString();
					if (s1.length() == 1)
					{
						edEmailText1.setText(s1.toUpperCase());
						edEmailText1.setSelection(edEmailText1.getText().length());
					}
					else
						if (start > 0 && (edEmailText1.getText().toString().charAt(start - 1) + "").equals(" "))
						{
							String s = edEmailText1.getText().toString();
							String d = edEmailText1.getText().toString().charAt(start) + "";
							s = s.substring(0, s.length() - 1);
							edEmailText1.setText(s + d.toUpperCase());

							edEmailText1.setSelection(edEmailText1.getText().length());
						}
				}
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		};
		edEmailText1.addTextChangedListener(upperCaseTextWatcher);
	}
    
    public static void textPasswordShowText(final EditText edEmailText1)
	{
		final TextWatcher upperCaseTextWatcher = new TextWatcher()
		{
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				if (edEmailText1.getText().toString().equals(""));
			}

			public void onTextChanged(CharSequence charSequence, int start, int before, int count)
			{
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
				}
			}, 1000);
			}
			@Override
			public void afterTextChanged(Editable s)
			{
			}
		};
		edEmailText1.addTextChangedListener(upperCaseTextWatcher);
	}
    
    public static boolean isFieldEmpty(String field) 
	{
		if (field.trim().length() > 0) 
		{
			return true;
		}
		return false;
	}
    
    public static boolean isNetworkAvailable(Context context) 
	{
		try 
		{
			ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
					|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
							.getState() == NetworkInfo.State.CONNECTING) 
			{
				return true;
			} 
			else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.getState() == NetworkInfo.State.CONNECTED
					|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
							.getState() == NetworkInfo.State.CONNECTING) 
			{
				return true;
			} 
				return false;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
    }
    

    
    public static byte[] getbyteArrayFromURL(String src) {
    	byte[] bytearray_of_drawable=null;
    	try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG,
					100, outstream);
			bytearray_of_drawable = outstream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return bytearray_of_drawable;
    }
    
    public static byte[] getbyteArrayFromSDCardPath(String src) {
    	byte[] bytearray_of_drawable=null;
    	Log.e("SdcardimagePath=",src);
    	String[] imagePath=src.split("//");
    	Log.e("SdcardimagePath=", imagePath[0]);
    	try {
//    		BitmapFactory.Options options = new BitmapFactory.Options();
//    		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//    		Bitmap bitmap = BitmapFactory.decodeFile(src, options);
    		
			Drawable d = Drawable.createFromPath(imagePath[1]);
			Bitmap bitmap = ((BitmapDrawable) d)
					.getBitmap();
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG,
					100, outstream);
			bytearray_of_drawable = outstream.toByteArray();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	return bytearray_of_drawable;
    }
    
    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

		final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

		int h= (int) (newHeight*densityMultiplier);
		int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

		photo=Bitmap.createScaledBitmap(photo, w, h, true);

		return photo;
		}
    
 
    public static ArrayList<String> parsePaymentData(String paymentResponse) {
    	ArrayList<String> paymentResposeList=new ArrayList<String>();
    	try
		{
			JSONObject objJson = new JSONObject(paymentResponse);
			//			JSONObject objPaymentJson = objJson.getJSONObject("payment");
			//			JSONObject objClientJson = objJson.getJSONObject("client");
			JSONObject objPaymentProofJson = objJson.getJSONObject("proof_of_payment")
					.getJSONObject("adaptive_payment");
//			strpayment_key=objPaymentProofJson.getString("pay_key");
			paymentResposeList.add(objPaymentProofJson.getString("pay_key"));
			//			u.setAmount(objPaymentJson.getString("amount"));
			//			u.setCurrency_code(objPaymentJson.getString("currency_code"));
			//			u.setShort_description(objPaymentJson.getString("short_description"));
			//
			//			u.setPlatform(objClientJson.getString("platform"));
			//			u.setPaypal_sdk_version(objClientJson.getString("paypal_sdk_version"));
			//			u.setProduct_name(objClientJson.getString("product_name"));
			//			u.setEnvironment(objClientJson.getString("environment"));
			//
			//			u.setPay_key(objPaymentProofJson.getString("pay_key"));
			//			u.setTimestamp(objPaymentProofJson.getString("timestamp"));
			//			u.setPayment_exec_status(objPaymentProofJson.getString("payment_exec_status"));
			//			u.setApp_id(objPaymentProofJson.getString("app_id"));
		}
		catch (JSONException e)
		{
			System.err.println(e.getLocalizedMessage());
			;
		}
    	return paymentResposeList;
	}
    public static Bitmap decodeFile(File f){
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

	        //The new size we want to scale to
	        final int REQUIRED_SIZE=70;

	        //Find the correct scale value. It should be the power of 2.
	        int scale=1;
	        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	            scale*=2;

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	    } catch (FileNotFoundException e) {}
	    return null;
	}
    
}
