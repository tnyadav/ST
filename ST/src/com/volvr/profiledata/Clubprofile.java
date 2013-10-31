package com.volvr.profiledata;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.volvr.beans.Clubs;
import com.volvr.beans.Stripper;
import com.volvr.beans.StripperInfo;

public class Clubprofile {

	private static String TAG="Clubprofile";

	public static ArrayList<Stripper> strippersListofClub=new ArrayList<Stripper>();
	public static Clubs clubs=new Clubs();

	public static  void parseProfileData(JSONObject jsonObject) {
		try {
			JSONObject jsonObject2=jsonObject.getJSONObject("user");
			StripperInfo.user_id=jsonObject2.optString("user_id");
			StripperInfo.user_type=jsonObject2.optString("user_type");
			StripperInfo.user_name=jsonObject2.optString("user_name");
			StripperInfo.user_fullname=jsonObject2.optString("user_fullname");
			StripperInfo.user_email=jsonObject2.optString("user_email");
			StripperInfo.user_sex=jsonObject2.optString("user_sex");
			StripperInfo.user_phone=jsonObject2.optString("user_phone");
			StripperInfo.user_birthday=jsonObject2.optString("user_birthday");
			StripperInfo.user_avatar=jsonObject2.optString("user_avatar");
			StripperInfo.user_original_pic=jsonObject2.optString("user_original_pic");
			StripperInfo.user_address_country=jsonObject2.optString("user_address_country");
			StripperInfo.user_address_city=jsonObject2.optString("user_address_city");
			StripperInfo.user_address_state=jsonObject2.optString("user_address_state");
			StripperInfo.user_address_zip=jsonObject2.optString("user_address_zip");
			StripperInfo.user_address_street_1=jsonObject2.optString("user_address_street_1");
			StripperInfo.user_address_street_2=jsonObject2.optString("user_address_street_2");
			//jsonObject2.optString("user_facebook");
			//jsonObject2.optString("user_facebook_id");
			StripperInfo.user_active=jsonObject2.optString("user_active");
			StripperInfo.user_account_type=jsonObject2.optString("user_account_type");
			StripperInfo.user_created=jsonObject2.optString("user_created");
			StripperInfo.user_referral_id=jsonObject2.optString("user_referral_id");
			StripperInfo.user_paypal_id=jsonObject2.optString("user_paypal_id");
			StripperInfo.user_last_logined=jsonObject2.optString("user_last_logined");
            StripperInfo.user_avg_rating=jsonObject2.optString("user_avg_rating");
//			jsonObject2.optString("user_occupation");
//			jsonObject2.optString("user_grossincome");
//			jsonObject2.optString("user_avg_rating");

			try {
				StripperInfo.user_subscription_type=jsonObject2.optString("user_subscription_type");
				StripperInfo.user_account_type=jsonObject2.optString("user_account_type");
				StripperInfo.subscription_expire_date=jsonObject2.optString("subscription_expire_date");
			} catch (Exception e) {
			}
			JSONObject jsonObject3=jsonObject.getJSONObject("clubs");

			Log.e(TAG, "jsonObject3 "+jsonObject3);

			clubs.setClub_id(jsonObject3.optString("club_id"));
			clubs.setClub_name(jsonObject3.optString("club_name"));
			clubs.setClub_area(jsonObject3.optString("club_area"));
			clubs.setClub_allowed_minage(jsonObject3.optString("club_allowed_minage"));
			clubs.setClub_dancercount(jsonObject3.optString("club_dancercount"));
			clubs.setClub_yearcount(jsonObject3.optString("club_yearcount"));
			clubs.setClub_topless(jsonObject3.optString("club_topless" )); 
			clubs.setClub_nude(jsonObject3.optString("club_nude" )); 
			clubs.setClub_juicebar(jsonObject3.optString("club_juicebar" )); 
			clubs.setClub_beerbar(jsonObject3.optString("club_beerbar" ));
			clubs.setClub_fulbar(jsonObject3.optString("club_fulbar" )); 
			clubs.setClub_foodkitchen(jsonObject3.optString("club_foodkitchen" ));
			clubs.setClub_eventcost(jsonObject3.optString("club_eventcost" ));
			clubs.setClub_address(jsonObject3.optString("club_address" ));
			clubs.setClub_city(jsonObject3.optString("club_city" )); 
			clubs.setClub_state(jsonObject3.optString("club_state" ));
			clubs.setClub_country(jsonObject3.optString("club_country" ));
			clubs.setClub_zip_code(jsonObject3.optString("club_zip_code" ));
			clubs.setClub_contact_number(jsonObject3.optString("club_contact_number" ));
			clubs.setIs_verified(jsonObject3.optString("is_verified" ));
			Log.d(TAG, ""+clubs);

			JSONArray jsonArray=jsonObject3.getJSONArray("striper");

			for (int i = 0; i < jsonArray.length(); i++) {
				Stripper stripper=new Stripper();
				JSONObject jsonObject5=jsonArray.getJSONObject(i);
				jsonObject5.optString("user_id");
				stripper.set_type(jsonObject5.optString("user_type"));
				stripper.setUser_fullname(jsonObject5.optString("user_fullname"));
				stripper.setUser_email(jsonObject5.optString("user_email"));
				stripper.setUser_sex(jsonObject5.optString("user_sex")); 
				stripper.setUser_phone(jsonObject5.optString("user_phone"));
				stripper.setUser_birthday(jsonObject5.optString("user_birthday"));
				stripper.setUser_avatar(jsonObject5.optString("user_avatar"));
				stripper.setUser_original_pic(jsonObject5.optString("user_original_pic"));
				stripper.setUser_address_country(jsonObject5.optString("user_address_country")); 
				stripper.setUser_address_city(jsonObject5.optString("user_address_city")); 
				stripper.setUser_address_state(jsonObject5.optString("user_address_state"));
				stripper.setUser_address_zip(jsonObject5.optString("user_address_zip"));
				stripper.setUser_address_street_1(jsonObject5.optString("user_address_street_1")); 
				stripper.setUser_address_street_2(jsonObject5.optString("user_address_street_2")); 
				//stripper.setUser_jsonObject4.optString("user_facebook"));
				//stripper.setUser_jsonObject4.optString("user_facebook_id"));
				stripper.setUser_active(jsonObject5.optString("user_active"));
				stripper.setUser_account_type(jsonObject5.optString("user_account_type")); 
				stripper.setUser_created(jsonObject5.optString("user_created"));
				stripper.setUser_referral_id(jsonObject5.optString("user_referral_id")); 
				stripper.setUser_paypal_id(jsonObject5.optString("user_paypal_id"));
				stripper.setUser_last_logined(jsonObject5.optString("user_last_logined"));
				stripper.setUser_avg_rating(jsonObject5.optString("user_avg_rating")); 
				stripper.setHair_color(jsonObject5.optString("hair_color")); 
				stripper.setEyes(jsonObject5.optString("eyes")); 
				stripper.setHeight(jsonObject5.optString("height"));
				stripper.setWeight(jsonObject5.optString("weight")); 
				stripper.setCup_size(jsonObject5.optString("cup_size"));
				stripper.setStats(jsonObject5.optString("stats"));
				stripper.setRace(jsonObject5.optString("race"));
				stripper.setWage_rate(jsonObject5.optString("wage_rate")); 
				stripper.setTravel(jsonObject5.optString("travel"));
				stripper.setLanguage(jsonObject5.optString("language")); 
				Log.e(TAG, ""+stripper);
				strippersListofClub.add(stripper);	
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}



	}


}
