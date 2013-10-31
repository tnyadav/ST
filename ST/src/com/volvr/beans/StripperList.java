package com.volvr.beans;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class StripperList {

	public static   String t="StripperInfo";

	public   String user_original_pic="";
	public   String user_address_country="";
	public   String user_type="";
	public   String user_paypal_id="";
	public   String user_facebook_id="";
	public   String user_facebook="";
	public   String user_address_city="";
	public   String user_email="";
	public   String user_created="";
	public   String user_address_zip="";
	public   String user_name="";
	public   String user_referral_id="";
	public   String user_address_street_2="";
	public   String user_address_state="";
	public   String user_phone="";
	public   String user_address_street_1="";
	public   String user_last_logined="";
	public   String user_id="";
	public   String user_birthday="";
	public   String user_sex="";
	public   String user_account_type="";
	public   String user_active="";
	public   String user_avatar="";
	public   String user_fullname="";
	
	public   String haicolor="";
	public   String stats;
	public   String rateing;
	
	

	public  ArrayList<WorkingAt> ALWorkingAt=new ArrayList<WorkingAt>();
	public static ArrayList<StripperList> stripperInfoList=new ArrayList<StripperList>();
	public static  StripperList parseStripperInfoForProfile(JSONObject User)
	{
    StripperList stripperInfoNew =new StripperList();
		try{

			stripperInfoNew.user_original_pic=User.optString("user_avatar");
			stripperInfoNew.user_address_country=User.optString("user_address_country");
			
			stripperInfoNew.user_address_city=User.optString("user_address_city");
			stripperInfoNew.user_email=User.optString("user_email");
//			stripperInfoNew.user_created=User.optString("user_created");
			stripperInfoNew.user_address_zip=User.optString("user_address_zip");
			stripperInfoNew.user_name=User.optString("user_name");
//			stripperInfoNew.user_referral_id=User.optString("user_referral_id");
			stripperInfoNew.user_address_street_2=User.optString("user_address_street_2");
			stripperInfoNew.user_address_state=User.optString("user_address_state");
//			stripperInfoNew.user_phone=User.optString("user_phone");
			stripperInfoNew.user_address_street_1=User.optString("user_address_street_1");
//			stripperInfoNew.user_last_logined=User.optString("stripperInfoNew.user_last_logined");
			stripperInfoNew.user_id=User.optString("user_id");
			stripperInfoNew.user_birthday=User.optString("user_birthday");
//			stripperInfoNew.user_sex=User.optString("user_sex");
//			stripperInfoNew.user_account_type=User.optString("user_account_type");
//			stripperInfoNew.user_active=User.optString("user_active");
//			stripperInfoNew.user_avatar=User.optString("stripperInfoNew.user_avatar");
			stripperInfoNew.user_fullname=User.optString("user_fullname");
			
			
			stripperInfoNew.haicolor=User.optString("hair_color");
//			stripperInfoNew.user_paypal_id=User.optString("user_paypal_id");
			stripperInfoNew.stats=User.optString("stats");
			stripperInfoNew.rateing=User.optString("user_avg_rating");
			
		} catch (Exception e)
		{
			Log.i(t, ""+e);
		}   

		try{
			JSONArray StriperWorkingAt=User.getJSONArray("working_at");
			stripperInfoNew.ALWorkingAt.clear();
			for (int i = 0; i < StriperWorkingAt.length(); i++) {
				JSONObject JSONWorkingAt =	StriperWorkingAt.getJSONObject(i);
				WorkingAt workingAt=new WorkingAt();

				workingAt.setClub_zip_code(JSONWorkingAt.optString("club_zip_code"));
				workingAt.setClub_city(JSONWorkingAt.optString("club_city"));
				workingAt.setClub_contact_number(JSONWorkingAt.optString("club_contact_number"));
				workingAt.setClub_country(JSONWorkingAt.optString("club_country"));
				workingAt.setClub_address(JSONWorkingAt.optString("club_address"));
				workingAt.setClub_state(JSONWorkingAt.optString("club_state"));
				workingAt.setClub_name(JSONWorkingAt.optString("club_name"));
				stripperInfoNew.ALWorkingAt.add(workingAt);
			}
		}catch (Exception e)
		{
			Log.i(t, ""+e);

		}
  
		return stripperInfoNew;

	}

    public static void parseStripperInfoList(JSONObject jsonObj) {
		try {
			JSONArray jsonArray=jsonObj.getJSONArray("stripper");
			stripperInfoList.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			stripperInfoList.add(parseStripperInfoForProfile(jsonArray.getJSONObject(i)));
		}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public static ArrayList<String> getName() {

		ArrayList<String> name=new ArrayList<String>();
		name.clear();
		Iterator<StripperList> iterator = stripperInfoList.iterator();
		while (iterator.hasNext()) {
			StripperList clubInfo=iterator.next();
			name.add(clubInfo.user_name);
		}
		return name;
	}
	public static ArrayList<String> getAddress() {

		ArrayList<String> address=new ArrayList<String>();
		address.clear();
		Iterator<StripperList> iterator = stripperInfoList.iterator();
		while (iterator.hasNext()) {
			StripperList clubInfo=iterator.next();
			address.add(clubInfo.user_address_city+" "+clubInfo.user_address_state+" "+clubInfo.user_address_country);
		}
		return address;
	}
	public static ArrayList<String> getImageUrl() {

		ArrayList<String> imageUrl=new ArrayList<String>();
		imageUrl.clear();
		Iterator<StripperList> iterator = stripperInfoList.iterator();
		while (iterator.hasNext()) {
			StripperList clubInfo=iterator.next();
			imageUrl.add(clubInfo.user_original_pic);
		}
		return imageUrl;
	}
    
	public static ArrayList<String> getUser_Id() {

		ArrayList<String> imageUrl=new ArrayList<String>();
		imageUrl.clear();
		Iterator<StripperList> iterator = stripperInfoList.iterator();
		while (iterator.hasNext()) {
			StripperList clubInfo=iterator.next();
			imageUrl.add(clubInfo.user_id);
		}
		return imageUrl;
	}
}
