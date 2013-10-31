package com.tny.volvr.dancer.home;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tnutil.Callback;
import com.example.tnutil.Callback1;
import com.example.tnutil.Util;
import com.tny.utils.Constant;
import com.tny.utils.ProjectUtils;
import com.tny.utils.SignupBean;
import com.tny.utils.UIUtils;
import com.tny.volvr.home.HomeLogin;
import com.volvr.search.R;

public class DancerSignup3 extends Activity {
	private EditText mage;
	private EditText mhairColor;
	private Spinner mrace;
	private EditText  mstats, mheight;
	private Button back,mDone;
	private ArrayList<String> arace = new ArrayList<String>();
	private ArrayList<String> haircolor = new ArrayList<String>();
	private Handler mHandler = new Handler();
	private static String t = "HomeStripperSignup3";
	DateFormat formatDateTime=DateFormat.getDateInstance();
	Calendar dateTime=Calendar.getInstance();
	
	static final int DATE_DIALOG_ID = 999;
	Context context;
	private String height,race,stats;
	private String agedata,serveragedata;
	private String haircolordata;
	private SharedPreferences mySharedPreferences1,mySharedPreferences2,mySharedPreferences3;
	int i=0,j=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_signup_3);
		context = this;

		mySharedPreferences1=getSharedPreferences("SIGNUP_1", 0);
		mySharedPreferences2 = context.getSharedPreferences("SIGNUP_2", 0);
		mySharedPreferences3 = context.getSharedPreferences("SIGNUP_3",0);

		setContent();
	}

	private void setContent() 
	{
		mrace = (Spinner) findViewById(R.id.stripper_signup3_race);
		mage = (EditText) findViewById(R.id.stripper_signup3_age);
		mage.setText(mySharedPreferences3.getString("agedata",""));
		mage.setOnClickListener(listener);
		serveragedata=mySharedPreferences3.getString("serveragedata","");
		mstats = (EditText) findViewById(R.id.stripper_signup3_stats);
		mstats.setText(mySharedPreferences3.getString("stats",""));
		mstats.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (mstats.getText().length()>i) {
					if (mstats.getText().length()==2||mstats.getText().length()==5) 
					{
						mstats.setText(mstats.getText().toString()+"/");

						mstats.setSelection(mstats.getText().length());
					}
				}


				i=mstats.getText().length();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		mheight = (EditText) findViewById(R.id.stripper_signup3_height);
		mheight.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
                if (j<mheight.getText().length()) {
					
					if (mheight.getText().length() == 1) {
						mheight.setText(mheight.getText().toString() + "'");
						mheight.setSelection(mheight.getText().length());

					}
				}
				
				j=mheight.getText().length();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				 
			}
		});
		mheight.setText(mySharedPreferences3.getString("height",""));

		mhairColor = (EditText) findViewById(R.id.stripper_signup3_haircolor);
		mhairColor.setText(mySharedPreferences3.getString("haircolordata",""));

		arace.add("Ethnicity");
		arace.add("Ethnicity1");
		arace.add("Ethnicity2");
		arace.add("Ethnicity3");


		haircolor.add("HairColor");
		haircolor.add("BROWN");
		haircolor.add("BLACK");
		haircolor.add("LIGHT BROWN");
		haircolor.add("LIGHT GRAY");


		ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item, arace);
		raceAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mrace.setAdapter(raceAdapter);

		back=(Button)findViewById(R.id.stripper_signup3_back);
		back.setOnClickListener(listener);
		mDone = (Button) findViewById(R.id.stripper_signup3_done);
		mDone.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.stripper_signup3_done:
				doSignIn();
				break;
			case R.id.stripper_signup3_back:
				onBackPressed();	
				break;
			case R.id.stripper_signup3_age:
				chooseDate();
				break;
			default:
				break;
			}

		}
	};
	public void chooseDate(){
		dateTime.set(1990, 01, 01);
		new DatePickerDialog(DancerSignup3.this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
	}
	DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			monthOfYear=monthOfYear+1;
			dateTime.set(Calendar.YEAR,year);
			dateTime.set(Calendar.MONTH, monthOfYear);
			String age=ProjectUtils.getAge(dayOfMonth+"/"+monthOfYear+"/"+year);
			if (Integer.parseInt(age)>17) {
				dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				mage.setText(""+age);
				//				mage.setText(formatDateTime.format(dateTime.getTime()));
				serveragedata=dayOfMonth+"/"+monthOfYear+"/"+year;
			}else {
				Toast.makeText(context, "Age must be 18 or greater", Toast.LENGTH_SHORT).show();
			}
			Log.i(t, year+"  "+monthOfYear+"  "+dayOfMonth);
		}
	};
	private void doSignIn() {
		height = mheight.getText().toString();
		Log.i("doSignIn",height);
		race = mrace.getSelectedItem().toString();
		stats = mstats.getText().toString();
		agedata = mage.getText().toString();
		haircolordata = mhairColor.getText().toString();
		if (race.equals("")) {
			UIUtils.showMessage(this, "Message",
					"You have to input Ethinicity.");
			return;
		} else if (stats.equals("")) {
			UIUtils.showMessage(this, "Message",
					"You have to input stats.");
			return;
		} else if (agedata.equals("")) {
			UIUtils.showMessage(this, "Message",
					"You have to input age.");
			return;
		} else if (haircolordata.equals("")) {
			UIUtils.showMessage(this, "Message",
					"You have to input haircolor.");
			return;
		}if (height.equals("")) {
			UIUtils.showMessage(this, "Message",
					"You have to input height.");
			return;
		}

		UIUtils.showSimpleSpinProgressDialog(context, "Registering...");
		Thread thread = new Thread(new Runnable() {
			public void run() {
				//String urlServer = "http://developer.avenuesocial.com/strippers/websrc/mobile_service.php";
				try {
					String name =mySharedPreferences1.getString("mName","");
					String username =mySharedPreferences1.getString("mUserName","");
					String password =mySharedPreferences1.getString("mPassword","");
					String email =mySharedPreferences1.getString("mEmail","");
					String phoneno=mySharedPreferences1.getString("mContactNo","");

					String state =mySharedPreferences2.getString("strState","");
					String country=mySharedPreferences2.getString("strCountry","");
					Log.i(t, state+"  "+country);

					String zipcode =mySharedPreferences2.getString("mZipCode","");
					String wage_rate =mySharedPreferences2.getString("mWageRate","");
					String paypal_id = mySharedPreferences2.getString("mPayPalId","");

					String clubname=mySharedPreferences2.getString("strClubName","");
					String clubaddress=mySharedPreferences2.getString("strClubAddress","");
					String clubnumber=mySharedPreferences2.getString("strClubNumber","");

					String usertype = "stripper";
					String image = SignupBean.mPicture;
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
					multipartEntity.addPart("action", new StringBody(
							"registration"));
					multipartEntity.addPart("user_fullname", new StringBody(
							name));
					multipartEntity.addPart("user_name", new StringBody(
							username));
					try {
						multipartEntity.addPart("user_avatar",
								new ByteArrayBody(bytearray_of_drawable,
										"image/bmp", "one.png"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					multipartEntity.addPart("user_password",
							new StringBody(password));
					multipartEntity.addPart("user_email", new StringBody(
							email));

					multipartEntity.addPart("user_phone", new StringBody(phoneno));
					//*******************************signup 2****************************************		
					multipartEntity.addPart("user_type", new StringBody(
							usertype));
					multipartEntity.addPart("user_address_country", new StringBody(
							country));
					multipartEntity.addPart("user_address_state", new StringBody(
							state));
					multipartEntity.addPart("user_address_zip", new StringBody(
							zipcode));
					multipartEntity.addPart("wage_rate", new StringBody(
							wage_rate));
					multipartEntity.addPart("user_paypal_id", new StringBody(
							paypal_id));
					//********************************others*****************************************			
					multipartEntity.addPart("club_name", new StringBody(
							clubname));
					multipartEntity.addPart("club_address", new StringBody(
							clubaddress));
					//					multipartEntity.addPart("working_at_club_id", new StringBody(
					//							clubemail));
					multipartEntity.addPart("club_contact_number", new StringBody(
							clubnumber));
					//*******************************signup 3****************************************
					multipartEntity.addPart("race", new StringBody(race));
					multipartEntity.addPart("stats", new StringBody(stats));
					multipartEntity.addPart("user_birthday", new StringBody(serveragedata));
					multipartEntity.addPart("hair_color", new StringBody(haircolordata));
					multipartEntity.addPart("height",new StringBody(height));
					Log.i(t, race+" "+stats+" "+serveragedata+" "+haircolordata+" "+height);
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
										Util.showCustomDialog(context, "Registration Successful.", "The activation link has been sent to the email you used for registration.Click the link to complete the cycle & sign in here!", new Callback1() {
											
											@Override
											public void ok() {
												// TODO Auto-generated method stub
												Intent i = new Intent(context,
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
													//doSignIn();
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


	/*public void showNetworkErrorMessage() {
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
		dlg.setTitle("Registration Successful.");
		dlg.setMessage("The activation link has been sent to the email you used for registration.Click the link to complete the cycle & sign in here!");
		dlg.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
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
		height = mheight.getText().toString();
		Log.i("doSignIn",height);
		race = mrace.getSelectedItem().toString();
		stats = mstats.getText().toString();
		agedata = mage.getText().toString();
		haircolordata = mhairColor.getText().toString();
		SharedPreferences.Editor editor = mySharedPreferences3.edit();
		editor.putString("race", race);
		editor.putString("height", height);
		editor.putString("stats", stats);
		editor.putString("agedata", agedata);
		editor.putString("serveragedata",serveragedata);
		editor.putString("haircolordata", haircolordata);
		editor.commit();
	}


}
