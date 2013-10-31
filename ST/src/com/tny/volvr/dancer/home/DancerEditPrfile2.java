package com.tny.volvr.dancer.home;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tny.utils.CountryOrState;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.OtherInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.search.R;

public class DancerEditPrfile2 extends BaseFragment{
	private View view;
	private static String t="HomeStripperEditPrfile2";
	private EditText zipcode;
	
	private EditText wagerate,referedid;

	private Button back,next;
	com.tny.utils.MyTextView addmore;
	private Button country,state,workingat;
	ArrayList<OtherInfo> otherList=new ArrayList<OtherInfo>();
	private LinearLayout mContainerView;
	String strZipcode,strWagerate,strReferelid,strCountry,strState;
    String strClubName="";
    String strClubNumber="";
    String strClubEmail="";
    String strClubAddress="";
    SharedPreferences mySharedPreferences;
	Intent i;
	private static int GET_COUNTRY=1131;
	private static int GET_STATE=1132;
	private static int GET_CLUB=1133;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
         view       =   inflater.inflate(R.layout.home_signup_2, container, false);
         mySharedPreferences = getActivity().getSharedPreferences("SIGNUP_2", 0);
        setContent();
      
        return view;
	}
	
	
	private void setContent()
	{
		mContainerView = (LinearLayout) view.findViewById(R.id.parentView);
		zipcode=(EditText)view.findViewById(R.id.stripper_signup2_zipcode);
	    wagerate=(EditText)view.findViewById(R.id.stripper_signup2_wagerate);
		referedid=(EditText)view.findViewById(R.id.stripper_signup2_referanceid);
		country=(Button)view.findViewById(R.id.stripper_signup2_country);
		country.setOnClickListener(listener);
		state=(Button)view.findViewById(R.id.stripper_signup2_state);
		state.setOnClickListener(listener);
		workingat=(Button)view.findViewById(R.id.stripper_signup2_workingat);
		workingat.setOnClickListener(listener);
        back=(Button)view.findViewById(R.id.stripper_signup2_back);
		back.setOnClickListener(listener);
		next=(Button)view.findViewById(R.id.stripper_signup_header_next1);
		next.setOnClickListener(listener);
		addmore=(com.tny.utils.MyTextView)view.findViewById(R.id.stripper_signup2_addmore);
		addmore.setOnClickListener(listener);
		
		addmore.setVisibility(View.GONE);
	
		
		if (StripperInfo.isProfileEdit) {
			setValueFromSharedPrefetance();
		}else {
			setValueFromStripperInfo();
		}
		
	}
private void setValueFromSharedPrefetance() {
	zipcode.setText(mySharedPreferences.getString("mZipCode", ""));	
	wagerate.setText(mySharedPreferences.getString("mWageRate", ""));
	referedid.setText(mySharedPreferences.getString("mPayPalId", ""));
	country.setText(mySharedPreferences.getString("mCountry", ""));
	state.setText(mySharedPreferences.getString("mState", ""));
	
	int i=mySharedPreferences.getInt("otherlistsize", 0);
	Log.i(t, "size "+i);
	otherList.clear();
	if (i>0) {
		Log.i(t, mySharedPreferences.getString("strClubName", ""));
		Log.i(t, mySharedPreferences.getString("strClubNumber", ""));
		Log.i(t, mySharedPreferences.getString("strClubEmail", ""));
		Log.i(t, mySharedPreferences.getString("strClubAddress", ""));
		
		String []namearray=mySharedPreferences.getString("strClubName", "").split("#");
		String []numberarray=mySharedPreferences.getString("strClubNumber", "").split("#");
		String []emailarray=mySharedPreferences.getString("strClubEmail", "").split("#");
		String []addressarray=mySharedPreferences.getString("strClubAddress", "").split("#");
		for (int j = 0; j < i; j++) {
			inflateEditRow();	
		}
		try {
			for (int j = 0; j < otherList.size(); j++) {
			otherList.get(j).getName().setText(namearray[j]);	
			otherList.get(j).getNumber().setText(numberarray[j]);
			otherList.get(j).getEmail().setText(emailarray[j]);
			otherList.get(j).getAddress().setText(addressarray[j]);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
private void setValueFromStripperInfo() {
	zipcode.setText(StripperInfo.user_address_zip);
	wagerate.setText(StripperInfo.wage_rate);
	referedid.setText(StripperInfo.user_referral_id);
	country.setText(StripperInfo.user_address_country);
	state.setText(StripperInfo.user_address_state);

	int i=mySharedPreferences.getInt("otherlistsize", 0);
	Log.i(t, "size "+i);
	otherList.clear();
	if (i>0) {
		
	}
}
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.stripper_signup_header_next1:
				next();
				break;
			case R.id.stripper_signup2_addmore:
				inflateEditRow();
			break;
			case R.id.stripper_signup2_back:
				getActivity().onBackPressed();
				break;
			case R.id.stripper_signup2_country:
				strippersignup2Country();
				break;
			case R.id.stripper_signup2_state:
				strippersignup2State();	
				break;
			case R.id.stripper_signup2_workingat:
				strippersignup2WorkingAt();
				break;
			default:
				break;
			}

		}
	};
	private void strippersignup2Country() {

		i=new Intent(getActivity(), CountryOrState.class);
		i.putExtra("whichList", "COUNTRY");
		getActivity().startActivityForResult(i,GET_COUNTRY);
	}

	private void strippersignup2State() {
		i=new Intent(getActivity(), CountryOrState.class);
		i.putExtra("whichList", "STATE");
		i.putExtra("countryCode", country.getText().toString());
		getActivity().startActivityForResult(i,GET_STATE);
	}
	private void strippersignup2WorkingAt() {
		i=new Intent(getActivity(), CountryOrState.class);
		i.putExtra("whichList", "WORKINGAT");
		getActivity().startActivityForResult(i,GET_CLUB);
		
	}
	private void next()
	{
		 mActivity.pushFragments(AppConstants.TAB_HOME, new DancerEditPrfile3(),true,true);
		}


	public void showNetworkErrorMessage(){
		Builder dlg = new AlertDialog.Builder(getActivity());
		dlg.setCancelable(false);
		dlg.setTitle("Error");
		dlg.setMessage("Network error has occured. Please check the network status of your phone and retry");
		dlg.setPositiveButton("Retry", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,	int which) {
				((Activity)getActivity()).finish();
				System.exit(0);
			}
		});
		dlg.setNegativeButton("Close", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int which) {
				((Activity)getActivity()).finish();
				System.exit(0);
			}
		});
		dlg.show();
	}

	private void inflateEditRow() {
		addmore.setVisibility(View.VISIBLE);
     OtherInfo otherinfo=new OtherInfo();
		LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.row, null);
		
		final EditText nameEditText = (EditText) rowView
				.findViewById(R.id.stripper_signup2_name);
		otherinfo.setName(nameEditText);
		final EditText numberEditText = (EditText) rowView
				.findViewById(R.id.stripper_signup2_contactno);
		otherinfo.setNumber(numberEditText);
		final EditText emailEditText = (EditText) rowView
				.findViewById(R.id.stripper_signup2_email);
		otherinfo.setEmail(emailEditText);
		final EditText addressEditText = (EditText) rowView
				.findViewById(R.id.stripper_signup2_address);
		otherinfo.setAddress(addressEditText);
		
		otherList.add(otherinfo);

		// Inflate at the end of all rows but before the "Add new" button
		mContainerView.addView(rowView, mContainerView.getChildCount() - 3);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
		 Log.i(t, "on pouse");
		StripperInfo.isProfileEdit=true;
		
		 strZipcode=zipcode.getText().toString();
		 strWagerate=wagerate.getText().toString();
		 strReferelid=referedid.getText().toString();	
		 strCountry=country.getText().toString();
		 strState=state.getText().toString();
		  
		 SharedPreferences.Editor editor = mySharedPreferences.edit();
		 
		 editor.putString("mCountry", strCountry);
		 editor.putString("mState", strState);
		 editor.putString("mZipCode", strZipcode);
		 editor.putString("mWageRate", strWagerate);
		 editor.putString("mPayPalId", strReferelid);
			int i=0;
			i=otherList.size();
			 Log.i(t, "suiz4"+i);
		 if (i>0) {
		    for (int j = 0; j < i; j++) {
					strClubName=strClubName+otherList.get(j).getName().getText().toString();
					strClubAddress=strClubName+otherList.get(j).getAddress().getText().toString();
					strClubEmail=strClubName+otherList.get(j).getEmail().getText().toString();
					strClubNumber=strClubName+otherList.get(j).getNumber().getText().toString();
					if (j!=i-1) {
						strClubName=strClubName+"#";
						strClubNumber=strClubNumber+"#";
						strClubEmail=strClubEmail+"#";
						strClubAddress=strClubAddress+"#";	
					 }
					}
				
		editor.putInt("otherlistsize", i);	
		editor.putString("strClubName", strClubName);
		editor.putString("strClubNumber", strClubNumber);
		editor.putString("strClubEmail", strClubEmail);
		editor.putString("strClubAddress", strClubAddress);
		
				
				}
		 editor.commit();
		
		    Log.i(t, mySharedPreferences.getString("strClubName", ""));
			Log.i(t, mySharedPreferences.getString("strClubNumber", ""));
			Log.i(t, mySharedPreferences.getString("strClubEmail", ""));
			Log.i(t, mySharedPreferences.getString("strClubAddress", ""));
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==Activity.RESULT_OK&&requestCode==GET_COUNTRY) {
			country.setText(data.getStringExtra("selectedValue"));
		}if (resultCode==Activity.RESULT_OK&&requestCode==GET_STATE) {
			state.setText(data.getStringExtra("selectedValue"));
		}if (resultCode==Activity.RESULT_OK&&requestCode==GET_CLUB) {
			workingat.setText(data.getStringExtra("selectedValue"));
			if (data.getStringExtra("selectedValue").equals("Other")) {
				inflateEditRow();
			}
		}
	}
}
