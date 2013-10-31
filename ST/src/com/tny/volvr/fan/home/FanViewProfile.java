package com.tny.volvr.fan.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

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

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.JsonResponse;
import com.tny.utils.ProjectUtils;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.common.RatingbarActivity;
import com.tny.volvr.dancer.home.DancerAddMore;
import com.tny.volvr.dancer.message.ChatMessages;
import com.volvr.beans.FanInfo;
import com.volvr.search.R;

public class FanViewProfile extends BaseFragment {
	private TextView name,name1,age,occupation,income,rating;
	private Button morepicture,block,sendmessage,addtofavwetote,rate;
	private Handler handler;
	private ImageView profileimage;
	private View view;
	private String t = "fanMyProfile",muserId="";
	private RatingBar ratingBar;
	private static int i;
	int position=0;
	FanInfo fanInfo;
	List<NameValuePair> entity = new ArrayList<NameValuePair>();
	public FanViewProfile(int arg2, String string) {
		position=arg2;
		muserId=string;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fan_view_profile, container, false);
		entity.clear();
		setContent();
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d("fan profile=", JsonResponse.JsonResponse);
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
						mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,new DancerAddMore(false), true, true);
					}
					else
						mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,new DancerAddMore(false), true, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		return view;
	}

	private void setContent() 
	{
		 fanInfo=FanInfo.FanInfolist.get(position);
		name = (TextView) view.findViewById(R.id.fan_view_profile_name);
		name.setText(fanInfo.user_name);
		name1 = (TextView) view.findViewById(R.id.fan_view_profile_name1);
		name1.setText(fanInfo.user_name);
		age = (TextView) view.findViewById(R.id.fan_view_profile_age);
		age.setText("Age:"+ProjectUtils.getAge(fanInfo.user_birthday));
		Log.i(t, fanInfo.user_birthday);
		
		occupation=(TextView)view.findViewById(R.id.fan_view_profile_occupation);
		occupation.setText( "Occupation:"+fanInfo.user_occupation);
		income=(TextView)view.findViewById(R.id.fan_view_profile_income);
		income.setText( "Income:"+fanInfo.user_grossincome);
		
		//***********************************
		
		morepicture = (Button) view
				.findViewById(R.id.fan_view_profile_morepicture);
		morepicture.setOnClickListener(listener);
		block = (Button) view
				.findViewById(R.id.fan_view_profile_block);
		block.setOnClickListener(listener);
		sendmessage = (Button) view
				.findViewById(R.id.fan_view_profile_sendmessage);
		sendmessage.setOnClickListener(listener);
		addtofavwetote = (Button) view
				.findViewById(R.id.fan_view_profile_addtofaverote);
		addtofavwetote.setOnClickListener(listener);
		rate=(Button)view.findViewById(R.id.fan_view_profile_rate);
		rate.setOnClickListener(listener);
		
		//****************************************
		profileimage = (ImageView) view
				.findViewById(R.id.fan_view_profile_imageview);
		imageLoader.DisplayImage(fanInfo.user_avatar, profileimage);
		
		ratingBar=(RatingBar)view.findViewById(R.id.ratingbar);
		String s=FanInfo.fanproFileInfo.user_avg_rating;
		if (s.equals("")) {
			s="0.0";
		}
		ratingBar.setRating(Float.parseFloat(s));
		ratingBar.setIsIndicator(true);
		
		rating=(TextView)view.findViewById(R.id.rateingtextview);
		rating.setText(s+"/5");
		
	}

	

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fan_view_profile_morepicture:
				if(Util1.isNetworkAvailable(getActivity())){
					entity.add(new BasicNameValuePair("action","getstripperimages"));
					entity.add(new BasicNameValuePair("user_id",muserId));
					new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading").execute("");
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());
				break;
			
			case R.id.fan_view_profile_block:
				
				break;
				case R.id.fan_view_profile_rate:
					mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
							new RatingbarActivity(muserId), true, true);
				break;
			case R.id.fan_view_profile_sendmessage:
				
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, new ChatMessages(fanInfo.user_name,muserId,fanInfo.user_avatar),true,true);
				
				
				break;
			case R.id.fan_view_profile_addtofaverote:
				
				
				break;
			
			default:
				break;
			}
		}
	};
}
