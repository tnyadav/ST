package com.tny.volvr.dancer.calendar;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.more.More_Notification;
import com.volvr.beans.Appointments;
import com.volvr.search.R;

public class ViewApointment extends BaseFragment{
	private View view;
	private Button back;
	com.tny.utils.MyTextView dateTitle;
	ListView listview;
	String currentDateValue="";
	Context context;
	public ViewApointment(String currentDate) {
		currentDateValue=currentDate; 
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_schedule_appointments, container, false);
		context=mActivity;
		setContent();
		return view;
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.stripper_apointment_back:
				getActivity().onBackPressed();	
				break;

			default:
				break;
			}
		}
	};

	private void setContent() {
			final ArrayList<ArrayList<String>> matchedAppointment=Appointments.getAppointmentForDate(currentDateValue);
			back=(Button)view.findViewById(R.id.stripper_apointment_back);
			back.setOnClickListener(listener);
			dateTitle=(com.tny.utils.MyTextView)view.findViewById(R.id.stripper_schedule_appointments_title);
			dateTitle.setText(currentDateValue);
			listview=(ListView)view.findViewById(R.id.stripper_schedule_appointments_list);
			listview.setAdapter(new ApointmentAdapter(matchedAppointment));
			/*listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					Log.e("clicked=",arg2+"");
					mActivity.pushFragments(AppConstants.TAB_CALENDAR, new ViewAppointmentDetails(matchedAppointment.get(arg2).get(2),matchedAppointment.get(arg2).get(1),matchedAppointment.get(arg2).get(0)),true,true);
				}
			});*/
	}
	public class ApointmentAdapter extends BaseAdapter
	{
		
		ArrayList< ArrayList<String>> appointmentDetail=new ArrayList<ArrayList<String>>();
	    Context context;
		ApointmentAdapter(ArrayList< ArrayList<String>> appointmentDetail1)
		{
		
			
			appointmentDetail.clear();
			appointmentDetail=appointmentDetail1;
		}
		public int getCount()
		{
			return appointmentDetail.size();
		}
		public String getItem(int position)
		{
			return null;
		}
		public long getItemId(int position)
		{
			return position;
		}
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater =(LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row;
			
			row = inflater.inflate(R.layout.stripper_schedule_appointments_listitem, parent, false);
			TextView timetxt = (TextView) row.findViewById(R.id.stripper_schedule_appointments_listitem_time);
			TextView commenttxt = (TextView) row.findViewById(R.id.stripper_schedule_appointments_listitem_text);
			Button view= (Button) row.findViewById(R.id.stripper_schedule_appointments_listitem_remove);
	/*		removebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","removeappointment"));
					entity.add(new BasicNameValuePair("user_id",""));
					entity.add(new BasicNameValuePair("apn_id",""+appointmentDetail.get(position).get(2)));
					try {
						AsyncTask<String, Void, String> async=new AsyncWebServiceProcessingTask1(context, entity, "Please wait..").execute("");
						String response=async.get();
						if (response.equals("")) {
							Util.showNetworkErrorMessage(context);
						} else {
							JSONObject jsonObject=new JSONObject(response);
							if (jsonObject.optString("status").equals("success")) {
								Toast.makeText(context, jsonObject.optString("msg"), Toast.LENGTH_LONG).show();
								appointmentDetail.remove(position);
								notifyDataSetChanged();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});*/
			view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.pushFragments(AppConstants.TAB_CALENDAR, new More_Notification("appointment"),true,true);
				
			}
		});
			timetxt.setText(""+appointmentDetail.get(position).get(0));
			commenttxt.setText(""+appointmentDetail.get(position).get(1));
			return (row);
		}
	}

}
