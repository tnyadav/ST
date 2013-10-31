package com.tny.volvr.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.CustomDateTimePicker;
import com.tny.utils.JsonResponse;
import com.tny.utils.UIUtils;
import com.tny.volvr.base.BaseFragment;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class AddOffers extends BaseFragment {
	private	View view;
	private	Button cancel,msavebtn,sdcalender,edcalender,maddMoreOfferbtn;
	private static String TAG="AddOffers";
	EditText mOfferName,mOfferDesc,startdate,enddate;
	String mOfferoffer_start_date,mOfferoffer_end_date,mClubId;
	Handler handler;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.add_offers, container, false);
		setContent();
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d("Add Offer", JsonResponse.JsonResponse);
				
			}
		};

		return view;
	}

	private void setContent() {
		cancel=(Button)view.findViewById(R.id.add_offer_cancel);
		msavebtn=(Button)view.findViewById(R.id.add_offer_save);

		mOfferName=(EditText)view.findViewById(R.id.add_offer_offer_name);
		mOfferDesc=(EditText)view.findViewById(R.id.add_offer_offer_doscription);
		cancel.setOnClickListener(listener);
		msavebtn.setOnClickListener(listener);
		
		startdate=(EditText)view.findViewById(R.id.addoffer_startdate);
		enddate=(EditText)view.findViewById(R.id.addoffer_enddate);
		
		sdcalender=(Button)view.findViewById(R.id.addoffer_startdate_calendar);
		sdcalender.setOnClickListener(listener);
		edcalender=(Button)view.findViewById(R.id.addoffer_enddate_calendar);
		edcalender.setOnClickListener(listener);
		
		maddMoreOfferbtn=(Button)view.findViewById(R.id.add_offer_add_another);
		maddMoreOfferbtn.setOnClickListener(listener);
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.add_offer_cancel:
				Log.i(TAG, "cancel");
				getActivity().onBackPressed();
				break;
			case R.id.add_offer_add_another:
				saveToServer(1);
				break;
			case R.id.addoffer_startdate_calendar:
				chooseDate(startdate);	

				break;
			case R.id.addoffer_enddate_calendar:
				chooseDate(enddate);	

				break;
				
			case R.id.add_offer_save:
				Log.i(TAG, "save");
				saveToServer(0);
				
				break;
			default:
				break;
			}
		}
	};

//	private void addMoreOffer() 
//	{
//		
//	}

	private void saveToServer(int i) {
		if (startdate.getText().toString().length()==0||enddate.getText().toString().length()==0||mOfferDesc.getText().toString().length()==0||mOfferName.getText().toString().length()==0) {
			Toast.makeText(mActivity, "Please enter all fields", Toast.LENGTH_LONG).show();
			return;
		}
		List<NameValuePair> entity = new ArrayList<NameValuePair>();
		entity.add(new BasicNameValuePair("action","addcluboffers"));
		entity.add(new BasicNameValuePair("club_id",Clubprofile.clubs.getClub_id()));
		entity.add(new BasicNameValuePair("offer_start_date",startdate.getText().toString()));
		entity.add(new BasicNameValuePair("offer_end_date",enddate.getText().toString()));
		entity.add(new BasicNameValuePair("offer_desc",mOfferDesc.getText().toString().trim()));
		entity.add(new BasicNameValuePair("offer_name",mOfferName.getText().toString().trim()));
		String response="";
		try {
			response=new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading").execute("").get();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONObject jsonResponse=new JSONObject(response);
			if(jsonResponse.optString("status").equalsIgnoreCase("success"))
			{
				//getActivity().onBackPressed();
				Toast.makeText(mActivity, "Offer added", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			UIUtils.showMessage(mActivity, "Error", "Server not responds");
		}
		if (i==0) {
			getActivity().onBackPressed();
		}
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
				editText.setText(year+"-"+(monthNumber+1)+"-"+calendarSelected.get(Calendar.DAY_OF_MONTH)
						+ " " + hour12 + ":" + min);
			}

			@Override
			public void onCancel() {

			}
		});
	
		custom.set24HourFormat(true);
	
		custom.setDate(Calendar.getInstance());
		custom.showDialog();
	

	}
}

