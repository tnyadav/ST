package com.volvr.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Favourites {
	          
	 private  final static  String  favouriteid="favourite_id" ;
     private  final static  String  userid="user_id" ;
     private  final static  String  favouriteuserid="favourite_user_id";
     private  final static  String  favouriteusertype="favourite_user_type" ;
     private  final static  String  favouriteusername="favourite_user_name" ;
     private  final static  String  favouriteuserfullname="favourite_user_fullname" ;
     private  final static  String  favouriteuseravatar="favourite_user_avatar" ;
	
	
	         private String  favourite_id ;
	         private String  user_id ;
	         private String  favourite_user_id;
	         private String  favourite_user_type ;
	         private String  favourite_user_name ;
	         private String  favourite_user_fullname ;
	         private String  favourite_user_avatar ;
			public String getFavourite_id() {
				return favourite_id;
			}
			public void setFavourite_id(String favourite_id) {
				this.favourite_id = favourite_id;
			}
			public String getUser_id() {
				return user_id;
			}
			public void setUser_id(String user_id) {
				this.user_id = user_id;
			}
			public String getFavourite_user_id() {
				return favourite_user_id;
			}
			public void setFavourite_user_id(String favourite_user_id) {
				this.favourite_user_id = favourite_user_id;
			}
			public String getFavourite_user_type() {
				return favourite_user_type;
			}
			public void setFavourite_user_type(String favourite_user_type) {
				this.favourite_user_type = favourite_user_type;
			}
			public String getFavourite_user_name() {
				return favourite_user_name;
			}
			public void setFavourite_user_name(String favourite_user_name) {
				this.favourite_user_name = favourite_user_name;
			}
			public String getFavourite_user_fullname() {
				return favourite_user_fullname;
			}
			public void setFavourite_user_fullname(String favourite_user_fullname) {
				this.favourite_user_fullname = favourite_user_fullname;
			}
			public String getFavourite_user_avatar() {
				return favourite_user_avatar;
			}
			public void setFavourite_user_avatar(String favourite_user_avatar) {
				this.favourite_user_avatar = favourite_user_avatar;
			}
			
			
			public static ArrayList<Favourites> alFavourites;
			
			public static void parseFavouriteList(JSONObject jsonObject) {
				alFavourites=new ArrayList<Favourites>();
				try {
					JSONArray jsonArray=jsonObject.getJSONArray("favourites");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject2=jsonArray.getJSONObject(i);
						Favourites favourites=new Favourites();
						favourites.setFavourite_id(jsonObject.optString(favouriteid));
						favourites.setFavourite_user_id(jsonObject2.optString(favouriteuserid));
						favourites.setUser_id(jsonObject2.optString(userid));
						favourites.setFavourite_user_type(jsonObject2.optString(favouriteusertype));
						favourites.setFavourite_user_name(jsonObject2.optString(favouriteusername));
						favourites.setFavourite_user_fullname(jsonObject2.optString(favouriteuserfullname));
						favourites.setFavourite_user_avatar(jsonObject2.optString(favouriteuseravatar));
					alFavourites.add(favourites);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public static ArrayList<String> getName(String userType) {
				ArrayList<String> nameList=new ArrayList<String>();
				if (alFavourites.size()>0) {
					for (int i = 0; i < alFavourites.size(); i++) {
						Favourites favourites=alFavourites.get(i);
						if (favourites.getFavourite_user_type().equals(userType)) {
							nameList.add(favourites.getFavourite_user_fullname());
						}
					}
				}
				
				return nameList;
			}
			public static ArrayList<String> getImageUrl(String userType) {
				ArrayList<String> imageUrlList=new ArrayList<String>();
				if (alFavourites.size()>0) {
				for (int i = 0; i < alFavourites.size(); i++) {
					Favourites favourites=alFavourites.get(i);
					if (favourites.getFavourite_user_type().equals(userType)) {
						imageUrlList.add(favourites.getFavourite_user_avatar());
					}
				  }
				}
				return imageUrlList;
			}
			public static ArrayList<String> getId(String userType) {
				ArrayList<String> idList=new ArrayList<String>();
				if (alFavourites.size()>0) {
					for (int i = 0; i < alFavourites.size(); i++) {
						Favourites favourites=alFavourites.get(i);
						if (favourites.getFavourite_user_type().equals(userType)) {
							idList.add(favourites.getFavourite_user_id());
						}
					}
				}
				
				return idList;
			}
}
