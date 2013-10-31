package com.tny.volvr.dancer.calendar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

@SuppressLint("NewApi")
public class AddSchedule extends BaseFragment{
	private View view;
	EditText eventname,location,startdate,enddate,reminder,repetition,description;
	String  strEventname,strLocation,strStartdate,strEnddate,strReminder,strRepetition,strDescription;

	private Button back,done,sdcalender,edcalender;
	DateFormat formatDateTime=DateFormat.getDateInstance();
	Calendar dateTime=Calendar.getInstance();


	String selecteduser_id="";

	
	public AddSchedule(String string) {
		selecteduser_id=string;	
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_add_schedule, container, false);
		setContent();

		return view;
	}
	private void done() {
		String user_Id="";
		if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
			user_Id=StripperInfo.user_id;
		if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
			
			//user_Id=StripperInfo.user_id;
		user_Id=Clubprofile.clubs.getClub_id();
		
		if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
			user_Id=FanInfo.fanproFileInfo.user_id;
		Log.e("selecteduser_id", selecteduser_id);
        Log.e("user_Id", user_Id);
		List<NameValuePair> entity = new ArrayList<NameValuePair>();
		entity.add(new BasicNameValuePair("action","requestappointment"));
		entity.add(new BasicNameValuePair("user_id",user_Id));
		entity.add(new BasicNameValuePair("strip_id",selecteduser_id));
		entity.add(new BasicNameValuePair("apn_start_date",strStartdate));
		entity.add(new BasicNameValuePair("apn_end_date",strEnddate));
		entity.add(new BasicNameValuePair("apn_description",strDescription));
		entity.add(new BasicNameValuePair("apn_length",""));
		entity.add(new BasicNameValuePair("apn_place",strLocation));
		entity.add(new BasicNameValuePair("apn_city",""));
		entity.add(new BasicNameValuePair("apn_state",""));
		entity.add(new BasicNameValuePair("apn_country",""));
		if (Util1.isNetworkAvailable(getActivity())) {
		
			new AsyncWebServiceProcessingTask(mActivity, entity, "Please wait.",new Callback() {
					
					@Override
					public void run(String response) {
						// TODO Auto-generated method stub
						if (response.equals("")) {
							UIUtils.showNetworkErrorMessage(getActivity());
						} else {
							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(response);
							if (jsonObject.optString("status").equals("success")) {
								getActivity().onBackPressed();
							}
							else{
								UIUtils.showMessage(getActivity(), "Message","Request Failed");
							}
							} catch (JSONException e) {
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
			strEventname=eventname.getText().toString();
			strLocation=location.getText().toString();
			strStartdate=startdate.getText().toString();
			strEnddate=enddate.getText().toString();
			strReminder=reminder.getText().toString();
			strRepetition=repetition.getText().toString();
			strDescription=description.getText().toString();
			
			
			switch (v.getId()) {
			case R.id.stripper_add_schedule_back:
				getActivity().onBackPressed();	
				break;
			case R.id.stripper_add_schedule_done:
				if(Util1.isNetworkAvailable(getActivity())){
					if(strEventname.isEmpty())
						UIUtils.showMessage(getActivity(), "Message",
								"Please enter event name.");
					else if(strStartdate.isEmpty())
						UIUtils.showMessage(getActivity(), "Message",
								"Please enter appointment start date.");
					else if(strEnddate.isEmpty())
						UIUtils.showMessage(getActivity(), "Message",
								"Please enter appointment end date.");
					else
					done();
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());
				break;
			case R.id.stripper_add_schedule_startdate_calendar:
				chooseDate(startdate);	

				break;
			case R.id.stripper_add_schedule_enddate_calendar:
				chooseDate(enddate);	

				break;
			default:
				break;
			}

		}
	};



	private void setContent() {
		back=(Button)view.findViewById(R.id.stripper_add_schedule_back);
		back.setOnClickListener(listener);
		done=(Button)view.findViewById(R.id.stripper_add_schedule_done);
		done.setOnClickListener(listener);
		sdcalender=(Button)view.findViewById(R.id.stripper_add_schedule_startdate_calendar);
		sdcalender.setOnClickListener(listener);
		edcalender=(Button)view.findViewById(R.id.stripper_add_schedule_enddate_calendar);
		edcalender.setOnClickListener(listener);

		eventname=(EditText)view.findViewById(R.id.stripper_add_schedule_eventname);
		location=(EditText)view.findViewById(R.id.stripper_add_schedule_location);
		startdate=(EditText)view.findViewById(R.id.stripper_add_schedule_startdate);
		enddate=(EditText)view.findViewById(R.id.stripper_add_schedule_enddate);
		reminder=(EditText)view.findViewById(R.id.stripper_add_schedule_reminder);
		repetition=(EditText)view.findViewById(R.id.stripper_add_schedule_repetation);
		description=(EditText)view.findViewById(R.id.stripper_add_schedule_description);



	}

	public void chooseDate(final EditText editText ){


		/*DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			monthOfYear=monthOfYear+1;
			dateTime.set(Calendar.YEAR,year);
			dateTime.set(Calendar.MONTH, monthOfYear);
			editText.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
			Log.i(t, year+"  "+monthOfYear+"  "+dayOfMonth);
		}
	};

new DatePickerDialog(mActivity, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();


		 */

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
		/**
		 * Pass Directly current time format it will return AM and PM if you set
		 * false
		 */
		custom.set24HourFormat(true);
		/**
		 * Pass Directly current data and time to show when it pop up
		 */
		custom.setDate(Calendar.getInstance());
		custom.showDialog();
		/*findViewById(R.id.button_date).setOnClickListener(
			new OnClickListener() {

				@Override
				public void onClick(View v) {
					custom.showDialog();
				}
			});*/

	}





	private void addschedule() {

	}


}
