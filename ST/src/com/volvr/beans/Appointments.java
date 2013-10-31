package com.volvr.beans;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Appointments {

	  private String   apn_id ;
	  private String   user_id ;
	  private String   strip_id;
	  private String   apn_start_date;
	  private String   apn_end_date ;
	  private String   apn_description ;
	  private String   apn_length ;
	  private String   apn_active ;
	  private String   apn_confirmed  ;
	  private String   apn_place  ;
	  private String   apn_city ;
	  private String   apn_state ;
	  private String   apn_country  ;
	  private String   apn_created_date ;
	  private String   apn_modified_date  ;
	  
	public String getApn_id() {
		return apn_id;
	}
	public void setApn_id(String apn_id) {
		this.apn_id = apn_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getStrip_id() {
		return strip_id;
	}
	public void setStrip_id(String strip_id) {
		this.strip_id = strip_id;
	}
	public String getApn_start_date() {
		return apn_start_date;
	}
	public void setApn_start_date(String apn_start_date) {
		this.apn_start_date = apn_start_date;
	}
	public String getApn_end_date() {
		return apn_end_date;
	}
	public void setApn_end_date(String apn_end_date) {
		this.apn_end_date = apn_end_date;
	}
	public String getApn_description() {
		return apn_description;
	}
	public void setApn_description(String apn_description) {
		this.apn_description = apn_description;
	}
	public String getApn_length() {
		return apn_length;
	}
	public void setApn_length(String apn_length) {
		this.apn_length = apn_length;
	}
	public String getApn_active() {
		return apn_active;
	}
	public void setApn_active(String apn_active) {
		this.apn_active = apn_active;
	}
	public String getApn_confirmed() {
		return apn_confirmed;
	}
	public void setApn_confirmed(String apn_confirmed) {
		this.apn_confirmed = apn_confirmed;
	}
	public String getApn_place() {
		return apn_place;
	}
	public void setApn_place(String apn_place) {
		this.apn_place = apn_place;
	}
	public String getApn_city() {
		return apn_city;
	}
	public void setApn_city(String apn_city) {
		this.apn_city = apn_city;
	}
	public String getApn_state() {
		return apn_state;
	}
	public void setApn_state(String apn_state) {
		this.apn_state = apn_state;
	}
	public String getApn_country() {
		return apn_country;
	}
	public void setApn_country(String apn_country) {
		this.apn_country = apn_country;
	}
	public String getApn_created_date() {
		return apn_created_date;
	}
	public void setApn_created_date(String apn_created_date) {
		this.apn_created_date = apn_created_date;
	}
	public String getApn_modified_date() {
		return apn_modified_date;
	}
	public void setApn_modified_date(String apn_modified_date) {
		this.apn_modified_date = apn_modified_date;
	}
	@Override
	public String toString() {
		return "Appointments [apn_id=" + apn_id + ", user_id=" + user_id
				+ ", strip_id=" + strip_id + ", apn_start_date="
				+ apn_start_date + ", apn_end_date=" + apn_end_date
				+ ", apn_description=" + apn_description + ", apn_length="
				+ apn_length + ", apn_active=" + apn_active
				+ ", apn_confirmed=" + apn_confirmed + ", apn_place="
				+ apn_place + ", apn_city=" + apn_city + ", apn_state="
				+ apn_state + ", apn_country=" + apn_country
				+ ", apn_created_date=" + apn_created_date
				+ ", apn_modified_date=" + apn_modified_date + "]";
	}
	
public static	ArrayList<Appointments> appointmentsList;
	
public static void parseAppointmentsList(JSONObject jsonObject) {
	appointmentsList=new ArrayList<Appointments>();
	try {
		JSONArray jsonArray=jsonObject.getJSONArray("appointments");
		
		for (int i = 0; i < jsonArray.length(); i++) {
			
			JSONObject jsonObject2=jsonArray.getJSONObject(i);
			  Appointments appointments=new Appointments();
			  appointments.setApn_id(jsonObject2.optString("apn_id"));
			  appointments.setUser_id(jsonObject2.optString("user_id"));
			  appointments.setStrip_id(jsonObject2.optString("strip_id"));
			  appointments.setApn_start_date(jsonObject2.optString("apn_start_date"));
			  appointments.setApn_end_date(jsonObject2.optString("apn_end_date"));
			  appointments.setApn_description(jsonObject2.optString("apn_description"));
			  appointments.setApn_length(jsonObject2.optString("apn_length"));
			  appointments.setApn_active(jsonObject2.optString("apn_active"));
			  appointments.setApn_confirmed(jsonObject2.optString("apn_confirmed"));
			  appointments.setApn_place(jsonObject2.optString("apn_place"));
			  appointments.setApn_city(jsonObject2.optString("apn_city"));
			  appointments.setApn_state(jsonObject2.optString("apn_state"));
			  appointments.setApn_country(jsonObject2.optString("apn_country"));
			  appointments.setApn_created_date(jsonObject2.optString("apn_created_date"));
			  appointments.setApn_modified_date(jsonObject2.optString("apn_modified_date"));
			  appointmentsList.add(appointments);
			
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public static ArrayList<String> getAppointmentDateList() {
	ArrayList<String> dateList=new ArrayList<String>();
	Iterator<Appointments> iterator=appointmentsList.iterator();
	while (iterator.hasNext()) {
		Appointments appointments=iterator.next();
		String dateTime=appointments.getApn_start_date();
		String date=dateTime.substring(0,10);
		Log.e("date", date);
		dateList.add(date);
		
	}
	return dateList;
}	
public static ArrayList<ArrayList<String>> getAppointmentForDate(String startDate) {
	ArrayList< ArrayList<String>> appointmentDetail=new ArrayList<ArrayList<String>>();
	appointmentDetail.clear();
	ArrayList<String> otherDetail;
	Iterator<Appointments> iterator=appointmentsList.iterator();
	Log.e("appointmentsList"," "+appointmentsList.size());
	while (iterator.hasNext()) {
		Appointments appointments=iterator.next();
		String dateTime=appointments.getApn_start_date();
		String date=dateTime.substring(0,10);
		String time=dateTime.substring(11);
		Log.e("dateTime", date+" "+time);
		if (date.equals(startDate)) 
		{
			Log.e("date matches"," matches");
			otherDetail=new ArrayList<String>();
			otherDetail.add(time);
			otherDetail.add(appointments.getApn_description());
			otherDetail.add(appointments.getApn_id());
			appointmentDetail.add(otherDetail);
		}
	}
	Log.e("dateTime",""+ appointmentDetail.size());
		return appointmentDetail;
		
	
}	
public static Appointments getAppointment(String id) {

	Appointments appointments=new Appointments();
	Iterator<Appointments> iterator=appointmentsList.iterator();
	Log.e("appointmentsList"," "+appointmentsList.size());
	while (iterator.hasNext()) {
		Appointments appointments1=iterator.next();
		String apnid=appointments1.getApn_id();
		
		if (id.equals(apnid)) 
		{
			appointments=appointments1;
			
		}
	}
	return appointments;
}
}
