package com.tny.volvr.dancer.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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
import android.widget.BaseAdapter;
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
import com.tny.volvr.more.QueueManagement;
import com.tny.volvr.more.QueueManagementForStripperClub;
import com.volvr.beans.FanInfo;
import com.volvr.beans.Payment;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class PaymentCalendar extends BaseFragment {
	public Calendar month;
	public CalendarAdapter11 adapter;
	Button addschedule;
	TextView title,mtravelStatus;
	public ArrayList<String> items; 
	View view;
	private int from;
	ArrayList<Payment> paymentslist=new ArrayList<Payment>();
	public PaymentCalendar(int from) {
		super();
		this.from=from;
	}
	public PaymentCalendar() {
		super();
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.calendar, container, false);
		//UIUtils.showMessage(mActivity, "", ""+paymentslist.size());
		setContent();
		return view;
	}

	private void setContent() {
		// itemsDate.add("12-12-2013");

	/*	title = (TextView) view
				.findViewById(R.id.title);*/
		mtravelStatus=(TextView)view.findViewById(R.id.stripper_profile_mytravlestatus);
		if (!UserInfo.USERTYPE.equals("stripper")) {
			mtravelStatus.setVisibility(View.GONE);
		}
		mtravelStatus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.pushFragments(AppConstants.TAB_CALENDAR, new DancerTravalStatus(),true,true);
			}
		});
		
		
		addschedule=(Button)view.findViewById(R.id.stripper_calender_done);
		if (!UserInfo.USERTYPE.equalsIgnoreCase("club")||from==1) {
			addschedule.setVisibility(View.GONE);
		}
		addschedule.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.pushFragments(AppConstants.TAB_CALENDAR, new TabMessages("appointment"),true,true);
			}
		});


		TextView previous = (TextView) view.findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month
						.getActualMinimum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) - 1),
							month.getActualMaximum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
				}
				refreshCalendar();
			}
		});

		TextView next = (TextView) view.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month
						.getActualMaximum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) + 1),
							month.getActualMinimum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
				}
				refreshCalendar();
			}
		});

		
	
		if (Util1.isNetworkAvailable(getActivity())) {
			String user_Id = "";
			if (UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
				user_Id = StripperInfo.user_id;
			if (UserInfo.USERTYPE.equalsIgnoreCase("club"))
				user_Id = StripperInfo.user_id;
			if (UserInfo.USERTYPE.equalsIgnoreCase("fan"))
				user_Id = FanInfo.fanproFileInfo.user_id;
			List<NameValuePair> entity = new ArrayList<NameValuePair>();
			entity.add(new BasicNameValuePair("action", "payments_get"));
			entity.add(new BasicNameValuePair("user_id", user_Id));

			Log.e("user_id", user_Id);
			new AsyncWebServiceProcessingTask(getActivity(), entity,
					"Please wait....", new Callback() {

						@Override
						public void run(String result) {
							// TODO Auto-generated method stub
							try {
								JSONObject jsonResponse = new JSONObject(result);
								if (jsonResponse.optString("status")
										.equalsIgnoreCase("success")) {
									Payment.parsePayment(jsonResponse);

									bindDataToCalendar();
								} else {
									bindDataToCalendar();
								}
							} catch (JSONException e) {
								e.printStackTrace();
								bindDataToCalendar();
							}
						}
					}).execute("");
		} else {
			bindDataToCalendar();
		}
	}

	private void bindDataToCalendar() {

		month = Calendar.getInstance();
		DatePicker dp = new DatePicker(getActivity());
		String datepick = "" + dp.getYear() + "-" + dp.getMonth() + "-"
				+ dp.getDayOfMonth();
		onNewIntent(datepick);

		items = new ArrayList<String>();
		
		if (Payment.paymentlist!=null) {
			 paymentslist=Payment.paymentlist;
		}
			  
		
	 
		
		adapter = new CalendarAdapter11(getActivity(), month,
				paymentslist);
		//UIUtils.showMessage(mActivity, "", ""+paymentslist.size());
		refreshCalendar();
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView date = (TextView) v.findViewById(R.id.date);
				Drawable[] drawable = date.getCompoundDrawables();
				// Log.e("drawable=",""+drawable[0]+"-"+drawable[1]+"-"+drawable[2]+"-"+drawable[3]);
				if (date instanceof TextView && !date.getText().equals("")
						&& drawable[3] != null) {
					//Intent intent = new Intent();
					String day = date.getText().toString();
					if (day.length() == 1) {
						day = "0" + day;
					}
				}

			}
		});
	}

	public void refreshCalendar() {
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		Handler handler = new Handler();
		handler.post(calendarUpdater);
		handler.post(calendarUpdater); // generate some random calendar items
	}

	public void onNewIntent(String date) {
		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
		month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
				Integer.parseInt(dateArr[2]));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();
			// format random values. You can implement a dedicated class to
			// provide real values
			for (int i = 0; i < 31; i++) {
				Random r = new Random();

				if (r.nextInt(10) > 6) {
					items.add(Integer.toString(i));
				}
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};

	class CalendarAdapter11 extends BaseAdapter {
		static final int FIRST_DAY_OF_WEEK = 0;
		private Context mContext;
		private java.util.Calendar month;
		private Calendar selectedDate;
		private ArrayList<String> items;
		private ArrayList<Payment> itemsDate;
		// references to our items
		public String[] days;

		public CalendarAdapter11(Context c, Calendar monthCalendar,
				ArrayList<Payment> itemsDate) {
			month = monthCalendar;
			selectedDate = (Calendar) monthCalendar.clone();
			mContext = c;
			month.set(Calendar.DAY_OF_MONTH, 1);
			this.items = new ArrayList<String>();
			this.itemsDate = itemsDate;
		//	Log.e("itemsDate size", "" + itemsDate.size());
			refreshDays();
		}

		public void setItems(ArrayList<String> items) {
			for (int i = 0; i != items.size(); i++) {
				if (items.get(i).length() == 1) {
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
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				LayoutInflater vi = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.calendar_item, null);

			}
			dayView = (TextView) v.findViewById(R.id.date);

			// disable empty days from the beginning
			if (days[position].equals("")) {
				dayView.setClickable(false);
				dayView.setFocusable(false);
			} else {
				// mark current day as focused
				if (month.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR)
						&& month.get(Calendar.MONTH) == selectedDate
								.get(Calendar.MONTH)
						&& days[position].equals(""
								+ selectedDate.get(Calendar.DAY_OF_MONTH))) {
					dayView.setTextColor(mContext.getResources().getColor(
							R.color.white));
					dayView.setCompoundDrawablesWithIntrinsicBounds(null, null,
							null, null);
				} else {
					dayView.setTextColor(mContext.getResources().getColor(
							R.color.calendergrey));
					dayView.setCompoundDrawablesWithIntrinsicBounds(null, null,
							null, null);
				}
			}
			dayView.setText(days[position]);

			Drawable img = mContext.getResources().getDrawable(
					R.drawable.reddot_image);
			// String
			// dateStr=days[position]+"-"+String.valueOf(month.get(Calendar.MONTH)+1)+"-"+month.get(Calendar.YEAR);
			String monthValue = "", dayValue = "";
			if (String.valueOf(month.get(Calendar.MONTH) + 1).length() == 1) {
				monthValue = "0"
						+ String.valueOf(month.get(Calendar.MONTH) + 1);
			} else {
				monthValue = String.valueOf(month.get(Calendar.MONTH) + 1);
			}
			if (days[position].length() == 1) {
				dayValue = "0" + days[position];
			} else {
				dayValue = days[position];
			}
			final String dateStr = month.get(Calendar.YEAR) + "-" + monthValue
					+ "-" + dayValue;

			for (int i = 0; i < itemsDate.size(); i++) {

				if (itemsDate.get(i).getStartdatetime()
						.equalsIgnoreCase(dateStr)) {
					dayView.setCompoundDrawablesWithIntrinsicBounds(null, null,
							null, img);
				}
			}
			// create date string for comparison
			String date = days[position];

			if (date.length() == 1) {
				date = "0" + date;
			}
			String monthStr = "" + (month.get(Calendar.MONTH) + 1);
			if (monthStr.length() == 1) {
				monthStr = "0" + monthStr;
			}
			dayView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Drawable[] drawable = dayView.getCompoundDrawables();
					if (drawable[3] != null) {
						if (from==0) {
							mActivity.pushFragments(AppConstants.TAB_CALENDAR,
									new QueueManagementForStripperClub(dateStr), true, true);
						}else {
							mActivity.pushFragments(AppConstants.TAB_MORE,
									new QueueManagement(dateStr), true, true);
						}
						
					}

				}
			});
			return v;
		}

		public void refreshDays() {
			// clear items
			items.clear();

			int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
			int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);

			// figure size of the array
			if (firstDay == 1) {
				days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
			} else {
				days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
			}

			int j = FIRST_DAY_OF_WEEK;

			// populate empty days before first real day
			if (firstDay > 1) {
				for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
					days[j] = "";
				}
			} else {
				for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
					days[j] = "";
				}
				j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
			}

			// populate days
			int dayNumber = 1;
			for (int i = j - 1; i < days.length; i++) {
				days[i] = "" + dayNumber;
				dayNumber++;
			}
		}
	}
}