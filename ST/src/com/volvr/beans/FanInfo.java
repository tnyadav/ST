package com.volvr.beans;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public  class FanInfo {
	public String user_id;
	public String user_name;
	public String user_fullname;
	public String user_email;
	public String user_birthday;
	public String user_avatar;
	public String user_original_pic;
	public String user_address_city;
	public String user_address_state;
	public String user_address_country;
	public String user_address_zip;
	public String user_address_street_1;
	public String user_address_street_2;
	public String user_occupation;
	public String user_grossincome;
	public String user_subscription_type="";
	public String user_account_type="";
	public String expiry_date="";
	public String user_avg_rating;
	public static ArrayList<FanInfo> FanInfolist=new ArrayList<FanInfo>(); 
	public static FanInfo fanproFileInfo;
	public FanInfo() {
	}
	public static void parseJson( JSONObject jsonObject) {
		try {
			JSONArray arrayClub=jsonObject.getJSONArray("fan");
			FanInfolist.clear();
			for (int i = 0; i < arrayClub.length(); i++) {
				JSONObject jsonObject2=arrayClub.getJSONObject(i);

				FanInfolist.add(parseFanProfile(jsonObject2));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static FanInfo parseFanProfile(JSONObject jsonObject2) {
		FanInfo fanInfo= new FanInfo();
		try {
			fanInfo.user_id=jsonObject2.optString("user_id");
			fanInfo.user_name=jsonObject2.optString("user_name");
			fanInfo.user_email=jsonObject2.optString("user_email");
			fanInfo.user_birthday=jsonObject2.optString("user_birthday");
			fanInfo.user_avatar=jsonObject2.optString("user_avatar");
			fanInfo.user_original_pic=jsonObject2.optString("user_original_pic");
			fanInfo.user_address_city=jsonObject2.optString("user_address_city");
			fanInfo.user_address_state=jsonObject2.optString("user_address_state");
			fanInfo.user_address_country=jsonObject2.optString("user_address_country");
			fanInfo.user_address_zip=jsonObject2.optString("user_address_zip");
			fanInfo.user_address_street_1=jsonObject2.optString("user_address_street_1");
			fanInfo.user_address_street_2=jsonObject2.optString("user_address_street_2");
			fanInfo.user_occupation=jsonObject2.optString("user_occupation");
			fanInfo.user_grossincome=jsonObject2.optString("user_grossincome");
			fanInfo.user_account_type=jsonObject2.optString("user_account_type");
			fanInfo.user_subscription_type=jsonObject2.optString("user_subscription_type");
			fanInfo.expiry_date=jsonObject2.optString("subscription_expire_date");
			
				fanInfo.user_avg_rating=jsonObject2.optString("user_avg_rating");
			
			
			try {
				fanInfo.user_fullname=jsonObject2.optString("user_fullname");
			} catch (Exception e) {
				e.printStackTrace();
			}
			fanproFileInfo=fanInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fanInfo;
	}
	public static ArrayList<String> getName() {

		ArrayList<String> name=new ArrayList<String>();
		name.clear();
		Iterator<FanInfo> iterator = FanInfolist.iterator();
		while (iterator.hasNext()) {
			FanInfo fanInfo=iterator.next();
			name.add(fanInfo.user_name);
		}
		return name;
	}
	public static ArrayList<String> getAddress() {

		ArrayList<String> address=new ArrayList<String>();
		address.clear();
		Iterator<FanInfo> iterator = FanInfolist.iterator();
		while (iterator.hasNext()) {
			FanInfo fanInfo=iterator.next();
			address.add(fanInfo.user_address_city+" "+fanInfo.user_address_state+" "+fanInfo.user_address_country);
		}
		return address;
	}
	public static ArrayList<String> getImageUrl() {

		ArrayList<String> imageUrl=new ArrayList<String>();
		imageUrl.clear();
		Iterator<FanInfo> iterator = FanInfolist.iterator();
		while (iterator.hasNext()) {
			FanInfo fanInfo=iterator.next();
			imageUrl.add(fanInfo.user_original_pic);
		}
		return imageUrl;
	}
	
	public static ArrayList<String> getUser_Id() {
		ArrayList<String> imageUrl=new ArrayList<String>();
		imageUrl.clear();
		Iterator<FanInfo> iterator = FanInfolist.iterator();
		while (iterator.hasNext()) {
			FanInfo clubInfo=iterator.next();
			imageUrl.add(clubInfo.user_id);
		}
		return imageUrl;
	}
}
