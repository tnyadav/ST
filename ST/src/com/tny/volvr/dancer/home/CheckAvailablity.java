package com.tny.volvr.dancer.home;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.Constant;
import com.tny.utils.CustomDateTimePicker;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.dancer.subscription.DancerSubscription;
import com.volvr.beans.FanInfo;
import com.volvr.beans.TravelStatusList;
import com.volvr.search.R;

@SuppressLint("NewApi")
public class CheckAvailablity extends BaseFragment{
	private View view;
	EditText startdate,enddate;
	String  strStartdate,strEnddate,stripperId,clubId="";

	private Button back,done,sdcalender,edcalender,book;
	DateFormat formatDateTime=DateFormat.getDateInstance();
	Calendar dateTime=Calendar.getInstance();
	 String info1="";
	 PaymentConfirmation confirm;
	 String strpayment_key="",ammount="",stDate,edDate,strippername;
	 private ArrayList<String> paymentinfo;
public CheckAvailablity(String stripperId,String strippername){
	this.stripperId=stripperId;
	this.strippername=strippername;
}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.check_stripper_availability, container, false);
		setContent();

		return view;
	}
	
	private void done() {
		strStartdate=startdate.getText().toString();
		strEnddate=enddate.getText().toString();
		
		 if(strStartdate.isEmpty()){
			UIUtils.showMessage(getActivity(), "Message",
					"Please enter appointment start date.");
			return;
		}
		else if(strEnddate.isEmpty()){
			UIUtils.showMessage(getActivity(), "Message",
					"Please enter appointment end date.");
			return;
		}
		
	
		
		if (Util1.isNetworkAvailable(getActivity())) {
			
			List<NameValuePair> entity = new ArrayList<NameValuePair>();
			entity.add(new BasicNameValuePair("action","travelstatus_get"));
			entity.add(new BasicNameValuePair("strip_user_id",stripperId));
			
			new AsyncWebServiceProcessingTask(mActivity, entity, "Getting travel status",new Callback() {
				
				@Override
				public void run(String result) {
					// TODO Auto-generated method stub
					if (result.equals("")) {
						UIUtils.showNetworkErrorMessage(getActivity());
					} else {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(result);
							if (jsonObject.optString("status").equals("success")) {
								TravelStatusList.parseTravelStatuslist(jsonObject);
								final TravelStatusList travelStatusList=check(strStartdate, strEnddate);
								final TextView info=(TextView)view.findViewById(R.id.info);
							
								if (travelStatusList!=null) {
									
									 info1=travelStatusList.ts_id+"\n"+travelStatusList.ts_title+"\n"+
									travelStatusList.ts_start_datetime+"\n"+travelStatusList.ts_end_datetime;
									
									
									if (Util1.isNetworkAvailable(getActivity())) {
											
											List<NameValuePair> entity = new ArrayList<NameValuePair>();
											entity.add(new BasicNameValuePair("action","getuserprofile"));
											entity.add(new BasicNameValuePair("user_id",travelStatusList.club_user_id));
											
											new AsyncWebServiceProcessingTask(mActivity, entity, "Getting travel status",new Callback() {
												
												@Override
												public void run(String result) {
													// TODO Auto-generated method stub
													if (result.equals("")) {
														UIUtils.showNetworkErrorMessage(getActivity());
													} else {
														JSONObject jsonObject;
														try {
															jsonObject = new JSONObject(result);
															if (jsonObject.optString("status").equals("success")) {
																
																JSONObject jsonObject2=jsonObject.getJSONObject("user");
																JSONObject jsonObject3=jsonObject.getJSONObject("clubs");
																info1="Stripper available at\nClub Name:"+jsonObject3.optString("club_name")+
																		"\nCity:"+jsonObject2.optString("user_address_city")+
																		"\nState:"+jsonObject2.optString("user_address_state")+
																		"\nCountry:"+jsonObject2.optString("user_address_country")+
																		"\nZip Code:"+jsonObject2.optString("user_address_zip");
																info.setText(info1);
																book.setVisibility(View.VISIBLE);
																clubId=travelStatusList.club_user_id;
																paymentinfo=new ArrayList<String>();
																paymentinfo.add(strippername);
																paymentinfo.add(jsonObject3.optString("club_name"));
																paymentinfo.add(strStartdate);
																paymentinfo.add("50");
																paymentinfo.add(stripperId);
																paymentinfo.add(clubId);
																paymentinfo.add(FanInfo.fanproFileInfo.user_id);
															}else {
																
															}
															
														} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														
													}
												
													
												}
											}).execute("");
											
												
											
										}
										else
											UIUtils.showNetworkErrorMessage(getActivity());
								
								} 
								
									
							
							}
							else if(jsonObject.optString("status").equals("error")){
								UIUtils.showMessage(getActivity(), "Message","No travel status found");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				
					
				}
			}).execute("");
			
				
			
		}
		else
			UIUtils.showNetworkErrorMessage(getActivity());

	}
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			
		
				
			
			switch (v.getId()) {
			case R.id.back:
				getActivity().onBackPressed();	
				break;
			case R.id.done:
			  if(Util1.isNetworkAvailable(getActivity())){
					
					done();
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());
				break;
			case R.id.startdate_calendar:
				chooseDate(startdate);	

				break;
			case R.id.enddate_calendar:
				chooseDate(enddate);	

				break;
			case R.id.book:
			//	onBuyPressed("50");
				PaymentProcess fragment = new PaymentProcess();
		        Bundle args = new Bundle();
		        args.putStringArrayList("paymentinfo", paymentinfo);
		        fragment.setArguments(args);
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
						fragment, true, true);

				break;
			
			default:
				break;
			}

		}
	};



	private void setContent() {
		back=(Button)view.findViewById(R.id.back);
		back.setOnClickListener(listener);
		done=(Button)view.findViewById(R.id.done);
		done.setOnClickListener(listener);
		book=(Button)view.findViewById(R.id.book);
		book.setOnClickListener(listener);
		book.setVisibility(View.GONE);
		sdcalender=(Button)view.findViewById(R.id.startdate_calendar);
		sdcalender.setOnClickListener(listener);
		edcalender=(Button)view.findViewById(R.id.enddate_calendar);
		edcalender.setOnClickListener(listener);

		startdate=(EditText)view.findViewById(R.id.startdate);
	
		enddate=(EditText)view.findViewById(R.id.enddate);



	}

	public void chooseDate(final EditText editText ){


		CustomDateTimePicker custom;

		custom = new CustomDateTimePicker(mActivity,
				new CustomDateTimePicker.ICustomDateTimeListener() {

			@Override
			public void onSet(Dialog dialog, Calendar calendarSelected,
					Date dateSelected, int year, String monthFullName,
					String monthShortName, int monthNumber, int date,
					String weekDayFullName, String weekDayShortName,
					int hour24, int hour12, int min, int sec,
					String AM_PM) {
				String hh = null;
				String mm = null;
				String ss;
				if (hour12<10) {
					hh="0"+hour12;
				}else {
					hh=""+hour12;
				}
				if (min<10) {
					mm="0"+min;
				}else {
					mm=""+min;
				}
				if (sec<10) {
					ss="0"+sec;
				}else {
					ss=""+sec;
				}
				editText.setText(year+"-"+(monthNumber+1)+"-"+calendarSelected.get(Calendar.DAY_OF_MONTH)
						+ " " + hh + ":" + mm +":"+sec);
			}

			@Override
			public void onCancel() {

			}
		});
		
		custom.set24HourFormat(true);
	
		custom.setDate(Calendar.getInstance());
		custom.showDialog();
	

	}





	private TravelStatusList check(String d1,String d2) {
		TravelStatusList travelStatusList = null;
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

		try {
			Date	date1 = curFormater.parse(d1 );
			Date date2 = curFormater.parse(d2 ); 
			for (int i = 0; i < TravelStatusList.altravelStatusLists.size(); i++) {
				TravelStatusList travelStatusList1=TravelStatusList.altravelStatusLists.get(i);
				Date tempDateSt=curFormater.parse(travelStatusList1.ts_start_datetime );
				Date tempDateEt=curFormater.parse(travelStatusList1.ts_end_datetime );
				
				
				if (date1.after(tempDateSt)&&date2.before(tempDateEt)) 
				{
					travelStatusList=travelStatusList1;
					stDate=d1;
					edDate=d2;
				}
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return travelStatusList;
	}
	public void onBuyPressed(String amout)
	{
		ammount=amout;
		PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(amout), "USD", "Voui'r");
		Intent intent = new Intent(getActivity(), PaymentActivity.class);
		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, DancerSubscription.CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, DancerSubscription.CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, Constant.adminEmailForPayment);

		// It's important to repeat the clientId here so that the SDK has it if Android restarts your 
		// app midway through the payment UI flow.
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "AVNO-hBS8pTShkFgmdGDuY1UdTK_Rw2MoeG7ToeFZCoD0sp4f-ZllD_14Qh2");
		intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "your-customer-id-in-your-system");
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

		startActivityForResult(intent, 0);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == Activity.RESULT_OK)
		{
			confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null)
			{
				try
				{
					Log.i("paymentExample", confirm.toJSONObject().toString(4));
					ArrayList<String> listData=Util1.parsePaymentData(confirm.toJSONObject().toString(4));
					List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","payments_add"));
					//entity.add(new BasicNameValuePair("user_id",StripperInfo.user_id));FanInfo.fanproFileInfo.user_id;
					
					entity.add(new BasicNameValuePair("fan_user_id",FanInfo.fanproFileInfo.user_id));
					entity.add(new BasicNameValuePair("club_user_id",clubId));
					entity.add(new BasicNameValuePair("strip_user_id",stripperId));
					
					entity.add(new BasicNameValuePair("start_datetime",stDate));
					entity.add(new BasicNameValuePair("end_datetime",edDate));
					
					entity.add(new BasicNameValuePair("amount",ammount));
					entity.add(new BasicNameValuePair("user_subscription_type","$"+ammount));
					entity.add(new BasicNameValuePair("payment_key",listData.get(0)));
					
					
					new AsyncWebServiceProcessingTask(getActivity(), entity, "Loading",new Callback() {
						
						@Override
						public void run(String result) {
							// TODO Auto-generated method stub
							try {
		    					JSONObject jsonResponse=new JSONObject(result);
		    					if(jsonResponse.optString("status").equalsIgnoreCase("success"))
		    					{
		    						UIUtils.showMessage(getActivity(), "Message","You are successfully subscribed with $"+ammount);
		    						//StripperInfo.subscription_expire_date=jsonResponse.optString("subscription_expire_date");
		    					//	StripperInfo.user_subscription_type=strSubscriptionType;
		    						Log.e("addpayinfo", "Str: "+stripperId+"club: "+clubId+"fan"+FanInfo.fanproFileInfo.user_id);
		    						mActivity.onBackPressed();
		    					}
		    					else
		    						UIUtils.showMessage(getActivity(), "Message","Your subscription failed. ");
		    				} catch (Exception e) {
		    					e.printStackTrace();
		    				}
						}
					}).execute("");
				}
				catch (JSONException e)
				{
					Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
				}
			}
		}
		else
			if (resultCode == Activity.RESULT_CANCELED)
			{
				Log.i("paymentExample", "The user canceled.");
			}
			else
				if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID)
				{
					Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
				}
	}

	@Override
	public void onDestroy()
	{
		getActivity().stopService(new Intent(getActivity(), PayPalService.class));
		super.onDestroy();
	}

}
