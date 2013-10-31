package com.tny.volvr.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;

import com.example.tnutil.Callback;
import com.example.tnutil.Util;
import com.tny.util.shorting.Fruit;
import com.tny.util.shorting.MyCallback;
import com.tny.util.shorting.MyComparator;
import com.tny.utils.Constant;
import com.tny.utils.HTTPUtils;
import com.tny.utils.UIUtils;
import com.tny.volvr.home.TabHome;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class SplashActivity extends Activity {
	
	
	Context context;
	SharedPreferences mSharedPrefrences; 
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(1);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.splash);
			context=this;
			
	          /* startActivity(new Intent(SplashActivity.this,Login.class));
	        	finish();	*/   
	            	
	            	mSharedPrefrences = PreferenceManager.getDefaultSharedPreferences(context);
	        		
	        		if (!mSharedPrefrences.getBoolean("islogout", true)) {
	        			new DoLogin().execute();
	        			
	        		}else {
	        			
	        			Timer timer = new Timer();
	        	        timer.schedule(new TimerTask() {
	        	            @Override
	        	            public void run() {
	        			    Intent intent=new Intent(context,TabHome.class);
	        			   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        			    startActivity(intent);
	        			  finish();
	        	            }
	        	              }, 3000);	
	        		}
	            	
	            
    }
    
	class DoLogin extends AsyncTask<Void, Void, String>
	{
		
		//private ProgressDialog bar; 
		
		
		
		@Override
		protected String doInBackground(Void... params) {
			String response="";
			try {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair("action", "login"));
				paramList.add(new BasicNameValuePair("user_name",
						mSharedPrefrences.getString("username_key", "")));
				paramList.add(new BasicNameValuePair("user_password",
						mSharedPrefrences.getString("userpassword_key", "")));
				 response = HTTPUtils.executeHttpGet(paramList,
						Constant.SERVER_URL);
			} catch (Exception e) {
				
				Log.e("doInBackground", ""+e);
				e.printStackTrace();
			}	
			
			// TODO Auto-generated method stub
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.e("DoLogin", ""+result);
			if (!result.equals("")) {
				try {
						JSONObject obj = new JSONObject(result);
						if (obj.getString("status").equals("success")) {
							JSONObject user_type = new JSONObject(obj.getString("user"));
							StripperInfo.PASSWORD = mSharedPrefrences.getString("userpassword_key", "");
							String userType=user_type.getString("user_type");
						    UserInfo.USERTYPE=userType;
										
						    			if (userType.equalsIgnoreCase("stripper")) {
											 StripperInfo.parseStripperInfoForProfile(obj);	 
											
										}if (userType.equalsIgnoreCase("club")) {
											Clubprofile.parseProfileData(obj);
											
										}if (userType.equalsIgnoreCase("fan")) {
											
											FanInfo.parseFanProfile(obj.getJSONObject("user"));	
										}
						 	 
						 	Intent intent=new Intent(context,MainTabActivity.class);
						 	startActivity(intent);
					        finish();
							
						
						} else {
							UIUtils.showMessage(context, "Message",
									"Login Failed.");
						}
					} catch (Exception e) {
						e.printStackTrace();
						Util.showCustomDialog(context, "Error", "Network error has occured. Please check the network status of your phone and retry",
								"Retry", "Close", new Callback() {
									
									@Override
									public void ok() {
										// TODO Auto-generated method stub
										new DoLogin().execute();
									}
									
									@Override
									public void cancel() {
										// TODO Auto-generated method stub
										((Activity) context).finish();
										System.exit(0);
									}
								});	
					}
			
			}else {
				Util.showCustomDialog(context, "Error", "Network error has occured. Please check the network status of your phone and retry",
						"Retry", "Close", new Callback() {
							
							@Override
							public void ok() {
								// TODO Auto-generated method stub
								new DoLogin().execute();
							}
							
							@Override
							public void cancel() {
								// TODO Auto-generated method stub
								((Activity) context).finish();
								System.exit(0);
							}
						});	
			}
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
	
		}


		}
/*	private void showNetworkErrorMessage() {
		Builder dlg = new AlertDialog.Builder(context);
		dlg.setCancelable(false);
		dlg.setTitle("Error");
		dlg.setMessage("Network error has occured. Please check the network status of your phone and retry");
		dlg.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new DoLogin().execute();
			}
		});
		dlg.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
				System.exit(0);
			}
		});
		dlg.show();
	}*/
   @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
	ArrayList<Fruit> arrayList=new ArrayList<Fruit>();
	for (int i = 0; i < 5; i++) {
		Fruit fruit=new Fruit("name"+i, "desc"+(5-i), i);
		arrayList.add(fruit);
	}
	
	Collections.sort(arrayList,new MyComparator(new MyCallback() {
		
		@Override
		public int run(Object o1, Object o2) {
			// TODO Auto-generated method stub
			Fruit f1=(Fruit) o1;
			Fruit f2=(Fruit) o2;
			
			 return f1.getFruitDesc().compareTo(f2.getFruitDesc());
		}
	}));
//	Collections.sort(arrayList,new MyComparator());
	
	for (int i = 0; i < arrayList.size(); i++) {
	arrayList.get(i).toString();
	Log.e("xxxxxxxxxxxxx", arrayList.get(i).toString());
	}
}
}