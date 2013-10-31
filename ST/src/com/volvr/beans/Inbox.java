package com.volvr.beans;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class Inbox {

			 private String  pm_id; 
			 private String  to_id;
			 private String from_id; 
			 private String message;
			 private String datetime;
			 private String  readstatus; 
			 private String  reciever_user_fullname;
			 private String  reciever_avatar;	
			 private String  sender_user_fullname;
			 public String getSender_user_fullname() {
				return sender_user_fullname;
			}
			public void setSender_user_fullname(String sender_user_fullname) {
				this.sender_user_fullname = sender_user_fullname;
			}
			public String getSender_avatar() {
				return sender_avatar;
			}
			public void setSender_avatar(String sender_avatar) {
				this.sender_avatar = sender_avatar;
			}

			private String  sender_avatar;	
			public String getPm_id() {
				return pm_id;
			}
			public void setPm_id(String pm_id) {
				this.pm_id = pm_id;
			}
			public String getTo_id() {
				return to_id;
			}
			public void setTo_id(String to_id) {
				this.to_id = to_id;
			}
			public String getFrom_id() {
				return from_id;
			}
			public void setFrom_id(String from_id) {
				this.from_id = from_id;
			}
			public String getMessage() {
				return message;
			}
			public void setMessage(String message) {
				this.message = message;
			}
			public String getDatetime() {
				return datetime;
			}
			public void setDatetime(String datetime) {
				this.datetime = datetime;
			}
			public String getReadstatus() {
				return readstatus;
			}
			public void setReadstatus(String readstatus) {
				this.readstatus = readstatus;
			}
			public String getReciever_user_fullname() {
				return reciever_user_fullname;
			}
			public void setReciever_user_fullname(String reciever_user_fullname) {
				this.reciever_user_fullname = reciever_user_fullname;
			}
			public String getReciever_avatar() {
				return reciever_avatar;
			}
			public void setReciever_avatar(String reciever_avatar) {
				this.reciever_avatar = reciever_avatar;
			}	
		
	public static ArrayList<Inbox> arrayList;
	public static ArrayList<Inbox> IncommingarrayList;
	public static ArrayList<Inbox> parseInbox(JSONObject jsonObject) {
		try {
		arrayList=new ArrayList<Inbox>();
		IncommingarrayList=new ArrayList<Inbox>();
		arrayList.clear();
		IncommingarrayList.clear();
		JSONObject jsonObject1=new JSONObject(jsonObject.optString("messages"));
		try {
			JSONArray jsonArray=jsonObject1.optJSONArray("sent");
			if (jsonArray.length()>0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2=jsonArray.optJSONObject(i);
				     	Inbox inbox=new Inbox();
					   inbox.setPm_id(jsonObject2.optString("pm_id"));
					   inbox.setTo_id(jsonObject2.optString("to_id"));
					   inbox.setFrom_id(jsonObject2.optString("from_id"));
					   inbox.setMessage(jsonObject2.optString("message"));
					   inbox.setDatetime(jsonObject2.optString("datetime"));
					   inbox.setDatetime(jsonObject2.optString("readstatus"));
					   inbox.setReciever_user_fullname(jsonObject2.optString("reciever_user_fullname"));
					   inbox.setReciever_avatar(jsonObject2.optString("reciever_avatar"));
					     arrayList.add(inbox);
					    
				}	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JSONArray jsonArray1=jsonObject1.optJSONArray("incoming");
		for (int i = 0; i < jsonArray1.length(); i++) {
			JSONObject jsonObject2=jsonArray1.optJSONObject(i);
		     	Inbox inbox=new Inbox();
			   inbox.setPm_id(jsonObject2.optString("pm_id"));
			   inbox.setTo_id(jsonObject2.optString("to_id"));
			   inbox.setFrom_id(jsonObject2.optString("from_id"));
			   inbox.setMessage(jsonObject2.optString("message"));
			   inbox.setDatetime(jsonObject2.optString("datetime"));
			   inbox.setDatetime(jsonObject2.optString("readstatus"));
			   inbox.setSender_user_fullname(jsonObject2.optString("sender_user_fullname"));
			   inbox.setSender_avatar(jsonObject2.optString("sender_avatar"));
			   IncommingarrayList.add(inbox);
			   Log.e("inbox", inbox.toString());
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return IncommingarrayList;
	}
	
	public static ArrayList<String> getName() 
	{
		ArrayList<String> name=new ArrayList<String>();
		name.clear();
		Iterator<Inbox> iterator = IncommingarrayList.iterator();
		while (iterator.hasNext()) {
			Inbox clubInfo=iterator.next();
			
			name.add(clubInfo.getSender_user_fullname());
		}
		return name;
	}
	public static ArrayList<String> getId() 
	{
		ArrayList<String> id=new ArrayList<String>();
		id.clear();
		Iterator<Inbox> iterator = IncommingarrayList.iterator();
		while (iterator.hasNext()) {
			Inbox clubInfo=iterator.next();
			
			id.add(clubInfo.getFrom_id());
		}
		return id;
	}
	public static ArrayList<String> getImageUrl() {
		ArrayList<String> image=new ArrayList<String>();
		image.clear();
		Iterator<Inbox> iterator = IncommingarrayList.iterator();
		while (iterator.hasNext()) {
			Inbox clubInfo=iterator.next();
			image.add(clubInfo.getSender_avatar());
		}
		return image;
	}
	
	public static ArrayList<String> getDescription() {
		ArrayList<String> image=new ArrayList<String>();
		image.clear();
		Iterator<Inbox> iterator = IncommingarrayList.iterator();
		while (iterator.hasNext()) {
			Inbox clubInfo=iterator.next();
			image.add(clubInfo.getMessage());
		}
		return image;
	}
	
	@Override
	public String toString() {
		return "Inbox : id=" + getPm_id()
				+ ", To_id=" + getTo_id() + ", From_id="
				+ getFrom_id() + ", Message=" + getMessage()
				+ ", Datetime=" + getDatetime() + ", Readstatus="
				+ getReadstatus() + ",user_fullname="
				+ getReciever_user_fullname() + ", avatar="
				+ getReciever_avatar() ;
	}		
			
	
}
