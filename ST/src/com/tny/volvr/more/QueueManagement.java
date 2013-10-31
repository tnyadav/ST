package com.tny.volvr.more;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.Payment;
import com.volvr.search.R;

public class QueueManagement extends BaseFragment{
	private View view;
	String date;
	private Button back;
	ArrayList<Payment> temp; 
	ListView listView;
	

	public QueueManagement(String date) {
		super();
		this.date=date;
		temp=new ArrayList<Payment>();
		for (int i = 0; i < Payment.paymentlist.size(); i++) {
			if (Payment.paymentlist.get(i).getStartdatetime().equalsIgnoreCase(date)) {
				temp.add(Payment.paymentlist.get(i));
			}
		}
	}

	/*public QueueManagement() {
		super();
		// TODO Auto-generated constructor stub
	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.queuemanageent, container, false);
		setContent();

		return view;
	}
	
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			
		
				
			
			switch (v.getId()) {
			case R.id.back:
				getActivity().onBackPressed();	
				break;
			
			
			
			
			default:
				break;
			}

		}
	};



	private void setContent() {
		back=(Button)view.findViewById(R.id.back);
		back.setOnClickListener(listener);
		TextView title=(TextView)view.findViewById(R.id.title);
		title.setText(date);
	/*	if(!UserInfo.USERTYPE.equalsIgnoreCase("club"))
			title.setVisibility(View.INVISIBLE);*/
		listView=(ListView)view.findViewById(R.id.queue_list);
	
		listView.setAdapter(new Queue(temp));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			//	UIUtils.showMessage(mActivity, "", ""+arg2);
				mActivity.pushFragments(AppConstants.TAB_MORE, new QueueManagementDetail(temp.get(arg2)),true,true);
			}
		});
		
		
	}

	public class Queue extends BaseAdapter
	{
		LayoutInflater inflater;
		ArrayList<Payment> payments;
		public ImageLoader imageLoader; 
		Queue(ArrayList<Payment> payment)
		{
			payments=payment;
        inflater=(LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		

		public int getCount()
		{
			return payments.size();
		}

		public String getItem(int position)
		{
			return null;
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{

		
			View row;

			row = inflater.inflate(R.layout.queue_row_item, parent, false);

			TextView name = (TextView) row.findViewById(R.id.name);
			TextView time = (TextView) row.findViewById(R.id.time);
			time.setText(temp.get(position).getStartdatetime());
			name.setText(temp.get(position).getStripuserfullname());
			return (row);

		}
	}
}
