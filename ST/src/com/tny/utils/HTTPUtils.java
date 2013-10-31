package com.tny.utils;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class HTTPUtils {
//	
//	public static String HTTPPost(String urlAddress, String... postDataPair) {
//		HttpParams myParams = new BasicHttpParams();
//
//		HttpConnectionParams.setConnectionTimeout(myParams, 10000);
//        HttpConnectionParams.setSoTimeout(myParams, 10000);
//      
//        
//		DefaultHttpClient hc=new DefaultHttpClient(myParams);   
//		ResponseHandler <String> res=new BasicResponseHandler();
//		
//		HttpPost postMethod=new HttpPost(urlAddress);
//			
//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(postDataPair.length / 2);
//		
//		for (int i = 0; i < postDataPair.length; i += 2) {
//			nameValuePairs.add(new BasicNameValuePair(postDataPair[i], postDataPair[i + 1]));
//		}
//		
//		String response = "";
//		try {
//			postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			response = hc.execute(postMethod,res);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return response;
//	}
	public static  String executeHttpGet(List<NameValuePair> pairs, String mUrl)
	 {
	  String responseData;
	  try{
		  HttpParams httpParameters = new BasicHttpParams();
		  HttpConnectionParams.setConnectionTimeout(httpParameters,30000);
		  DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);

//	  HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(mUrl);
	        httppost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));

	HttpResponse response = httpclient.execute(httppost);
	HttpEntity getresponse=response.getEntity();
	responseData=EntityUtils.toString(getresponse);
	 responseData = responseData.trim();
	   Log.e("rsponse in utility class...",responseData);
	  }  
	  catch (Exception e) 
	  {
	   e.printStackTrace();
	   responseData="~Exception~";
	  }
	  return responseData;
	 }


	 public static boolean isNetworkAvailable(Context context)
	 {
	  ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	  if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
	    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING)
	  {
	   return true;
	  }
	  else
	   if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
	   || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING)
	   {
	    return true;
	   }
	   else
	    return false;
	 }

	 public static void textFormatterCapitalizeFirstLetter(final EditText edEmailText1)
	 {
	  final TextWatcher upperCaseTextWatcher = new TextWatcher()
	  {
	   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
	   {
	    if (edEmailText1.getText().toString().equals(""))
	     ;
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
	    //    
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
}
