package com.tny.volvr.dancer.home;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tny.utils.CountryOrState;
import com.volvr.beans.Country;
import com.volvr.beans.OtherInfo;
import com.volvr.beans.UserInfo;
import com.volvr.beans.WorkingAt;
import com.volvr.search.R;

public class DancerSignup2 extends Activity {

	private EditText zipcode;
	private EditText wagerate;
	private EditText referedid;
	private Button back,next;
	com.tny.utils.MyTextView addmore;
	private Button country;
	private Button state;
	private Button workingat;
	ArrayList<WorkingAt> ALclub;
	ArrayList<Country> ALcountry;
	//ArrayList<String> countryList=new ArrayList<String>();
	ArrayList<String> workingList=new ArrayList<String>();
	ArrayList<OtherInfo> otherList=new ArrayList<OtherInfo>();
	Handler mHandler = new Handler();
	private static String t="HomeStripperSignup2";
	private LinearLayout mContainerView;
	Context context;
	String strZipcode,strWagerate,strReferelid,strCountry,strState,strWorkingat;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_signup_2);
		context=this;
		mySharedPreferences = context.getSharedPreferences("SIGNUP_2", 0);
		setContent();
	}
	private void setContent()
	{
		mContainerView = (LinearLayout) findViewById(R.id.parentView);
		zipcode=(EditText)findViewById(R.id.stripper_signup2_zipcode);
		zipcode.setText(mySharedPreferences.getString("mZipCode", ""));
		wagerate=(EditText)findViewById(R.id.stripper_signup2_wagerate);
		wagerate.setText(mySharedPreferences.getString("mWageRate", ""));
		wagerate.setVisibility(View.GONE);
		referedid=(EditText)findViewById(R.id.stripper_signup2_referanceid);
		referedid.setText(mySharedPreferences.getString("mPayPalId", ""));
		
		country=(Button)findViewById(R.id.stripper_signup2_country);
		country.setOnClickListener(listener);
		country.setText(mySharedPreferences.getString("mCountry", ""));
		state=(Button)findViewById(R.id.stripper_signup2_state);
		state.setOnClickListener(listener);
		state.setText(mySharedPreferences.getString("mState", ""));
		
		workingat=(Button)findViewById(R.id.stripper_signup2_workingat);
		workingat.setOnClickListener(listener);
		workingat.setText(mySharedPreferences.getString("mWorkingat", ""));
		back=(Button)findViewById(R.id.stripper_signup2_back);
		back.setOnClickListener(listener);
		next=(Button)findViewById(R.id.stripper_signup_header_next1);
		if (UserInfo.USERTYPE=="stripper") {
			next.setBackgroundResource(R.drawable.cbtn_next);
		}else if (UserInfo.USERTYPE=="club") {
			next.setBackgroundResource(R.drawable.btn_done);
		}else if (UserInfo.USERTYPE=="fan") {
			next.setBackgroundResource(R.drawable.btn_done);
		}
		next.setOnClickListener(listener);
		addmore=(com.tny.utils.MyTextView)findViewById(R.id.stripper_signup2_addmore);
		addmore.setOnClickListener(listener);
		addmore.setVisibility(View.GONE);
		int i=mySharedPreferences.getInt("otherlistsize", 0);
		Log.i(t, "size "+i);
		otherList.clear();
		if (i>0) {
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
				e.printStackTrace();
			}
		}
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.stripper_signup_header_next1:
			//	if (UserInfo.USERTYPE=="stripper") {
					next();
			//		}
				break;
			case R.id.stripper_signup2_addmore:
				inflateEditRow();
				break;
			case R.id.stripper_signup2_back:
				onBackPressed();	
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

	private void next()
	{
		Intent i=new Intent(context, DancerSignup3.class);
		startActivity(i);
	}

	private void strippersignup2Country() {

		i=new Intent(context, CountryOrState.class);
		i.putExtra("whichList", "COUNTRY");
		startActivityForResult(i,GET_COUNTRY);
	}

	private void strippersignup2State() {
		i=new Intent(context, CountryOrState.class);
		i.putExtra("whichList", "STATE");
		i.putExtra("countryCode", country.getText().toString());
		startActivityForResult(i,GET_STATE);
	}
	private void strippersignup2WorkingAt(){
		i=new Intent(context, CountryOrState.class);
		i.putExtra("whichList", "WORKINGAT");
		startActivityForResult(i,GET_CLUB);
		
	}
	
/*	public void showNetworkErrorMessage(){
		Builder dlg = new AlertDialog.Builder(context);
		dlg.setCancelable(false);
		dlg.setTitle("Error");
		dlg.setMessage("Network error has occured. Please check the network status of your phone and retry");
		dlg.setPositiveButton("Retry", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,	int which) {
			//	((Activity)context).finish();
				//System.exit(0);
			}
		});
		dlg.setNegativeButton("Close", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int which) {
				((Activity)context).finish();
				System.exit(0);
			}
		});
		dlg.show();
	}*/

	private void inflateEditRow() {
		addmore.setVisibility(View.VISIBLE);
		OtherInfo otherinfo=new OtherInfo();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		mContainerView.addView(rowView, mContainerView.getChildCount() - 3);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK&&requestCode==GET_COUNTRY) {
			country.setText(data.getStringExtra("selectedValue"));
		}if (resultCode==RESULT_OK&&requestCode==GET_STATE) {
			state.setText(data.getStringExtra("selectedValue"));
		}if (resultCode==RESULT_OK&&requestCode==GET_CLUB) {
			workingat.setText(data.getStringExtra("selectedValue"));
			if (data.getStringExtra("selectedValue").equals("Other")) {
				inflateEditRow();
			}
		}
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		setPrereranceValue();
	}
	private void setPrereranceValue() {
		strZipcode=zipcode.getText().toString();
		strWagerate=wagerate.getText().toString();
		strReferelid=referedid.getText().toString();
		strWorkingat=workingat.getText().toString();
		strCountry=country.getText().toString();
		strState=state.getText().toString();
		
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		//editor.putInt("countryselection", country.getSelectedItemPosition());
	//	editor.putInt("stateselection", state.getSelectedItemPosition());
		
		editor.putString("mCountry", strCountry);
		editor.putString("mState", strState);
		editor.putString("mWorkingat", strWorkingat);
		
		
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
}

