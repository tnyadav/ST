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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.tnutil.Callback;
import com.example.tnutil.Callback1;
import com.example.tnutil.Util;
import com.tny.utils.Constant;
import com.tny.utils.SignupDetail;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.base.MainTabActivity;
import com.volvr.beans.StripperInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class ClubEditProfile3 extends BaseFragment {
	private EditText et_SquareFoot,et_MinAge,et_DancerCount,et_YearCount;
	private RadioGroup rbgTopless,rbgFullNude,rbgJouseBar,rbgBearBar,rbgFullBar,rbgFoodKitchen,rbgEventCast;
	private String strSquareFoot,strMinAge,strDancerCount,strYearCount,strTopless,strFullNude,strJouseBar,strBearBar,strFullBar,strFoodKitchen,strEventCast;
	private Button back,mDone;
	
	private static String t = "ClubSignup3";
	/*DateFormat formatDateTime=DateFormat.getDateInstance();
	Calendar dateTime=Calendar.getInstance();
	static final int DATE_DIALOG_ID = 999;*/
	//mActivity mActivity;
	private Handler mHandler = new Handler();
	
	private View view;
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
         view       =   inflater.inflate(R.layout.club_signup_3, container, false);
       
         setContent();
        return view;
	}
	
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	  setContent();
}
	private void setContent() {
		
		if (!StripperInfo.isProfileEdit1) {
			
			 SignupDetail.S_SquareFoot=Clubprofile.clubs.getClub_area();
			 SignupDetail.S_MinAge=Clubprofile.clubs.getClub_allowed_minage();
			 SignupDetail.S_DancerCount=Clubprofile.clubs.getClub_dancercount();
			 SignupDetail.S_YearCount=Clubprofile.clubs.getClub_yearcount();
			 SignupDetail.S_Topless=Clubprofile.clubs.getClub_topless();
			 SignupDetail.S_FullNude=Clubprofile.clubs.getClub_nude();
			 SignupDetail.S_JouseBar=Clubprofile.clubs.getClub_juicebar();
			 SignupDetail.S_BearBar=Clubprofile.clubs.getClub_beerbar();
			 SignupDetail.S_FullBar=Clubprofile.clubs.getClub_fulbar();
			 SignupDetail.S_FoodKitchen=Clubprofile.clubs.getClub_foodkitchen();
			 SignupDetail.S_EventCast=Clubprofile.clubs.getClub_eventcost();
			
			
		}
		
		
		
		back=(Button)view.findViewById(R.id.club_signup_3_back);
		back.setOnClickListener(listener);
		mDone = (Button) view.findViewById(R.id.club_signup_3_done);
		mDone.setOnClickListener(listener);
	
		// edittext	
	et_SquareFoot=(EditText)view.findViewById(R.id.club_signup_3_square_footage);
	et_SquareFoot.setText(SignupDetail.S_SquareFoot);
	et_MinAge=(EditText)view.findViewById(R.id.club_signup_3_minimum_age);
	et_MinAge.setText(SignupDetail.S_MinAge);
	et_DancerCount=(EditText)view.findViewById(R.id.club_signup_3_dancer_count);
	et_DancerCount.setText(SignupDetail.S_DancerCount);
	et_YearCount=(EditText)view.findViewById(R.id.club_signup_3_year_count);	
	et_YearCount.setText(SignupDetail.S_YearCount);
	
	//Radio group
	rbgTopless=(RadioGroup)view.findViewById(R.id.club_signup_3_radio_topless);
	rbgFullNude=(RadioGroup)view.findViewById(R.id.club_signup_3_radio_full_nude);
	rbgJouseBar=(RadioGroup)view.findViewById(R.id.club_signup_3_radio_juice_bar);
	rbgBearBar=(RadioGroup)view.findViewById(R.id.club_signup_3_radio_beer_bar);
	rbgFullBar=(RadioGroup)view.findViewById(R.id.club_signup_3_radio_full_bar);
	rbgFoodKitchen=(RadioGroup)view.findViewById(R.id.club_signup_3_radio_full_kitchen);
	rbgEventCast=(RadioGroup)view.findViewById(R.id.club_signup_3_radio_event_cast);
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

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.club_signup_3_done:
				if(Util1.isNetworkAvailable(getActivity()))
					doSignIn();
					else
					{
						UIUtils.showNetworkErrorMessage(getActivity());
					}
				break;
			case R.id.club_signup_3_back:
				getActivity().onBackPressed();	
				break;
		/*	case R.id.stripper_signup3_age:
				chooseDate();*/
			//break;
			default:
				break;
			}
			
		}
	};

	
	private void setValues() {
		
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
	
		
	}
	private void doSignIn() {
		
		UIUtils.showSimpleSpinProgressDialog(mActivity, "Updating...");
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
						multipartEntity.addPart("action", new StringBody("editclubprofile"));
						
						multipartEntity.addPart("user_id", new StringBody(StripperInfo.user_id));
						multipartEntity.addPart("user_password",new StringBody(SignupDetail.S_Password));
						
						multipartEntity.addPart("user_paypal_id", new StringBody(
								SignupDetail.S_PayPalId));
						try {
							multipartEntity.addPart("user_avatar",
									new ByteArrayBody(bytearray_of_drawable,
											"image/bmp", "one.png"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}	
			//*******************************signup 2****************************************		
						/*multipartEntity.addPart("user_type", new StringBody(
								UserInfo.USERTYPE));
					*/
						multipartEntity.addPart("club_zip_code", new StringBody(
								SignupDetail.S_ZipCode));
					   multipartEntity.addPart("club_country", new StringBody(
								SignupDetail.S_Country));
						multipartEntity.addPart("club_state", new StringBody(
								SignupDetail.S_State));
						
						multipartEntity.addPart("club_name", new StringBody(SignupDetail.S_UserFname));
						multipartEntity.addPart("club_area", new StringBody(strSquareFoot));
						multipartEntity.addPart("club_allowed_minage", new StringBody(strMinAge));	
						multipartEntity.addPart("club_dancercount", new StringBody(strDancerCount));
						multipartEntity.addPart("club_yearcount", new StringBody(strYearCount));
						multipartEntity.addPart("club_topless", new StringBody(strTopless));
						multipartEntity.addPart("club_juicebar", new StringBody(strJouseBar));
						multipartEntity.addPart("club_beerbar", new StringBody(strBearBar));
						multipartEntity.addPart("club_fulbar", new StringBody(strFullBar));
						multipartEntity.addPart("club_foodkitchen", new StringBody(strFoodKitchen));
						multipartEntity.addPart("club_eventcost", new StringBody(strEventCast));
						multipartEntity.addPart("club_nude", new StringBody(strFullNude));
						
						httppost.setEntity(multipartEntity);
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity getresponse = response.getEntity();
						String jsonstr = EntityUtils.toString(getresponse);
						Log.e("response", "" + jsonstr);
						
						final String serverResponseMessage = jsonstr;
						Log.e(t, "serverResponseMessage  "
								+ serverResponseMessage);
						UIUtils.removeSimpleSpinProgressDialog();
					
					mHandler.post(new Runnable() {
						public void run() {
							if (serverResponseMessage == null
									|| serverResponseMessage.equals("")) {
								System.out
										.println("(SignInActivity)response is null ");
								Log.e(t,
										"(SignInActivity)response is null ");
							} else {
								try {
									JSONObject obj = new JSONObject(
											serverResponseMessage);
									if (obj.getString("status").equals(
											"success")) {
										
										
									StripperInfo.user_fullname=SignupDetail.S_UserFname;
									Clubprofile.clubs.setClub_area(strSquareFoot);
									Clubprofile.clubs.setClub_allowed_minage(strMinAge);
									Clubprofile.clubs.setClub_dancercount(strDancerCount);
									Clubprofile.clubs.setClub_yearcount(strYearCount);
									Clubprofile.clubs.setClub_topless(strTopless);
									Clubprofile.clubs.setClub_juicebar(strJouseBar);
									Clubprofile.clubs.setClub_beerbar(strBearBar);
									Clubprofile.clubs.setClub_fulbar(strFullBar);
									Clubprofile.clubs.setClub_foodkitchen(strFoodKitchen);
									Clubprofile.clubs.setClub_eventcost(strEventCast);
									Clubprofile.clubs.setClub_nude(strFullNude);
									
									Clubprofile.clubs.setClub_state(SignupDetail.S_State);
									Clubprofile.clubs.setClub_country(SignupDetail.S_Country);
									Clubprofile.clubs.setClub_zip_code(SignupDetail.S_ZipCode);
									
									StripperInfo.user_paypal_id=SignupDetail.S_PayPalId;
									 
									Util.showCustomDialog(mActivity, "Message", "Your profile changes have been Saved.",new Callback1() {
										
										@Override
										public void ok() {
											// TODO Auto-generated method stub
											SignupDetail.clearData();
											Intent i = new Intent(mActivity, MainTabActivity.class);
											i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											startActivity(i);
											mActivity.finish();
										}
									});	
										
										
									
										
									} else if (obj.getString("status")
											.equals("error")) {
										UIUtils.showMessage(mActivity,
												"Message",
												obj.getString("msg"));
									}
								} catch (JSONException e) {
									Util.showCustomDialog(mActivity, "Error", "Network error has occured. Please check the network status of your phone and retry", "Retry", "Close", new Callback() {
										
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
				} catch (Exception e) {
					UIUtils.removeSimpleSpinProgressDialog();
//					UIUtils.showMessage(mActivity, "Message", "Registration Failed.");
				}
			}
		});
		thread.start();
		}
	

	

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StripperInfo.isProfileEdit1=true;
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
