package com.volvr.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Payment {
	
	private final static String payment_id="payment_id";
	private final static String strip_user_id= "strip_user_id" ; 	
	 private final static String club_user_id="club_user_id" ; 	
	 private final static String fan_user_id="fan_user_id" ; 	
	 private final static String user_subscription_type="user_subscription_type" ; 	
	 private final static String amount="amount" ; 	
	 private final static String payment_key="payment_key" ; 	
	 private final static String start_datetime="start_datetime" ; 	
	 private final static String end_datetime="end_datetime" ;
	 private final static String objectlistname="payments" ;
	 
     private final static String strip_user_name="strip_user_name";
	 private final static String  strip_user_fullname="strip_user_fullname";
	 private final static String  club_user_name="club_user_name";
	 private final static String  club_user_fullname ="club_user_fullname";
	 private final static String  club_user_club_name="club_user_club_name";
	 private final static String  fan_user_name="fan_user_name";
	 private final static String  fan_user_fullname="fan_user_fullname";
	 private final static String arrived_status="arrived_status";
	 private final static String  no_see_status= "no_see_status";
	 private final static String  created_datetime="created_datetime";
	 private final static String modified_datetime= "modified_datetime";
	 
	 
	 
	 
	 
	 private  String paymentid;
	 private  String stripuserid ; 	
	 private  String clubuserid ; 	
	 private  String fanuserid; 	
	 private  String usersubscriptiontype; 	
	 private  String amounts;
	 private  String paymentkey; 	
	 private  String startdatetime; 	
	 private  String enddatetime;
	 
	 private  String  stripusername;
	 private  String  stripuserfullname;
	 private  String  clubusername;
	 private  String  clubuserfullname;
	 private  String  clubuserclubname;
	 private  String  fanusername;
	 private  String  fanuserfullname;
	 private  String  arrivedstatus;
	 private  String  noseestatus;
	 private  String  createddatetime;
	 private  String  modifieddatetime;
	 
	 
	public String getStripuserid() {
		return stripuserid;
	}
	public void setStripuserid(String stripuserid) {
		this.stripuserid = stripuserid;
	}
	public String getClubuserid() {
		return clubuserid;
	}
	public void setClubuserid(String clubuserid) {
		this.clubuserid = clubuserid;
	}
	public String getFanuserid() {
		return fanuserid;
	}
	public void setFanuserid(String fanuserid) {
		this.fanuserid = fanuserid;
	}
	public String getUsersubscriptiontype() {
		return usersubscriptiontype;
	}
	public void setUsersubscriptiontype(String usersubscriptiontype) {
		this.usersubscriptiontype = usersubscriptiontype;
	}
	public String getAmounts() {
		return amounts;
	}
	public void setAmounts(String amounts) {
		this.amounts = amounts;
	}
	public String getPaymentkey() {
		return paymentkey;
	}
	public void setPaymentkey(String paymentkey) {
		this.paymentkey = paymentkey;
	}
	public String getStartdatetime() {
		return startdatetime;
	}
	public void setStartdatetime(String startdatetime) {
		
		this.startdatetime = startdatetime.substring(0,10);
	}
	public String getEnddatetime() {
		return enddatetime;
	}
	
	//********************************************
	public String getStripusername() {
		return stripusername;
	}
	public void setStripusername(String stripusername) {
		this.stripusername = stripusername;
	}
	public String getStripuserfullname() {
		return stripuserfullname;
	}
	public void setStripuserfullname(String stripuserfullname) {
		this.stripuserfullname = stripuserfullname;
	}
	public String getClubusername() {
		return clubusername;
	}
	public void setClubusername(String clubusername) {
		this.clubusername = clubusername;
	}
	public String getClubuserfullname() {
		return clubuserfullname;
	}
	public void setClubuserfullname(String clubuserfullname) {
		this.clubuserfullname = clubuserfullname;
	}
	public String getClubuserclubname() {
		return clubuserclubname;
	}
	public void setClubuserclubname(String clubuserclub_name) {
		this.clubuserclubname = clubuserclub_name;
	}
	public String getFanusername() {
		return fanusername;
	}
	public void setFanusername(String fanusername) {
		this.fanusername = fanusername;
	}
	public String getFanuserfullname() {
		return fanuserfullname;
	}
	public void setFanuserfullname(String fanuserfullname) {
		this.fanuserfullname = fanuserfullname;
	}
	public String getArrivedstatus() {
		return arrivedstatus;
	}
	public void setArrivedstatus(String arrivedstatus) {
		this.arrivedstatus = arrivedstatus;
	}
	public String getNoseestatus() {
		return noseestatus;
	}
	public void setNoseestatus(String noseestatus) {
		this.noseestatus = noseestatus;
	}
	public String getCreateddatetime() {
		return createddatetime;
	}
	public void setCreateddatetime(String createddatetime) {
		this.createddatetime = createddatetime;
	}
	public String getModifieddatetime() {
		return modifieddatetime;
	}
	public void setModifieddatetime(String modifieddatetime) {
		this.modifieddatetime = modifieddatetime;
	}
	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	 
	public String getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}
	

	public static ArrayList<Payment> paymentlist;
	public static void parsePayment(JSONObject jsonObject) {
		paymentlist =new ArrayList<Payment>();
		try {
		//	JSONArray jsonArray=jsonObject.getJSONArray(objectlistname);
			JSONArray jsonArray=jsonObject.getJSONArray("payments");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				Payment payment=new Payment();
				payment.setPaymentid(jsonObject2.optString(payment_id));
				payment.setStripuserid(jsonObject2.optString(strip_user_id));
				payment.setClubuserid(jsonObject2.optString(club_user_id));
				payment.setFanuserid(jsonObject2.optString(fan_user_id));
				payment.setUsersubscriptiontype(jsonObject2.optString(user_subscription_type));
				payment.setAmounts(jsonObject2.optString(amount));
				payment.setPaymentkey(jsonObject2.optString(payment_key));
				payment.setStartdatetime(jsonObject2.optString(start_datetime));
				payment.setEnddatetime(jsonObject2.optString(end_datetime));
				payment.setStripusername(jsonObject2.optString(strip_user_name));
				
				payment.setStripuserfullname(jsonObject2.optString(strip_user_fullname));
				payment.setClubusername(jsonObject2.optString(club_user_name));
				payment.setClubuserclubname(jsonObject2.optString(club_user_club_name));
				payment.setClubuserfullname(jsonObject2.optString(club_user_fullname));
				payment.setFanusername(jsonObject2.optString(fan_user_name));
				payment.setFanuserfullname(jsonObject2.optString(fan_user_fullname));
				payment.setArrivedstatus(jsonObject2.optString(arrived_status));
				payment.setNoseestatus(jsonObject2.optString(no_see_status));
				payment.setCreateddatetime(jsonObject2.optString(created_datetime));
				payment.setModifieddatetime(jsonObject2.optString(modified_datetime));
				
				
				paymentlist.add(payment);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public String toString() {
		String s="Time :" + startdatetime +  "\nAmmount :$" 
	             + amounts  + "\nArrived Status : " + arrivedstatus+"\nDate created : "+createddatetime ;
		return s;
	}
	

}

























