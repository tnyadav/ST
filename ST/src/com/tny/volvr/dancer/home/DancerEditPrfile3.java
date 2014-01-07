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

import android.app.DatePickerDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.base.MainTabActivity;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class DancerEditPrfile3 extends BaseFragment {
	private View view;
	private EditText mage;
	private EditText mhairColor;
	private Spinner mrace;
	private EditText mstats, mheight;
	private Button back, mDone;
	private ArrayList<String> arace = new ArrayList<String>();
	private Handler mHandler = new Handler();
	private static String t = "HomeStripperSignup3";
	DateFormat formatDateTime = DateFormat.getDateInstance();
	Calendar dateTime = Calendar.getInstance();
	static final int DATE_DIALOG_ID = 999;
	private String height, race, stats;
	private String agedata, serveragedata;
	private String haircolordata;
	private SharedPreferences mySharedPreferences1, mySharedPreferences2,
	mySharedPreferences3;

	String name, userFname, password, email, state, country, zipcode,
	wage_rate, paypal_id, clubname, clubaddress, clubnumber, clubemai,
	clubemail;
	String usertype = "stripper";
	String image = SignupBean.mPicture;
	byte[] bytearray_of_drawable = null;
	int chk,sts;
	 String serverResponseMessage = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.home_signup_3, container, false);

		mySharedPreferences1 = getActivity()
				.getSharedPreferences("SIGNUP_1", 0);
		mySharedPreferences2 = getActivity()
				.getSharedPreferences("SIGNUP_2", 0);
		mySharedPreferences3 = getActivity()
				.getSharedPreferences("SIGNUP_3", 0);
		setContent();
		return view;
	}

	private void setContent() {
		mrace = (Spinner) view.findViewById(R.id.stripper_signup3_race);
		mage = (EditText) view.findViewById(R.id.stripper_signup3_age);
		mage.setOnClickListener(listener);
		serveragedata = mySharedPreferences3.getString("serveragedata", "");
		mstats = (EditText) view.findViewById(R.id.stripper_signup3_stats);
		mstats.setText(StripperInfo.stats);
		mstats.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				if (sts<mstats.getText().length()) {
					if (mstats.getText().length()==2||mstats.getText().length()==5) 
					{
						mstats.setText(mstats.getText().toString()+"/");
						mstats.setSelection(mstats.getText().length());
					}
				}
				
				sts=mstats.getText().length();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		mheight = (EditText) view.findViewById(R.id.stripper_signup3_height);
		mheight.setText(StripperInfo.height);
		mheight.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (chk<mheight.getText().length()) {
					
					if (mheight.getText().length() == 1) {
						mheight.setText(mheight.getText().toString() + "'");
						mheight.setSelection(mheight.getText().length());

					}
				}
				
				chk=mheight.getText().length();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		mhairColor = (EditText) view
				.findViewById(R.id.stripper_signup3_haircolor);
		mhairColor.setText(StripperInfo.hair_color);

		arace.add("Ethnicity");
		arace.add("Ethnicity1");
		arace.add("Ethnicity2");
		arace.add("Ethnicity3");

		ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, arace);
		raceAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mrace.setAdapter(raceAdapter);

		back = (Button) view.findViewById(R.id.stripper_signup3_back);
		back.setOnClickListener(listener);
		mDone = (Button) view.findViewById(R.id.stripper_signup3_done);
		mDone.setOnClickListener(listener);

		if (StripperInfo.isProfileEdit1) {
			setValueFromSharedPrefetance();
		} else {
			setValueFromStripperInfo();
		}
	}

	private void setValueFromStripperInfo() {
		mage.setText(StripperInfo.user_birthday);
		mstats.setText(StripperInfo.stats);
		for (int i = 0; i < arace.size(); i++) {
			if (arace.get(i).toString().equalsIgnoreCase(StripperInfo.race)) {
				mrace.setSelection(i);
			}
		}
		mhairColor.setText(StripperInfo.hair_color);
		mheight.setText(StripperInfo.height);

	}

	private void setValueFromSharedPrefetance() {
		mage.setText(mySharedPreferences3.getString("agedata", ""));
		mstats.setText(mySharedPreferences3.getString("stats", ""));
		for (int i = 0; i < arace.size(); i++) {
			if (arace
					.get(i)
					.toString()
					.equalsIgnoreCase(
							mySharedPreferences3.getString("race", ""))) {
				mrace.setSelection(i);
			}
		}
		mhairColor.setText(mySharedPreferences3.getString("haircolordata", ""));
		mheight.setText(mySharedPreferences3.getString("height", ""));
	}

	private OnClickListener listener = new OnClickListener() {
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.stripper_signup3_done:
				if(Util1.isNetworkAvailable(getActivity()))
					doSignIn();
				else
				{
					UIUtils.showNetworkErrorMessage(getActivity());
				}
				break;

			case R.id.stripper_signup3_back:
				getActivity().onBackPressed();
				break;
			case R.id.stripper_signup3_age:
				chooseDate();
				break;
			default:
				break;
			}

		}
	};

	public void chooseDate() {
		new DatePickerDialog(getActivity(), d, dateTime.get(Calendar.YEAR),
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
			String age = ProjectUtils.getAge(dayOfMonth + "/" + monthOfYear
					+ "/" + year);
			if (Integer.parseInt(age) > 20) {
				dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				mage.setText("" + age);
				// mage.setText(formatDateTime.format(dateTime.getTime()));
				serveragedata = dayOfMonth + "/" + monthOfYear + "/" + year;
			} else {
				Toast.makeText(getActivity(), "Age must be 21 or greater",
						Toast.LENGTH_SHORT).show();
			}
			Log.i(t, year + "  " + monthOfYear + "  " + dayOfMonth);
		}
	};

	private void doSignIn() {
		height = mheight.getText().toString();
		Log.i("doSignIn", height);
		race = mrace.getSelectedItem().toString();
		stats = mstats.getText().toString();
		agedata = mage.getText().toString();
		haircolordata = mhairColor.getText().toString();

		if (race.equals("")) {
			UIUtils.showMessage(getActivity(), "Message",
					"You have to input Ethinicity.");
			return;
		} else if (stats.equals("")) {
			UIUtils.showMessage(getActivity(), "Message",
					"You have to input stats.");
			return;
		} else if (agedata.equals("")) {
			UIUtils.showMessage(getActivity(), "Message",
					"You have to input age.");
			return;
		} else if (haircolordata.equals("")) {
			UIUtils.showMessage(getActivity(), "Message",
					"You have to input haircolor.");
			return;
		}if (height.equals("")) {
			UIUtils.showMessage(getActivity(), "Message",
					"You have to input height.");
			return;
		}

		UIUtils.showSimpleSpinProgressDialog(getActivity(), "Updating...");
		Thread thread = new Thread(new Runnable() {
			public void run() {
					name = mySharedPreferences1.getString("mName", "");
					userFname = mySharedPreferences1.getString("mUserName", "");
					password = mySharedPreferences1.getString("mPassword", "");
					email = mySharedPreferences1.getString("mEmail", "");

					state = mySharedPreferences2.getString("mState", "");
					country = mySharedPreferences2.getString("mCountry", "");
					zipcode = mySharedPreferences2.getString("mZipCode", "");
					wage_rate = mySharedPreferences2.getString("mWageRate", "");
					paypal_id = mySharedPreferences2.getString("mPayPalId", "");

					clubname = mySharedPreferences2
							.getString("strClubName", "");
					clubaddress = mySharedPreferences2.getString(
							"strClubAddress", "");
					clubnumber = mySharedPreferences2.getString(
							"strClubNumber", "");
					clubemail = mySharedPreferences2.getString("strClubEmail",
							"");
					usertype = "stripper";
					image = SignupBean.mPicture;
					bytearray_of_drawable = null;
					Log.e("image=", "==" + image);

										try {
											Drawable d = Drawable.createFromPath(image);
											Bitmap drawable_bitmap = ((BitmapDrawable) d)
													.getBitmap();
											ByteArrayOutputStream outstream = new ByteArrayOutputStream();
											drawable_bitmap.compress(Bitmap.CompressFormat.JPEG,
													100, outstream);
											bytearray_of_drawable = outstream.toByteArray();
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

					HttpPost httppost = new HttpPost(Constant.SERVER_URL);
					HttpParams params1 = new BasicHttpParams();
					params1.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
							HttpVersion.HTTP_1_1);
					HttpClient httpclient = new DefaultHttpClient(params1);
					MultipartEntity multipartEntity = new MultipartEntity();
					/*
					 * post data: action = editprofile & user_id = xxx &
					 * user_fullname = xxx & user_sex =xxx & user_phone = xxx &
					 * user_birthday=xxx & user_address_country=xxx &
					 * user_address_state = xxx & user_address_city =xxx &
					 * user_address_zip =xxx & user_address_street_1=xxx &
					 * user_address_street_2=xxx & user_paypal_id =xxx
					 */
					
					try {
						multipartEntity.addPart("action", new StringBody(
								"editprofile"));
						multipartEntity.addPart("user_fullname", new StringBody(
								name));
						try {
							multipartEntity.addPart("user_avatar",
									new ByteArrayBody(bytearray_of_drawable,
											"image/bmp", "one.png"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						multipartEntity.addPart("user_id", new StringBody(""
								+ StripperInfo.user_id));
						multipartEntity.addPart("user_address_country",
								new StringBody(country));
						multipartEntity.addPart("user_address_state",
								new StringBody(state));
						multipartEntity.addPart("user_address_zip", new StringBody(
								zipcode));

						multipartEntity.addPart("user_paypal_id", new StringBody(
								paypal_id));
						multipartEntity.addPart("user_birthday", new StringBody(
								serveragedata));

						Log.i(t, race + " " + stats + " " + serveragedata + " "
								+ haircolordata + " " + height);

						httppost.setEntity(multipartEntity);
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity getresponse = response.getEntity();
						serverResponseMessage = EntityUtils.toString(getresponse);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					Log.i(t, "serverResponseMessage  " + serverResponseMessage);
					UIUtils.removeSimpleSpinProgressDialog();
					mHandler.post(new Runnable() {
						public void run() {
							if (serverResponseMessage == null
									|| serverResponseMessage.equals("")) {
								System.out
								.println("(SignInActivity)response is null ");
								Log.i(t, "(SignInActivity)response is null ");
							} else {
								try {
									JSONObject obj = new JSONObject(
											serverResponseMessage);
									if (obj.getString("status").equals(
											"success")) {
										StripperInfo.user_fullname = name;
										StripperInfo.user_address_country = country;
										StripperInfo.user_address_state = state;
										StripperInfo.user_address_zip = zipcode;
										StripperInfo.user_paypal_id = paypal_id;
										StripperInfo.user_birthday = serveragedata;

										updateProfessionalProfile();
										// showNetworkErrorMessage1(obj.getString("msg"));

									} else if (obj.getString("status").equals(
											"error")) {
										UIUtils.showMessage(getActivity(),
												"Message", obj.getString("msg"));
									}

								} catch (JSONException e) {
									Util.showCustomDialog(
											mActivity,
											"Error",
											"Network error has occured. Please check the network status of your phone and retry",
											"Retry", "Close", new Callback() {

												@Override
												public void ok() {
													// TODO Auto-generated
													// method stub
												
												}

												@Override
												public void cancel() {
													// TODO Auto-generated
													// method stub
													System.exit(0);
												}
											});
								}
							}
						}

					});
				/*} catch (Exception e) {
					UIUtils.removeSimpleSpinProgressDialog();
					UIUtils.showMessage(getActivity(), "Message",
							"Updating Profile details failed.");
				}*/
			}

		});
		thread.start();

	}

	private void updateProfessionalProfile() {
		
			HttpPost httppost = new HttpPost(Constant.SERVER_URL);
			HttpParams params1 = new BasicHttpParams();
			params1.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
					HttpVersion.HTTP_1_1);
			HttpClient httpclient = new DefaultHttpClient(params1);
			MultipartEntity multipartEntity = new MultipartEntity();

			try {
				multipartEntity
				.addPart("action", new StringBody("editstripdetail"));
				multipartEntity.addPart("user_id", new StringBody(""
						+ StripperInfo.user_id));

				multipartEntity.addPart("wage_rate", new StringBody(wage_rate));

				// ********************************others*****************************************
				multipartEntity.addPart("club_name", new StringBody(clubname));
				multipartEntity
				.addPart("club_address", new StringBody(clubaddress));
				multipartEntity.addPart("working_at_club_id", new StringBody(
						clubemail));
				multipartEntity.addPart("club_contact_number", new StringBody(
						clubnumber));

				// *******************************signup
				// 3****************************************
				multipartEntity.addPart("race", new StringBody(race));
				multipartEntity.addPart("stats", new StringBody(stats));

				multipartEntity
				.addPart("hair_color", new StringBody(haircolordata));
				multipartEntity.addPart("height", new StringBody(height));

				Log.i(t, race + " " + stats + " " + serveragedata + " "
						+ haircolordata + " " + height);

				httppost.setEntity(multipartEntity);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity getresponse = response.getEntity();
				String jsonstr = EntityUtils.toString(getresponse);
				Log.d("response", "" + jsonstr);
				serverResponseMessage = jsonstr;

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
						Log.i(t, "serverResponseMessage  " + serverResponseMessage);
			UIUtils.removeSimpleSpinProgressDialog();
			mHandler.post(new Runnable() {
				public void run() {
					if (serverResponseMessage == null
							|| serverResponseMessage.equals("")) {
						System.out.println("(SignInActivity)response is null ");
						Log.i(t, "(SignInActivity)response is null ");
					} else {
						try {
							JSONObject obj = new JSONObject(
									serverResponseMessage);
							if (obj.getString("status").equals("success")) {
								StripperInfo.race = race;
								StripperInfo.stats = stats;
								StripperInfo.hair_color = haircolordata;
								StripperInfo.height = height;
								Util.showCustomDialog(
										mActivity,
										"Message",
										"Your profile changes have been Saved.",
										new Callback1() {

											@Override
											public void ok() {
												// TODO Auto-generated method
												// stub
												SharedPreferences.Editor editor = mySharedPreferences3
														.edit();
												editor.putString("height", "");
												editor.putString("race", "");
												editor.putString("stats", "");
												editor.putString("agedata", "");
												editor.putString(
														"serveragedata", "");
												editor.putString(
														"haircolordata", "");
												editor.commit();

												SharedPreferences.Editor editor2 = mySharedPreferences2
														.edit();
												editor2.putString("mZipCode",
														"");
												editor2.putString("mWageRate",
														"");
												editor2.putString("mPayPalId",
														"");

												editor.putString("mCountry", "");
												editor.putString("mState", "");
												editor.putString("mWorkingat",
														"");

												editor2.putInt("otherlistsize",
														0);
												editor2.putString(
														"strClubName", "");
												editor2.putString(
														"strClubAddress", "");
												editor2.putString(
														"strClubNumber", "");
												editor2.commit();
												Intent i = new Intent(
														mActivity,
														MainTabActivity.class);
												startActivity(i);
												mActivity.finish();
											}
										});

							} else if (obj.getString("status").equals("error")) {
								UIUtils.showMessage(getActivity(), "Message",
										obj.getString("msg"));
							}

						} catch (JSONException e) {
							Util.showCustomDialog(
									mActivity,
									"Error",
									"Network error has occured. Please check the network status of your phone and retry",
									"Retry", "Close", new Callback() {

										@Override
										public void ok() {
											// TODO Auto-generated method stub

										}

										@Override
										public void cancel() {
											// TODO Auto-generated method stub
											System.exit(0);
										}
									});
						}
					}
				}

			});
	
	}



	@Override
	public void onPause() {
		super.onPause();
		StripperInfo.isProfileEdit1 = true;
		height = mheight.getText().toString();
		Log.i("doSignIn", height);
		race = mrace.getSelectedItem().toString();
		stats = mstats.getText().toString();
		agedata = mage.getText().toString();
		haircolordata = mhairColor.getText().toString();
		SharedPreferences.Editor editor = mySharedPreferences3.edit();
		editor.putString("race", race);
		editor.putString("height", height);
		editor.putString("stats", stats);
		editor.putString("agedata", agedata);
		editor.putString("serveragedata", serveragedata);
		editor.putString("haircolordata", haircolordata);
		editor.commit();
	}
}
