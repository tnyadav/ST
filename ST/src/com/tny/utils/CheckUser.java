package com.tny.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

public class CheckUser {
	private static String t="CheckUser";
	static Handler mHandler = new Handler();
	static String message="",actionstr="";
	public static void checkDetail(final Context context,final EditText editText,final String action) {
		
		if (action.equals("check_username")) {
			message="user name";
			actionstr="user_name";
		}else {
			message="EmailId";
			actionstr="user_email";
		}
		
		UIUtils.showSimpleSpinProgressDialog(context, "validating...");
		Thread thread = new Thread(new Runnable() 
		{
			public void run() {
				final String response;
				try {
					List<NameValuePair> paramList = new ArrayList<NameValuePair>();
					paramList.add(new BasicNameValuePair("action", action));
					paramList.add(new BasicNameValuePair(actionstr,""+editText.getText().toString()));
					response = HTTPUtils.executeHttpGet(paramList,
							Constant.SERVER_URL);
					Log.i(t, "" + response);
					UIUtils.removeSimpleSpinProgressDialog();
				//	Handler mHandler=new Handler();
					mHandler.post(new Runnable() {
						public void run() {
							if (response == null || response.equals("")) {
								System.out
										.println("(SignInActivity)response is null ");
								Log.i(t, "(SignInActivity)response is null ");
							} else {
								try {
									JSONObject obj = new JSONObject(response);
									Log.i(t,
											"(SignInActivity)status is success  "
													+ response);
									if(obj.getString("status").equalsIgnoreCase("error"))
									{
									UIUtils.showMessage(context, "Message",
											""+obj.getString("msg"));
									editText.setText("");
									}
								} catch (JSONException e) {
								}
							}
						}
					});
				} 
				catch (Exception e) {
					UIUtils.removeSimpleSpinProgressDialog();
					UIUtils.showMessage(context, "Message", "Access Failed.");
				}
			}

		});
		thread.start();
	}

}
