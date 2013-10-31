package com.tny.volvr.dancer.home;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.CustomDateTimePicker;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class AddTravelStatus extends BaseFragment{
	private View view;
	EditText eventname,clubname,startdate,enddate,description;
	String  strEventname,strClubname,strStartdate,strEnddate,strDescription,strClubId;

	private Button back,done,sdcalender,edcalender;
	DateFormat formatDateTime=DateFormat.getDateInstance();
	Calendar dateTime=Calendar.getInstance();

	String []list;
	//String selecteduser_id="";

	
	/*public AddTravelStatus(String string) {
		selecteduser_id=string;	
		
	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_add_travel_status, container, false);
		setContent();

		return view;
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void done() {
	
		if(strEventname.isEmpty()){
			UIUtils.showMessage(getActivity(), "Message",
					"Please enter title.");
			return;
		}
		else if(strStartdate.isEmpty()){
			UIUtils.showMessage(getActivity(), "Message",
					"Please enter appointment start date.");
			return;
		}
		else if(strEnddate.isEmpty()){
			UIUtils.showMessage(getActivity(), "Message",
					"Please enter appointment end date.");
			return;
		}
		else if(strClubId.isEmpty()){
			UIUtils.showMessage(getActivity(), "Message",
					"Please select club.");
			return;
		}
	Log.e("strClubId",strClubId+"  "+StripperInfo.user_id);
	
		List<NameValuePair> entity = new ArrayList<NameValuePair>();
		
		entity.add(new BasicNameValuePair("action","travelstatus_add"));
		entity.add(new BasicNameValuePair("ts_title",strEventname));
		entity.add(new BasicNameValuePair("strip_user_id",StripperInfo.user_id));
		entity.add(new BasicNameValuePair("club_user_id",strClubId));
		entity.add(new BasicNameValuePair("ts_start_datetime",strStartdate));
		entity.add(new BasicNameValuePair("ts_end_datetime",strEnddate));
		entity.add(new BasicNameValuePair("ts_description",strDescription));
		
		if (Util1.isNetworkAvailable(getActivity())) {
			
				new AsyncWebServiceProcessingTask(mActivity, entity, "Please wait.",new Callback() {
					
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
									getActivity().onBackPressed();
								}
								else{
									UIUtils.showMessage(getActivity(), "Message","Request Failed");
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
		@SuppressWarnings("null")
		@Override
		public void onClick(View v){
			
			strEventname=eventname.getText().toString();
			
			strStartdate=startdate.getText().toString();
			strEnddate=enddate.getText().toString();
			strDescription=description.getText().toString();
			
			
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
			case R.id.club:
				
		 list=StripperInfo.clubName().toArray(new String[StripperInfo.clubName().size()]);
		
     		if (list==null) {
			list=new String[]{"No club found"};
     		}
				 final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
				    builder.setTitle("Select Club")
				           .setItems(list, new DialogInterface.OnClickListener() {
				               public void onClick(DialogInterface dialog, int which) {
				            	   if (!list[0].equals("No club found")) {
				            		   strClubId=StripperInfo.getClubId(which);
				            		   clubname.setText(list[which]);
								}
				             
				           
				           }
				    });
				    builder.show();
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
		
		sdcalender=(Button)view.findViewById(R.id.startdate_calendar);
		sdcalender.setOnClickListener(listener);
		edcalender=(Button)view.findViewById(R.id.enddate_calendar);
		edcalender.setOnClickListener(listener);

		eventname=(EditText)view.findViewById(R.id.title);
		clubname=(EditText)view.findViewById(R.id.club);
		clubname.setOnClickListener(listener);
		startdate=(EditText)view.findViewById(R.id.startdate);
		enddate=(EditText)view.findViewById(R.id.enddate);
		description=(EditText)view.findViewById(R.id.description);



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





	private void addschedule() {

	}


}
