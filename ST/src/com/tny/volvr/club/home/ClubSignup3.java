package com.tny.volvr.club.home;


import java.io.ByteArrayOutputStream;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.tnutil.Callback;
import com.example.tnutil.Callback1;
import com.example.tnutil.Util;
import com.tny.utils.Constant;
import com.tny.utils.SignupDetail;
import com.tny.utils.UIUtils;
import com.tny.volvr.home.HomeLogin;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class ClubSignup3 extends Activity {
	private EditText et_SquareFoot,et_MinAge,et_DancerCount,et_YearCount;
	private RadioGroup rbgTopless,rbgFullNude,rbgJouseBar,rbgBearBar,rbgFullBar,rbgFoodKitchen,rbgEventCast;
	private String strSquareFoot,strMinAge,strDancerCount,strYearCount,strTopless,strFullNude,strJouseBar,strBearBar,strFullBar,strFoodKitchen,strEventCast;
	private Button back,mDone;
	
	private static String t = "ClubSignup3";
	/*DateFormat formatDateTime=DateFormat.getDateInstance();
	Calendar dateTime=Calendar.getInstance();
	static final int DATE_DIALOG_ID = 999;*/
	Context context;
	private Handler mHandler = new Handler();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.club_signup_3);
		context = this;
	  
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	  setContent();
}
	private void setContent() {
		Thread thread = new Thread(new Runnable() {
			public void run() {

		back=(Button)findViewById(R.id.club_signup_3_back);
		back.setOnClickListener(listener);
		mDone = (Button) findViewById(R.id.club_signup_3_done);
		mDone.setOnClickListener(listener);
	
		// edittext	
	et_SquareFoot=(EditText)findViewById(R.id.club_signup_3_square_footage);
	et_SquareFoot.setText(SignupDetail.S_SquareFoot);
	et_MinAge=(EditText)findViewById(R.id.club_signup_3_minimum_age);
	et_MinAge.setText(SignupDetail.S_MinAge);
	et_DancerCount=(EditText)findViewById(R.id.club_signup_3_dancer_count);
	et_DancerCount.setText(SignupDetail.S_DancerCount);
	et_YearCount=(EditText)findViewById(R.id.club_signup_3_year_count);	
	et_YearCount.setText(SignupDetail.S_YearCount);
	
	//Radio group
	rbgTopless=(RadioGroup)findViewById(R.id.club_signup_3_radio_topless);
	rbgFullNude=(RadioGroup)findViewById(R.id.club_signup_3_radio_full_nude);
	rbgJouseBar=(RadioGroup)findViewById(R.id.club_signup_3_radio_juice_bar);
	rbgBearBar=(RadioGroup)findViewById(R.id.club_signup_3_radio_beer_bar);
	rbgFullBar=(RadioGroup)findViewById(R.id.club_signup_3_radio_full_bar);
	rbgFoodKitchen=(RadioGroup)findViewById(R.id.club_signup_3_radio_full_kitchen);
	rbgEventCast=(RadioGroup)findViewById(R.id.club_signup_3_radio_event_cast);
	Log.d("values",SignupDetail.S_Topless+" "+SignupDetail.S_FullNude);
	if (SignupDetail.S_Topless.length()>0) {
		if (SignupDetail.S_Topless.equals("Yes")) {
			rbgTopless.check(R.id.topless_radio0);
		}else {
			rbgTopless.check(R.id.topless_radio1);
		}
	}
	if (SignupDetail.S_FullNude.length()>0) {
		if (SignupDetail.S_FullNude.equals("Yes")) {
			rbgFullNude.check(R.id.full_nude_radio0);
		}else {
			rbgFullNude.check(R.id.full_nude_radio1);
		}
	}
	if (SignupDetail.S_JouseBar.length()>0) {
		if (SignupDetail.S_JouseBar.equals("Yes")) {
			rbgJouseBar.check(R.id.juice_bar_radio0);
		}else {
			rbgJouseBar.check(R.id.juice_bar_radio1);
		}
	}
	if (SignupDetail.S_BearBar.length()>0) {
		if (SignupDetail.S_BearBar.equals("Yes")) {
			rbgBearBar.check(R.id.beer_bar_radio0);
		}else {
			rbgBearBar.check(R.id.beer_bar_radio1);
		}
	}
	if (SignupDetail.S_FullBar.length()>0) {
		if (SignupDetail.S_FullBar.equals("Yes")) {
			rbgFullBar.check(R.id.full_bar_radio0);
		}else {
			rbgFullBar.check(R.id.full_bar_radio1);
		}
	}
	if (SignupDetail.S_FoodKitchen.length()>0) {
		if (SignupDetail.S_FoodKitchen.equals("Yes")) {
			rbgFoodKitchen.check(R.id.food_kitchen_radio0);
		}else {
			rbgFoodKitchen.check(R.id.food_kitchen_radio1);
		}
	}
	
	if (SignupDetail.S_EventCast.length()>0) {
		if (SignupDetail.S_EventCast.equals("Yes")) {
			rbgEventCast.check(R.id.event_cast_radio0);
		}else {
			rbgEventCast.check(R.id.event_cast_radio1);
		}
	}
			}
		});
		thread.start();
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.club_signup_3_done:
				/*Intent i = new Intent(context,
						HomeStripperLogin.class);
		        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);*/
				doSignIn();
				break;
			case R.id.club_signup_3_back:
				onBackPressed();	
				break;
		/*	case R.id.stripper_signup3_age:
				chooseDate();*/
			//break;
			default:
				break;
			}
			
		}
	};
	/*public void chooseDate(){
    	new DatePickerDialog(ClubSignup3.this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }
	DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			monthOfYear=monthOfYear+1;
			dateTime.set(Calendar.YEAR,year);
			dateTime.set(Calendar.MONTH, monthOfYear);
			String age=ProjectUtils.getAge(dayOfMonth+"/"+monthOfYear+"/"+year);
			if (Integer.parseInt(age)>20) {
				dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				mage.setText(""+age);
//				mage.setText(formatDateTime.format(dateTime.getTime()));
				serveragedata=dayOfMonth+"/"+monthOfYear+"/"+year;
			}else {
				Toast.makeText(context, "Age must be 21 or greater", Toast.LENGTH_SHORT).show();
			}
			Log.i(t, year+"  "+monthOfYear+"  "+dayOfMonth);
		}
	};*/
	
	
	private void setValues() {
		/*Thread thread = new Thread(new Runnable() {
			public void run() {*/
				strSquareFoot=et_SquareFoot.getText().toString();
				strMinAge=et_SquareFoot.getText().toString();
				strDancerCount=et_SquareFoot.getText().toString();
				strYearCount=et_SquareFoot.getText().toString();
				int checkedRadioButton =	rbgTopless.getCheckedRadioButtonId();
				switch (checkedRadioButton) {
				case R.id.topless_radio0:
					strTopless="Yes";
					break;
		        case R.id.topless_radio1:
		        	strTopless="No";
					break;
				default:
					break;
				}
				 checkedRadioButton =	rbgFullNude.getCheckedRadioButtonId();
				switch (checkedRadioButton) {
				case R.id.full_nude_radio0:
					strFullNude="Yes";
					break;
		        case R.id.full_nude_radio1:
		        	strFullNude="No";
					break;
				default:
					break;
				}
				 checkedRadioButton =	rbgJouseBar.getCheckedRadioButtonId();
				switch (checkedRadioButton) {
				case R.id.juice_bar_radio0:
					strJouseBar="Yes";
					break;
		        case R.id.juice_bar_radio1:
		        	strJouseBar="No";
					break;
				default:
					break;
				}
				 checkedRadioButton =	rbgBearBar.getCheckedRadioButtonId();
				switch (checkedRadioButton) {
				case R.id.beer_bar_radio0:
					strBearBar="Yes";
					break;
		        case R.id.beer_bar_radio1:
		        	strBearBar="No";
					break;
				default:
					break;
				}
				 checkedRadioButton =	rbgFullBar.getCheckedRadioButtonId();
				switch (checkedRadioButton) {
				case R.id.full_bar_radio0:
					strFullBar="Yes";
					break;
		        case R.id.full_bar_radio1:
		        	strFullBar="No";
					break;
				default:
					break;
				}
				 checkedRadioButton =	rbgFoodKitchen.getCheckedRadioButtonId();
				switch (checkedRadioButton) {
				case R.id.food_kitchen_radio0:
					strFoodKitchen="Yes";
					break;
		        case R.id.food_kitchen_radio1:
		        	strFoodKitchen="No";
					break;
				default:
					break;
				}
				 checkedRadioButton =	rbgEventCast.getCheckedRadioButtonId();
				switch (checkedRadioButton) {
				case R.id.event_cast_radio0:
					strEventCast="Yes";
					break;
		        case R.id.event_cast_radio1:
		        	strEventCast="No";
					break;
				default:
					break;
				}
		/*		
			}
		});
		thread.start();*/
		
	}
	private void doSignIn() {
		UIUtils.showSimpleSpinProgressDialog(context, "Registering...");
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					setValues();
					String image = SignupDetail.S_Picture;
					byte[] bytearray_of_drawable=null;
					Log.e("image=","=="+image);

					try {
						Drawable d = Drawable.createFromPath(image);
						Bitmap drawable_bitmap = ((BitmapDrawable) d)
								.getBitmap();
						ByteArrayOutputStream outstream = new ByteArrayOutputStream();
						drawable_bitmap.compress(Bitmap.CompressFormat.PNG,
								100, outstream);
						bytearray_of_drawable = outstream.toByteArray();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
						HttpPost httppost = new HttpPost(Constant.SERVER_URL);
						HttpParams params1 = new BasicHttpParams();
						params1.setParameter(
								CoreProtocolPNames.PROTOCOL_VERSION,
								HttpVersion.HTTP_1_1);
						HttpClient httpclient = new DefaultHttpClient(params1);
						MultipartEntity multipartEntity = new MultipartEntity();
				//*******************************signup 1****************************************		
						multipartEntity.addPart("action", new StringBody("registration"));
						multipartEntity.addPart("club_name", new StringBody(SignupDetail.S_UserFname));
						multipartEntity.addPart("user_name", new StringBody(SignupDetail.S_UserName));
						multipartEntity.addPart("user_password",new StringBody(SignupDetail.S_Password));
						multipartEntity.addPart("user_email", new StringBody(SignupDetail.S_Email));
						try {
							multipartEntity.addPart("user_avatar",
									new ByteArrayBody(bytearray_of_drawable,
											"image/bmp", "one.png"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
			//*******************************signup 2****************************************		
						multipartEntity.addPart("user_type", new StringBody(
								UserInfo.USERTYPE));
						multipartEntity.addPart("club_zip_code", new StringBody(
								SignupDetail.S_ZipCode));
						
						multipartEntity.addPart("user_paypal_id", new StringBody(
								SignupDetail.S_PayPalId));
						
						multipartEntity.addPart("club_country", new StringBody(
								SignupDetail.S_Country));
						multipartEntity.addPart("club_state", new StringBody(
								SignupDetail.S_State));
						
						multipartEntity.addPart("club_area", new StringBody(strSquareFoot));
						multipartEntity.addPart("club_allowed_minage", new StringBody(strMinAge));	
						multipartEntity.addPart("club_dancercount", new StringBody(strDancerCount));	
						multipartEntity.addPart("club_topless", new StringBody(strTopless));
						multipartEntity.addPart("club_juicebar", new StringBody(strJouseBar));
						multipartEntity.addPart("club_beerbar", new StringBody(strBearBar));
						multipartEntity.addPart("club_foodkitchen", new StringBody(strFoodKitchen));
						multipartEntity.addPart("club_eventcost", new StringBody(strEventCast));
						multipartEntity.addPart("club_nude", new StringBody(strFullNude));
						
						httppost.setEntity(multipartEntity);
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity getresponse = response.getEntity();
						String jsonstr = EntityUtils.toString(getresponse);
						Log.d("response", "" + jsonstr);
						
						final String serverResponseMessage = jsonstr;
						Log.i(t, "serverResponseMessage  "
								+ serverResponseMessage);
						UIUtils.removeSimpleSpinProgressDialog();
					
					mHandler.post(new Runnable() {
						public void run() {
							if (serverResponseMessage == null
									|| serverResponseMessage.equals("")) {
								System.out
										.println("(SignInActivity)response is null ");
								Log.i(t,
										"(SignInActivity)response is null ");
							} else {
								try {
									JSONObject obj = new JSONObject(
											serverResponseMessage);
									if (obj.getString("status").equals(
											"success")) {
										//showNetworkErrorMessage1(obj.getString("msg"));
										Util.showCustomDialog(
												context,
												"Registration Successful.",
												"The activation link has been sent to the email you used for registration.Click the link to complete the cycle & sign in here!",
												new Callback1() {

													@Override
													public void ok() {
														// TODO Auto-generated
														// method stub
														Intent i = new Intent(
																context,
																HomeLogin.class);
														i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
														startActivity(i);
													}
												});
									} else if (obj.getString("status")
											.equals("error")) {
										UIUtils.showMessage(context,
												"Message",
												obj.getString("msg"));
									}
								} catch (JSONException e) {
									Util.showCustomDialog(context, "Error", "Network error has occured. Please check the network status of your phone and retry",
											"Retry", "Close", new Callback() {
												
												@Override
												public void ok() {
													// TODO Auto-generated method stub
													
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
//					UIUtils.showMessage(context, "Message", "Registration Failed.");
				}
			}
		});
		thread.start();
		}
	
/*
	public void showNetworkErrorMessage() {
		Builder dlg = new AlertDialog.Builder(context);
		dlg.setCancelable(false);
		dlg.setTitle("Error");
		dlg.setMessage("Network error has occured. Please check the network status of your phone and retry");
		dlg.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dlg.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				((Activity) context).finish();
				System.exit(0);
			}
		});
		dlg.show();
	}*/
/*	private void showNetworkErrorMessage1(String msg) 
	{
		Builder dlg = new AlertDialog.Builder(context);
		dlg.setCancelable(false);
		dlg.setTitle("Message");
		dlg.setMessage(msg);
		dlg.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SignupDetail.clearData();
				Intent i = new Intent(context,
						HomeLogin.class);
		        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		
		dlg.show();
	}*/

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 setValues();
		 SignupDetail.S_SquareFoot=strSquareFoot;
		 SignupDetail.S_MinAge=strMinAge;
		 SignupDetail.S_DancerCount=strDancerCount;
		 SignupDetail.S_YearCount=strYearCount;
		 SignupDetail.S_Topless=strTopless;
		 SignupDetail.S_FullNude=strFullNude;
		 SignupDetail.S_JouseBar=strJouseBar;
		 SignupDetail.S_BearBar=strBearBar;
		 SignupDetail.S_FullBar=strFullBar;
		 SignupDetail.S_FoodKitchen=strFoodKitchen;
		 SignupDetail.S_EventCast=strEventCast;
		
	}


}
