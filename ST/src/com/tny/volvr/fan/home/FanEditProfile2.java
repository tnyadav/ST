package com.tny.volvr.fan.home;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tnutil.Callback1;
import com.example.tnutil.Util;
import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.CountryOrState;
import com.tny.utils.ProjectUtils;
import com.tny.utils.SignupDetail;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.FanInfo;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class FanEditProfile2 extends BaseFragment {


	public Button back,next;

	//Button gender;
	Handler mHandler = new Handler();
	private static String t="HomeStripperSignup2";


	private Button bt_age,bt_gender;
	String strImgepath="";
	private RelativeLayout bt_imageUpload;
	private EditText et_fullname,et_occupation,et_grossincum;

	private String strfullname,stroccupation,strgrossincum,strage,strgender,strimagepath;

	DateFormat formatDateTime = DateFormat.getDateInstance();
	Calendar dateTime = Calendar.getInstance();

	private View view;

	static final int DATE_DIALOG_ID = 999;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.fan_signup_2, container, false);

		setContent();
		return view;
	}
	private void setContent()
	{
		back=(Button)view.findViewById(R.id.fan_signup2_back);
		back.setOnClickListener(listener);
		next=(Button)view.findViewById(R.id.fan_signup2_done);
		next.setOnClickListener(listener);

		bt_age=(Button)view.findViewById(R.id.fan_signup2_age);
		bt_age.setOnClickListener(listener);
		strage=FanInfo.fanproFileInfo.user_birthday;
		if (FanInfo.fanproFileInfo.user_birthday.length()>5) {
			bt_age.setText(ProjectUtils.getAge(FanInfo.fanproFileInfo.user_birthday));
		}

		bt_gender=(Button)view.findViewById(R.id.fan_signup_gender);
		bt_gender.setOnClickListener(listener);
		bt_gender.setText("Male");

		bt_imageUpload=(RelativeLayout)view.findViewById(R.id.fan_signup2_picture_rel);
		bt_imageUpload.setVisibility(View.GONE);

		et_fullname=(EditText)view.findViewById(R.id.fan_signup2_username);
		et_fullname.setText(FanInfo.fanproFileInfo.user_fullname);
		Util1.textFormatterCapitalizeFirstLetter(et_fullname);

		et_occupation=(EditText)view.findViewById(R.id.fan_signup2_occupation);
		et_occupation.setText(FanInfo.fanproFileInfo.user_occupation);
		et_grossincum=(EditText)view.findViewById(R.id.fan_signup2_grossincome);
		et_grossincum.setText(FanInfo.fanproFileInfo.user_grossincome);
	}
	private void setValue() {

		strfullname=et_fullname.getText().toString();
		stroccupation=et_occupation.getText().toString();
		strgrossincum=et_grossincum.getText().toString();
		strgender=bt_gender.getText().toString();


	}
	public void setGender(View v) {

		Intent i=new Intent(mActivity, CountryOrState.class);
		i.putExtra("whichList", "GENDER");
		i.putExtra("countryCode", "");
		getActivity().startActivityForResult(i,1234);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1234&&resultCode==Activity.RESULT_OK) {
			bt_gender.setText(data.getStringExtra("selectedValue"));
			strgender=data.getStringExtra("selectedValue");
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
				if(Util1.isNetworkAvailable(getActivity()))
					doneEditProfile();
				else
				{
					UIUtils.showNetworkErrorMessage(getActivity());
				}
				break;

			case R.id.fan_signup2_back:
				getActivity().onBackPressed();	
				break;
			case R.id.fan_signup_gender:
				Intent i=new Intent(mActivity, CountryOrState.class);
				i.putExtra("whichList", "GENDER");
				i.putExtra("countryCode", "");
				getActivity().startActivityForResult(i,1234);
				break;

			case R.id.fan_signup2_age:
				chooseDate();

				break;

			default:
				break;
			}
		}
	};

	private void doneEditProfile() {
		/*UIUtils.showSimpleSpinProgressDialog(mActivity, "Updating...");
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String urlServer = "http://developer.avenuesocial.com/strippers/websrc/mobile_service.php";
				try {
					setValue();
					HttpPost httppost = new HttpPost(urlServer);
					HttpParams params1 = new BasicHttpParams();
					params1.setParameter(
							CoreProtocolPNames.PROTOCOL_VERSION,
							HttpVersion.HTTP_1_1);
					HttpClient httpclient = new DefaultHttpClient(params1);
					MultipartEntity multipartEntity = new MultipartEntity();
					//***********************************************************************		
					multipartEntity.addPart("action", new StringBody("editprofile"));
					multipartEntity.addPart("user_fullname", new StringBody(strfullname));
					//multipartEntity.addPart("user_name", new StringBody(FanInfo.fanproFileInfo.user_name));
					multipartEntity.addPart("user_id", new StringBody(FanInfo.fanproFileInfo.user_id));
					multipartEntity.addPart("user_password",new StringBody(SignupDetail.S_Password));
					multipartEntity.addPart("user_type", new StringBody(UserInfo.USERTYPE));
					multipartEntity.addPart("user_email", new StringBody(FanInfo.fanproFileInfo.user_email));
					multipartEntity.addPart("user_birthday", new StringBody(strage));
					multipartEntity.addPart("user_occupation", new StringBody(stroccupation));
					multipartEntity.addPart("user_grossincome", new StringBody(strgrossincum));
					multipartEntity.addPart("user_sex", new StringBody("male"));

					//gender

					Log.e("vlues",strfullname+ " * "+FanInfo.fanproFileInfo.user_name+"  "+FanInfo.fanproFileInfo.user_id+" * "+FanInfo.fanproFileInfo.user_email+" * "+strage+" "+stroccupation+" "+strgrossincum);

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
										showNetworkErrorMessage1(obj.getString("msg"));




									} else if (obj.getString("status")
											.equals("error")) {
										UIUtils.showMessage(mActivity,
												"Message",
												obj.getString("msg"));
									}
								} catch (Exception e) {
									Util.showNetworkErrorMessage(getActivity());
								}
							}
						}
					});
				} catch (Exception e) {
					UIUtils.removeSimpleSpinProgressDialog();
					//					UIUtils.showMessage(mActivity, "Message", "Registration Failed.");
				}
			}
		});
		thread.start();*/
		
		
		setValue();
		List<NameValuePair> entity = new ArrayList<NameValuePair>();
		entity.add(new BasicNameValuePair("action", "editprofile"));
		entity.add(new BasicNameValuePair("user_fullname", strfullname));
		entity.add(new BasicNameValuePair("user_id", FanInfo.fanproFileInfo.user_id));
		entity.add(new BasicNameValuePair("user_password", SignupDetail.S_Password));
		entity.add(new BasicNameValuePair("user_type",UserInfo.USERTYPE ));
		entity.add(new BasicNameValuePair("user_email", FanInfo.fanproFileInfo.user_email));
		entity.add(new BasicNameValuePair("user_birthday",strage ));
		entity.add(new BasicNameValuePair("user_occupation",stroccupation ));
		entity.add(new BasicNameValuePair("user_grossincome",strgrossincum ));
		entity.add(new BasicNameValuePair("user_sex","male" ));
		
		new AsyncWebServiceProcessingTask(mActivity, entity, "Updating...", new Callback() {
			
			@Override
			public void run(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonResponse=new JSONObject(result);
					if(jsonResponse.optString("status").equalsIgnoreCase("success"))
					{
						Util.showCustomDialog(mActivity, "Message", jsonResponse.getString("msg"), new Callback1() {
							
							@Override
							public void ok() {
								// TODO Auto-generated method stub
								mActivity.mStacks.get(mActivity.mCurrentTab).pop();
								//	mActivity.mStacks.get(mActivity.mCurrentTab).pop();
									getActivity().onBackPressed();
							}
						});
						FanInfo.fanproFileInfo.user_fullname=strfullname;
						FanInfo.fanproFileInfo.user_birthday=strage;
						FanInfo.fanproFileInfo.user_occupation=stroccupation;
						FanInfo.fanproFileInfo.user_grossincome=strgrossincum;
						
					}
					else
						UIUtils.showMessage(getActivity(), "Message","Try again later. ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).execute("");
	}
	public void chooseDate() {
		new DatePickerDialog(mActivity, d, dateTime.get(Calendar.YEAR),
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
			String strage1 = ProjectUtils.getAge(dayOfMonth + "/" + monthOfYear
					+ "/" + year);
			if (Integer.parseInt(strage1) > 20) {
				dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				bt_age.setText("" + strage1);
				// mage.setText(formatDateTime.format(dateTime.getTime()));
				strage = dayOfMonth + "/" + monthOfYear + "/" + year;
			} else {
				Toast.makeText(mActivity, "Age must be 21 or greater",
						Toast.LENGTH_SHORT).show();
			}
			Log.i(t, year + "  " + monthOfYear + "  " + dayOfMonth);
		}
	};


//	public void showNetworkErrorMessage() {
//		Builder dlg = new AlertDialog.Builder(mActivity);
//		dlg.setCancelable(false);
//		dlg.setTitle("Error");
//		dlg.setMessage("Network error has occured. Please check the network status of your phone and retry");
//		dlg.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//			}
//		});
//		dlg.setNegativeButton("Close", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				((Activity) mActivity).finish();
//				System.exit(0);
//			}
//		});
//		dlg.show();
//	}
/*	private void showNetworkErrorMessage1(String msg) 
	{
		Builder dlg = new AlertDialog.Builder(mActivity);
		dlg.setCancelable(false);
		dlg.setTitle("Message.");
		dlg.setMessage(msg);
		dlg.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mActivity.mStacks.get(mActivity.mCurrentTab).pop();
			//	mActivity.mStacks.get(mActivity.mCurrentTab).pop();
				getActivity().onBackPressed();
				//mActivity.pushFragments(AppConstants.TAB_HOME, new FanMyProfile(), true,true);
			}
		});

		dlg.show();

	}*/
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		setValue();
		/*SignupDetail.S_UserFname=strfullname;
		SignupDetail.S_gender=strgender;
		SignupDetail.S_occupation=stroccupation;
		SignupDetail.S_grossincum=strgrossincum;*/
	}
}

