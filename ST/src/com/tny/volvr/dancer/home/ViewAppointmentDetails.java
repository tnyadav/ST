package com.tny.volvr.dancer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tny.volvr.base.BaseFragment;
import com.volvr.search.R;

public class ViewAppointmentDetails extends BaseFragment{
	private String t="StripperViewReview";
	private View view;
	private com.tny.utils.MyTextView name,description,date;
	private Button back,mhmebtn;
	String title="",descriptionstr="",datestr="";
	public ViewAppointmentDetails(String titleValue,String descValue,String dateValue) {
		title=titleValue;
		descriptionstr=descValue;
		datestr=dateValue;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.view_offer_details, container, false);
		setContent();
		return view;
	}
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.view_back_button:
				getActivity().onBackPressed();	
				break;
				
			case R.id.view_home_button:
				getActivity().onBackPressed();
				getActivity().onBackPressed();
				break;
				
			default:
				break;
			}
		}
	};

	private void setContent() 
	{
		back=(Button)view.findViewById(R.id.view_back_button);
		back.setOnClickListener(listener);
		mhmebtn=(Button)view.findViewById(R.id.view_home_button);
		mhmebtn.setOnClickListener(listener);
		name=(com.tny.utils.MyTextView)view.findViewById(R.id.View_created_title);
		name.setText(title);
		description=(com.tny.utils.MyTextView)view.findViewById(R.id.View_title_description);
		description.setText(descriptionstr);
		date=(com.tny.utils.MyTextView)view.findViewById(R.id.View_status_from);
		date.setText(datestr);
	}
}
