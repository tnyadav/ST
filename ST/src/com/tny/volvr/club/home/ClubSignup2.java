package com.tny.volvr.club.home;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.tny.utils.CountryOrState;
import com.tny.utils.SignupDetail;
import com.volvr.search.R;

public class ClubSignup2 extends Activity {

	private Button country, state, licence,back,next;
	private EditText zipcode, referalid;
	
	private static String t = "ClubSignup2";

	Context context;
	String strZipcode, strReferelid, strCountry, strState,
			strlicence;
	
	private static int GET_COUNTRY=1231;
	private static int GET_STATE=1232;
	private static int GET_LICENCE=1233;
	
	Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.club_signup_2);
		context = this;
		
		setContent();
	}

	private void setContent() {
		// buttons
		
		back=(Button)findViewById(R.id.club_signup2_back);
		back.setOnClickListener(listener);
	
		next=(Button)findViewById(R.id.club_signup2_header_next1);
		next.setOnClickListener(listener);
		
		country = (Button) findViewById(R.id.club_signup2_country);
		country.setText(SignupDetail.S_Country);
		country.setOnClickListener(listener);
		state = (Button) findViewById(R.id.club_signup2_state);
		state.setText(SignupDetail.S_State);
		state.setOnClickListener(listener);
		licence = (Button) findViewById(R.id.club_signup2_license);
		licence.setOnClickListener(listener);
		if (SignupDetail.S_License.length()>0) {
			licence.setText(fileName(SignupDetail.S_License));	
		}
		
		// edittext

		zipcode = (EditText) findViewById(R.id.club_signup2_zipcode);
		zipcode.setText(SignupDetail.S_ZipCode);
	
		referalid = (EditText) findViewById(R.id.club_signup2_referanceid);
		referalid.setText(SignupDetail.S_PayPalId);

	}

	
	

	/*public void clubSignup2Country(View v) {

		i=new Intent(context, CountryOrState.class);
		i.putExtra("whichList", "COUNTRY");
		i.putExtra("countryCode", "");
		startActivityForResult(i,GET_COUNTRY);
	}

	public void clubSignup2State(View v) {
		i=new Intent(context, CountryOrState.class);
		i.putExtra("whichList", "STATE");
		i.putExtra("countryCode", country.getText().toString());
		startActivityForResult(i,GET_STATE);
	}
*/
	public void clubSignup2Licence(View v) {
		
	}
	public void clubSignup2WorkingAt(View v) {

	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) {
			case R.id.club_signup2_header_next1:
				Intent i = new Intent(context, ClubSignup3.class);
				startActivity(i);
				break;
			case R.id.club_signup2_back:
				onBackPressed();	
				break;

			case R.id.club_signup2_country:
				i=new Intent(context, CountryOrState.class);
				i.putExtra("whichList", "COUNTRY");
				i.putExtra("countryCode", "");
				startActivityForResult(i,GET_COUNTRY);
				break;
			case R.id.club_signup2_state:
				i=new Intent(context, CountryOrState.class);
				i.putExtra("whichList", "STATE");
				i.putExtra("countryCode", country.getText().toString());
				startActivityForResult(i,GET_STATE);
				break;
			case R.id.club_signup2_license:
				 i = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, GET_LICENCE);
				break;
				
				
			default:
				break;
			}

		}
	};

	
	
	@Override
	protected void onPause() {
		super.onPause();
		
		SignupDetail.S_Country = country.getText().toString();
		SignupDetail.S_State = state.getText().toString();
		SignupDetail.S_ZipCode = zipcode.getText().toString();
		SignupDetail.S_PayPalId = referalid.getText().toString();

		
	
	}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode==RESULT_OK&&requestCode==GET_COUNTRY) {
		try {
			country.setText(data.getStringExtra("selectedValue"));
		} catch (Exception e) {
		}
		
	}if (resultCode==RESULT_OK&&requestCode==GET_STATE) {
		try {
			state.setText(data.getStringExtra("selectedValue"));
		} catch (Exception e) {
		}
	}if (requestCode == GET_LICENCE&&resultCode==Activity.RESULT_OK) {
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
		cursor.moveToFirst(); 
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String strLicensepath = cursor.getString(columnIndex);
		SignupDetail.S_License=strLicensepath;

		Log.i(t, strLicensepath);
		cursor.close();             
		licence.setText(fileName(strLicensepath));
		//		            imgUpload.setImageBitmap(BitmapFactory.decodeFile(picturePath));

	}else {

	
}
}
public String fileName(String f) {
	Log.i("fileName", ""+f);
	Uri u = Uri.parse(f);

	File f1 = new File("" + u);


	return f1.getName();
}
}
