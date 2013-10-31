package com.tny.volvr.more;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.UIUtils;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.Payment;
import com.volvr.search.R;

public class QueueManagementDetail extends BaseFragment{
	private View view;
	
	private Button back,arrived,seeshow;
	private TextView name,time,other;
    private Payment payment;
	
	public QueueManagementDetail(Payment payment) {
		super();
		this.payment=payment;
		// TODO Auto-generated constructor stub
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.queuemanageent_detail, container, false);
		setContent();

		return view;
	}
	
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			
			List<NameValuePair> entity = new ArrayList<NameValuePair>();
			entity.add(new BasicNameValuePair("action","payments_edit"));
			entity.add(new BasicNameValuePair("payment_id",payment.getPaymentid()));
			entity.add(new BasicNameValuePair("user_subscription_type",payment.getUsersubscriptiontype()));
			entity.add(new BasicNameValuePair("amount",payment.getAmounts()));
			entity.add(new BasicNameValuePair("payment_key","payments_edit"));
			entity.add(new BasicNameValuePair("start_datetime",payment.getStartdatetime()));
			entity.add(new BasicNameValuePair("end_datetime",payment.getEnddatetime()));
		    entity.add(new BasicNameValuePair("strip_user_id",payment.getStripuserid())) ; 	
			entity.add(new BasicNameValuePair("club_user_id",payment.getClubuserid())) ; 	
			entity.add(new BasicNameValuePair("fan_user_id",payment.getFanuserid())); 	
			entity.add(new BasicNameValuePair("strip_user_name",payment.getStripusername()));
			entity.add(new BasicNameValuePair("strip_user_fullname",payment.getStripuserfullname()));
			entity.add(new BasicNameValuePair(" club_user_name",payment.getClubusername()));
			entity.add(new BasicNameValuePair("club_user_fullname",payment.getClubuserfullname()));
			entity.add(new BasicNameValuePair("club_user_club_name",payment.getClubuserclubname()));
			entity.add(new BasicNameValuePair("fan_user_name",payment.getFanusername()));
			entity.add(new BasicNameValuePair("fan_user_fullname",payment.getFanuserfullname()));
			entity.add(new BasicNameValuePair("created_datetime",payment.getCreateddatetime()));
			entity.add(new BasicNameValuePair("modified_datetime",payment.getModifieddatetime()));
			
			switch (v.getId()) {
			case R.id.back:
				getActivity().onBackPressed();	
				break;
			case R.id.arrived:
				
				entity.add(new BasicNameValuePair("arrived_status","Yes"));
				entity.add(new BasicNameValuePair("no_see_status",payment.getNoseestatus()));
				
				new AsyncWebServiceProcessingTask(mActivity, entity, "Please wait...", new Callback() {
					
					@Override
					public void run(String response) {
						// TODO Auto-generated method stub
						
							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(response);
							if (jsonObject.optString("status").equals("success")) {
								Toast.makeText(mActivity,"Updated", Toast.LENGTH_SHORT).show();
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
				}).execute("");
				break;
			case R.id.see:
				entity.add(new BasicNameValuePair("arrived_status",payment.getArrivedstatus()));
				entity.add(new BasicNameValuePair("no_see_status","Yes"));
	           
				new AsyncWebServiceProcessingTask(mActivity, entity, "Please wait...", new Callback() {
					
					@Override
					public void run(String response) {
						// TODO Auto-generated method stub
					
							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(response);
							if (jsonObject.optString("status").equals("success")) {
								Toast.makeText(mActivity,"Updated", Toast.LENGTH_SHORT).show();
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
				}).execute("");
			default:
				break;
			}

		}
	};



	private void setContent() {
		back=(Button)view.findViewById(R.id.back);
		back.setOnClickListener(listener);
		arrived=(Button)view.findViewById(R.id.arrived);
		arrived.setOnClickListener(listener);

		seeshow=(Button)view.findViewById(R.id.see);
		seeshow.setOnClickListener(listener);
		TextView title=(TextView)view.findViewById(R.id.title);
		

		name=(TextView)view.findViewById(R.id.name);
		time=(TextView)view.findViewById(R.id.time);
		//other=(TextView)view.findViewById(R.id.other);
		String strname = "Dancer: "+payment.getStripuserfullname()+"\nFan: "+payment.getFanuserfullname();
		name.setText(strname);
		time.setText(payment.toString());
		
		
		
	}

	
}
