package com.tny.volvr.fan.subscription;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Constant;
import com.tny.utils.JsonResponse;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.dancer.subscription.DancerSubscription;
import com.volvr.beans.FanInfo;
import com.volvr.search.R;

public class FanSubscription extends BaseFragment {
	private Button /*back,*/msubscription1,msubscription2,msubscription3/*,msubscription4*/;
	private Handler handler;
	private PaymentConfirmation confirm;
	private View view;
	private String strPaymentAmmount="",strSubscriptionType="",mexpiry_date="",mcurrentDate="";
	private Date expiryDate=new Date(),currentDate=new Date();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fan_suscription, container, false);

		Intent intent = new Intent(getActivity(), PayPalService.class);
		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, DancerSubscription.CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, DancerSubscription.CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,Constant.adminEmailForPayment);
		getActivity().startService(intent);

		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d("Fan subscription=", JsonResponse.JsonResponse);
				try {
					JSONObject jsonResponse=new JSONObject(JsonResponse.JsonResponse);
					if(jsonResponse.optString("status").equalsIgnoreCase("success"))
					{
						UIUtils.showMessage(getActivity(), "Message","You are successfully subscribed to "+strSubscriptionType+" Pass.");
						FanInfo.fanproFileInfo.expiry_date=jsonResponse.optString("subscription_expire_date");
						FanInfo.fanproFileInfo.user_subscription_type=strSubscriptionType;
					}
					else
						UIUtils.showMessage(getActivity(), "Message","Your subscription failed. ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		setContent();
		return view;
	}

	private void setContent() 
	{
		//    	back=(Button)view.findViewById(R.id.fan_suscription_back);
		//    	back.setOnClickListener(listener);

		msubscription1=(Button)view.findViewById(R.id.fan_suscription1);
		msubscription2=(Button)view.findViewById(R.id.fan_suscription2);
		msubscription3=(Button)view.findViewById(R.id.fan_suscription3);
		msubscription1.setOnClickListener(listener);
		msubscription1.setOnClickListener(listener);
		msubscription2.setOnClickListener(listener);
		msubscription3.setOnClickListener(listener);
	}

	@Override
	public void onResume() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			expiryDate = dateFormat.parse(FanInfo.fanproFileInfo.expiry_date);
			mexpiry_date = dateFormat.format(expiryDate);
			System.out.println("mexpiry_date="+mexpiry_date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Date date = new Date();
			mcurrentDate=dateFormat.format(date);
			currentDate=dateFormat.parse(mcurrentDate);
			System.out.println("current date="+dateFormat.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(FanInfo.fanproFileInfo.user_account_type.equalsIgnoreCase("Free"))
		{

		}
		else
		{
			if(expiryDate.after(currentDate))
			{
				UIUtils.showMessage(getActivity(), "Message","Your " +FanInfo.fanproFileInfo.user_subscription_type+" pass subscription exists.");
			}
		}
		super.onResume();
	}

	private OnClickListener listener        =   new OnClickListener(){
		@Override
		public void onClick(View v){
			switch (v.getId()) 
			{
			case R.id.fan_suscription1:
				strSubscriptionType="daily";
				if(FanInfo.fanproFileInfo.user_account_type.equalsIgnoreCase("Free"))
				{
					strPaymentAmmount="5.99";
					onBuyPressed("5.99");
				}
				else
				{
					if(expiryDate.after(currentDate))
					{
						UIUtils.showMessage(getActivity(), "Message","Your " +FanInfo.fanproFileInfo.user_subscription_type+" pass subscription exists.");
					}
					else{
						strPaymentAmmount="5.99";
						onBuyPressed("5.99");
					}
				}
				break;

			case R.id.fan_suscription2:
				strSubscriptionType="weekend";
				if(FanInfo.fanproFileInfo.user_account_type.equalsIgnoreCase("Free"))
				{
					strPaymentAmmount="20.99";
					onBuyPressed("20.99");
				}
				else
				{
					if(expiryDate.after(currentDate))
					{
						UIUtils.showMessage(getActivity(), "Message","Your " +FanInfo.fanproFileInfo.user_subscription_type+" pass subscription exists.");
					}
					else{
						strPaymentAmmount="20.99";
						onBuyPressed("20.99");
					}
				}				
				break;

			case R.id.fan_suscription3:
				strSubscriptionType="monthly";
				if(FanInfo.fanproFileInfo.user_account_type.equalsIgnoreCase("Free"))
				{
					strPaymentAmmount="49.99";
					onBuyPressed("49.99");
				}
				else
				{
					if(expiryDate.after(currentDate))
					{
						UIUtils.showMessage(getActivity(), "Message","Your " +FanInfo.fanproFileInfo.user_subscription_type+" pass subscription exists.");
					}
					else{
						strPaymentAmmount="49.99";
						onBuyPressed("49.99");
					}
				}
				break;

				//			case R.id.fan_suscription_back:
				//			onBackPressed();
				//				break;
			default:
				break;
			}
		}
	};

	public void onBuyPressed(String amout)
	{
		if(!Util1.isNetworkAvailable(getActivity()))
		{
			UIUtils.showMessage(getActivity(), "Message", "Error in connection.Please try again");
		}
		else
		{
			PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(amout), "USD", "Voui'r");
			Intent intent = new Intent(getActivity(), PaymentActivity.class);
			intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, DancerSubscription.CONFIG_ENVIRONMENT);
			intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, DancerSubscription.CONFIG_CLIENT_ID);
			intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,  Constant.adminEmailForPayment);
			intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "AVNO-hBS8pTShkFgmdGDuY1UdTK_Rw2MoeG7ToeFZCoD0sp4f-ZllD_14Qh2");
			intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "your-customer-id-in-your-system");
			intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
			startActivityForResult(intent, 0);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == Activity.RESULT_OK)
		{
			confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null)
			{
				try
				{
					Log.i("paymentExample", confirm.toJSONObject().toString(4));
					if(!Util1.isNetworkAvailable(getActivity()))
					{
						UIUtils.showMessage(getActivity(), "Message", "Error in connection.Please try again");
					}
					else
					{
						ArrayList<String> listData=Util1.parsePaymentData(confirm.toJSONObject().toString(4));
						List<NameValuePair> entity = new ArrayList<NameValuePair>();
						entity.add(new BasicNameValuePair("action","addpayinfo"));
						entity.add(new BasicNameValuePair("user_id",FanInfo.fanproFileInfo.user_id));
						entity.add(new BasicNameValuePair("price",strPaymentAmmount));
						entity.add(new BasicNameValuePair("user_subscription_type",strSubscriptionType));
						entity.add(new BasicNameValuePair("payment_key",listData.get(0)));
						new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading").execute("");
					}

				}
				catch (JSONException e)
				{
					Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
				}
			}
		}
		else
			if (resultCode == Activity.RESULT_CANCELED)
			{
				Log.i("paymentExample", "The user canceled.");
			}
			else
				if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID)
				{
					Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
				}
	}


	@Override
	public void onDestroy()
	{
		getActivity().stopService(new Intent(getActivity(), PayPalService.class));
		super.onDestroy();
	}

}
