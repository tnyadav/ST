package com.tny.volvr.club.home;

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
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.common.AddOffers;
import com.tny.volvr.dancer.home.DancerAddMore;
import com.tny.volvr.dancer.home.ViewReview;
import com.tny.volvr.more.StripperViewOffer;
import com.volvr.beans.Stripper;
import com.volvr.beans.StripperInfo;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class ClubMyProfile extends BaseFragment {

	private TextView clubname,strippername;
	private TextView stripperhaircolor;
	private TextView stripperage;
	private TextView stripperstatus;
	private TextView clubrateing,stripperrateing;
	private TextView clubviewreview,stripperviewreviews;
	private TextView clubviewallinfo,stripperviewall;
	private TextView clubaddress;

	private Button edit,addoffers,viewoffers;
	private Button addresspre;
	private Button addressnext,viewMorePhotoes;
	//private ImageView rating1,rating2,rating3,rating4,rating5;
	private ImageView clubprofileimage,stripperprofileimage;
	private View view;
	Handler handler;
	private static String t = "StripperMyProfile";
	private static int i;
	private RatingBar ratingBarclub,ratingBarstripper;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.club_myprofile, container, false);
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
					//	mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(false), true, true);
						mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(), true, true);
					}
					else
					//	mActivity.pushFragments(AppConstants.TAB_HOME,new DancerAddMore(false), true, true);
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
		clubprofileimage = (ImageView) view
				.findViewById(R.id.club_profile_imageview);
		//clubprofileimage.setImageBitmap(ProjectUtils.getBitmapFromURL(StripperInfo.user_avatar));
        imageLoader.DisplayImage(StripperInfo.user_avatar, clubprofileimage);
		clubname = (TextView) view.findViewById(R.id.club_profile_name);
		clubname.setText(Clubprofile.clubs.getClub_name());
		clubaddress= (TextView) view.findViewById(R.id.club_profile_address);
		clubaddress.setText("Address : "+StripperInfo.user_address_city+","+StripperInfo.user_address_state+","+StripperInfo.user_address_country);
		clubname= (TextView) view.findViewById(R.id.club_profile_number);
		clubviewallinfo = (TextView) view
				.findViewById(R.id.club_profile_viewallinfo);
		clubviewallinfo.setOnClickListener(listener);
		clubrateing = (TextView) view
				.findViewById(R.id.club_profile_rateingtextview);
		clubrateing.setText(StripperInfo.user_avg_rating+"/5");
		clubviewreview = (TextView) view
				.findViewById(R.id.club_profile_viewreview);
		clubviewreview.setOnClickListener(listener);

		viewoffers=(Button) view
				.findViewById(R.id.club_profile_view_offers);
		viewoffers.setOnClickListener(listener);
		addoffers=(Button) view
				.findViewById(R.id.club_profile_add_offers);
		addoffers.setOnClickListener(listener);
		Log.i(t, StripperInfo.user_birthday);
		viewMorePhotoes= (Button) view
				.findViewById(R.id.club_profile_view_more_photos);
		viewMorePhotoes.setOnClickListener(listener);
		
		ratingBarclub=(RatingBar) view.findViewById(R.id.ratingbar);
		ratingBarclub.setRating(Float.parseFloat(StripperInfo.user_avg_rating));
		ratingBarclub.setIsIndicator(true);
        
		
		//********************************Stripper*************************		
		stripperprofileimage	 = (ImageView) view
				.findViewById(R.id.club_stripper_profile_imageview);

		strippername = (TextView) view.findViewById(R.id.club_stripper_profile_name);

		stripperhaircolor= (TextView) view.findViewById(R.id.club_stripper_profile_hair_color);
		stripperage= (TextView) view.findViewById(R.id.club_stripper_profile_age);

		stripperstatus = (TextView) view.findViewById(R.id.club_stripper_profile_status);
		//stripperstatus.setText("Stats: " + StripperInfo.stats);

		
		
		//setRating();
		
		stripperrateing = (TextView) view
				.findViewById(R.id.club_stripper_profile_rateingtextview);
    	stripperviewreviews = (TextView) view
				.findViewById(R.id.club_stripper_profile_viewreview);
		stripperviewreviews.setOnClickListener(listener);
		stripperviewall = (TextView) view
				.findViewById(R.id.club_stripper_profile_viewall);
		stripperviewall.setOnClickListener(listener);
		edit = (Button) view.findViewById(R.id.club_Profile_edit);
		edit.setOnClickListener(listener);
		ratingBarstripper=(RatingBar)view.findViewById(R.id.ratingbar_1);
       //******************************************************
		addresspre = (Button) view
				.findViewById(R.id.club_profile_addressprevious);
		addresspre.setOnClickListener(listener);
		addressnext = (Button) view
				.findViewById(R.id.club_profile_addressnext);
		addressnext.setOnClickListener(listener);

		// ************************************************************************************
		i = 0;
		if (Clubprofile.strippersListofClub.size() > 0) {
			setAddress(i);
		}
		//Util.showMessage(mActivity, "", ""+Clubprofile.strippersListofClub.size());
	}


	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.club_Profile_edit:
				StripperInfo.isProfileEdit=false;
				StripperInfo.isProfileEdit1=false;
				SignupDetail.clearData();
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new ClubEditProfile1(), true, true);
				break;
			case R.id.club_profile_view_more_photos:

				List<NameValuePair> entity = new ArrayList<NameValuePair>();
				entity.add(new BasicNameValuePair("action","getstripperimages"));
				entity.add(new BasicNameValuePair("user_id",StripperInfo.user_id));
			/*	new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading").execute("");
			*/	
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
				break;

			case R.id.club_profile_viewallinfo:
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new ClubProfileAllInfo(), true, true);
				break;
			case R.id.club_stripper_profile_viewreview:
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new ViewReview(StripperInfo.user_fullname,StripperInfo.user_id,StripperInfo.user_avg_rating,StripperInfo.user_avatar), true, true);
				break;
			/*case R.id.stripper_profile_buy:

				break;*/
			case R.id.club_profile_view_offers:
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new StripperViewOffer("getcluboffers"), true, true);
				break;
			case R.id.club_profile_add_offers:
				Log.i(t, " club_profile_add_offers clicked");
				mActivity.pushFragments(AppConstants.TAB_HOME,
						new AddOffers(), true, true);
				break;
			case R.id.club_profile_addressprevious:
				if (Clubprofile.strippersListofClub.size() > 0) {
					i -= 1;
					Log.i(t, "" + i);
					if (i == -1) {
						i += 1;
					}
					if (i > -1) {
						setAddress(i);
					}
				}

				break;
			case R.id.club_profile_addressnext:
				if (Clubprofile.strippersListofClub.size() > 0) {
					i += 1;
					if (i == Clubprofile.strippersListofClub.size()) {
						i -= 1;
					}
					Log.i(t, Clubprofile.strippersListofClub.size() + "  " + i);

					if (i < Clubprofile.strippersListofClub.size()) {
						setAddress(i);
					}
				}
				break;
			default:
				break;
			}
		}
	};

	private void setAddress(int i) {
		    Stripper stripper = Clubprofile.strippersListofClub.get(i);
		  //  Util.showMessage(mActivity, "",""+stripper.getUser_fullname()+"\n"+ ProjectUtils.getAge(stripper.getUser_birthday())+"\n"+stripper.getHair_color());
		  //  stripperprofileimage.setImageBitmap(ProjectUtils.getBitmapFromURL(stripper.getUser_avatar()));
		    imageLoader.DisplayImage(stripper.getUser_avatar(),stripperprofileimage);
			strippername.setText(stripper.getUser_fullname());
			stripperage.setText(ProjectUtils.getAge(stripper.getUser_birthday()));
			stripperhaircolor.setText(stripper.getHair_color());
			stripperstatus.setText(stripper.getStats());
			ratingBarstripper.setRating(Float.parseFloat(stripper.getUser_avg_rating()));
			ratingBarstripper.setIsIndicator(true);
			
	}

}
