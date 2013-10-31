 package com.tny.volvr.fan.home;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tnutil.Callback;
import com.example.tnutil.Callback1;
import com.example.tnutil.Util;
import com.tny.utils.Constant;
import com.tny.utils.CountryOrState;
import com.tny.utils.ProjectUtils;
import com.tny.utils.SignupDetail;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.home.HomeLogin;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class FanSignup2 extends Activity {

	
	private Button back,next;

	//Button gender;
	Handler mHandler = new Handler();
	private static String t="HomeStripperSignup2";
	
	Context context;
	private Button bt_imageUpload,bt_age,bt_gender;
	String strImgepath="";

	private EditText et_fullname,et_occupation,et_grossincum;
	
	private String strfullname,stroccupation,strgrossincum,strage,strgender,strimagepath;

	DateFormat formatDateTime = DateFormat.getDateInstance();
	Calendar dateTime = Calendar.getInstance();
	static final int DATE_DIALOG_ID = 999;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fan_signup_2);
		context=this;
		
		setContent();
	}
	private void setContent()
	{
		back=(Button)findViewById(R.id.fan_signup2_back);
		back.setOnClickListener(listener);
		next=(Button)findViewById(R.id.fan_signup2_done);
		next.setOnClickListener(listener);
		
		bt_age=(Button)findViewById(R.id.fan_signup2_age);
		bt_age.setOnClickListener(listener);
		if (SignupDetail.S_Age.length()>0) {
			bt_age.setText(ProjectUtils.getAge(SignupDetail.S_Age));
		}
		
		
		bt_gender=(Button)findViewById(R.id.fan_signup_gender);
		bt_gender.setText(SignupDetail.S_gender);
		bt_gender.setOnClickListener(listener);
		bt_imageUpload=(Button)findViewById(R.id.fan_signup2_picture);
		if (SignupDetail.S_Picture.length()>0) {
			bt_imageUpload.setText(fileName(SignupDetail.S_Picture));
		}
		bt_imageUpload.setOnClickListener(listener);
		
	
		et_fullname=(EditText)findViewById(R.id.fan_signup2_username);
		et_fullname.setText(SignupDetail.S_UserFname);
		Util1.textFormatterCapitalizeFirstLetter(et_fullname);
		
		et_occupation=(EditText)findViewById(R.id.fan_signup2_occupation);
		et_occupation.setText(SignupDetail.S_occupation);
		et_grossincum=(EditText)findViewById(R.id.fan_signup2_grossincome);
		et_grossincum.setText(SignupDetail.S_grossincum);
	}
	private void setValue() {
	
		strfullname=et_fullname.getText().toString();
		
		stroccupation=et_occupation.getText().toString();
		strgrossincum=et_grossincum.getText().toString();
		strgender=bt_gender.getText().toString();
		
		
	}
	public void setGender(View v) {

		Intent i=new Intent(context, CountryOrState.class);
		i.putExtra("whichList", "GENDER");
		i.putExtra("countryCode", "");
		startActivityForResult(i,1234);
	}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode == 1&&resultCode==Activity.RESULT_OK) {
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
		cursor.moveToFirst(); 
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		strImgepath = cursor.getString(columnIndex);
		SignupDetail.S_Picture=strImgepath;

		Log.i(t, strImgepath);
		cursor.close();             
		bt_imageUpload.setText(fileName(strImgepath));
		//		            imgUpload.setImageBitmap(BitmapFactory.decodeFile(picturePath));

	}
	if (requestCode == 1234&&resultCode==Activity.RESULT_OK) {
	
		bt_gender.setText(data.getStringExtra("selectedValue"));
		SignupDetail.S_gender=data.getStringExtra("selectedValue");
		
	}
}

public String fileName(String f) {
	Log.i("fileName", ""+f);
	Uri u = Uri.parse(f);

	File f1 = new File("" + u);


	return f1.getName();
}

public void fanSignup2Age(View v) {
	chooseDate();
}
	private OnClickListener listener        =   new OnClickListener()
	{
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			
	         	case R.id.fan_signup2_done:
	         		doSignIn();
				break;
		
			case R.id.fan_signup2_back:
				onBackPressed();	
				break;
			case R.id.fan_signup2_age:
			chooseDate();	
				break;
				
			case R.id.fan_signup_gender:
				Intent i=new Intent(context, CountryOrState.class);
				i.putExtra("whichList", "GENDER");
				i.putExtra("countryCode", "");
				startActivityForResult(i,1234);	
				break;	
			case R.id.fan_signup2_picture:
				Intent i1 = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i1, 1);	
				break;
				
			default:
				break;
			}
		}
	};

	private void doSignIn() {
		
		UIUtils.showSimpleSpinProgressDialog(context, "Registering...");
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					
		setValue();
					String usertype = "club";
				   
				    byte[] bytearray_of_drawable=null;
				   
				    
					try {
						Drawable d = Drawable.createFromPath(SignupDetail.S_Picture);
						Bitmap drawable_bitmap = ((BitmapDrawable) d)
								.getBitmap();
						ByteArrayOutputStream outstream = new ByteArrayOutputStream();
						drawable_bitmap.compress(Bitmap.CompressFormat.JPEG,
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
					multipartEntity.addPart("user_fullname", new StringBody(SignupDetail.S_UserFname));
					multipartEntity.addPart("user_name", new StringBody(SignupDetail.S_UserName));
					multipartEntity.addPart("user_password",new StringBody(SignupDetail.S_Password));
					multipartEntity.addPart("user_type", new StringBody(
							UserInfo.USERTYPE));
					multipartEntity.addPart("user_email", new StringBody(SignupDetail.S_Email));
					try {
						multipartEntity.addPart("user_avatar",
								new ByteArrayBody(bytearray_of_drawable,
										"image/bmp", "one.png"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	
					multipartEntity.addPart("user_birthday", new StringBody(SignupDetail.S_Age));
					
					multipartEntity.addPart("fan_gender", new StringBody(SignupDetail.S_gender));
					multipartEntity.addPart("user_occupation", new StringBody(stroccupation));
					multipartEntity.addPart("user_grossincome", new StringBody(strgrossincum));
				
					
					
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
										SignupDetail.clearData();
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
	public void chooseDate() {
		new DatePickerDialog(context, d, dateTime.get(Calendar.YEAR),
				dateTime.get(Calendar.MONTH),
				dateTime.get(Calendar.DAY_OF_MONTH)).show();
	}

	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			monthOfYear = monthOfYear + 1;
			dateTime.set(Calendar.YEAR, year);
			dateTime.set(Calendar.MONTH, monthOfYear);
			String strage = ProjectUtils.getAge(dayOfMonth + "/" + monthOfYear
					+ "/" + year);
			if (Integer.parseInt(strage) > 17) {
				dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				bt_age.setText("" + strage);
				// mage.setText(formatDateTime.format(dateTime.getTime()));
				SignupDetail.S_Age = dayOfMonth + "/" + monthOfYear + "/" + year;
			} else {
				Toast.makeText(context, "Age must be 18 or greater",
						Toast.LENGTH_SHORT).show();
			}
			Log.i(t, year + "  " + monthOfYear + "  " + dayOfMonth);
		}
	};


/*	public void showNetworkErrorMessage() {
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
				
			}
		});
		dlg.show();
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
	}*/
/*	private void showNetworkErrorMessage1(String msg) 
	{
		Builder dlg = new AlertDialog.Builder(context);
		dlg.setCancelable(false);
		dlg.setTitle("Registration Successful.");
		dlg.setMessage("The activation link has been sent to the email you used for registration.Click the link to complete the cycle & sign in here!");
		dlg.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		dlg.show();
	
	}*/
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		setValue();
		SignupDetail.S_UserFname=strfullname;
		SignupDetail.S_gender=strgender;
		SignupDetail.S_occupation=stroccupation;
		SignupDetail.S_grossincum=strgrossincum;
	}
}

