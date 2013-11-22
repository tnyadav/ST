package com.tny.volvr.dancer.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.Constant;
import com.tny.utils.JsonResponse;
import com.tny.utils.ProjectUtils;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.common.RatingbarActivity;
import com.tny.volvr.dancer.message.ChatMessages;
import com.tny.volvr.dancer.subscription.DancerSubscription;
import com.volvr.beans.FanInfo;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.StripperList;
import com.volvr.beans.UserInfo;
import com.volvr.beans.WorkingAt;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class DancerViewProfile extends BaseFragment {

	private TextView name,age,haicolor,status,rateing, viewreviews,viewallinfo,address1,address2;
	private Button morepicture,addtofaverote,review,sendmessage,block,book,travelstatus,addresspre,addressnext;
	private ImageView profileimage;
	private RatingBar ratingBar;
	private View view;
	private String t = "StripperMyProfile",stripperId="";
	private static int i;
	private int position=0;
	Handler handler;
	StripperList stripperList;
	private PaymentConfirmation confirm;
	private String strPaymentAmmount="",strSubscriptionType="";
	public static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_NO_NETWORK;
    public static final String CONFIG_CLIENT_ID = "AVNO-hBS8pTShkFgmdGDuY1UdTK_Rw2MoeG7ToeFZCoD0sp4f-ZllD_14Qh2"/*"AVNO-hBS8pTShkFgmdGDuY1UdTK_Rw2MoeG7ToeFZCoD0sp4f-ZllD_14Qh2"*/;

	
	public DancerViewProfile(int arg2,String stripper_id) {
		position=arg2;
		stripperId=stripper_id;
		stripperList=StripperList.stripperInfoList.get(position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.stripper_book, container, false);
		setContent();
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d("DANCER PROFILE", JsonResponse.JsonResponse);
				try {
					ArrayList<String> imageUrls=new ArrayList<String>();
					JSONObject jsonResponse=new JSONObject(JsonResponse.JsonResponse);
					if(jsonResponse.optString("status").equalsIgnoreCase("success"))
					{
						JSONArray imageArray=new JSONArray(jsonResponse.getString("stripper_images"));
						for (int i = 0; i < imageArray.length(); i++) {
							JSONObject imageURL=imageArray.getJSONObject(i);
							imageUrls.add(imageURL.getString("original_image"));
						}
						DancerAddMore.imageUrls=imageUrls;
						mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,new DancerAddMore(), true, true);
					}
					else
						mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,new DancerAddMore(), true, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		return view;
	}

	private void setContent() 
	{
		name = (TextView) view.findViewById(R.id.stripper_book_name);
		name.setText(stripperList.user_fullname);
		age = (TextView) view.findViewById(R.id.stripper_book_age);
		Log.i(t, stripperList.user_birthday);
         try {
		 age.setText("Age: "+ProjectUtils.getAge(stripperList.user_birthday));
		 } catch (Exception e) { // TODO Auto-generated catch block
		  e.printStackTrace(); }
         haicolor = (TextView) view.findViewById(R.id.stripper_book_hair_color);
		 haicolor.setText(stripperList.haicolor);
		 
		 status = (TextView) view.findViewById(R.id.stripper_book_status);
	     status.setText("Stats: "+stripperList.stats);
	   //  status.setText("Stats: tnhvghgjhghgjgkj");
			
		// Toast.makeText(getActivity(), "Stats: "+stripperList.stats, 1).show();
		
		rateing = (TextView) view
				.findViewById(R.id.stripper_book_rateingtextview);
		rateing.setText(Float.parseFloat(stripperList.rateing)+"/5");
		viewreviews = (TextView) view
				.findViewById(R.id.stripper_book_viewreview);
		viewreviews.setOnClickListener(listener);
		viewallinfo = (TextView) view
				.findViewById(R.id.stripper_book_viewallinfo);
		viewallinfo.setOnClickListener(listener);
		
		//**********************
		
		review = (Button) view.findViewById(R.id.stripper_book_rate);
		review.setOnClickListener(listener);
		sendmessage = (Button) view.findViewById(R.id.stripper_book_message);
		sendmessage.setOnClickListener(listener);
		block = (Button) view.findViewById(R.id.stripper_book_report);
		block.setOnClickListener(listener);
		book = (Button) view.findViewById(R.id.stripper_book_book);
		book.setOnClickListener(listener);
		travelstatus = (Button) view.findViewById(R.id.stripper_book_travel);
		travelstatus.setOnClickListener(listener);
		
		//******************************
		
		profileimage = (ImageView) view.findViewById(R.id.stripper_book_imageview);
		imageLoader.DisplayImage(stripperList.user_original_pic, profileimage);
		//profileimage.setImageBitmap(getBitmapFromURL(stripperList.user_original_pic));
		
		morepicture = (Button) view.findViewById(R.id.stripper_book_morepicture);
		morepicture.setOnClickListener(listener);
		addtofaverote = (Button) view.findViewById(R.id.stripper_book_addtofaverot);
		addtofaverote.setOnClickListener(listener);
		
		//************************
		
		address1 = (TextView) view.findViewById(R.id.stripper_book_addressprevious);
		address2 = (TextView) view.findViewById(R.id.stripper_book_address2);
			
		
		addresspre = (Button) view
				.findViewById(R.id.stripper_book_addressprevious);
		addresspre.setOnClickListener(listener);
		addressnext = (Button) view
				.findViewById(R.id.stripper_book_addressnext);
		addressnext.setOnClickListener(listener);
		ratingBar=(RatingBar)view.findViewById(R.id.ratingbar);
		ratingBar.setRating(Float.parseFloat(stripperList.rateing));
		ratingBar.setIsIndicator(true);
		
		// ************************************************************************************
		i = 0;
		if (StripperInfo.ALWorkingAt.size() > 0) {
			setAddress(i);
		}
	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.stripper_book_morepicture:
				if(Util1.isNetworkAvailable(getActivity())){
					List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","getstripperimages"));
					entity.add(new BasicNameValuePair("user_id",stripperList.user_id));
					new AsyncWebServiceProcessingTask(getActivity(), entity, "Loading",new Callback() {
						
						@Override
						public void run(String result) {
							// TODO Auto-generated method stub
							try {
								ArrayList<String> imageUrls=new ArrayList<String>();
								JSONObject jsonResponse=new JSONObject(result);
								if(jsonResponse.optString("status").equalsIgnoreCase("success"))
								{
									JSONArray imageArray=new JSONArray(jsonResponse.getString("stripper_images"));
									for (int i = 0; i < imageArray.length(); i++) {
										JSONObject imageURL=imageArray.getJSONObject(i);
										imageUrls.add(imageURL.getString("original_image"));
									}
									DancerAddMore.imageUrls=imageUrls;
									mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,new DancerAddMore(false), true, true);
								}
								else
									mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,new DancerAddMore(false), true, true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).execute("");
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());
				break;
			case R.id.stripper_book_travel:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
						new DancerTravalStatus(stripperId), true, true);
				break;
				
			case R.id.stripper_book_rate:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
						new RatingbarActivity(stripperId), true, true);
				break;
			case R.id.stripper_book_message:
				mActivity.pushFragments(AppConstants.TAB_MESSAGES, new ChatMessages(stripperList.user_name,stripperId,stripperList.user_original_pic),true,true);
					
				break;
			case R.id.stripper_book_report:
				
				break;
	       case R.id.stripper_book_addtofaverot:
	    	   if(Util1.isNetworkAvailable(getActivity())){
	    		   String user_Id = null;
	    			if(UserInfo.USERTYPE.equalsIgnoreCase("club"))
					
					user_Id=Clubprofile.clubs.getClub_id();
					
					if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
						user_Id=FanInfo.fanproFileInfo.user_id;
	    		   
					
					List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","favourites_add"));
					entity.add(new BasicNameValuePair("user_id",user_Id));
					entity.add(new BasicNameValuePair("favourite_user_id",stripperList.user_id));
					entity.add(new BasicNameValuePair("user_type","stripper"));
					
					new AsyncWebServiceProcessingTask(getActivity(), entity, "Please Wait...",new Callback() {
						
						@Override
						public void run(String result) {
							// TODO Auto-generated method stub
							try {
								JSONObject jsonResponse=new JSONObject(result);
								Toast.makeText(mActivity,jsonResponse.optString("msg"), 3).show();
								/*if(jsonResponse.optString("status").equalsIgnoreCase("success"))
								{
								
								}*/
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).execute("");
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());
				break;
			case R.id.stripper_book_book:
				if(!UserInfo.USERTYPE.equalsIgnoreCase("club")){
					
					mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
							new CheckAvailablity(stripperId,stripperList.user_fullname), true, true);
				}
			
				break;
				
				//****************************************************************		
				
			case R.id.stripper_book_viewallinfo:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
						new DancerProfileAllInfo(position), true, true);
				break;
			case R.id.stripper_book_viewreview:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
						new ViewReview(stripperList.user_name,stripperList.user_id,stripperList.rateing,stripperList.user_original_pic), true, true);
				break;
				
			case R.id.stripper_book_addressprevious:
				if (StripperInfo.ALWorkingAt.size() > 0) {
					i -= 1;
					Log.i(t, "" + i);
					if (i == -1) {
						i += 1; 
					}
					if (i > -1) {
						setAddress(i);
					}
				}

				Log.i(t, StripperInfo.ALWorkingAt.size() + "  " + i);

					break;
			case R.id.stripper_book_addressnext:
				if (StripperInfo.ALWorkingAt.size() > 0) {
					i += 1;
					if (i == StripperInfo.ALWorkingAt.size()) {
						i -= 1;
					}
					if (i < StripperInfo.ALWorkingAt.size()) {
						setAddress(i);
					}
				}
				break;
			default:
				break;
			}
		}
	};

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void setAddress(int i) {
		WorkingAt workingat = StripperInfo.ALWorkingAt.get(i);
		String address="";
		if(workingat.getClub_name().isEmpty())
		{

		}
		else
		{
			address1.setText(workingat.getClub_name());
		}
		if(!workingat.getClub_address().isEmpty())
		{
			address=""+workingat.getClub_address();
		}
		if(!workingat.getClub_city().isEmpty())
		{
			address=address+"\n"+workingat.getClub_city();
		}
		if(!workingat.getClub_state().isEmpty())
		{
			address=address+","+workingat.getClub_state();
		}
		if(!workingat.getClub_country().isEmpty())
		{
			address=address+","+workingat.getClub_country();
		}
		if(!workingat.getClub_contact_number().isEmpty())
		{
			address=address+"\n"+workingat.getClub_contact_number();
		}
		//		address2.setText(workingat.getClub_address() + "\n"
		//				+ workingat.getClub_city() + "," + workingat.getClub_state()
		//				+ "," + workingat.getClub_country() + "\n"
		//				+ workingat.getClub_contact_number());
		address2.setText(address);
	}
	public void onBuyPressed(String amout)
	{
		if(!Util1.isNetworkAvailable(getActivity()))
		{
			UIUtils.showMessage(getActivity(), "Message", "Error in connection.Please try again!");
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
						entity.add(new BasicNameValuePair("user_id",StripperInfo.user_id));
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
}
