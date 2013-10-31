package com.tny.volvr.dancer.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.dancer.home.DancerTravalStatus;
import com.tny.volvr.dancer.message.TabMessages;
import com.volvr.beans.Appointments;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class TabCalendar extends BaseFragment {
	public Calendar month;
	public CalendarAdapter adapter;
	//public Handler handler;
	Button addschedule;
	com.tny.utils.MyTextView mtravelStatus,notification,message;
	public ArrayList<String> items; // container to store some random calendar items
	
	public ArrayList<String> itemsDate=new ArrayList<String>();
	View view;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.calendar, container, false);
            setContent();
        return view;
    }
private void setContent()
{
//itemsDate.add("12-12-2013");
	
	mtravelStatus=(com.tny.utils.MyTextView)view.findViewById(R.id.stripper_profile_mytravlestatus);
	mtravelStatus.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			mActivity.pushFragments(AppConstants.TAB_CALENDAR, new DancerTravalStatus(),true,true);
		}
	});
	
	addschedule=(Button)view.findViewById(R.id.stripper_calender_done);
	addschedule.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			mActivity.pushFragments(AppConstants.TAB_CALENDAR, new TabMessages("appointment"),true,true);
		}
	});
	
    TextView previous  = (TextView)view.findViewById(R.id.previous);
    previous.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(month.get(Calendar.MONTH)== month.getActualMinimum(Calendar.MONTH)) {				
				month.set((month.get(Calendar.YEAR)-1),month.getActualMaximum(Calendar.MONTH),1);
			} else {
				month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
			}
			refreshCalendar();
		}
	});
    
    TextView next  = (TextView)view.findViewById(R.id.next);
    next.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(month.get(Calendar.MONTH)== month.getActualMaximum(Calendar.MONTH)) {				
				month.set((month.get(Calendar.YEAR)+1),month.getActualMinimum(Calendar.MONTH),1);
			} else {
				month.set(Calendar.MONTH,month.get(Calendar.MONTH)+1);
			}
			refreshCalendar();
		}
	});
    
    // appointment
    /*notification=(com.tny.utils.MyTextView)view.findViewById(R.id.stripper_profile_mytravlestatus);
    notification.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			mActivity.pushFragments(AppConstants.TAB_CALENDAR, new DancerTravalStatus(),true,true);
		}
	});*/
	message=(com.tny.utils.MyTextView)view.findViewById(R.id.stripper_profile_mytravlestatus);
	
    
    
	if(Util1.isNetworkAvailable(getActivity())){
		String user_Id="";
		 if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
			 user_Id=StripperInfo.user_id;
         	if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
         		 user_Id=StripperInfo.user_id;
         	if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
         		 user_Id=FanInfo.fanproFileInfo.user_id;
		List<NameValuePair> entity = new ArrayList<NameValuePair>();
		entity.add(new BasicNameValuePair("action","getappointments"));
		entity.add(new BasicNameValuePair("user_id",user_Id));
		
		Log.e("user_id",user_Id);
		new AsyncWebServiceProcessingTask(getActivity(), entity, "Please wait....",new Callback() {
			
			@Override
			public void run(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonResponse=new JSONObject(result);
					if(jsonResponse.optString("status").equalsIgnoreCase("success"))
					{
						Appointments.parseAppointmentsList(jsonResponse);
						bindDataToCalendar();
					}
					else{
						bindDataToCalendar();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					bindDataToCalendar();
				}
			}
		}).execute(""); 	
	}else {
        bindDataToCalendar();
	}
}
private void bindDataToCalendar() 
{
	try{
	itemsDate=Appointments.getAppointmentDateList();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	month = Calendar.getInstance();
	DatePicker dp=new DatePicker(getActivity());
	String datepick=""+dp.getYear()+"-"+dp.getMonth()+"-"+dp.getDayOfMonth();
    onNewIntent(datepick);
    
    items = new ArrayList<String>();
    adapter = new CalendarAdapter(getActivity(), month,itemsDate);
    refreshCalendar();
    GridView gridview = (GridView)view.findViewById(R.id.gridview);
    gridview.setAdapter(adapter);
    gridview.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	TextView date = (TextView)v.findViewById(R.id.date);
	    	Drawable[] drawable=date.getCompoundDrawables();
//	    	Log.e("drawable=",""+drawable[0]+"-"+drawable[1]+"-"+drawable[2]+"-"+drawable[3]);
	        if(date instanceof TextView && !date.getText().equals("") && drawable[3]!=null) {
	        //	Intent intent = new Intent();
	        	String day = date.getText().toString();
	        	if(day.length()==1) {
	        		day = "0"+day;
	        	}
	        	mActivity.pushFragments(AppConstants.TAB_CALENDAR, new ViewApointment(android.text.format.DateFormat.format("yyyy-MM", month)+"-"+day),true,true);
	        }
	        
	    }
	});
}
public void refreshCalendar()
{
	TextView title  = (TextView)view.findViewById(R.id.title);
	title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	adapter.refreshDays();
	adapter.notifyDataSetChanged();
	Handler   handler = new Handler();
	handler.post(calendarUpdater);
	handler.post(calendarUpdater); // generate some random calendar items				
}

public void onNewIntent(String date) {
	String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
	month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
}

public Runnable calendarUpdater = new Runnable() {
	
	@Override
	public void run() {
		items.clear();
		// format random values. You can implement a dedicated class to provide real values
		for(int i=0;i<31;i++) {
			Random r = new Random();
			
			if(r.nextInt(10)>6)
			{
				items.add(Integer.toString(i));
			}
		}

		adapter.setItems(items);
		adapter.notifyDataSetChanged();
	}
};

	}