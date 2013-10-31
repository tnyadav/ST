package com.volvr.beans;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public  class ClubInfo {
	public String club_id;
	public String club_name;
	public String club_thumb_pic;
	public String club_original_pic;
	public String club_address;
	public String club_city;
	public String club_user_id;
	public String club_user_id1;
	@Override
	public String toString() {
		return "ClubInfo [club_id=" + club_id + ", club_name=" + club_name
				+ ", club_thumb_pic=" + club_thumb_pic + ", club_original_pic="
				+ club_original_pic + "id----------"+club_user_id;
	}

	public String club_state;
	public String club_country;
	public String club_zip_code;
	public String club_contact_number;
	public String club_owner_id;
	public String is_verified;
	

	public static ArrayList<ClubInfo> clubInfolist=new ArrayList<ClubInfo>(); 
	public ClubInfo() {
	}
	public static void parseJson( JSONObject jsonObject) {
		try {
			JSONArray arrayClub=jsonObject.getJSONArray("clubs");
			clubInfolist.clear();
			for (int i = 0; i < arrayClub.length(); i++) {
				JSONObject jsonObject2=arrayClub.getJSONObject(i);
				ClubInfo clubInfo= new ClubInfo();
				clubInfo.club_id=jsonObject2.optString("club_id");
				clubInfo.club_name=jsonObject2.optString("club_name");
				clubInfo.club_thumb_pic=jsonObject2.optString("club_thumb_pic");
				clubInfo.club_original_pic=jsonObject2.optString("club_original_pic");
				clubInfo.club_address=jsonObject2.optString("club_address");
				clubInfo.club_city=jsonObject2.optString("club_city");
				clubInfo.club_state=jsonObject2.optString("club_state");
				clubInfo.club_country=jsonObject2.optString("club_country");
				clubInfo.club_zip_code=jsonObject2.optString("club_zip_code");
				clubInfo.club_contact_number=jsonObject2.optString("club_contact_number");
				clubInfo.club_owner_id=jsonObject2.optString("club_owner_id");
				clubInfo.is_verified=jsonObject2.optString("is_verified");
				clubInfo.club_user_id=jsonObject2.optString("user_id");
			
				Log.e("clubInfo.club_user_id", clubInfo.club_user_id);
				clubInfolist.add(clubInfo);
				Log.e("clubdetail", clubInfo.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getName() {
		ArrayList<String> name=new ArrayList<String>();
		name.clear();
		Iterator<ClubInfo> iterator = clubInfolist.iterator();
		while (iterator.hasNext()) {
			ClubInfo clubInfo=iterator.next();
			name.add(clubInfo.club_name);
		}
		return name;
	}
	public static ArrayList<String> getAddress() {

		ArrayList<String> address=new ArrayList<String>();
		address.clear();
		Iterator<ClubInfo> iterator = clubInfolist.iterator();
		while (iterator.hasNext()) {
			ClubInfo clubInfo=iterator.next();
			address.add(clubInfo.club_address);
		}
		return address;
	}
	public static ArrayList<String> getImageUrl() {

		ArrayList<String> imageUrl=new ArrayList<String>();
		imageUrl.clear();
		Iterator<ClubInfo> iterator = clubInfolist.iterator();
		while (iterator.hasNext()) {
			ClubInfo clubInfo=iterator.next();
			imageUrl.add(clubInfo.club_thumb_pic);
		}
		return imageUrl;
	}
	
	public static ArrayList<String> getUser_Id() {
		ArrayList<String> imageUrl=new ArrayList<String>();
		imageUrl.clear();
		Iterator<ClubInfo> iterator = clubInfolist.iterator();
		while (iterator.hasNext()) {
			ClubInfo clubInfo=iterator.next();
			imageUrl.add(clubInfo.club_user_id);
		}
		return imageUrl;
	}
}
