package com.tny.volvr.dancer.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.CountryOrState;
import com.tny.utils.UIUtils;
import com.tny.volvr.base.BaseFragment;
import com.volvr.search.R;

public class PaymentProcess extends BaseFragment{
	private View view;
	private TextView dancername,dancerclubname,bookdate,amount,cardtype,month,year;
	private Button back,next, country,state;
	private EditText fname,lname,cardnumber,cardvarificationnumber,address1,address2,city,zipcode;
	private String strCardtype,strMonth,strYear,strCountry,strState,strFname,strLname,strCardNumber,strCardVarificationNumber,strAddress1,strAddress2,strCity,strZipCode,strCountry1,strState1;
	private static final String SELECT_CARD_TITLE="Card Type";
	private static final String SELECT_MONTH_TITLE="Select Month";
	private static final String SELECT_YEAR_TITLE="Select Year";
	private static final int GET_COUNTRY=1131;
	private static final int GET_STATE=1132;
	private Intent i;
	ArrayList<String> paymentinfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view       =   inflater.inflate(R.layout.payment_process, container, false);
	//	setContent();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContent();
	}
	private void setContent() 
	{
	 paymentinfo=getArguments().getStringArrayList("paymentinfo");
	 
	 
	 back=(Button)view.findViewById(R.id.payment_process_back);
	 back.setOnClickListener(listener);
	 
	 next=(Button)view.findViewById(R.id.payment_processr_next1);
	 next.setOnClickListener(listener);
	 
		dancername=(TextView)view.findViewById(R.id.payment_process_dancername);
		dancername.setText(paymentinfo.get(0));
		dancerclubname=(TextView)view.findViewById(R.id.payment_process_dancerclunname);
		dancerclubname.setText(paymentinfo.get(1));
		bookdate=(TextView)view.findViewById(R.id.payment_process_date);
		bookdate.setText(paymentinfo.get(2));
		amount=(TextView)view.findViewById(R.id.payment_process_ammount);
		amount.setText(paymentinfo.get(3));
		
	
		
		cardtype=(TextView)view.findViewById(R.id.payment_process_cardtype);
		cardtype.setOnClickListener(listener);
		month=(TextView)view.findViewById(R.id.payment_process_month);
		month.setOnClickListener(listener);
		year=(TextView)view.findViewById(R.id.payment_process_year);
		year.setOnClickListener(listener);
		country=(Button)view.findViewById(R.id.payment_process_country);
		country.setOnClickListener(listener);
		state=(Button)view.findViewById(R.id.payment_process_state);
		state.setOnClickListener(listener);
		
		
		fname=(EditText)view.findViewById(R.id.payment_process_firstname);
		lname=(EditText)view.findViewById(R.id.payment_process_lastname);
		cardnumber=(EditText)view.findViewById(R.id.payment_process_cardnumber);
		cardvarificationnumber=(EditText)view.findViewById(R.id.payment_process_cardvarificationnumber);
		address1=(EditText)view.findViewById(R.id.payment_process_address1);
		address2=(EditText)view.findViewById(R.id.payment_process_address2);
		city=(EditText)view.findViewById(R.id.payment_process_city);
		zipcode=(EditText)view.findViewById(R.id.payment_process_zipcode);

		
	}

	List<NameValuePair> entity = new ArrayList<NameValuePair>();
	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v)
		{
		
			switch (v.getId()) {
			case R.id.payment_process_back:
			getActivity().onBackPressed();
				break;
			case R.id.payment_processr_next1:
			done();
					break;
			case R.id.payment_process_cardtype:
				pickvalue1(SELECT_CARD_TITLE,R.array.card_type, cardtype);
				break;
			case R.id.payment_process_month:
				pickvalue(SELECT_MONTH_TITLE,R.array.amonth, month);
				break;
			case R.id.payment_process_year:
				pickvalue(SELECT_YEAR_TITLE,R.array.ayear, year);
				break;
			case R.id.payment_process_state:
				getState();
				break;
			case R.id.payment_process_country:
				getCountry();
				break;
			}
		}
	};
	private void  done() {
		strCardtype=cardtype.getText().toString();
		strMonth=month.getText().toString();
		strYear=year.getText().toString();
		strCountry=country.getText().toString();
		strState=state.getText().toString();
		strFname=fname.getText().toString();
		strLname=lname.getText().toString();
		strCardNumber=cardnumber.getText().toString();
		strCardVarificationNumber=cardvarificationnumber.getText().toString();
		strAddress1=address1.getText().toString();
		strAddress2=address2.getText().toString();
		strCity=city.getText().toString();
		strZipCode=zipcode.getText().toString();
		
		if (strCardNumber.equals("")) {
			Toast.makeText(mActivity, "Please select card type", Toast.LENGTH_SHORT).show();
			return;
		}else if (strMonth.equals("")) {
			Toast.makeText(mActivity, "Please select month", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strYear.equals("")) {
			Toast.makeText(mActivity, "Please select year", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strCountry.equals("")) {
			Toast.makeText(mActivity, "Please select country", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strState.equals("")) {
			Toast.makeText(mActivity, "Please select state", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strFname.equals("")) {
			Toast.makeText(mActivity, "Please enter first name", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strLname.equals("")) {
			Toast.makeText(mActivity, "Please enter last name", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strCardNumber.equals("")) {
			Toast.makeText(mActivity, "Please enter card number", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strCardVarificationNumber.equals("")) {
			Toast.makeText(mActivity, "Please enter card verification number", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strAddress1.equals("")) {
			Toast.makeText(mActivity, "Please enter address 1", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strAddress2.equals("")) {
			Toast.makeText(mActivity, "Please enter address 2", Toast.LENGTH_SHORT).show();
			return;	
		}else if (strZipCode.equals("")) {
			Toast.makeText(mActivity, "Please enter zip code", Toast.LENGTH_SHORT).show();
			return;	
		}
		entity.add(new BasicNameValuePair("action","paypalpayment"));
		entity.add(new BasicNameValuePair("dancerid",paymentinfo.get(4)));
		entity.add(new BasicNameValuePair("clubid",paymentinfo.get(5)));
		entity.add(new BasicNameValuePair("fanid",paymentinfo.get(6)));
		entity.add(new BasicNameValuePair("ondate",paymentinfo.get(2)));
		entity.add(new BasicNameValuePair("amount",paymentinfo.get(3)));
		
		entity.add(new BasicNameValuePair("firstname",strFname));
		entity.add(new BasicNameValuePair("lastname",strLname));
		
		entity.add(new BasicNameValuePair("cardtype",strCardtype));
		entity.add(new BasicNameValuePair("cardnumber",strCardNumber));
		entity.add(new BasicNameValuePair("expMonth",strMonth));
		entity.add(new BasicNameValuePair("expYear",strYear));
		entity.add(new BasicNameValuePair("cardcvv",strCardVarificationNumber));
		entity.add(new BasicNameValuePair("address1",strAddress1));
		entity.add(new BasicNameValuePair("address2",strAddress2));
		entity.add(new BasicNameValuePair("city",strCity));
		entity.add(new BasicNameValuePair("state",strState1));
		entity.add(new BasicNameValuePair("country",strCountry1));
		entity.add(new BasicNameValuePair("zip",strZipCode));
		
		new AsyncWebServiceProcessingTask(mActivity, entity, "Please Wait",new Callback() {
			
			@Override
			public void run(String result) {
				// TODO Auto-generated method stub
				if (result.equals("")) {
					UIUtils.showNetworkErrorMessage(getActivity());
				} else {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(result);
						Toast.makeText(mActivity, jsonObject.optString("status"), Toast.LENGTH_SHORT).show();
					//	if (jsonObject.optString("status").equals("success")) {
							
							mActivity.onBackPressed();
							
					/*	}else {
							
						}*/
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			
				
			}
		}).execute("");
		
	}
	
	private void pickvalue(String title,int array,final TextView textView) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
		final String[] cmd = getResources().getStringArray(array);
		alertDialogBuilder.setTitle(title);
    	alertDialogBuilder.setItems(array,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
					     	textView.setText(cmd[which]);
						

					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}
	private void pickvalue1(String title,int array,final TextView textView) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
		final String[] cmd = {"Visa","MasterCard","Amex","Discover"};
		alertDialogBuilder.setTitle(title);
    	alertDialogBuilder.setItems(array,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
					     	textView.setText(cmd[which]);
						

					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}
	private void getCountry() {

		i=new Intent(getActivity(), CountryOrState.class);
		i.putExtra("whichList", "COUNTRY");
		getActivity().startActivityForResult(i,GET_COUNTRY);
	}
	private void getState() {
		i=new Intent(getActivity(), CountryOrState.class);
		i.putExtra("whichList", "STATE");
		i.putExtra("countryCode", country.getText().toString());
		getActivity().startActivityForResult(i,GET_STATE);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==Activity.RESULT_OK&&requestCode==GET_COUNTRY) {
			country.setText(data.getStringExtra("selectedValue"));
		}if (resultCode==Activity.RESULT_OK&&requestCode==GET_STATE) {
			state.setText(data.getStringExtra("selectedValue"));
			strState1=data.getStringExtra("steteCode");
			strCountry1=data.getStringExtra("countryCode");
			
		}
	}
}
