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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class AsyncWebServiceProcessingTask extends AsyncTask<String,Void , String>{

	private ProgressDialog bar; 
	private Context context;
	private Handler handler;
	private List<NameValuePair> entity;
	private String message;
	private Callback callback;

	public AsyncWebServiceProcessingTask(Context context,Handler handler,List<NameValuePair> entity,String message)
	{
		this.context=context;
		this.handler=handler;
		this.entity=entity;
		this.message=message;
	}
	public AsyncWebServiceProcessingTask(Context context,List<NameValuePair> entity,String message,Callback callback)
	{
		this.context=context;
		
		this.entity=entity;
		this.message=message;
		this.callback=callback;
	}

	@Override
	protected String doInBackground(String... params) {
		String responseData="no response";
		try{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,30000);
			DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);

			HttpPost httppost = new HttpPost(Constant.SERVER_URL);
			httppost.setEntity(new UrlEncodedFormEntity(entity, HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity getresponse=response.getEntity();
			responseData=EntityUtils.toString(getresponse);
			responseData = responseData.trim();
			Log.e("rsponse in AsyncWebServiceProcessingTask class...",responseData);
		}  
		catch (Exception e) 
		{
			e.printStackTrace();

		}
		return responseData;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		bar.dismiss();
		if (result.equals("no response")) {
			UIUtils.showNetworkErrorMessage(context);
		}else {
			JsonResponse.JsonResponse=result;
			if (handler!=null) {
				handler.sendEmptyMessage(0);
			}
			
			if (callback!=null) {
				callback.run(result);
			}
			
		}

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		bar = new ProgressDialog(context);
		bar.setMessage(message);
		bar.setCancelable(false);
		bar.show();

	}

}
