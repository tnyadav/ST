package com.tny.volvr.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.tny.utils.LogoutUtil;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.dancer.calendar.PaymentCalendar;
import com.volvr.beans.UserInfo;
import com.volvr.search.R;

public class TabMore extends BaseFragment {
	private  com.tny.utils.MyTextView logoutbtn,notificationbtn,contactusbtn,searchbtn,historybtn,favouritebtn,refralbtn,helpbtn,offer;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.more, container, false);
		setContent();
		return view;
	}

	private void setContent() {
		logoutbtn=(com.tny.utils.MyTextView)view.findViewById(R.id.more_logout);
		notificationbtn=(com.tny.utils.MyTextView)view.findViewById(R.id.more_notification);
		contactusbtn=(com.tny.utils.MyTextView)view.findViewById(R.id.more_contactus);
		searchbtn=(com.tny.utils.MyTextView)view.findViewById(R.id.more_search);
		if (!UserInfo.USERTYPE.equals("club")) {
			searchbtn.setVisibility(View.GONE);
		}
		historybtn=(com.tny.utils.MyTextView)view.findViewById(R.id.more_history);
		favouritebtn=(com.tny.utils.MyTextView)view.findViewById(R.id.more_favorate);
		refralbtn=(com.tny.utils.MyTextView)view.findViewById(R.id.more_refferaldetail);
		helpbtn=(com.tny.utils.MyTextView)view.findViewById(R.id.more_help);
		offer=(com.tny.utils.MyTextView)view.findViewById(R.id.more_offer);

		logoutbtn.setOnClickListener(listener);
		notificationbtn.setOnClickListener(listener);
		contactusbtn.setOnClickListener(listener);
		searchbtn.setOnClickListener(listener);
		historybtn.setOnClickListener(listener);
		favouritebtn.setOnClickListener(listener);
		refralbtn.setOnClickListener(listener);
		helpbtn.setOnClickListener(listener);
		offer.setOnClickListener(listener);
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.more_history:
				mActivity.pushFragments(AppConstants.TAB_MORE, new More_History(),true,true);
				break;

			case R.id.more_search:
				mActivity.pushFragments(AppConstants.TAB_MORE, new PaymentCalendar(1),true,true);
				break;

			case R.id.more_help:
				mActivity.pushFragments(AppConstants.TAB_MORE, new More_Help(),true,true);
				break;

			case R.id.more_favorate:
				mActivity.pushFragments(AppConstants.TAB_MORE, new More_Favourite(),true,true);
				break;

			case R.id.more_offer:
				mActivity.pushFragments(AppConstants.TAB_MORE, new StripperViewOffer("getallcluboffers"),true,true);
				break;

			case R.id.more_refferaldetail:
				mActivity.pushFragments(AppConstants.TAB_MORE, new More_refral_details(),true,true);
				break;

			case R.id.more_contactus:
				mActivity.pushFragments(AppConstants.TAB_MORE, new More_Contactus(),true,true);
				break;

			case R.id.more_notification:
				mActivity.pushFragments(AppConstants.TAB_MORE, new More_Notification(),true,true);
				break;

			case R.id.more_logout:
			LogoutUtil.logout(mActivity,imageLoader);	
				break;


			default:
				break;
			}

		}
	};

}

