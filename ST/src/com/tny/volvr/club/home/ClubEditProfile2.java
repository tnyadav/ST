package com.tny.volvr.club.home;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tny.utils.CountryOrState;
import com.tny.utils.SignupDetail;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.volvr.beans.StripperInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class ClubEditProfile2 extends BaseFragment {

	private Button country, state, licence,back,next;
	private EditText zipcode, referalid;
	
	private static String t = "ClubSignup2";


	String strZipcode, strReferelid, strCountry, strState,
			strlicence;
	

	private static int GET_COUNTRY=1231;
	private static int GET_STATE=1232;
	//private static int GET_LICENCE=1233;
	private View view;
	Intent i;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
         view       =   inflater.inflate(R.layout.club_signup_2, container, false);
       
         setContent();
        return view;
	}
	
	private void setContent() {
		// buttons
		if (!StripperInfo.isProfileEdit) {
			Log.e(t, "first time");
			SignupDetail.S_Country =Clubprofile.clubs.getClub_country();
			SignupDetail.S_State = Clubprofile.clubs.getClub_state();
			SignupDetail.S_ZipCode = Clubprofile.clubs.getClub_zip_code();
			SignupDetail.S_PayPalId =StripperInfo.user_paypal_id;
		}
		
		back=(Button)view.findViewById(R.id.club_signup2_back);
		back.setOnClickListener(listener);
	
		next=(Button)view.findViewById(R.id.club_signup2_header_next1);
		next.setOnClickListener(listener);
		
		country = (Button) view.findViewById(R.id.club_signup2_country);
		country.setText(SignupDetail.S_Country);
		country.setOnClickListener(listener);
		state = (Button) view.findViewById(R.id.club_signup2_state);
		state.setText(SignupDetail.S_State);
		state.setOnClickListener(listener);
		zipcode = (EditText) view.findViewById(R.id.club_signup2_zipcode);
		zipcode.setText(SignupDetail.S_ZipCode);
		referalid = (EditText) view.findViewById(R.id.club_signup2_referanceid);
		referalid.setText(SignupDetail.S_PayPalId);
		
		
		
		licence = (Button) view.findViewById(R.id.club_signup2_license);
		licence.setVisibility(View.GONE);
	}

	
	

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.club_signup2_header_next1:
				setValue();
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new ClubEditProfile3(), true, true);
				break;
			case R.id.club_signup2_back:
				setValue();
				getActivity().onBackPressed();	
				
				break;

			case R.id.club_signup2_country:
				i=new Intent(mActivity, CountryOrState.class);
				i.putExtra("whichList", "COUNTRY");
				i.putExtra("countryCode", "");
				getActivity().startActivityForResult(i,GET_COUNTRY);
				break;
			case R.id.club_signup2_state:
				i=new Intent(mActivity, CountryOrState.class);
				i.putExtra("whichList", "STATE");
				i.putExtra("countryCode", country.getText().toString());
				getActivity().startActivityForResult(i,GET_STATE);
				break;

				
				
			default:
				break;
			}

		}
	};

	private void setValue() {
		StripperInfo.isProfileEdit=true;
		SignupDetail.S_Country = country.getText().toString();
		SignupDetail.S_State = state.getText().toString();
		SignupDetail.S_ZipCode = zipcode.getText().toString();
		SignupDetail.S_PayPalId = referalid.getText().toString();

		
	
	}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode==Activity.RESULT_OK&&requestCode==GET_COUNTRY) {
		try {
			country.setText(data.getStringExtra("selectedValue"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}if (resultCode==Activity.RESULT_OK&&requestCode==GET_STATE) {
		try {
			state.setText(data.getStringExtra("selectedValue"));
		} catch (Exception e) {
		}
	
	}
}
public String fileName(String f) {
	Log.i("fileName", ""+f);
	Uri u = Uri.parse(f);

	File f1 = new File("" + u);


	return f1.getName();
}
}
