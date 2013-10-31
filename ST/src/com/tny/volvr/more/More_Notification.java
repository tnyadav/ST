package com.tny.volvr.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tny.volvr.base.BaseFragment;
import com.volvr.search.R;

public class More_Notification extends BaseFragment{
private View view;
private String apnId;
private Button apperove,disapprove;
private com.tny.utils.MyTextView content;


public More_Notification(String apnId)
{
	this.apnId=apnId;
}
public More_Notification()
{
	
}
@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view       =   inflater.inflate(R.layout.stripper_more_notification, container, false);
	        setContent();
	       
	        return view;
	}
	
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
				/*case R.id.schedule_disapprove:
				getActivity().onBackPressed();	
				break;*/
				case R.id.schedule_approve:
					
					break;
				case R.id.schedule_disapprove:
				
					break;
					
			default:
				break;
			}

		}
	};
	 
private void setContent() {
//	Appointments appointments=Appointments.getAppointment(apnId);
	content=(com.tny.utils.MyTextView)view.findViewById(R.id.schedule_content);
	content.setText(""/*"Name:\nStart Date:"+appointments.getApn_start_date()+"\nEnd Date:"+appointments.getApn_end_date()
			+"\nDiscription"+appointments.getApn_description()
			+"Place:"+appointments.getApn_place()
			+"\nCity:"+appointments.getApn_city()+"\nState:"+appointments.getApn_state()+"\nCountry:"+appointments.getApn_country()
			+"\nDate Created:"+appointments.getApn_created_date()*/);
	apperove=(Button)view.findViewById(R.id.schedule_approve);
	apperove.setOnClickListener(listener);
	disapprove=(Button)view.findViewById(R.id.schedule_disapprove);
	disapprove.setOnClickListener(listener);
	
	/*backbtn=(Button)view.findViewById(R.id.contact_us_back);
	backbtn.setOnClickListener(listener);*/
}
}
