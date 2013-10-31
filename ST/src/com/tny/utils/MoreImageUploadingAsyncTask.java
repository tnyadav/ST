package com.tny.utils;


import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.volvr.beans.StripperInfo;

public class MoreImageUploadingAsyncTask extends AsyncTask<String,Void , String>{

	private ProgressDialog bar; 
	private Context context;
	private Handler handler;
	private String message,action="";
	ArrayList<String> imageUrl=new ArrayList<String>();
	public MoreImageUploadingAsyncTask(Context context,Handler handler,String action,String message,ArrayList<String> imageUrl)
	{
		this.imageUrl=imageUrl;
		this.context=context;
		this.handler=handler;
		this.message=message;
		this.action=action;
	}

	@Override
	protected String doInBackground(String... params) {
		String responseData="no response";
		byte[] bytearray_of_drawable=null;
		try{
			Log.e("imageUrl size=", ""+imageUrl.size());
			for (int i = 0; i < imageUrl.size(); i++) {
				if(imageUrl.get(i).contains("http"))
				{
					bytearray_of_drawable=Util1.getbyteArrayFromURL(imageUrl.get(i));
				}
				else /*if(imageUrl.contains("sdCard"))*/
				{
					bytearray_of_drawable=Util1.getbyteArrayFromSDCardPath(imageUrl.get(i));
				}
				HttpPost httppost = new HttpPost(Constant.SERVER_URL);
				HttpParams params1 = new BasicHttpParams();
				params1.setParameter(
						CoreProtocolPNames.PROTOCOL_VERSION,
						HttpVersion.HTTP_1_1);
				HttpClient httpclient = new DefaultHttpClient(params1);
				MultipartEntity multipartEntity = new MultipartEntity();
				multipartEntity.addPart("action", new StringBody(
						action));
				multipartEntity.addPart("user_id", new StringBody(
						StripperInfo.user_id));
				multipartEntity.addPart("user_avatar",
						new ByteArrayBody(bytearray_of_drawable,
								"image/bmp",String.valueOf(i+1)+".png"));
				
				httppost.setEntity(multipartEntity);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity getresponse = response.getEntity();
				String jsonstr = EntityUtils.toString(getresponse);
				Log.d("response", "" + jsonstr);
				responseData=jsonstr;
		  }  
		}
		catch (Exception e) 
		{
			e.printStackTrace();

		}
		return responseData;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		bar.dismiss();
		if (result.equals("no response")) {
			UIUtils.showNetworkErrorMessage(context);
		}else {
			JsonResponse.JsonResponse=result;
			handler.sendEmptyMessage(0);
		}

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		bar = new ProgressDialog(context);
		bar.setMessage(message);
		bar.setCancelable(false);
		bar.show();

	}

}
