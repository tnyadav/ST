package com.tny.volvr.dancer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperList;
import com.volvr.search.R;

public class DancerProfileAllInfo extends BaseFragment {

	private TextView info;
	private Button back;

	private View view;

	private static String t = "DancerProfileAllInfo";
	private int position;
	
	public  DancerProfileAllInfo(int position) {
		this.position=position;
	}
	;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.dancer_profile_all_info, container, false);
		setContent();
		
		return view;
	}

	private void setContent() 
	{
		back=(Button)view.findViewById(R.id.back);
		back.setOnClickListener(listener);
		

		info=(TextView)view.findViewById(R.id.info);

	
		setClubInformation();
	}
	  
	private void setClubInformation()
	{
		StripperList stripperList = StripperList.stripperInfoList.get(position);
		String allinfo="User Email: "+stripperList.user_email+"\n"
				+"Phone No: "+stripperList.user_phone+"\n"
				+"Birthday: "+stripperList.user_birthday+"\n"
				+"Country: "+stripperList.user_address_country+"\n"
				+"City: "+stripperList.user_address_city+"\n"
				+"State: "+stripperList.user_address_state+"\n"
				+"Zip Code: "+stripperList.user_address_zip+"\n"
				+"Last_Logined: "+stripperList.user_last_logined;
			info.setText(allinfo);	
				
		
	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.back:
				getActivity().onBackPressed();
				break;
			default:
				break;
			}
		}
	};
}
