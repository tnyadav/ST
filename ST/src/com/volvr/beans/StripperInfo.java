package com.volvr.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class StripperInfo {

	public  static String t="StripperInfo";
	public  static String PASSWORD="";
	public  static boolean isProfileEdit=false;
	public  static boolean isProfileEdit1=false;
	public  static String weight="";
	public  static String height="";
	public  static String wage_rate="";
	public  static String hair_color="";
	public  static String stats="";
	public  static String cup_size="";
	public  static String language="";    
	public  static String travel="";
	public  static String race="";
	public  static String eyes="";
	public  static String user_original_pic="";
	public  static String user_address_country="";
	public  static String user_type="";
	public  static String user_paypal_id="";
	public  static String user_facebook_id="";
	public  static String user_facebook="";
	public  static String user_address_city="";
	public  static String user_email="";
	public  static String user_created="";
	public  static String user_address_zip="";
	public  static String user_name="";
	public  static String user_referral_id="";
	public  static String user_address_street_2="";
	public  static String user_address_state="";
	public  static String user_phone="";
	public  static String user_address_street_1="";
	public  static String user_last_logined="";
	public  static String user_id="";
	public  static String user_birthday="";
	public  static String user_sex="";
	public  static String user_active="";
	public  static String user_avatar="";
	public  static String user_fullname="";
	public  static String user_avg_rating="";
	public  static String user_subscription_type="";
	public  static String user_account_type="";
	public  static String subscription_expire_date="";
	public static ArrayList<WorkingAt> ALWorkingAt=new ArrayList<WorkingAt>();
	public static ArrayList<StripperReviews> ALStripperReviews=new ArrayList<StripperReviews>();
	public static ArrayList<UserAppointments> ALUserAppointment=new ArrayList<UserAppointments>();
	public static ArrayList<OfferBean> ALUserOffer=new ArrayList<OfferBean>();

	public static boolean parseStripperInfoForProfile(JSONObject jsonObj)
	{
		Log.i(t, "  before try"+jsonObj);
		try{
			JSONObject User=jsonObj.getJSONObject("user");
			user_original_pic=User.optString("user_original_pic");
			user_address_country=User.optString("user_address_country");
			user_type=User.optString("user_type");
			user_paypal_id=User.optString("user_paypal_id");
			user_facebook_id=User.optString("user_facebook_id");
			user_facebook=User.optString("user_facebook");
			user_address_city=User.optString("user_address_city");
			user_email=User.optString("user_email");
			user_created=User.optString("user_created");
			user_address_zip=User.optString("user_address_zip");
			user_name=User.optString("user_name");
			user_referral_id=User.optString("user_referral_id");
			user_address_street_2=User.optString("user_address_street_2");
			user_address_state=User.optString("user_address_state");
			user_phone=User.optString("user_phone");
			user_address_street_1=User.optString("user_address_street_1");
			user_last_logined=User.optString("user_last_logined");
			user_id=User.optString("user_id");
			user_birthday=User.optString("user_birthday");
			user_sex=User.optString("user_sex");
			user_account_type=User.optString("user_account_type");
			user_active=User.optString("user_active");
			user_avatar=User.optString("user_avatar");
			user_fullname=User.optString("user_fullname");
			if (User.optString("user_avg_rating").equalsIgnoreCase("null")) {
				user_avg_rating=""+0;	
			}
			else
			user_avg_rating=(int)Float.parseFloat(User.optString("user_avg_rating"))+"";
			user_subscription_type=User.optString("user_subscription_type");
			user_account_type=User.optString("user_account_type");
			subscription_expire_date=User.optString("subscription_expire_date");
		} catch (Exception e)
		{
			Log.i(t, ""+e);
		}   
		try{
			JSONObject StriperInfo=jsonObj.getJSONObject("stripper_info");

			weight=StriperInfo.optString("weight");
			height=StriperInfo.optString("height");
			wage_rate=StriperInfo.optString("wage_rate");
			hair_color=StriperInfo.optString("hair_color");
			stats=StriperInfo.optString("stats");
			cup_size=StriperInfo.optString("cup_size");
			language=StriperInfo.optString("language");    
			travel=StriperInfo.optString("travel");
			race=StriperInfo.optString("race");
			eyes=StriperInfo.optString("eyes");
		}  catch (JSONException e)
		{
			Log.i(t, ""+e);
		} 

		try{
			JSONArray StriperWorkingAt=jsonObj.getJSONArray("stripper_working_at");
			ALWorkingAt.clear();
			for (int i = 0; i < StriperWorkingAt.length(); i++) {
				JSONObject JSONWorkingAt =	StriperWorkingAt.getJSONObject(i);
				WorkingAt workingAt=new WorkingAt();
				if (!JSONWorkingAt.optString("club_id").equals("")) {
					workingAt.setClub_id(JSONWorkingAt.optString("club_id"));
					workingAt.setClub_zip_code(JSONWorkingAt.optString("club_zip_code"));
					workingAt.setClub_city(JSONWorkingAt.optString("club_city"));
					workingAt.setClub_contact_number(JSONWorkingAt.optString("club_contact_number"));
					workingAt.setClub_country(JSONWorkingAt.optString("club_country"));
					workingAt.setClub_address(JSONWorkingAt.optString("club_address"));
					workingAt.setClub_state(JSONWorkingAt.optString("club_state"));
					workingAt.setClub_name(JSONWorkingAt.optString("club_name"));
					workingAt.setClub_user_id(JSONWorkingAt.optString("club_user_id"));
					ALWorkingAt.add(workingAt);
				}
				
			}
		}catch (JSONException e)
		{
			Log.i(t, ""+e);
		}

		return true;
	}
	public static boolean parseReviewProfile(JSONObject jsonObj)
	{
		Log.i(t, "  before try"+jsonObj);
		try{
			JSONArray StripperReviews=jsonObj.getJSONArray("reviews");
			ALStripperReviews.clear();

			for (int i = 0; i < StripperReviews.length(); i++) 
			{
				JSONObject JSONStripperReviews =	StripperReviews.getJSONObject(i);
				StripperReviews stripperreviews=new StripperReviews();
				stripperreviews.setReview_id(JSONStripperReviews.optString("review_id"));
				Log.i(t, stripperreviews.getReview_id());
				stripperreviews.setProvider_user_id(JSONStripperReviews.optString("review_from_id"));
				Log.i(t, stripperreviews.getProvider_user_id());
				stripperreviews.setReview_rating(JSONStripperReviews.optString("review_rating"));
				Log.i(t, stripperreviews.getReview_rating());
				stripperreviews.setReview_comments(JSONStripperReviews.optString("review_comments"));
				Log.i(t, stripperreviews.getReview_comments());
				stripperreviews.setReview_writed(JSONStripperReviews.optString("review_writed"));
				Log.i(t, stripperreviews.getReview_writed());
				stripperreviews.setReview_is_show(JSONStripperReviews.optString("review_is_show"));
				Log.i(t, stripperreviews.getReview_is_show());
				stripperreviews.setReview_provider_name(JSONStripperReviews.optString("review_provider_name"));
				stripperreviews.setReview_provider_thumb(JSONStripperReviews.optString("review_provider_thumb"));
				ALStripperReviews.add(stripperreviews);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;	
	}

	public static boolean parseAppointmentProfile(JSONObject jsonObj)
	{
		Log.i(t, "  before try"+jsonObj);
		try{
			JSONArray UserAppointments=jsonObj.getJSONArray("appointments");
			ALUserAppointment.clear();
			for (int i = 0; i < UserAppointments.length(); i++) {
				JSONObject JSONSUserAppointments =	UserAppointments.getJSONObject(i);
				UserAppointments userappointments=new UserAppointments();
				userappointments.setApn_id(JSONSUserAppointments.optString("apn_id"));
				Log.i(t, userappointments.getApn_id());
				userappointments.setRequested_user_id(JSONSUserAppointments.optString("requested_user_id"));
				Log.i(t, userappointments.getRequested_user_id());
				userappointments.setApn_start_date(JSONSUserAppointments.optString("apn_start_date"));
				Log.i(t, userappointments.getApn_start_date());
				userappointments.setApn_end_date(JSONSUserAppointments.optString("apn_end_date"));
				Log.i(t, userappointments.getApn_end_date());
				userappointments.setApn_description(JSONSUserAppointments.optString("apn_description"));
				Log.i(t, userappointments.getApn_description());
				userappointments.setApn_length(JSONSUserAppointments.optString("apn_length"));
				Log.i(t, userappointments.getApn_length());
				userappointments.setApn_active(JSONSUserAppointments.optString("apn_active"));
				Log.i(t, userappointments.getApn_active());
				userappointments.setApn_confirmed(JSONSUserAppointments.optString("apn_confirmed"));
				Log.i(t, userappointments.getApn_confirmed());
				userappointments.setApn_place(JSONSUserAppointments.optString("apn_place"));
				Log.i(t, userappointments.getApn_place());
				userappointments.setApn_city(JSONSUserAppointments.optString("apn_city"));
				Log.i(t, userappointments.getApn_city());
				userappointments.setApn_state(JSONSUserAppointments.optString("apn_state"));
				Log.i(t, userappointments.getApn_state());
				userappointments.setApn_country(JSONSUserAppointments.optString("apn_country"));
				Log.i(t, userappointments.getApn_country());
				userappointments.setApn_created_date(JSONSUserAppointments.optString("apn_created_date"));
				Log.i(t, userappointments.getApn_created_date());
				userappointments.setApn_modified_date(JSONSUserAppointments.optString("apn_modified_date"));
				Log.i(t, userappointments.getApn_modified_date());
				ALUserAppointment.add(userappointments);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;	
	}

	public static boolean parseAllClubOffer(JSONObject jsonObj)
	{
		Log.i(t, "  before try"+jsonObj);
		try{
			JSONArray useroffer=jsonObj.getJSONArray("club_offers");
			ALUserOffer.clear();
			for (int i = 0; i < useroffer.length(); i++) {
				JSONObject JSONuseroffer =	useroffer.getJSONObject(i);
				OfferBean userofferBean=new OfferBean();
				userofferBean.setClub_id((JSONuseroffer.optString("club_id")));
				userofferBean.setOffer_id((JSONuseroffer.optString("offer_id")));
				userofferBean.setClub_name((JSONuseroffer.optString("club_name")));
				userofferBean.setClub_thumb_pic((JSONuseroffer.optString("club_thumb_pic")));
				userofferBean.setOffer_start_date((JSONuseroffer.optString("offer_start_date")));
				userofferBean.setOffer_end_date((JSONuseroffer.optString("offer_end_date")));
				userofferBean.setOffer_desc((JSONuseroffer.optString("offer_desc")));
				userofferBean.setIs_active((JSONuseroffer.optString("is_active")));

				ALUserOffer.add(userofferBean);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;	
	}
public static ArrayList<String> clubName() {
	
	ArrayList<String> clubName = new ArrayList<String>(); 
	
	if(ALWorkingAt.size() >0 )
	{
	
		
		for (int i = 0; i < ALWorkingAt.size(); i++) {
			String clubName1=ALWorkingAt.get(i).getClub_name();
			if (clubName1!=null&&!clubName1.equalsIgnoreCase("")) {
				clubName.add(clubName1);
			}
				
		
			
		}
	}
	
	return clubName;
}

public static String getClubId(int index) {
	return ALWorkingAt.get(index).getClub_user_id();
}
}
