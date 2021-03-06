package com.tny.volvr.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tny.volvr.base.BaseFragment;
import com.volvr.search.R;

public class More_Help extends BaseFragment{
	private View view;
	Button backbtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.stripper_help, container, false);
		setContent();
		return view;
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.stripper_help_back:
				getActivity().onBackPressed();	
				break;

			default:
				break;
			}

		}
	};
	private void setContent() {
//		fantxt=(com.tny.utils.MyTextView)view.findViewById(R.id.refral_details_fans);
//		clubtxt=(com.tny.utils.MyTextView)view.findViewById(R.id.refral_details_clubs);
//		strippertxt=(com.tny.utils.MyTextView)view.findViewById(R.id.refral_details_strippers);
//		subscription_month=(com.tny.utils.MyTextView)view.findViewById(R.id.refral_details_subscription_month);

		backbtn=(Button)view.findViewById(R.id.stripper_help_back);
		backbtn.setOnClickListener(listener);
	}
}
