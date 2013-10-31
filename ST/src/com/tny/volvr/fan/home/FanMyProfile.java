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
import com.tny.utils.Callback;
import com.tny.utils.JsonResponse;
import com.tny.utils.ProjectUtils;
import com.tny.utils.SignupDetail;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.dancer.home.DancerAddMore;
import com.volvr.beans.FanInfo;
import com.volvr.search.R;

public class FanMyProfile extends BaseFragment {

	private TextView name;
	private com.tny.utils.MyTextView mname;
	private TextView age,rating;
	private TextView occupation;
	private TextView income;
	private Button edit;
	private Button morepicture;
	private ImageView profileimage;
	private View view;
	private String t = "fanMyProfile";
	private RatingBar ratingBar;
	private Handler handler;
	private static int i;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fan_myprofile, container, false);
		setContent();
		
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d(t, JsonResponse.JsonResponse);
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
						mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(), true, true);
					}
					else
						mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(), true, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		return view;
	}

	private void setContent() 
	{
		name = (TextView) view.findViewById(R.id.fan_profile_name);
		name.setText(FanInfo.fanproFileInfo.user_fullname);
		
		mname= (com.tny.utils.MyTextView) view.findViewById(R.id.fan_profile_name1);
		mname.setText(FanInfo.fanproFileInfo.user_name);
		
		age = (TextView) view.findViewById(R.id.fan_profile_age);
		age.setText("Age:"+ProjectUtils.getAge(FanInfo.fanproFileInfo.user_birthday));
		Log.i(t, FanInfo.fanproFileInfo.user_birthday);
		
		occupation=(TextView)view.findViewById(R.id.fan_profile_occupation);
		occupation.setText( "Occupation:"+FanInfo.fanproFileInfo.user_occupation);
		
		income=(TextView)view.findViewById(R.id.fan_profile_income);
		income.setText( "Income:"+FanInfo.fanproFileInfo.user_grossincome);
		
		edit = (Button) view.findViewById(R.id.fan_Profile_edit);
		edit.setOnClickListener(listener);
		
		morepicture = (Button) view.findViewById(R.id.fan_profile_morepicture);
		morepicture.setOnClickListener(listener);
		
		profileimage = (ImageView) view.findViewById(R.id.fan_profile_imageview);
		imageLoader.DisplayImage(FanInfo.fanproFileInfo.user_avatar, profileimage);
		
		ratingBar=(RatingBar)view.findViewById(R.id.ratingbar);
		ratingBar.setRating(Float.parseFloat(FanInfo.fanproFileInfo.user_avg_rating));
		ratingBar.setIsIndicator(true);
		
		rating=(TextView)view.findViewById(R.id.rateingtextview);
		rating.setText(Float.parseFloat(FanInfo.fanproFileInfo.user_avg_rating)+"/5");
	
		
	}



	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fan_Profile_edit:
				SignupDetail.clearData();
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new FanEditProfile1(), true, true);

				break;
			case R.id.fan_profile_morepicture:
				if(Util1.isNetworkAvailable(getActivity())){
				 	List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","getstripperimages"));
					entity.add(new BasicNameValuePair("user_id",FanInfo.fanproFileInfo.user_id));
					/*new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading").execute("");*/
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
									//						DancerAddMore.InitialImageUrls=imageUrls;
									mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(), true, true);
								}
								else
									mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(), true, true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).execute("");
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());
				break;
			default:
				break;
			}
		}
	};
}
