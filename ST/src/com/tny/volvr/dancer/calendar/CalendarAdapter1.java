package com.tny.volvr.dancer.calendar;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.volvr.beans.Payment;
import com.volvr.search.R;

public class CalendarAdapter1 extends BaseAdapter {
	static final int FIRST_DAY_OF_WEEK =0;
	private Context mContext;
	private java.util.Calendar month;
	private Calendar selectedDate;
	private ArrayList<String> items;
	private ArrayList<Payment> itemsDate;
	// references to our items
	public String[] days;
	public CalendarAdapter1(Context c, Calendar monthCalendar, ArrayList<Payment> itemsDate) {
		month = monthCalendar;
		selectedDate = (Calendar)monthCalendar.clone();
		mContext = c;
		month.set(Calendar.DAY_OF_MONTH, 1);
		this.items = new ArrayList<String>();
		this.itemsDate=itemsDate;
		Log.e("itemsDate size", ""+itemsDate.size());
		refreshDays();
	}

	public void setItems(ArrayList<String> items) {
		for(int i = 0;i != items.size();i++){
			if(items.get(i).length()==1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}


	public int getCount() {
		return days.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final TextView dayView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_item, null);

		}
		dayView = (TextView)v.findViewById(R.id.date);

		// disable empty days from the beginning
		if(days[position].equals("")) {
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}
		else {
			// mark current day as focused
			if(month.get(Calendar.YEAR)== selectedDate.get(Calendar.YEAR) && month.get(Calendar.MONTH)== selectedDate.get(Calendar.MONTH) && days[position].equals(""+selectedDate.get(Calendar.DAY_OF_MONTH))) {
				dayView.setTextColor(mContext.getResources().getColor(R.color.white));
				dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			}
			else {
				dayView.setTextColor(mContext.getResources().getColor(R.color.calendergrey));
				dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null );
			}
		}
		dayView.setText(days[position]);

		Drawable img = mContext.getResources().getDrawable( R.drawable.reddot_image );
		//		String dateStr=days[position]+"-"+String.valueOf(month.get(Calendar.MONTH)+1)+"-"+month.get(Calendar.YEAR);
		String monthValue="",dayValue="";
		if(String.valueOf(month.get(Calendar.MONTH)+1).length()==1)
		{
			monthValue="0"+String.valueOf(month.get(Calendar.MONTH)+1);
		}
		else
		{
			monthValue=String.valueOf(month.get(Calendar.MONTH)+1);
		}
		if(days[position].length()==1)
		{
			dayValue="0"+days[position];
		}
		else
		{
			dayValue=days[position];
		}
		final String dateStr=month.get(Calendar.YEAR)+"-"+monthValue+"-"+dayValue;
		
		for (int i = 0; i < itemsDate.size(); i++) {
			
			if(itemsDate.get(i).getStartdatetime().equalsIgnoreCase(dateStr))
			{
				dayView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, img );
			}
		}
		// create date string for comparison
		String date = days[position];

		if(date.length()==1) {
			date = "0"+date;
		}
		String monthStr = ""+(month.get(Calendar.MONTH)+1);
		if(monthStr.length()==1) {
			monthStr = "0"+monthStr;
		}
		dayView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Drawable[] drawable=dayView.getCompoundDrawables();	
			if (drawable[3]!=null) {
				//UIUtils.showMessage(mContext, "inside adapter", dateStr);
				//mContext.pushFragments(AppConstants.TAB_CALENDAR, new DancerTravalStatus(),true,true);
			}
				
			}
		});
		return v;
	}

	public void refreshDays()
	{
		// clear items
		items.clear();

		int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = (int)month.get(Calendar.DAY_OF_WEEK);

		// figure size of the array
		if(firstDay==1){
			days = new String[lastDay+(FIRST_DAY_OF_WEEK*6)];
		}
		else {
			days = new String[lastDay+firstDay-(FIRST_DAY_OF_WEEK+1)];
		}

		int j=FIRST_DAY_OF_WEEK;

		// populate empty days before first real day
		if(firstDay>1) {
			for(j=0;j<firstDay-FIRST_DAY_OF_WEEK;j++) {
				days[j] = "";
			}
		}
		else {
			for(j=0;j<FIRST_DAY_OF_WEEK*6;j++) {
				days[j] = "";
			}
			j=FIRST_DAY_OF_WEEK*6+1; // sunday => 1, monday => 7
		}

		// populate days
		int dayNumber = 1;
		for(int i=j-1;i<days.length;i++) {
			days[i] = ""+dayNumber;
			dayNumber++;
		}
	}
}