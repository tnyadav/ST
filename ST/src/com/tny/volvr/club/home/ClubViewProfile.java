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
import android.widget.Toast;

import com.tny.utils.AsyncWebServiceProcessingTask;
import com.tny.utils.Callback;
import com.tny.utils.JsonResponse;
import com.tny.utils.ProjectUtils;
import com.tny.utils.UIUtils;
import com.tny.utils.Util1;
import com.tny.volvr.base.AppConstants;
import com.tny.volvr.base.BaseFragment;
import com.tny.volvr.common.RatingbarActivity;
import com.tny.volvr.dancer.home.DancerAddMore;
import com.tny.volvr.dancer.home.ViewReview;
import com.tny.volvr.dancer.message.ChatMessages;
import com.volvr.beans.ClubInfo;
import com.volvr.beans.FanInfo;
import com.volvr.beans.Stripper;
import com.volvr.beans.StripperInfo;
import com.volvr.beans.UserInfo;
import com.volvr.beans.WorkingAt;
import com.volvr.profiledata.Clubprofile;
import com.volvr.search.R;

public class ClubViewProfile extends BaseFragment {

	private TextView rateing, viewreviews,viewallinfo,address1,address2,clubname,clubaddress;
	private Button morepicture,addtofaverote,review,sendmessage,block,join;
	private TextView strippername,stripperage,stripperhaircolor,stripperstatus,stripperrateing,stripperviewreviews;
	private Button addresspre;
	private Button addressnext;
	private ImageView clubprofileimage,stripperprofileimage;
	private RatingBar ratingBarclub,ratingBarstripper;
	
	private View view;
	private String t = "StripperMyProfile",stripperId="";
	private static int i;
	private int position=0;
	Handler handler;
	public ClubViewProfile(int arg2,String stripper_id) {
		position=arg2;
		stripperId=stripper_id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.club_view_profile, container, false);
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
		ClubInfo clubs=ClubInfo.clubInfolist.get(position);
		clubname = (TextView) view.findViewById(R.id.club_view_profile_name);
		clubname.setText(ClubInfo.clubInfolist.get(position).club_name);
		clubaddress= (TextView) view.findViewById(R.id.club_view_profile_address);
		clubaddress.setText("Address : "+UIUtils.checkBlanck(clubs.club_city,false)+" "+UIUtils.checkBlanck(clubs.club_state,false)+" "+UIUtils.checkBlanck(clubs.club_country,true));
		
		//clubname= (TextView) view.findViewById(R.id.club_profile_number);
		
		rateing = (TextView) view
				.findViewById(R.id.club_view_profile_rateingtextview);
		viewreviews = (TextView) view
				.findViewById(R.id.club_view_profile_viewreview);
		viewreviews.setOnClickListener(listener);
		viewallinfo = (TextView) view
				.findViewById(R.id.club_view_profile_viewallinfo);
		viewallinfo.setOnClickListener(listener);
		
		//**********************
		
		review = (Button) view.findViewById(R.id.club_view_profile_rate);
		review.setOnClickListener(listener);
		sendmessage = (Button) view.findViewById(R.id.club_view_profile_message);
		sendmessage.setOnClickListener(listener);
		block = (Button) view.findViewById(R.id.club_view_profile_report);
		block.setOnClickListener(listener);
			clubprofileimage = (ImageView) view
				.findViewById(R.id.club_view_profile_imageview);
			imageLoader.DisplayImage(ClubInfo.clubInfolist.get(position).club_thumb_pic, clubprofileimage);
		

		morepicture = (Button) view.findViewById(R.id.club_view_profile_morepicture);
		morepicture.setOnClickListener(listener);
		addtofaverote = (Button) view.findViewById(R.id.club_view_profile_addtofaverot);
		addtofaverote.setOnClickListener(listener);
		
		
		//************************
		addresspre = (Button) view
				.findViewById(R.id.club_view_profile_stripper_addressprevious);
		addresspre.setOnClickListener(listener);
		addressnext = (Button) view
				.findViewById(R.id.club_view_stripper_profile_addressnext);
		addressnext.setOnClickListener(listener);
		
		ratingBarclub=(RatingBar)view.findViewById(R.id.ratingbar_club);
		//	ratingBarclub.setRating(Float.parseFloat(ClubInfo.clubInfolist.get(position).club));
			ratingBarclub.setIsIndicator(true);
		
		// ************************************************************************************
		//************************************stripper*****************************************
		
		stripperprofileimage	 = (ImageView) view
				.findViewById(R.id.club_stripper_profile_imageview);

		strippername = (TextView) view.findViewById(R.id.club_view_profile_stripper_profile_name);
		stripperhaircolor= (TextView) view.findViewById(R.id.club_view_profile_stripper_profile_hair_color);
		stripperage= (TextView) view.findViewById(R.id.club_view_profile_stripper_profile_age);
		stripperstatus = (TextView) view.findViewById(R.id.club_view_profile_stripper_profile_status);
		stripperrateing = (TextView) view
				.findViewById(R.id.club_stripper_profile_rateingtextview);
		stripperviewreviews = (TextView) view
				.findViewById(R.id.club_view_profile_stripper_profile_viewreview);
		stripperviewreviews.setOnClickListener(listener);
		join=(Button)view.findViewById(R.id.join);
		join.setOnClickListener(listener);
		
		if (!UserInfo.USERTYPE.equals("stripper")) {
			join.setVisibility(view.GONE);
		}else if (searchclub()) {
			join.setVisibility(view.GONE);
		}
		
		ratingBarstripper=(RatingBar)view.findViewById(R.id.ratingbar_stripper);
		ratingBarstripper.setIsIndicator(true);
		i = 0;
		if (StripperInfo.ALWorkingAt.size() > 0) {
			setAddress(i);
		}
	}

/*	public static Bitmap getBitmapFromURL(String src) {
		try {
			Log.e("src", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			Log.e("Bitmap", "returned");
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Exception", e.getMessage());
			return null;
		}
	}*/

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.club_view_profile_morepicture:
				if(Util1.isNetworkAvailable(getActivity())){
					List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","getstripperimages"));
					entity.add(new BasicNameValuePair("user_id",stripperId));
					new AsyncWebServiceProcessingTask(getActivity(), handler, entity, "Loading").execute("");
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());
				break;
		
			case R.id.club_view_profile_rate:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
						new RatingbarActivity(stripperId), true, true);
				break;
				
			case R.id.club_view_profile_message:
				//mActivity.pushFragments(AppConstants.TAB_MESSAGES, new ReplayMessages(ClubInfo.clubInfolist.get(position).club_name,stripperId,ClubInfo.clubInfolist.get(position).club_thumb_pic,""),true,true);
				mActivity.pushFragments(AppConstants.TAB_MESSAGES, new ChatMessages(ClubInfo.clubInfolist.get(position).club_name,stripperId,ClubInfo.clubInfolist.get(position).club_thumb_pic),true,true);
				
				break;
			case R.id.club_view_profile_report:

				break;
			
				//****************************************************************		
				
			case R.id.club_view_profile_viewallinfo:
				ClubProfileAllInfoForOthers clubProfileAllInfoForOthers = new ClubProfileAllInfoForOthers();
				clubProfileAllInfoForOthers.setId(ClubInfo.clubInfolist.get(position).club_user_id);
				
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION, clubProfileAllInfoForOthers,true,true);
				
				break;
				
			case R.id.club_view_profile_addtofaverot:
				
				  if(Util1.isNetworkAvailable(getActivity())){
		    		   String user_Id = null;
		    			if(UserInfo.USERTYPE.equalsIgnoreCase("stripper"))
						
						user_Id=StripperInfo.user_id;
						
						if(UserInfo.USERTYPE.equalsIgnoreCase("fan"))
							user_Id=FanInfo.fanproFileInfo.user_id;
		    		   
						List<NameValuePair> entity = new ArrayList<NameValuePair>();
						entity.add(new BasicNameValuePair("action","favourites_add"));
						entity.add(new BasicNameValuePair("user_id",user_Id));
						entity.add(new BasicNameValuePair("favourite_user_id",ClubInfo.clubInfolist.get(position).club_id));
						entity.add(new BasicNameValuePair("user_type","club"));
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
			case R.id.join:
				
				if (Util1.isNetworkAvailable(getActivity())) {
					
					
					
					List<NameValuePair> entity = new ArrayList<NameValuePair>();
					entity.add(new BasicNameValuePair("action","joinclub"));
					entity.add(new BasicNameValuePair("strip_id",StripperInfo.user_id));
					//entity.add(new BasicNameValuePair("strip_id","91"));
					entity.add(new BasicNameValuePair("club_id",ClubInfo.clubInfolist.get(position).club_id));
					//UIUtils.showMessage(mActivity, "", StripperInfo.user_id+ "  "+stripperId);
					new AsyncWebServiceProcessingTask(mActivity, entity, "Please Wait...",new Callback() {
						
						@Override
						public void run(String result) {
							// TODO Auto-generated method stub
							if (result.equals("")) {
								UIUtils.showNetworkErrorMessage(getActivity());
							} else {
								JSONObject jsonObject;
								try {
									jsonObject = new JSONObject(result);
									if (jsonObject.optString("status").equals("success")) {
										Toast.makeText(mActivity, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
										}else {
											Toast.makeText(mActivity, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
										}
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						
							
						}
					}).execute("");
					
						
					
				}
				else
					UIUtils.showNetworkErrorMessage(getActivity());

				break;	
			case R.id.club_view_profile_viewreview:
				mActivity.pushFragments(AppConstants.TAB_SUBSCRIPTION,
						new ViewReview(ClubInfo.clubInfolist.get(position).club_name,ClubInfo.clubInfolist.get(position).club_id,"",ClubInfo.clubInfolist.get(position).club_thumb_pic), true, true);
				break;
				
			case R.id.club_view_profile_stripper_addressprevious:
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
			case R.id.club_view_stripper_profile_addressnext:
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
		try {
		    Stripper stripper = Clubprofile.strippersListofClub.get(i);
		    imageLoader.DisplayImage(stripper.getUser_avatar(), stripperprofileimage);
		  //  stripperprofileimage.setImageBitmap(ProjectUtils.getBitmapFromURL(stripper.getUser_avatar()));
			strippername.setText(stripper.getUser_fullname());
			stripperage.setText(ProjectUtils.getAge(stripper.getUser_birthday()));
			stripperhaircolor.setText(stripper.getHair_color());
			stripperstatus.setText(stripper.getStats());
			ratingBarstripper.setRating(Float.parseFloat(stripper.getUser_avg_rating()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
private boolean searchclub() {
	boolean foundclub=false;
	for(WorkingAt workingAt : StripperInfo.ALWorkingAt) {
	    if(workingAt.getClub_id() != null && workingAt.getClub_id().equals(ClubInfo.clubInfolist.get(position).club_user_id)) {
	       System.out.println("Found it!");
	       foundclub=true;
	       break;
	     }
	}
	return foundclub;
	
}
}
