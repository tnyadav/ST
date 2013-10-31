package com.tny.volvr.home;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tnutil.Callback;
import com.example.tnutil.Util;
import com.tny.utils.Constant;
import com.tny.utils.HTTPUtils;
import com.tny.utils.LogoutUtil;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.MainTabActivity;
import com.tny.volvr.club.home.ClubSignup1;
import com.tny.volvr.dancer.home.DancerSignup1;
import com.tny.volvr.fan.home.FanSignup1;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class HomeLogin extends Activity 
{
	EditText username;
	EditText password;
	com.tny.utils.MyTextView forgetpass;
	Button login;
	Button signup;
	private SharedPreferences loginInfo;
	private String PREFRENCES_NAME = "Remember";
	private CheckBox remember;
	String name = "", pass = "";
	private Dialog dialog;
	Context context;
	Handler mHandler = new Handler();
	private static String t = "StripperLogin";
	String emailformat = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	Intent i;
	private void setContent() {
		i=getIntent();
		username = (EditText) findViewById(R.id.login_username);
		password = (EditText) findViewById(R.id.login_password);
		Util1.textPasswordShowText(password);
		forgetpass = (com.tny.utils.MyTextView) findViewById(R.id.login_forgetpassword);
		forgetpass.setOnClickListener(listener);

		login = (Button) findViewById(R.id.login_login);
		login.setOnClickListener(listener);
		signup = (Button) findViewById(R.id.login_signup);
		signup.setOnClickListener(listener);
	}

	private void doSignIn() {
		final String strUserName = username.getText().toString();
		final String strPassword = password.getText().toString();

		if (strUserName.equals("")) {
			UIUtils.showMessage(context, "Message",
					"Please enter your username.");
			return;
		} else if (strPassword.equals("")) {
			UIUtils.showMessage(context, "Message",
					"Please enter password.");
			return;
		}

		UIUtils.showSimpleSpinProgressDialog(context, "Signing...");
		Thread thread = new Thread(new Runnable() {
			public void run() {
				final String response;
				try {
					List<NameValuePair> paramList = new ArrayList<NameValuePair>();
					paramList.add(new BasicNameValuePair("action", "login"));
					paramList.add(new BasicNameValuePair("user_name",
							strUserName));
					paramList.add(new BasicNameValuePair("user_password",
							strPassword));
					response = HTTPUtils.executeHttpGet(paramList,
							Constant.SERVER_URL);
					UIUtils.removeSimpleSpinProgressDialog();
					mHandler.post(new Runnable() {
						public void run() {
							if (response == null || response.equals("")) {
								Log.i(t, "(SignInActivity)response is null ");
							} else {
								try {
									JSONObject obj = new JSONObject(response);
									/*Log.i(t,"(SignInActivity)status is success  "
											+ response);*/
									if (obj.getString("status").equals("success")) {
										JSONObject user_type = new JSONObject(obj.getString("user"));
										StripperInfo.PASSWORD = strPassword;
										LogoutUtil.setLoginInfo(strUserName, strPassword, context);
										
										if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
										{
										if(UserInfo.USERTYPE.equalsIgnoreCase(user_type.getString("user_type")))
										{
											StripperInfo.parseStripperInfoForProfile(obj);
											Intent i = new Intent(context,
													MainTabActivity.class);
											startActivity(i);
											finish();
										}
										else
										{
											UIUtils.showMessage(context, "Message",
													"Login Failed.");
										}
										}
										else if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
										{
										if(UserInfo.USERTYPE.equalsIgnoreCase(user_type.getString("user_type")))
										{
											Clubprofile.parseProfileData(obj);
											Intent i = new Intent(context,
													MainTabActivity.class);
											startActivity(i);
											finish();
										}
										else
										{
											UIUtils.showMessage(context, "Message",
													"Login Failed.");
										}
										
										}else if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
										{
										if(UserInfo.USERTYPE.equalsIgnoreCase(user_type.getString("user_type")))
										{
											FanInfo.parseFanProfile(obj.getJSONObject("user"));
											Intent i = new Intent(context,
													MainTabActivity.class);
											startActivity(i);
											finish();
										}
										else
										{
											UIUtils.showMessage(context, "Message",
													"Login Failed.");
										}
										
										}
										else 
										{
											Intent i = new Intent(context,
													MainTabActivity.class);
											startActivity(i);
//											UIUtils.showMessage(context, "Message",
//													"Profile Screen is under development..");
										}
									} else {
										UIUtils.showMessage(context, "Message",
												"Login Failed.");
									}
								} catch (JSONException e) {
									Util.showCustomDialog(context, "Error", "Network error has occured. Please check the network status of your phone and retry", "Retry", "Close", new Callback() {
										
										@Override
										public void ok() {
											// TODO Auto-generated method stub
											doSignIn();
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
						}
					});
				} catch (Exception e) {
					UIUtils.removeSimpleSpinProgressDialog();
					UIUtils.showMessage(context, "Message", "Login Failed.");
				}

			}

		});
		thread.start();
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == login) {
				doSignIn();
			}
			else if (v == forgetpass) {
				forgetpwd();
			} else if (v == signup) {
				if (UserInfo.USERTYPE.equals("club")) {
					Intent i = new Intent(context, ClubSignup1.class);
					startActivity(i);
				}else if (UserInfo.USERTYPE.equals("fan")) {
					Intent i = new Intent(context, FanSignup1.class);
					startActivity(i);
				}
				else if (UserInfo.USERTYPE.equals("stripper")) {
					Intent i = new Intent(context, DancerSignup1.class);
					startActivity(i);
				}	
			}
		}
	};

	private void forgetpwd() {
		forgetpass.setPaintFlags(forgetpass.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.sendemail);
		final EditText message = (EditText) dialog
				.findViewById(R.id.registermessase);
		final TextView text = (TextView) dialog.findViewById(R.id.visibletext);
		TextView recover = (TextView) dialog.findViewById(R.id.registertitle);
		recover.setText("Recover your password");
		text.setText("Password will be send to your email address !");
		Button registerbtn = (Button) dialog.findViewById(R.id.registerbutton);
		Button continuebtn = (Button) dialog.findViewById(R.id.continuebutton);
		continuebtn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				if (Pattern.compile(emailformat)
						.matcher(message.getText().toString()).matches()) {
					UIUtils.showSimpleSpinProgressDialog(context,
							"Recovering...");
					Thread thread = new Thread(new Runnable() {
						public void run() {
							mHandler.post(new Runnable() {
								public void run() {
									try {
										HttpClient httpclient = new DefaultHttpClient();
										HttpPost httppost = new HttpPost(
												Constant.SERVER_URL);
										List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
												1);
										nameValuePairs
										.add(new BasicNameValuePair(
												"email", message
												.getText()
												.toString()));

										nameValuePairs
										.add(new BasicNameValuePair(
												"action",
												"forgotpassword"));
										nameValuePairs
										.add(new BasicNameValuePair(
												"user_email", message
												.getText()
												.toString()));

										httppost.setEntity(new UrlEncodedFormEntity(
												nameValuePairs));
										HttpResponse response = httpclient
												.execute(httppost);
										UIUtils.removeSimpleSpinProgressDialog();
										HttpEntity getresponse = response
												.getEntity();
										String jsonstr = EntityUtils
												.toString(getresponse);
										Log.e("noteresponse=", jsonstr);

										JSONObject json = new JSONObject(
												jsonstr);

										String akg = json.getString("status");
										if (akg.equalsIgnoreCase("success")) {
											UIUtils.removeSimpleSpinProgressDialog();
											dialog.dismiss();
											final Dialog dialog = new Dialog(
													context);
											dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
											dialog.setContentView(R.layout.contactme);
											TextView message = (TextView) dialog
													.findViewById(R.id.message);
											Button okbtn = (Button) dialog
													.findViewById(R.id.okbutton);
											message.setText("Check Your Email !");
											okbtn.setOnClickListener(new OnClickListener() {

												public void onClick(View arg0) {
													dialog.dismiss();
												}
											});
											dialog.show();
										} else {
											if (akg.equalsIgnoreCase("no")) {
												UIUtils.removeSimpleSpinProgressDialog();
												dialog.dismiss();
												final Dialog dialog = new Dialog(
														context);
												dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
												dialog.setContentView(R.layout.contactme);
												TextView message = (TextView) dialog
														.findViewById(R.id.message);
												Button okbtn = (Button) dialog
														.findViewById(R.id.okbutton);
												message.setText("Forget Password Failed!");
												okbtn.setOnClickListener(new OnClickListener() {

													public void onClick(
															View arg0) {
														dialog.dismiss();
													}
												});
												dialog.show();
											}
										}
									} catch (Exception e) {
										UIUtils.removeSimpleSpinProgressDialog();
										dialog.dismiss();
									}
								}
							});
						}
					});
					thread.start();
				} else {
					text.setText("Enter Correct Email Address !");
				}
			}
		});

		registerbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_login);
		context = this;
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		setContent();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UserInfo.clearData(context);
	}

}
